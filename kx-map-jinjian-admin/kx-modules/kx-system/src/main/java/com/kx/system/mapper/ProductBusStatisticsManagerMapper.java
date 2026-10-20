package com.kx.system.mapper;

import com.kx.system.domain.ProductBusStatisticsManager;

import java.util.List;

public interface ProductBusStatisticsManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductBusStatisticsManager record);

    int insertSelective(ProductBusStatisticsManager record);

    ProductBusStatisticsManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductBusStatisticsManager record);

    int updateByPrimaryKey(ProductBusStatisticsManager record);

    List<ProductBusStatisticsManager> selectPage(ProductBusStatisticsManager productBusStatisticsManager);
}
