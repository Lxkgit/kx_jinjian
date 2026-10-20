package com.kx.system.mapper;

import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.param.SearchAnchorChangeLogParam;
import com.kx.system.domain.param.SearchPaChangeLogListParam;

import java.util.Date;
import java.util.List;

/**
 * @author KANGXU
 * @description 针对表【pa_change_log(变更记录表)】的数据库操作Mapper
 * @createDate 2024-04-13 14:42:37
 * @Entity com.kx.system.domain.PaChangeLog
 */
public interface PaChangeLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PaChangeLog record);

    int insertSelective(PaChangeLog record);

    PaChangeLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaChangeLog record);

    int updateByPrimaryKey(PaChangeLog record);

    List<PaChangeLog> selectPaChangeLogList(SearchPaChangeLogListParam paChangeLog);

    /**
     * 根据分页参数导出记录，需要手动进行分页
     *
     * @param param
     * @return
     */
    List<PaChangeLog> selectPaChangeLogExportList(SearchPaChangeLogListParam param);

    List<PaChangeLog> selectAnchorChangeLog(SearchAnchorChangeLogParam param);

    /**
     * 根据分页参数导出锚点变更记录，需要手动进行分页
     *
     * @param param
     * @return
     */
    List<PaChangeLog> selectAnchorChangeLogExportList(SearchAnchorChangeLogParam param);

    List<PaChangeLog> selectYesterdayLog(Date date);
}
