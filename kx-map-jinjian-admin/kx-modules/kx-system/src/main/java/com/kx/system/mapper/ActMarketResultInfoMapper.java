package com.kx.system.mapper;

import com.kx.system.domain.ActMarketResultInfo;

public interface ActMarketResultInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActMarketResultInfo record);

    int insertSelective(ActMarketResultInfo record);

    ActMarketResultInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActMarketResultInfo record);

    int updateByPrimaryKey(ActMarketResultInfo record);
}