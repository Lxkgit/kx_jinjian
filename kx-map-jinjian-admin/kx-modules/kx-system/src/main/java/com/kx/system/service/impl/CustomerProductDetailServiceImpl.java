package com.kx.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kx.system.domain.customer.CustomerProductDetail;
import com.kx.system.service.CustomerProductDetailService;
import com.kx.system.mapper.CustomerProductDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 86176
* @description 针对表【customer_product_detail(客户持有产品详情)】的数据库操作Service实现
* @createDate 2024-01-18 11:13:45
*/
@Service
public class CustomerProductDetailServiceImpl extends ServiceImpl<CustomerProductDetailMapper, CustomerProductDetail>
    implements CustomerProductDetailService{

}




