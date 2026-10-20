package com.kx.system.mapper;

import com.kx.system.domain.ProductBusStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ProductBusStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductBusStatistics record);

    int insertSelective(ProductBusStatistics record);

    ProductBusStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductBusStatistics record);

    int updateByPrimaryKey(ProductBusStatistics record);

    ProductBusStatistics selectByProductId(Long productId);

    List<ProductBusStatistics> selectPage(ProductBusStatistics productBusStatistics);

}
