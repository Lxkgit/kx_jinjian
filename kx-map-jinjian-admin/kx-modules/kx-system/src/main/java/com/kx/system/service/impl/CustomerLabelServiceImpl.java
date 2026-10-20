package com.kx.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.service.CustomerLabelService;
import com.kx.system.mapper.CustomerLabelMapper;
import org.springframework.stereotype.Service;

/**
* @author 86176
* @description 针对表【customer_label(用户标签)】的数据库操作Service实现
* @createDate 2024-01-18 11:13:45
*/
@Service
public class CustomerLabelServiceImpl extends ServiceImpl<CustomerLabelMapper, CustomerLabel>
    implements CustomerLabelService{

}




