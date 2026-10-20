package com.kx.system.mapper;

import com.kx.system.domain.customer.CustomerProductDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86176
* @description 针对表【customer_product_detail(客户持有产品详情)】的数据库操作Mapper
* @createDate 2024-01-18 11:13:45
* @Entity com.kx.system.domain.customer.CustomerProductDetail
*/
public interface CustomerProductDetailMapper extends BaseMapper<CustomerProductDetail> {

    List<CustomerProductDetail> selectListByHoldId(@Param("holdId") Long holdId);
}




