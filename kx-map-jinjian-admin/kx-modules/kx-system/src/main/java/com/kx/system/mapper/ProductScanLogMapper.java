package com.kx.system.mapper;

import com.kx.system.domain.ProductScanLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ProductScanLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductScanLog record);

    int insertSelective(ProductScanLog record);

    ProductScanLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductScanLog record);

    int updateByPrimaryKey(ProductScanLog record);

    List<ProductScanLog> selectByProductId(Long productId);

}
