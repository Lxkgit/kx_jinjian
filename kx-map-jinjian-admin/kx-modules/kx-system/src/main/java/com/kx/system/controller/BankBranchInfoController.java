package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.BankBranchInfo;
import com.kx.system.service.IBankBranchInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bank/branch")
@Api(tags = "银行网点管理")
public class BankBranchInfoController extends BaseController {

    @Resource
    private IBankBranchInfoService bankBranchInfoService;

    /**
     * 按行政区划代码查询银行网点
     *
     * @param areaCode        行政区划代码，支持省/市/区/街道/村各级编码
     * @param includeChildren true=本级及下辖，false=仅本级（默认 false）
     * @param bankType        银行类型枚举code（可选，不传则不过滤），见 BankTypeEnum：1=国家开发银行，4=中国工商银行，10=招商银行，35=农村商业银行 等
     */
    @ApiOperation("按行政区划代码查询银行网点")
    @GetMapping("/list")
    public AjaxResult listByAreaCode(
            @RequestParam String areaCode,
            @RequestParam(defaultValue = "false") boolean includeChildren,
            @RequestParam(required = false) Integer bankType) {
        List<BankBranchInfo> list = bankBranchInfoService.selectByAreaCode(areaCode, includeChildren, bankType);
        return AjaxResult.success(list);
    }
}