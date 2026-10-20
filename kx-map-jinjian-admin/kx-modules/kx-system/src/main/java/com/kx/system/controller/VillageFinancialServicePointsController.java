package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.common.TableDataInfoModel;
import com.kx.system.domain.dto.VillageFinancialServicePointsQuery;
import com.kx.system.service.IVillageFinancialServicePointsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author zxy
 */
@RestController
@RequestMapping("villageFinancial/service/points")
@Api(tags = "乡村金融服务网点信息管理")
public class VillageFinancialServicePointsController extends BaseController {

    // todo 农村金融服务站

    @Autowired
    private IVillageFinancialServicePointsService villageFinancialServicePointsService;

    @ApiOperation(value = "乡村金融网点服务列表")
    @PostMapping(value = "/list")
    public TableDataInfo getVillageFinancialServicePoints(@RequestBody VillageFinancialServicePointsQuery servicePointsQuery) {
        startPage(servicePointsQuery);
        return getDataTable(villageFinancialServicePointsService.selectVillageFinancialServicePointsList(servicePointsQuery));
    }


    @ApiOperation(value = "乡村金融服务锚点跑批")
    @GetMapping(value = "/villageFinancialAnchCode")
    public AjaxResult villageFinancialAnchCode(){
        villageFinancialServicePointsService.villageFinancialAnchCode();
        return AjaxResult.success();
    }


    @ApiOperation(value = "乡村金融服务锚点")
    @GetMapping(value = "/anchor")
    public TableDataInfo add(@RequestParam("gridCode") String gridCode){
        return getDataTable(villageFinancialServicePointsService.getAnchorInfo(gridCode));
    }

    @ApiOperation(value = "批量导出金融服务站")
    @PostMapping(value = "/exportBatch")
    public AjaxResult exportBatch(HttpServletResponse response, @RequestBody VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException {
        villageFinancialServicePointsService.exportBatch(response,village);
        return AjaxResult.success("导出成功");
    }

    @ApiOperation(value = "test")
    @PostMapping(value = "/exportBatch/test")
    public AjaxResult exportBatchTest(VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException {
        villageFinancialServicePointsService.exportBatchTest(village);
        return AjaxResult.success("导出成功");
    }

}
