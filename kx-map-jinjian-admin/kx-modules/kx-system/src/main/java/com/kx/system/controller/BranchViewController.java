package com.kx.system.controller;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.BranchEvents;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.service.BranchViewService;
import com.kx.system.service.IAnchorPointService;
import com.kx.system.service.ICreditCustomerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/branch/view")
@Api(tags = "网点视图管理")
public class BranchViewController {
    @Autowired
    private ICreditCustomerInfoService creditCustomerInfoService;

    @Autowired
    private IAnchorPointService anchorPointService;

    @Autowired
    private BranchViewService branchViewService;

    @GetMapping("/queryCustomerByAddr")
    @ApiOperation("根据地址搜索网点")
    public AjaxResult queryCustomerByAddr(@RequestParam String addressDesc) {
        return AjaxResult.success(anchorPointService.queryCustomerByAddr(addressDesc));
    }

    @ApiOperation(value = "网点锚点落格跑批")
    @GetMapping(value = "/anchCode")
    public AjaxResult anchCode(){
        anchorPointService.anchCode();
        return AjaxResult.success();
    }

    /**
     * 五级边界跑批
     * @return
     */
    @GetMapping("/boundaryByAddress")
    @ApiOperation("根据地址搜索网点")
    public AjaxResult tests() {
        return AjaxResult.success(anchorPointService.tests());
    }

    @GetMapping("/queryCustomerByAnchorName")
    @ApiOperation("根据名称搜索网点")
    public AjaxResult queryCustomerByAnchor(@RequestParam String anchorName) {
        return AjaxResult.success(anchorPointService.queryCustomerByAnchor(anchorName));
    }

    @GetMapping("/queryCustomerByAnchor")
    @ApiOperation("根据网格搜索网点")
    public AjaxResult queryCustomerByBranch(@RequestParam String branchName) {
        return AjaxResult.success(anchorPointService.queryCustomerByBranch(branchName));
    }

    @PostMapping("/branchBifferentiate")
    @ApiOperation("责任网点和协助网点显示")
    public AjaxResult branchBifferentiate(@RequestParam String anchorName) {
        return AjaxResult.success(branchViewService.branchBifferentiate(anchorName));
    }

    @GetMapping("/branchInfo/info")
    @ApiOperation("根据网点名称获取360视图网点信息")
    public AjaxResult queryBranchInfo(@RequestParam String customerName) {
        return AjaxResult.success(branchViewService.selectBranchInfo(customerName));
    }

    @GetMapping("/branchInfo/service")
    @ApiOperation("根据网点名称获取360视图服务")
    public AjaxResult queryBranchService(@RequestParam String customerName) {
        return AjaxResult.success(branchViewService.selectBranchService(customerName));
    }

    @GetMapping("/branchInfo/evensRecently")
    @ApiOperation("根据网点名称查询最近一次事件")
    public AjaxResult queryBranchEvent(@RequestParam String customerName) {
        return AjaxResult.success(branchViewService.queryBranchEvent(customerName));
    }

    @GetMapping("/branchInfo/evensAll")
    @ApiOperation("根据网点名称查询最历史事件")
    public AjaxResult queryBranchEventAll(@RequestParam String customerName) {
        return AjaxResult.success(branchViewService.queryBranchEventAll(customerName));
    }
    @PostMapping("/branchInfo/insertEven")
    @ApiOperation("添加事件")
    public AjaxResult insertBranchEvent(@RequestBody @Valid BranchEvents branchEvents) {
        return AjaxResult.success(branchViewService.insertBranchEvent(branchEvents));
    }

    @DeleteMapping("/branchInfo/deleteEven")
    @ApiOperation("删除事件")
    public AjaxResult deleteBranchEvent(@RequestParam  Long id) {
        return AjaxResult.success(branchViewService.deleteBranchEvent(id));
    }

    @PutMapping("/branchInfo/updateEven")
    @ApiOperation("更新事件")
    public AjaxResult updateBranchEvent(@RequestBody @Valid  BranchEvents branchEvents) {
        return AjaxResult.success(branchViewService.updateBranchEvent(branchEvents));
    }

    @GetMapping("/category/path")
    @ApiOperation("显示网格层级")
    public AjaxResult getCategoryPath(@RequestParam("code") String code) {
        return AjaxResult.success(branchViewService.getCategoryPath(code));
    }

    @GetMapping("/branchInfo/gridInfo")
    @ApiOperation("根据网格编码查询网格信息")
    public AjaxResult getGridInfo(@RequestParam("dutyNetworkName") String dutyNetworkName) {
        return AjaxResult.success(branchViewService.getGridManageInfo(dutyNetworkName));
    }

    @PostMapping("/branchInfo/cashServiceInfo")
    @ApiOperation("根据网格编码查询网格现金服务点")
    public AjaxResult getCashServiceInfo(@RequestBody List<String> gridCodes) {
        return AjaxResult.success(branchViewService.getCashServiceInfo(gridCodes));
    }

    @PostMapping("/branchInfo/financialServiceInfo")
    @ApiOperation("根据网格编码查询网格金融服务站")
    public AjaxResult getFinancialServiceInfo(@RequestBody List<String> gridCodes) {
        return AjaxResult.success(branchViewService.getFinancialServiceInfo(gridCodes));
    }

}
