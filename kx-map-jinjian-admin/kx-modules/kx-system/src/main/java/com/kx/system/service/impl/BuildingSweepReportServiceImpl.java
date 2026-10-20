package com.kx.system.service.impl;

import com.kx.common.core.exception.ServiceException;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.domain.buildingsweep.BuildingSweepReport;
import com.kx.system.domain.buildingsweep.BuildingSweepReviewRequest;
import com.kx.system.domain.riskclue.RiskClueReport;
import com.kx.system.api.domain.SysUser;
import com.kx.system.mapper.BuildingSweepReportMapper;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.IBuildingSweepReportService;
import com.kx.system.util.MinioUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuildingSweepReportServiceImpl implements IBuildingSweepReportService {

    private static final int PENDING_REVIEW = 1;
    private static final int REVIEW_PASSED = 2;
    private static final int RETURNED = 3;

    @Resource
    private BuildingSweepReportMapper buildingSweepReportMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private MinioUtils minioUtils;

    @Override
    public List<BuildingSweepReport> selectBuildingSweepReportList(BuildingSweepReport query) {
        return buildingSweepReportMapper.selectBuildingSweepReportList(query);
    }

    @Override
    public BuildingSweepReport selectBuildingSweepReportById(Long id) {
        BuildingSweepReport report = buildingSweepReportMapper.selectBuildingSweepReportById(id);
        if (report == null) {
            throw new ServiceException("扫楼上报记录不存在或已删除");
        }
        if (StringUtils.isNotEmpty(report.getEvidenceMaterials())) {
            Map<String, String> previewUrls = new LinkedHashMap<>();
            Arrays.stream(report.getEvidenceMaterials().split(","))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .forEach(name -> {
                    String url = minioUtils.getUrlByObjectName(name);
                    if (StringUtils.isNotEmpty(url)) {
                        previewUrls.put(name, url);
                    }
                });
            report.setEvidencePreviewUrls(previewUrls);
        }
        return report;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBuildingSweepReport(BuildingSweepReport report) {
        Date now = new Date();
        String operator = currentUsername();
        report.setReportNo("TMP" + System.nanoTime());
        report.setReviewStatus(PENDING_REVIEW);
        fillCurrentReporter(report);
        report.setReportTime(now);
        report.setCreateTime(now);
        report.setCreateBy(operator);
        report.setUpdateTime(now);
        report.setUpdateBy(operator);
        report.setDelFlag("0");
        normalizeReport(report);

        int rows = buildingSweepReportMapper.insertBuildingSweepReport(report);
        if (rows > 0) {
            String date = new SimpleDateFormat("yyyyMMdd").format(report.getInspectionTime());
            String reportNo = "SL" + date + String.format("%06d", report.getId());
            buildingSweepReportMapper.updateReportNo(report.getId(), reportNo);
            report.setReportNo(reportNo);
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBuildingSweepReport(BuildingSweepReport report) {
        requireId(report.getId());
        BuildingSweepReport existing = selectBuildingSweepReportById(report.getId());
        if (existing.getReviewStatus() == null || existing.getReviewStatus() != PENDING_REVIEW) {
            throw new ServiceException("只有待复核的记录可以编辑");
        }
        normalizeReport(report);
        report.setUpdateBy(currentUsername());
        report.setUpdateTime(new Date());
        int rows = buildingSweepReportMapper.updateBuildingSweepReport(report);
        if (rows == 0) {
            throw new ServiceException("记录状态已变化，请刷新后重试");
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reviewBuildingSweepReport(BuildingSweepReviewRequest request) {
        if (request.getReviewResult() != REVIEW_PASSED && request.getReviewResult() != RETURNED) {
            throw new ServiceException("复核结果只能为复核通过或退回补正");
        }
        normalizeRelatedClue(request);
        fillCurrentReviewer(request);
        int rows = buildingSweepReportMapper.reviewBuildingSweepReport(request);
        if (rows == 0) {
            throw new ServiceException("该记录已被复核或不存在，请刷新后重试");
        }
        return rows;
    }

    @Override
    public List<RiskClueReport> selectRelatedClueOptions(String keyword) {
        return buildingSweepReportMapper.selectRelatedClueOptions(keyword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resubmitBuildingSweepReport(BuildingSweepReport report) {
        requireId(report.getId());
        normalizeReport(report);
        report.setUpdateBy(currentUsername());
        report.setUpdateTime(new Date());
        report.setReportTime(new Date());
        int rows = buildingSweepReportMapper.resubmitBuildingSweepReport(report);
        if (rows == 0) {
            throw new ServiceException("只有退回补正的记录可以重新上报");
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBuildingSweepReport(Long id) {
        requireId(id);
        int rows = buildingSweepReportMapper.deleteBuildingSweepReport(id, currentUsername());
        if (rows == 0) {
            throw new ServiceException("只有待复核的记录可以删除");
        }
        return rows;
    }

    private void normalizeReport(BuildingSweepReport report) {
        normalizeDepartments(report);
        report.setAreaFullName(joinArea(report));
        if (StringUtils.isNotEmpty(report.getSubjectTypes())) {
            report.setSubjectTypes(report.getSubjectTypes().replace("，", ",").replaceAll("\\s+", ""));
        }
        if (StringUtils.isNotEmpty(report.getEvidenceMaterials())) {
            report.setEvidenceMaterials(report.getEvidenceMaterials().replace("，", ","));
        }
    }

    private void normalizeDepartments(BuildingSweepReport report) {
        List<Long> leadIds = parseDeptIds(report.getLeadDeptCode(), "请选择牵头部门");
        if (leadIds.size() != 1) {
            throw new ServiceException("牵头部门只能选择一个");
        }
        Map<Long, String> leadDeptMap = findDeptNames(leadIds);
        report.setLeadDeptCode(String.valueOf(leadIds.get(0)));
        report.setLeadDeptName(leadDeptMap.get(leadIds.get(0)));

        if (StringUtils.isEmpty(report.getParticipantDeptCodes())) {
            report.setParticipantDeptCodes(null);
            report.setParticipantDeptNames(null);
            normalizeParticipantPersons(report, leadIds, new java.util.ArrayList<>());
            return;
        }
        List<Long> participantIds = parseDeptIds(report.getParticipantDeptCodes(), "参与部门参数错误");
        Map<Long, String> participantDeptMap = findDeptNames(participantIds);
        report.setParticipantDeptCodes(participantIds.stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
        report.setParticipantDeptNames(participantIds.stream()
                .map(participantDeptMap::get).collect(Collectors.joining(",")));
        normalizeParticipantPersons(report, leadIds, participantIds);
    }

    private void normalizeParticipantPersons(BuildingSweepReport report, List<Long> leadIds,
                                             List<Long> participantDeptIds) {
        if (StringUtils.isEmpty(report.getParticipantPersonUserNames())) {
            report.setParticipantPersonUserNames(null);
            report.setParticipantPersonNames(null);
            return;
        }
        List<String> userNames = Arrays.stream(report.getParticipantPersonUserNames().split(","))
                .map(String::trim).filter(StringUtils::isNotEmpty)
                .distinct().collect(Collectors.toList());
        if (userNames.isEmpty()) {
            throw new ServiceException("参与排查人员参数错误");
        }
        List<Long> rootDeptIds = new java.util.ArrayList<>(leadIds);
        rootDeptIds.addAll(participantDeptIds);
        rootDeptIds = rootDeptIds.stream().distinct().collect(Collectors.toList());
        List<SysUser> users = sysUserMapper.selectEnabledUsersInDeptScopes(userNames, rootDeptIds);
        Map<String, String> userNameMap = users.stream().collect(Collectors.toMap(
                SysUser::getUserName,
                user -> StringUtils.isNotEmpty(user.getNickName()) ? user.getNickName() : user.getUserName(),
                (left, right) -> left, LinkedHashMap::new));
        if (userNameMap.size() != userNames.size()) {
            throw new ServiceException("所选参与人员不在牵头部门或参与部门的本级及下辖范围内");
        }
        report.setParticipantPersonUserNames(String.join(",", userNames));
        report.setParticipantPersonNames(userNames.stream()
                .map(userNameMap::get).collect(Collectors.joining(",")));
    }

    private List<Long> parseDeptIds(String value, String errorMessage) {
        if (StringUtils.isEmpty(value)) {
            throw new ServiceException(errorMessage);
        }
        try {
            return Arrays.stream(value.split(","))
                    .map(String::trim)
                    .filter(StringUtils::isNotEmpty)
                    .map(Long::valueOf)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (NumberFormatException exception) {
            throw new ServiceException(errorMessage);
        }
    }

    private Map<Long, String> findDeptNames(List<Long> deptIds) {
        if (deptIds.isEmpty()) {
            return new LinkedHashMap<>();
        }
        Map<Long, String> result = sysDeptMapper.selectEnabledDeptByIds(deptIds).stream()
                .collect(Collectors.toMap(dept -> dept.getDeptId(), dept -> dept.getDeptName(),
                        (left, right) -> left, LinkedHashMap::new));
        if (result.size() != deptIds.size()) {
            throw new ServiceException("所选部门不存在或已停用，请重新选择");
        }
        return result;
    }

    private void normalizeRelatedClue(BuildingSweepReviewRequest request) {
        if (request.getIsRelatedClue() == null
                || (request.getIsRelatedClue() != 0 && request.getIsRelatedClue() != 1)) {
            throw new ServiceException("请选择是否关联线索");
        }
        if (request.getIsRelatedClue() == 0) {
            request.setRelatedClueNo(null);
            return;
        }
        if (StringUtils.isEmpty(request.getRelatedClueNo())) {
            throw new ServiceException("请选择关联线索");
        }
        if (buildingSweepReportMapper.countAvailableRelatedClue(request.getRelatedClueNo()) == 0) {
            throw new ServiceException("关联线索不存在或尚未提交");
        }
    }

    private String joinArea(BuildingSweepReport report) {
        StringBuilder area = new StringBuilder();
        append(area, report.getProvinceName());
        append(area, report.getCityName());
        append(area, report.getDistrictName());
        append(area, report.getStreetName());
        append(area, report.getCommunityName());
        return area.toString();
    }

    private void append(StringBuilder target, String value) {
        if (StringUtils.isNotEmpty(value) && target.indexOf(value) < 0) {
            target.append(value);
        }
    }

    private String currentUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception ignored) {
            return "";
        }
    }

    private void fillCurrentReporter(BuildingSweepReport report) {
        SysUser user = currentUser();
        if (user != null) {
            report.setReporterUserName(user.getUserName());
            report.setReporterNickName(StringUtils.isNotEmpty(user.getNickName())
                    ? user.getNickName() : user.getUserName());
            return;
        }
        report.setReporterUserName(currentUsername());
        report.setReporterNickName(currentUsername());
    }

    private void fillCurrentReviewer(BuildingSweepReviewRequest request) {
        SysUser user = currentUser();
        if (user != null) {
            request.setReviewerUserName(user.getUserName());
            request.setReviewerNickName(StringUtils.isNotEmpty(user.getNickName())
                    ? user.getNickName() : user.getUserName());
            return;
        }
        request.setReviewerUserName(currentUsername());
        request.setReviewerNickName(currentUsername());
    }

    private SysUser currentUser() {
        try {
            return SecurityUtils.getLoginUser() == null ? null : SecurityUtils.getLoginUser().getUser();
        } catch (Exception ignored) {
            return null;
        }
    }

    private void requireId(Long id) {
        if (id == null) {
            throw new ServiceException("记录ID不能为空");
        }
    }
}
