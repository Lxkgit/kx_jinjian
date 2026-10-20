package com.kx.system.service;

import com.kx.system.domain.ProductBusStatistics;
import com.kx.system.domain.ProductBusStatisticsManager;
import com.kx.system.domain.ProductInfo;
import com.kx.system.domain.ProductScanLog;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> selectProductInfoList(ProductInfo productInfo);

    int insertProductInfo(ProductInfo productInfo);

    ProductBusStatistics queryBusStatisticsByProductId(Long productId);

    List<ProductScanLog> productScanLogList(ProductScanLog productScanLog);

    List<ProductBusStatistics> productBusStatisticsPage(ProductBusStatistics productBusStatistics);


    List<ProductBusStatisticsManager> productBusStatisticsManagerList(ProductBusStatisticsManager productBusStatisticsManager);


}
