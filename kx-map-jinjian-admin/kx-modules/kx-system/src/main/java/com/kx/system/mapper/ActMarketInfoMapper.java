package com.kx.system.mapper;

import com.kx.system.domain.ActMarketInfo;
import com.kx.system.domain.vo.ActMarketInfoVo;

import java.util.List;

public interface ActMarketInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActMarketInfo record);

    int insertSelective(ActMarketInfo record);

    ActMarketInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActMarketInfo record);
    int updateByPrimaryKey(ActMarketInfo record);


    List<ActMarketInfoVo> selectPage(ActMarketInfoVo record);

    ActMarketInfoVo selectActMarketInfoAndResultById(Long id);
    List<ActMarketInfoVo> selectActMarketInfoAndResultList(ActMarketInfoVo record);

    List<ActMarketInfoVo> selectAll();


}
