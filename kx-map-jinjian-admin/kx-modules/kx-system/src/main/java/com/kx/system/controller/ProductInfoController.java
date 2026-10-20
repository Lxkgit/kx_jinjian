package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.ProductBusStatistics;
import com.kx.system.domain.ProductBusStatisticsManager;
import com.kx.system.domain.ProductInfo;
import com.kx.system.domain.ProductScanLog;
import com.kx.system.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/standard/product")
public class ProductInfoController extends BaseController {

    @Resource
    private ProductInfoService productInfoService;

    @GetMapping("/page")
    public TableDataInfo productPage(ProductInfo productInfo) {
        startPage();
        List<ProductInfo> productInfoList = productInfoService.selectProductInfoList(productInfo);
        return getDataTable(productInfoList);
    }

    @PostMapping("/save")
    public AjaxResult saveProduct(@RequestBody  ProductInfo productInfo) {
        int row = productInfoService.insertProductInfo(productInfo);
        return row > 0 ? AjaxResult.success() : AjaxResult.error();
    }


    @GetMapping("/qr/scanning/statistics")
    public AjaxResult qrScanning(Long productId) {
        return AjaxResult.success(productInfoService.queryBusStatisticsByProductId(productId));
    }

    @GetMapping("/qr/scanning/cust/list")
    public TableDataInfo qrScanningCustList(ProductScanLog productScanLog) {
        startPage();
        List<ProductScanLog> productScanLogs = productInfoService.productScanLogList(productScanLog);
        return getDataTable(productScanLogs);
    }

    @GetMapping("/per/statistics/page")
    public TableDataInfo perStatistics(ProductBusStatistics productBusStatistics) {
        startPage();
        List<ProductBusStatistics> list = productInfoService.productBusStatisticsPage(productBusStatistics);
        return getDataTable(list);
    }


    @GetMapping("/per/statistics/detail")
    public TableDataInfo perStatisticsDetail(ProductBusStatisticsManager productBusStatisticsManager) {
        startPage();
        List<ProductBusStatisticsManager> productBusStatisticsManagers = productInfoService.productBusStatisticsManagerList(productBusStatisticsManager);
        return getDataTable(productBusStatisticsManagers);
    }





}
