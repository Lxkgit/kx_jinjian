package com.kx.system.service.impl;

import com.kx.common.core.exception.ServiceException;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.riskclue.RiskClueFeedbackRecord;
import com.kx.system.domain.riskclue.RiskClueFile;
import com.kx.system.domain.riskclue.RiskClueReport;
import com.kx.system.domain.riskclue.RiskClueReviewRecord;
import com.kx.system.domain.riskclue.RiskClueReviewRequest;
import com.kx.system.domain.riskclue.RiskClueTransferRecord;
import com.kx.system.mapper.RiskClueReportMapper;
import com.kx.system.service.IRiskClueReportService;
import com.kx.system.util.MinioUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RiskClueReportServiceImpl implements IRiskClueReportService {
    private static final int DRAFT = 0;
    private static final int PENDING_REVIEW = 1;
    private static final int REVIEW_PASSED = 2;
    private static final int RETURNED = 3;

    @Resource
    private RiskClueReportMapper riskClueReportMapper;

    @Resource
    private MinioUtils minioUtils;

    @Override
    public List<RiskClueReport> selectRiskClueReportList(RiskClueReport query) {
        return riskClueReportMapper.selectRiskClueReportList(query);
    }

    @Override
    public RiskClueReport selectRiskClueReportById(Long id) {
        requireId(id);
        RiskClueReport report = riskClueReportMapper.selectRiskClueReportById(id);
        if (report == null) {
            throw new ServiceException("线索不存在或已删除");
        }
        List<RiskClueFile> files = riskClueReportMapper.selectFilesByClueId(id);
        files.forEach(file -> {
            if (StringUtils.isNotEmpty(file.getObjectName())) {
                file.setFileUrl(minioUtils.getUrlByObjectName(file.getObjectName()));
            }
        });
        report.setFiles(files);
        report.setReviewRecords(riskClueReportMapper.selectReviewRecordsByClueId(id));
        report.setTransferRecord(riskClueReportMapper.selectTransferRecordByClueId(id));
        report.setFeedbackRecords(riskClueReportMapper.selectFeedbackRecordsByClueId(id));
        return report;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RiskClueReport addRiskClueReport(RiskClueReport report) {
        normalize(report);
        validateForSubmit(report);
        Date now = new Date();
        prepareNewReport(report, PENDING_REVIEW, now);
        report.setReviewRound(1);
        report.setFirstReportTime(now);
        report.setReportTime(now);
        insertReportAndFiles(report);
        return report;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RiskClueReport saveRiskClueDraft(RiskClueReport report) {
        normalize(report);
        if (report.getId() == null) {
            prepareNewReport(report, DRAFT, new Date());
            report.setReviewRound(0);
            insertReportAndFiles(report);
            return report;
        }

        RiskClueReport existing = selectRiskClueReportById(report.getId());
        if (!isEditable(existing.getReviewStatus())) {
            throw new ServiceException("当前状态不允许保存修改");
        }
        updateContent(report, existing);
        return report;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRiskClueReport(RiskClueReport report) {
        requireId(report.getId());
        normalize(report);
        validateForSubmit(report);
        RiskClueReport existing = selectRiskClueReportById(report.getId());
        if (existing.getReviewStatus() != DRAFT && existing.getReviewStatus() != PENDING_REVIEW) {
            throw new ServiceException("只有草稿或待复核线索可以直接修改");
        }
        return updateContent(report, existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int submitDraftReport(RiskClueReport report) {
        requireId(report.getId());
        normalize(report);
        validateForSubmit(report);
        RiskClueReport existing = selectRiskClueReportById(report.getId());
        if (existing.getReviewStatus() != DRAFT) {
            throw new ServiceException("只有草稿可以提交");
        }
        return submitExisting(report, existing, 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resubmitRiskClueReport(RiskClueReport report) {
        requireId(report.getId());
        normalize(report);
        validateForSubmit(report);
        RiskClueReport existing = selectRiskClueReportById(report.getId());
        if (existing.getReviewStatus() != RETURNED) {
            throw new ServiceException("只有退回补正的线索可以补正重提");
        }
        return submitExisting(report, existing, existing.getReviewRound() + 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reviewRiskClueReport(RiskClueReviewRequest request) {
        if (request.getReviewResult() == null
                || (request.getReviewResult() != REVIEW_PASSED && request.getReviewResult() != RETURNED)) {
            throw new ServiceException("复核结果只能为复核通过或退回补正");
        }
        if (isBlank(request.getReviewOpinion())) {
            throw new ServiceException("复核意见不能为空");
        }
        RiskClueReport existing = selectRiskClueReportById(request.getId());
        if (existing.getReviewStatus() != PENDING_REVIEW) {
            throw new ServiceException("该线索已被复核，请刷新后重试");
        }
        String operator = currentUsername();
        int rows = riskClueReportMapper.updateReviewStatus(existing.getId(), request.getReviewResult(), operator);
        if (rows == 0) {
            throw new ServiceException("线索状态已变化，请刷新后重试");
        }

        Date now = new Date();
        RiskClueReviewRecord record = new RiskClueReviewRecord();
        record.setClueId(existing.getId());
        record.setReviewRound(existing.getReviewRound());
        record.setReviewResult(request.getReviewResult());
        record.setReviewOpinion(request.getReviewOpinion().trim());
        record.setReviewTime(now);
        record.setCreateTime(now);
        fillReviewer(record);
        riskClueReportMapper.insertReviewRecord(record);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int transferRiskClueReport(RiskClueTransferRecord record) {
        requireId(record.getClueId());
        if (isBlank(record.getTransferBasis()) || isBlank(record.getReceiveOrgName())
                || record.getTransferMethod() == null || isBlank(record.getDocumentInfo())
                || record.getTransferTime() == null) {
            throw new ServiceException("请完整填写移送信息");
        }
        String operator = currentUsername();
        int rows = riskClueReportMapper.updateTransferStatus(record.getClueId(), operator);
        if (rows == 0) {
            throw new ServiceException("只有复核通过且未移送的线索可以移送");
        }
        record.setTransferBasis(record.getTransferBasis().trim());
        record.setReceiveOrgName(record.getReceiveOrgName().trim());
        record.setDocumentInfo(record.getDocumentInfo().trim());
        record.setCreateTime(new Date());
        fillTransferOperator(record);
        riskClueReportMapper.insertTransferRecord(record);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int feedbackRiskClueReport(RiskClueFeedbackRecord record) {
        requireId(record.getClueId());
        if (isBlank(record.getFeedbackContent()) || record.getFeedbackTime() == null) {
            throw new ServiceException("反馈内容和反馈时间不能为空");
        }
        String operator = currentUsername();
        int rows = riskClueReportMapper.updateFeedbackStatus(record.getClueId(), operator);
        if (rows == 0) {
            throw new ServiceException("只有已移送且待反馈的线索可以登记反馈");
        }
        record.setFeedbackContent(record.getFeedbackContent().trim());
        record.setCreateBy(operator);
        record.setCreateTime(new Date());
        riskClueReportMapper.insertFeedbackRecord(record);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRiskClueReport(Long id) {
        requireId(id);
        int rows = riskClueReportMapper.deleteRiskClueReport(id, currentUsername());
        if (rows == 0) {
            throw new ServiceException("只有草稿或待复核且未移送的线索可以删除");
        }
        return rows;
    }

    private void prepareNewReport(RiskClueReport report, int status, Date now) {
        String operator = currentUsername();
        report.setClueNo("TMP" + System.nanoTime());
        report.setReviewStatus(status);
        report.setTransferStatus(0);
        report.setFeedbackStatus(0);
        report.setDelFlag("0");
        report.setCreateBy(operator);
        report.setCreateTime(now);
        report.setUpdateBy(operator);
        report.setUpdateTime(now);
        fillReporter(report);
    }

    private void insertReportAndFiles(RiskClueReport report) {
        int rows = riskClueReportMapper.insertRiskClueReport(report);
        if (rows == 0) {
            throw new ServiceException("线索保存失败");
        }
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String clueNo = "CL" + date + String.format("%03d", report.getId());
        riskClueReportMapper.updateClueNo(report.getId(), clueNo);
        report.setClueNo(clueNo);
        replaceFiles(report);
    }

    private int updateContent(RiskClueReport report, RiskClueReport existing) {
        report.setOriginalReviewStatus(existing.getReviewStatus());
        report.setUpdateBy(currentUsername());
        report.setUpdateTime(new Date());
        int rows = riskClueReportMapper.updateRiskClueContent(report);
        if (rows == 0) {
            throw new ServiceException("线索状态已变化，请刷新后重试");
        }
        replaceFiles(report);
        return rows;
    }

    private int submitExisting(RiskClueReport report, RiskClueReport existing, int reviewRound) {
        Date now = new Date();
        report.setOriginalReviewStatus(existing.getReviewStatus());
        report.setReviewRound(reviewRound);
        report.setFirstReportTime(existing.getFirstReportTime() == null ? now : existing.getFirstReportTime());
        report.setReportTime(now);
        report.setUpdateBy(currentUsername());
        report.setUpdateTime(now);
        int rows = riskClueReportMapper.submitRiskClueReport(report);
        if (rows == 0) {
            throw new ServiceException("线索状态已变化，请刷新后重试");
        }
        replaceFiles(report);
        return rows;
    }

    private void replaceFiles(RiskClueReport report) {
        riskClueReportMapper.deleteFilesByClueId(report.getId());
        List<RiskClueFile> validFiles = new ArrayList<>();
        if (report.getFiles() != null) {
            int order = 0;
            Date now = new Date();
            String operator = currentUsername();
            for (RiskClueFile file : report.getFiles()) {
                if (file == null || file.getFileType() == null || file.getFileType() < 1
                        || file.getFileType() > 2 || isBlank(file.getObjectName())) {
                    continue;
                }
                file.setId(null);
                file.setClueId(report.getId());
                file.setOriginalName(isBlank(file.getOriginalName()) ? file.getObjectName() : file.getOriginalName());
                file.setFileExt(isBlank(file.getFileExt()) ? extension(file.getOriginalName()) : file.getFileExt());
                file.setSortOrder(order++);
                file.setDelFlag("0");
                file.setCreateBy(operator);
                file.setCreateTime(now);
                validFiles.add(file);
            }
        }
        if (!validFiles.isEmpty()) {
            riskClueReportMapper.batchInsertFiles(validFiles);
        }
        report.setFiles(validFiles);
    }

    private void validateForSubmit(RiskClueReport report) {
        if (report.getClueSource() == null || report.getClueSource() < 1 || report.getClueSource() > 9) {
            throw new ServiceException("请选择线索来源");
        }
        if (isBlank(report.getClueTitle())) {
            throw new ServiceException("线索标题不能为空");
        }
        if (report.getClueType() == null || report.getClueType() < 1 || report.getClueType() > 5) {
            throw new ServiceException("请选择线索类型");
        }
        if (isBlank(report.getRiskSubject())) {
            throw new ServiceException("涉险主体不能为空");
        }
        if (isBlank(report.getClueDescription()) || report.getClueDescription().trim().length() < 10) {
            throw new ServiceException("线索描述不能少于10个字符");
        }
        if (isBlank(report.getSubjectAddress())) {
            throw new ServiceException("涉险主体地址不能为空");
        }
        if (report.getRiskLevel() == null || report.getRiskLevel() < 1 || report.getRiskLevel() > 3) {
            throw new ServiceException("请选择风险等级");
        }
        boolean hasPhoto = false;
        if (report.getFiles() != null) {
            for (RiskClueFile file : report.getFiles()) {
                if (file != null && Integer.valueOf(1).equals(file.getFileType()) && !isBlank(file.getObjectName())) {
                    hasPhoto = true;
                    break;
                }
            }
        }
        if (!hasPhoto) {
            throw new ServiceException("请至少上传一张现场照片");
        }
    }

    private void normalize(RiskClueReport report) {
        if (report == null) {
            throw new ServiceException("线索信息不能为空");
        }
        report.setClueTitle(trim(report.getClueTitle()));
        report.setRiskSubject(trim(report.getRiskSubject()));
        report.setUnifiedSocialCreditCode(trim(report.getUnifiedSocialCreditCode()));
        report.setClueDescription(trim(report.getClueDescription()));
        report.setSubjectAddress(trim(report.getSubjectAddress()));
        report.setContactName(trim(report.getContactName()));
        report.setContactPhone(trim(report.getContactPhone()));
        if (isBlank(report.getLocationAddress())) {
            report.setLocationAddress(report.getSubjectAddress());
        } else {
            report.setLocationAddress(report.getLocationAddress().trim());
        }
        if (isBlank(report.getCoordinateSystem())) {
            report.setCoordinateSystem("GCJ02");
        }
    }

    private void fillReporter(RiskClueReport report) {
        SysUser user = currentUser();
        if (user == null) {
            String username = currentUsername();
            report.setReporterUserName(username);
            report.setReporterNickName(username);
            report.setReportOrgName("");
            return;
        }
        report.setReporterUserName(user.getUserName());
        report.setReporterNickName(StringUtils.isNotEmpty(user.getNickName()) ? user.getNickName() : user.getUserName());
        SysDept dept = user.getDept();
        if (dept != null) {
            report.setReportOrgCode(dept.getOrgNum());
            report.setReportOrgName(dept.getDeptName());
            if (StringUtils.isEmpty(report.getAreaCode())) {
                report.setAreaCode(dept.getAreaCode());
            }
        } else {
            report.setReportOrgName("");
        }
    }

    private void fillReviewer(RiskClueReviewRecord record) {
        SysUser user = currentUser();
        if (user == null) {
            record.setReviewerUserName(currentUsername());
            record.setReviewerNickName(currentUsername());
            return;
        }
        record.setReviewerUserId(user.getUserId());
        record.setReviewerUserName(user.getUserName());
        record.setReviewerNickName(StringUtils.isNotEmpty(user.getNickName()) ? user.getNickName() : user.getUserName());
        if (user.getDept() != null) {
            record.setReviewerDeptId(user.getDept().getDeptId());
            record.setReviewerDeptName(user.getDept().getDeptName());
        }
    }

    private void fillTransferOperator(RiskClueTransferRecord record) {
        SysUser user = currentUser();
        if (user == null) {
            record.setOperatorUserName(currentUsername());
            record.setOperatorNickName(currentUsername());
            return;
        }
        record.setOperatorUserId(user.getUserId());
        record.setOperatorUserName(user.getUserName());
        record.setOperatorNickName(StringUtils.isNotEmpty(user.getNickName()) ? user.getNickName() : user.getUserName());
    }

    private boolean isEditable(Integer status) {
        return status != null && (status == DRAFT || status == PENDING_REVIEW || status == RETURNED);
    }

    private String currentUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception ignored) {
            return "";
        }
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
            throw new ServiceException("线索ID不能为空");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }

    private String extension(String name) {
        if (isBlank(name) || name.lastIndexOf('.') < 0) {
            return "";
        }
        return name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    }
}
