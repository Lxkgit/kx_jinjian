package com.kx.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.constants.SysDictTypeConstants;
import com.kx.system.domain.AnchorChangeLogVo;
import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.enums.AnchorPointType;
import com.kx.system.domain.enums.LogOperateType;
import com.kx.system.domain.enums.LogType;
import com.kx.system.domain.param.SearchAnchorChangeLogParam;
import com.kx.system.domain.param.SearchPaChangeLogListParam;
import com.kx.system.mapper.PaChangeLogMapper;
import com.kx.system.mapper.SysDictDataMapper;
import com.kx.system.service.IPaChangeLogService;
import com.kx.system.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.kx.system.domain.enums.LogType.*;
import static com.kx.system.util.convert.PageResultConvert.convertPage;

/**
 * @author KANGXU
 * @description 针对表【pa_change_log(变更记录表)】的数据库操作Service实现
 * @createDate 2024-04-12 11:23:16
 */
@Service
public class PaChangeLogServiceImpl implements IPaChangeLogService {

    private static Map<LogType, Set<String>> fieldWhiteList = new HashMap<>();

    static {
        Set<String> anchorFieleWhiteList = new HashSet<>();
        anchorFieleWhiteList.add("type");
        anchorFieleWhiteList.add("anchorName");
        anchorFieleWhiteList.add("addressType");
        anchorFieleWhiteList.add("addressDesc");
        fieldWhiteList.put(ANCHOR_MODIFY, anchorFieleWhiteList);
        Set<String> gridFieldWhiteList = new HashSet<>();
        gridFieldWhiteList.add("code");
        fieldWhiteList.put(GRID_MODIFY, gridFieldWhiteList);
        fieldWhiteList.put(GRID_USER_MODIFY, new HashSet<>());
    }

    @Autowired
    private UserUtil userUtil;

