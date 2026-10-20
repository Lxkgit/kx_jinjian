package com.kx.system.mapper;

import com.kx.system.domain.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface ProductInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
    List<ProductInfo> selectPage(ProductInfo productInfo);
}
