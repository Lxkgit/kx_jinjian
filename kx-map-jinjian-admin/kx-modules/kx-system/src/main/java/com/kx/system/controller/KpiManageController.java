package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.vo.KpiIndicatorVO;
import com.kx.system.service.IKpiIndicatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户搜索记录Controller
 *
 * @author xifan_h
 * @date 2022-03-24
 */
@RestController
@Api(tags = "考核管理")
@RequestMapping("/kpiManage")
@Validated
@Slf4j
public class KpiManageController extends BaseController {
    @Resource
    private IKpiIndicatorService kpiIndicatorService;

    @ApiOperation(value = "考核指标 - 查询指标", response = KpiIndicatorVO.class)
    @GetMapping("/indicator/selectIndicator")
    public TableDataInfo selectIndicator(@ApiParam("指标名称") String indicatorName,
                                         @ApiParam("创建人") String createUserName,
                                         Integer pageNum, Integer pageSize) {
        List<KpiIndicatorVO> list = kpiIndicatorService.selectIndicator(indicatorName, createUserName, null, null,
                pageNum, pageSize);
        return getDataTable(list);
    }
}
