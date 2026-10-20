package com.kx.system.controller;

import com.alibaba.fastjson2.JSONObject;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.service.CashServicePointsService;

import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.common.TableDataInfoModel;
import com.kx.system.domain.dto.CashServicePointsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.kx.common.core.utils.PageUtils.startPage;
import static com.kx.system.domain.common.TableDataInfoModel.getDataTable;

@RestController
@RequestMapping("/service/points")
@Api(tags = "现金服务网点信息管理")
public class CashServicePointsController {

    // todo 现金服务点

    @Autowired
    private CashServicePointsService cashServicePointsService;


    /**
     * 模板下载
     * @return
     */
    @ApiOperation(value = "excel动态模板下载", notes = "传入客户类型")
    @GetMapping(value = "/modelDownload")
    public AjaxResult modelDownload() {
        return AjaxResult.success(cashServicePointsService.modelDownload());
    }


    @ApiOperation(value = "查看现金网点")
    @PostMapping(value = "/list")
    public JSONObject getCashServicePoints(@RequestBody CashServicePointsDTO cash){
        return cashServicePointsService.getCashServicePoints(cash);
    }

    @ApiOperation(value = "查看现金网点详情")
    @GetMapping(value = "/details")
    public CashServicePoints getCashDetails(@RequestParam("id") long id){
        CashServicePoints list = cashServicePointsService.getCashDetails(id);
        return list;
    }

    @ApiOperation(value = "现金锚点")
    @GetMapping(value = "/anchor")
    public TableDataInfoModel<List<CashServicePoints>> add(@RequestParam("gridCode") String gridCode){
        return getDataTable(cashServicePointsService.getAnchorInfo(gridCode));
    }

    @ApiOperation(value = "获取当前层级服务网点数量")
    @GetMapping(value = "/cashServiceTotal")
    public AjaxResult getCashServiceTotal(@RequestParam("gridCode") String gridCode){
        return AjaxResult.success(cashServicePointsService.getCashServiceTotal(gridCode));
    }

    @ApiOperation(value = "现金锚点跑批")
    @GetMapping(value = "/cashAnchCode")
    public AjaxResult cashAnchCode(){
        cashServicePointsService.cashAnchCode();
        return AjaxResult.success();
    }

    @ApiOperation(value = "锚点跑批")
    @GetMapping(value = "/anchCode")
    public AjaxResult anchCode(){
        cashServicePointsService.anchCode();
        return AjaxResult.success();
    }

    @ApiOperation(value = "批量导入现金服务网点")
    @PostMapping(value = "/importBatch")
    public AjaxResult importBatch(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        cashServicePointsService.importBatch(inputStream);
        return AjaxResult.success("导入成功");
    }

    @ApiOperation(value = "批量导出现金服务网点")
    @PostMapping(value = "/exportBatch")
    public AjaxResult exportBatch(HttpServletResponse response, @RequestBody CashServicePoints cashServicePoints) throws UnsupportedEncodingException {
        cashServicePointsService.exportBatch(response,cashServicePoints);
        return AjaxResult.success("导出成功");
    }

    @ApiOperation(value = "批量导出现金服务网点test")
    @PostMapping(value = "/exportBatch/test")
    public AjaxResult exportBatchTest(CashServicePoints cashServicePoints) throws UnsupportedEncodingException {
        cashServicePointsService.exportBatchTest(cashServicePoints);
        return AjaxResult.success("导出成功");
    }


}
