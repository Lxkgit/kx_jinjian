package com.kx.system.service;

import com.kx.system.domain.AnchorChangeLogVo;
import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.enums.LogOperateType;
import com.kx.system.domain.enums.LogType;
import com.kx.system.domain.param.SearchAnchorChangeLogParam;
import com.kx.system.domain.param.SearchPaChangeLogListParam;

import java.util.List;

/**
 * @author KANGXU
 * @description 针对表【pa_change_log(变更记录表)】的数据库操作Service
 * @createDate 2024-04-12 11:23:16
 */
public interface IPaChangeLogService {

    /**
     * 查询变更记录
     *
     * @param id 变更记录主键
     * @return 变更记录
     */
    PaChangeLog selectPaChangeLogById(Long id);

    /**
     * 查询变更记录列表
     *
     * @param param 查询参数
     * @return 变更记录集合
     */
    List<PaChangeLog> selectPaChangeLogList(SearchPaChangeLogListParam param);

    /**
     * 查询需要导出的变更记录
     *
     * @param param
     * @return
     */
    List<PaChangeLog> selectPaChangeLogExportList(SearchPaChangeLogListParam param);

    /**
     * 新增变更记录
     *
     * @param logType        日志类型
     * @param logOperateType 操作类型
     * @param configBefore   修改前配置
     * @param configUpdate   修改后配置
     * @param logContent     日志操作内容
     */
    void insertPaChangeLog(LogType logType, LogOperateType logOperateType, String subject, Object configBefore,
                           Object configUpdate, String logContent);

    /**
     * 查询锚点变更记录
     *
     * @param param 查询参数
     * @return 锚点变更记录
     */
    List<AnchorChangeLogVo> selectAnchorChangeLogList(SearchAnchorChangeLogParam param);

    /**
     * 查询需要导出的锚点变更记录
     *
     * @param paChangeLog
     * @return
     */
    List<PaChangeLog> selectAnchorChangeLogExportList(SearchAnchorChangeLogParam paChangeLog);
}
