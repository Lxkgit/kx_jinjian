package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.service.IPaViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.kx.system.domain.enums.OverviewType.*;


@Api(tags = {"小微视图"})
@RestController
@RequestMapping("/small")
public class PaViewController extends BaseController {

    @Resource
    private IPaViewService paViewService;

    @ApiOperation(value = "存款模块查询", notes = "传入主键")
    @GetMapping(value = "/deposit/getByQuery")
    public AjaxResult getDepositByQuery(PaViewSmallParam paViewSmallParam) {
        return AjaxResult.success(paViewService.query(paViewSmallParam, DEPOSIT_MODULE));
    }

    @ApiOperation(value = "网格概况模块查询", notes = "传入主键")
    @GetMapping(value = "/overview/getByQuery")
    public AjaxResult getOverviewByQuery(PaViewSmallParam paViewSmallParam) {
        return AjaxResult.success(paViewService.queryOverview(paViewSmallParam));
    }

    @ApiOperation(value = "贷款模块查询", notes = "传入主键")
    @GetMapping(value = "/loan/getByQuery")
    public AjaxResult getLoanByQuery(PaViewSmallParam paViewSmallParam) {
        return AjaxResult.success(paViewService.query(paViewSmallParam, LOAN));
    }

    @ApiOperation(value = "凡荣贷模块查询", notes = "传入主键")
    @GetMapping(value = "/prosperousLoan/getByQuery")
    public AjaxResult getProsperousLoanByQuery(PaViewSmallParam paViewSmallParam) {
        return AjaxResult.success(paViewService.query(paViewSmallParam, PROSPEROUS_LOAN));
    }
}
