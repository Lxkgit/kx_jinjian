package com.kx.system.mapper;

import com.kx.system.domain.ActStatisticsManager;

import java.util.List;

public interface ActStatisticsManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActStatisticsManager record);

    int insertSelective(ActStatisticsManager record);

    ActStatisticsManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActStatisticsManager record);

    int updateByPrimaryKey(ActStatisticsManager record);
    List<ActStatisticsManager> selectByActId(Long actId);
    int batchInsert(List<ActStatisticsManager> list);
}
