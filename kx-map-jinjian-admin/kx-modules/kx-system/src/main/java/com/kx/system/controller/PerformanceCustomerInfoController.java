package com.kx.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.customer.AcquiringCustomerInfo;
import com.kx.system.domain.customer.BankType;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.param.SearchPaChangeLogListParam;
import com.kx.system.service.IPerformanceCustomerInfoService;
import com.kx.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 绩效客户信息Controller
 *
 * @author kangxu
 * @date 2024-04-01
 */
@RestController
@RequestMapping("/performance/customer")
@Api(tags = "绩效客户信息")
public class PerformanceCustomerInfoController extends BaseController
{
    @Autowired
    private IPerformanceCustomerInfoService performanceCustomerInfoService;

    @Autowired
    private ISysDeptService deptService;
    /**
     * 查询绩效客户信息列表
     */
    @PostMapping("/list")
    @ApiOperation("查询绩效客户信息列表")
    public TableDataInfo list(@RequestBody PerformanceCustomerInfo performanceCustomerInfo){
        startPage(performanceCustomerInfo);
        List<PerformanceCustomerInfo> list = performanceCustomerInfoService.list(performanceCustomerInfo);
        return getDataTable(list);
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        return performanceCustomerInfoService.esQueryPerformanceCustomerInfoList(performanceCustomerInfo,loginUser);
    }

    /**
     * 查询网点分类
     */
    @GetMapping("/listByBank")
    @ApiOperation("查询网点分类")
    public AjaxResult listByBank(Long groupId){
        List<BankType> list = performanceCustomerInfoService.listByBank(groupId);
        return AjaxResult.success(list);
    }


    /**
     * 导出绩效客户信息列表
     */
    @Log(title = "绩效客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出绩效客户信息列表")
    public void export(HttpServletResponse response, PerformanceCustomerInfo performanceCustomerInfo)
    {
        List<PerformanceCustomerInfo> list = performanceCustomerInfoService.selectPerformanceCustomerInfoList(performanceCustomerInfo);
        ExcelUtil<PerformanceCustomerInfo> util = new ExcelUtil<PerformanceCustomerInfo>(PerformanceCustomerInfo.class);
        util.exportExcel(response, list, "绩效客户信息数据");
    }

    /**
     * 获取绩效客户信息详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取绩效客户信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(performanceCustomerInfoService.selectPerformanceCustomerInfoById(id));
    }

    /**
     * 新增绩效客户信息
     */
    @Log(title = "绩效客户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增绩效客户信息")
    public AjaxResult add(@RequestBody PerformanceCustomerInfo performanceCustomerInfo)
    {
        return toAjax(performanceCustomerInfoService.insertPerformanceCustomerInfo(performanceCustomerInfo));
    }

    /**
     * 修改绩效客户信息
     */
    @ApiOperation("修改绩效客户信息")
    @Log(title = "绩效客户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PerformanceCustomerInfo performanceCustomerInfo)
    {
        return toAjax(performanceCustomerInfoService.updatePerformanceCustomerInfo(performanceCustomerInfo));
    }

    /**
     * 删除绩效客户信息
     */
    @Log(title = "绩效客户信息", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除绩效客户信息")
    public AjaxResult remove(@RequestBody PerformanceCustomerInfo performanceCustomerInfo)
    {
        return toAjax(performanceCustomerInfoService.deletePerformanceCustomerInfoById(performanceCustomerInfo.getId()));
    }


    @ApiOperation(value = "网点信息模板下载", notes = "模板下载")
    @GetMapping(value = "/modelDownload")
    public AjaxResult modelDownload() {
        return AjaxResult.success(performanceCustomerInfoService.modelDownload());
    }


    @ApiOperation(value = "批量更新网点信息")
    @PostMapping(value = "/importBatch")
    public AjaxResult importBatch(MultipartFile file, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        performanceCustomerInfoService.importBatch(inputStream, request);
        return AjaxResult.success("ok");
    }


    @PostMapping("/anchorExport")
    @ApiOperation("网点信息导出")
    public void anchorExport(HttpServletResponse response, @RequestBody PerformanceCustomerInfo anchorExport){
        List<AnchorExport> list = performanceCustomerInfoService.selectAllAnchor(anchorExport);
        new ExcelUtil<>(AnchorExport.class).exportExcel(response, list, "变更记录数据");
    }


    @ApiOperation(value = "获取银行分类")
    @GetMapping(value = "/getDeptClassify")
    public AjaxResult getDeptClassify(){
        return AjaxResult.success(deptService.getDeptClassify());
    }


}
