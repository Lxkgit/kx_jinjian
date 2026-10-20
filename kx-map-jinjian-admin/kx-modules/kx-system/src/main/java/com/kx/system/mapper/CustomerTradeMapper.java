package com.kx.system.mapper;

import com.kx.system.domain.customer.CustomerTrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 86176
* @description 针对表【customer_trade(商户交易金额表)】的数据库操作Mapper
* @createDate 2024-01-18 11:13:45
* @Entity com.kx.system.domain.customer.CustomerTrade
*/
public interface CustomerTradeMapper extends BaseMapper<CustomerTrade> {

    List<CustomerTrade> selectCustomerTradeList(@Param("customerId") Long customerId, @Param("tradeCardNum") Long tradeCardNum, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}




