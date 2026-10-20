package com.kx.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kx.system.domain.customer.CustomerTrade;
import com.kx.system.service.CustomerTradeService;
import com.kx.system.mapper.CustomerTradeMapper;
import org.springframework.stereotype.Service;

/**
* @author 86176
* @description 针对表【customer_trade(商户交易金额表)】的数据库操作Service实现
* @createDate 2024-01-18 11:13:45
*/
@Service
public class CustomerTradeServiceImpl extends ServiceImpl<CustomerTradeMapper, CustomerTrade>
    implements CustomerTradeService{

}