    @Resource
    private PaChangeLogMapper paChangeLogMapper;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public PaChangeLog selectPaChangeLogById(Long id) {
        return paChangeLogMapper.selectByPrimaryKey(id);
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<PaChangeLog> selectPaChangeLogList(SearchPaChangeLogListParam param) {
        paramEndDateAddDay(param);
        return paChangeLogMapper.selectPaChangeLogList(param);
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<PaChangeLog> selectPaChangeLogExportList(SearchPaChangeLogListParam param) {
        paramEndDateAddDay(param);
        param.handlePageExportParam();
        Map<String, String> dictMap = sysDictDataMapper.selectDictDataByType(SysDictTypeConstants.LOG_OPERATOR_TYPE).stream()
                .collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));
        List<PaChangeLog> paChangeLogList = paChangeLogMapper.selectPaChangeLogExportList(param);
        List<PaChangeLog> list = new ArrayList<>();
        paChangeLogList.stream().forEach(p -> {
            PaChangeLog paChangeLog = new PaChangeLog();
            paChangeLog.setOperateTypeVal(dictMap.get(p.getOperateType()));
            paChangeLog.setLogContent(p.getLogContent());
            paChangeLog.setManagerNum(p.getManagerNum());
            paChangeLog.setOperatorName(p.getOperatorName());
            paChangeLog.setCreateTimeStr(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, p.getCreateTime()));
            list.add(paChangeLog);
        });
        return list;
    }

    private void paramEndDateAddDay(SearchPaChangeLogListParam param) {
        //由于日志记录时间到秒，而查询时间到天，所以要对endtime做加一天处理
        if (param.getEndTime() != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(param.getEndTime());
            instance.add(Calendar.DAY_OF_YEAR, 1);
            param.setEndTime(instance.getTime());
        }
    }

    @Override
    public void insertPaChangeLog(LogType logType, LogOperateType logOperateType, String subject, Object configBefore,
                                  Object configUpdate, String logContent) {
        if (configBefore == null && configUpdate == null) {
            return;
        }
        String beforeString, updateString;

        // 如果记录配置中有列表,或者修改前后有为空的，则直接记录
        if (configBefore instanceof Collection || configUpdate instanceof Collection || configBefore == null
                || configUpdate == null) {
            beforeString = JSON.toJSONString(configBefore);
            updateString = JSON.toJSONString(configUpdate);
        } else {
            // 记录对象变化，可以去掉未改变的字段
            JSONObject before = JSONObject.from(configBefore);
            JSONObject update = JSONObject.from(configUpdate);
            if (logType.equals(ANCHOR_MODIFY)
                    && checkUnNeedSaveAnchorLog((AnchorPoint) configBefore, (AnchorPoint) configUpdate)) {
                return;
            } else {
                removeUnnecessaryProperties(logType, before, update);
            }
            // 如果没有字段发生改变，可以不记录日志
            if (before.isEmpty() && update.isEmpty()) {
                return;
            }
            beforeString = JSON.toJSONString(before);
            updateString = JSON.toJSONString(update);
        }

        SysUser loginUser = userUtil.getLoginUser();
        PaChangeLog paChangeLog = new PaChangeLog();
        paChangeLog.setSubject(subject);
        paChangeLog.setLogType(logType.getCode());
        paChangeLog.setOperateType(logOperateType.getCode());
        paChangeLog.setConfigBefore(beforeString);
        paChangeLog.setConfigUpdate(updateString);
        paChangeLog.setLogContent(logContent);
        paChangeLog.setOperatorId(loginUser == null ? null : loginUser.getUserId());
        paChangeLog.setOperatorName(loginUser == null ? null : loginUser.getNickName());
        paChangeLog.setCreateTime(new Date());
        paChangeLogMapper.insert(paChangeLog);
    }

    /**
     * 锚点变更，只有当地址类型或者地址详情发生变更才记录
     *
     * @param configBefore
     * @param configUpdate
     * @return
     */
    private boolean checkUnNeedSaveAnchorLog(AnchorPoint configBefore, AnchorPoint configUpdate) {
        return StringUtils.equals(configBefore.getAddressDesc(), configUpdate.getAddressDesc())
                && Objects.equals(configBefore.getAddressType(), configUpdate.getAddressType());
    }

    /**
     * 移除前后没有变更的字段 针对锚点变更更记录，设置移除白名单
     *
     * @param logType
     * @param before
     * @param update
     */
    private void removeUnnecessaryProperties(LogType logType, JSONObject before, JSONObject update) {
        Iterator<String> it = before.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (fieldWhiteList.get(logType).contains(key)) {
                continue;
            }
            if (Objects.equals(before.get(key), update.get(key))) {
                it.remove();
                update.remove(key);
            }
        }
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<AnchorChangeLogVo> selectAnchorChangeLogList(SearchAnchorChangeLogParam param) {
        handleSearchAnchorChangeLogParam(param);
        List<PaChangeLog> paChangeLogs = paChangeLogMapper.selectAnchorChangeLog(param);
        return convertPage(paChangeLogs, this::convert);
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<PaChangeLog> selectAnchorChangeLogExportList(SearchAnchorChangeLogParam param) {
        handleSearchAnchorChangeLogParam(param);
        return paChangeLogMapper.selectAnchorChangeLogExportList(param);
    }

    private void handleSearchAnchorChangeLogParam(SearchAnchorChangeLogParam param) {
        param.setOldAddressTypeCodes(StringUtils.isEmpty(param.getOldAddressTypeCodes()) ? null
                : "\"addressType\":" + param.getOldAddressTypeCodes());
        param.setNewAddressTypeCodes(StringUtils.isEmpty(param.getNewAddressTypeCodes()) ? null
                : "\"addressType\":" + param.getNewAddressTypeCodes());
        param.handlePageExportParam();
    }

    private AnchorChangeLogVo convert(PaChangeLog paChangeLog) {
        AnchorPoint anchorPointBefore = JSON.parseObject(paChangeLog.getConfigBefore(), AnchorPoint.class);
        AnchorPoint anchorPointAfter = JSON.parseObject(paChangeLog.getConfigUpdate(), AnchorPoint.class);
        AnchorChangeLogVo vo = new AnchorChangeLogVo();
        vo.setId(paChangeLog.getId());
        vo.setAnchorName(anchorPointBefore.getAnchorName());
        vo.setTypeName(AnchorPointType.getByCode(anchorPointBefore.getType()).getName());
        vo.setOldAddress(anchorPointBefore.getAddressDesc());
        vo.setOldAddressType(anchorPointBefore.getAddressType());
        vo.setNewAddress(anchorPointAfter.getAddressDesc());
        vo.setNewAddressType(anchorPointAfter.getAddressType());
        vo.setOperatorName(paChangeLog.getOperatorName());
        vo.setCreateTime(paChangeLog.getCreateTime());
        if (anchorPointBefore.getAnchorName() == null) {
            vo.setAnchorName(paChangeLog.getSubject());
        }
        return vo;
    }
}
