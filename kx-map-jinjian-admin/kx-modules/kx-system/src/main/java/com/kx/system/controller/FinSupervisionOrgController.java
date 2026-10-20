package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.FinSupervisionOrg;
import com.kx.system.service.IFinSupervisionOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/supervision/org")
@Api(tags = "金融监管机构管理")
public class FinSupervisionOrgController extends BaseController {

    @Resource
    private IFinSupervisionOrgService finSupervisionOrgService;

    /**
     * 按行政区划代码查询金融监管机构
     *
     * @param areaCode        行政区划代码，支持省/市/区/街道/村各级编码
     * @param includeChildren true=本级及下辖，false=仅本级（默认 false）
     * @param level           层级过滤：1-省级 2-市级 3-区级，不传则不过滤
     */
    @ApiOperation("按行政区划代码查询金融监管机构")
    @GetMapping("/list")
    public AjaxResult listByAreaCode(
            @RequestParam String areaCode,
            @RequestParam(defaultValue = "false") boolean includeChildren,
            @RequestParam(required = false) Integer level) {
        List<FinSupervisionOrg> list = finSupervisionOrgService.selectByAreaCode(areaCode, includeChildren, level);
        return AjaxResult.success(list);
    }
}