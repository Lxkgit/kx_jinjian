package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.param.CreditHeatMapParam;
import com.kx.system.service.ICreditCustomerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 授信客户信息Controller
 *
 * @author kangxu
 * @date 2024-04-01
 */
@RestController
@RequestMapping("/creditCustomerInfo")
@Api(tags = "授信客户信息")
public class CreditCustomerInfoController extends BaseController {
    @Autowired
    private ICreditCustomerInfoService creditCustomerInfoService;

    /**
     * 查询授信客户信息列表
     */
    @PostMapping("/list")
    @ApiOperation("查询授信客户信息列表")
    public TableDataInfo list(@RequestBody CreditCustomerInfo creditCustomerInfo) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return creditCustomerInfoService.esCreditCustomerInfoList(creditCustomerInfo,loginUser);
    }

    /**
     * 导出授信客户信息列表
     */
    @Log(title = "授信客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出授信客户信息列表")
    public void export(HttpServletResponse response, CreditCustomerInfo creditCustomerInfo) {
        List<CreditCustomerInfo> list = creditCustomerInfoService.selectCreditCustomerInfoList(creditCustomerInfo);
        ExcelUtil<CreditCustomerInfo> util = new ExcelUtil<CreditCustomerInfo>(CreditCustomerInfo.class);
        util.exportExcel(response, list, "授信客户信息数据");
    }

    /**
     * 获取授信客户信息详细信息
     */
    @GetMapping(value = "/{customNm}")
    @ApiOperation("获取授信客户信息详细信息")
    public AjaxResult getInfo(@PathVariable("customNm") String customNm) {
        return success(creditCustomerInfoService.selectCreditCustomerInfoByCustomNm(customNm));
    }

    /**
     * 新增授信客户信息
     */
    @Log(title = "授信客户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增授信客户信息")
    public AjaxResult add(@RequestBody CreditCustomerInfo creditCustomerInfo) {
        return toAjax(creditCustomerInfoService.insertCreditCustomerInfo(creditCustomerInfo));
    }

    /**
     * 修改授信客户信息
     */
    @ApiOperation("修改授信客户信息")
    @Log(title = "授信客户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody CreditCustomerInfo creditCustomerInfo) {
        return toAjax(creditCustomerInfoService.updateCreditCustomerInfo(creditCustomerInfo));
    }


    @ApiOperation("授信客户热力图")
    @PostMapping("/heatMap")
    public AjaxResult heatMap(@RequestBody CreditHeatMapParam param) {
        Map<String, Integer> heatMap = creditCustomerInfoService.heatMap(param);

        return AjaxResult.success(heatMap);
    }
}
