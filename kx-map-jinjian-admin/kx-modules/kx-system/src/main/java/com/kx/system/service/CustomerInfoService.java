package com.kx.system.service;


import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.customer.CustomerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kx.system.domain.customer.CustomerIntroducer;
import com.kx.system.domain.dto.CustomerLabelReq;
import com.kx.system.domain.dto.CustomerMobilizeDTO;
import com.kx.system.domain.dto.CustomerUpdateInfoReq;
import com.kx.system.domain.req.CustomerReq;
import com.kx.system.domain.req.DeptIdReq;
import com.kx.system.domain.req.TradeRecordReq;

import java.util.List;

/**
* @author 86176
* @description 针对表【customer_info(客户信息主表)】的数据库操作Service
* @createDate 2024-01-18 11:13:45
*/
public interface CustomerInfoService extends IService<CustomerInfo> {

    TableDataInfo queryPage(CustomerReq customerReq);

    AjaxResult queryDetail(Long customerId);

    AjaxResult tradeRecord(TradeRecordReq tradeRecordReq);

    AjaxResult mobilize(CustomerMobilizeDTO customerMobilizeDTO);

    AjaxResult archival(CustomerUpdateInfoReq customerUpdateInfoReq);

    AjaxResult updateInfo(CustomerUpdateInfoReq customerUpdateInfoReq);

    AjaxResult editCustomerLabel(CustomerLabelReq customerLabelReq);

    AjaxResult queryBusinessManage(DeptIdReq deptIdReq);

    void customerToFiling();

    List<CustomerIntroducer> queryCustomerIntroducer(CustomerIntroducer customerIntroducer);

    String getIsBankCustomer(String identity, String phone);
}
