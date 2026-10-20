package com.kx.system.controller;

import javax.servlet.http.HttpServletResponse;

import com.kx.system.domain.customer.AcquiringCustomerInfo;
import com.kx.system.service.IAcquiringCustomerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.page.TableDataInfo;

import java.util.List;

/**
 * 收单客户信息Controller
 *
 * @author kangxu
 * @date 2024-04-01
 */
@RestController
@RequestMapping("/acquiring/customer")
@Api(tags = "收单客户信息")
public class AcquiringCustomerInfoController extends BaseController
{
    @Autowired
    private IAcquiringCustomerInfoService acquiringCustomerInfoService;

    /**
     * 查询收单客户信息列表
     */
    @PostMapping("/list")
    @ApiOperation("查询收单客户信息列表")
    public TableDataInfo list(@RequestBody AcquiringCustomerInfo acquiringCustomerInfo)
    {
        startPage(acquiringCustomerInfo);
        List<AcquiringCustomerInfo> list = acquiringCustomerInfoService.selectAcquiringCustomerInfoList(acquiringCustomerInfo);
        return getDataTable(list);
    }

    /**
     * 导出收单客户信息列表
     */
    @Log(title = "收单客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出收单客户信息列表")
    public void export(HttpServletResponse response, AcquiringCustomerInfo acquiringCustomerInfo)
    {
        List<AcquiringCustomerInfo> list = acquiringCustomerInfoService.selectAcquiringCustomerInfoList(acquiringCustomerInfo);
        ExcelUtil<AcquiringCustomerInfo> util = new ExcelUtil<AcquiringCustomerInfo>(AcquiringCustomerInfo.class);
        util.exportExcel(response, list, "收单客户信息数据");
    }

    /**
     * 获取收单客户信息详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收单客户信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(acquiringCustomerInfoService.selectAcquiringCustomerInfoById(id));
    }

    /**
     * 新增收单客户信息
     */
    @Log(title = "收单客户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增收单客户信息")
    public AjaxResult add(@RequestBody AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return toAjax(acquiringCustomerInfoService.insertAcquiringCustomerInfo(acquiringCustomerInfo));
    }

    /**
     * 修改收单客户信息
     */
    @ApiOperation("修改收单客户信息")
    @Log(title = "收单客户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return toAjax(acquiringCustomerInfoService.updateAcquiringCustomerInfo(acquiringCustomerInfo));
    }

    /**
     * 删除收单客户信息
     */
    @Log(title = "收单客户信息", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除收单客户信息")
    public AjaxResult remove(@RequestBody AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return toAjax(acquiringCustomerInfoService.deleteAcquiringCustomerInfoById(acquiringCustomerInfo.getId()));
    }
}
