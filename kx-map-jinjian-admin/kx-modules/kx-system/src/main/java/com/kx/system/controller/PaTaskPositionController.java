package com.kx.system.controller;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.param.ListPaTaskPositionParam;
import com.kx.system.service.IPaTaskPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/paTaskPosition")
@Api(tags = "时迹查询")
public class PaTaskPositionController {
    @Resource
    IPaTaskPositionService paTaskPositionService;

    @PostMapping("/list")
    @ApiOperation("查询列表")
    public AjaxResult list(@RequestBody ListPaTaskPositionParam param) {
        return AjaxResult.success(paTaskPositionService.list(param));
    }

}
