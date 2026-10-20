package com.kx.system.controller;

import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.customer.CustomerIntroducer;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.customer.CustomerVisits;
import com.kx.system.domain.dto.*;
import com.kx.system.domain.req.CustomerReq;
import com.kx.system.domain.req.DeptIdReq;
import com.kx.system.domain.req.TradeRecordReq;
import com.kx.system.service.CustomerInfoService;
import com.kx.system.service.CustomerVisitsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Api(tags = "客户管理")
public class CustomerController extends BaseController {

    @Resource
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerVisitsService customerVisitsService;

    @PostMapping("/page")
    @ApiOperation("对私、对公、商户客户管理分页查询")
    public TableDataInfo page(@RequestBody CustomerReq customerReq) {
        if (null == customerReq.getCustomerType() || (null == customerReq.getBankInterFlag() && null == customerReq.getCommonPoolFlag())) {
            return new TableDataInfo();
        }
        TableDataInfo tableDataInfo = customerInfoService.queryPage(customerReq);
        return tableDataInfo;
    }

    @PostMapping("/filingPage")
    @ApiOperation("客户管理分页查询")
    public TableDataInfo filingPage(@RequestBody CustomerReq customerReq) {
        if (null == customerReq.getCustomerType()) {
            return new TableDataInfo();
        }
        TableDataInfo tableDataInfo = customerInfoService.queryPage(customerReq);
        return tableDataInfo;
    }


    @GetMapping("/queryDetail/{customerId}")
    @ApiOperation("客户详情")
    public AjaxResult queryDetail(@PathVariable("customerId") Long customerId) {
        return customerInfoService.queryDetail(customerId);
    }

    @GetMapping("/queryVisits")
    @ApiOperation("客户走访信息")
    public AjaxResult queryVisits(VisitDTO visitDTO) {
        List<CustomerVisits> customerVisits = customerVisitsService.queryVisits(visitDTO);
        customerVisits.forEach(a->{
            if (StringUtils.isNotEmpty(a.getVisitsManager())&& StringUtils.isNotEmpty(a.getVisitsManagerCode())){
                String name = ManagerStringUtil.managerNumSplitName(a.getVisitsManagerCode(), a.getVisitsManager());
                a.setVisitsManager(name);
            }
        });

        return AjaxResult.success(customerVisits);
    }


    @GetMapping("/queryCustomer")
    @ApiOperation("根据姓名、手机号、证件号查询客户信息")
    public AjaxResult queryCustomerIntroducer(CustomerIntroducer customerIntroducer) {
        return AjaxResult.success(customerInfoService.queryCustomerIntroducer(customerIntroducer));
    }

    @PostMapping("/tradeRecord")
    @ApiOperation("交易记录查询")
    public AjaxResult tradeRecord(@RequestBody TradeRecordReq tradeRecordReq) {
        return customerInfoService.tradeRecord(tradeRecordReq);
    }

    @PostMapping("/mobilize")
    @ApiOperation("客户调动")
    public AjaxResult mobilize(@RequestBody CustomerMobilizeDTO customerMobilizeDTO) {
        return customerInfoService.mobilize(customerMobilizeDTO);
    }

    @PostMapping("/archival")
    @ApiOperation("建档")
    public AjaxResult archival(@RequestBody CustomerUpdateInfoReq customerUpdateInfoReq) {
        return customerInfoService.archival(customerUpdateInfoReq);
    }

    @PostMapping("/updateInfo")
    @ApiOperation("更新")
    public AjaxResult updateInfo(@RequestBody CustomerUpdateInfoReq customerUpdateInfoReq) {
        return customerInfoService.updateInfo(customerUpdateInfoReq);
    }

    @PostMapping("/editCustomerLabel")
    @ApiOperation("新增、编辑客户标签")
    public AjaxResult editCustomerLabel(@RequestBody CustomerLabelReq customerLabelReq) {
        return customerInfoService.editCustomerLabel(customerLabelReq);
    }

    @PostMapping("/queryBusinessManage")
    @ApiOperation("根据部门查询客户经理")
    public AjaxResult queryBusinessManage(@RequestBody DeptIdReq deptIdReq) {
        return customerInfoService.queryBusinessManage(deptIdReq);
    }

//    @Scheduled(cron = "0 0 1 * * ?")
//    @PostConstruct
    @PostMapping("/customerToFiling")
    @ApiOperation("基础信息同步至档案")
    public void customerToFiling(){
        customerInfoService.customerToFiling();
    }


    @ApiOperation(value = "证件号查询是否为行内客户", notes = "传入证件号")
    @GetMapping(value = "/getIsBankCustomer")
    public AjaxResult getIsBankCustomer(String identity,String phone){
        return AjaxResult.success(customerInfoService.getIsBankCustomer(identity,phone));
    }
}
