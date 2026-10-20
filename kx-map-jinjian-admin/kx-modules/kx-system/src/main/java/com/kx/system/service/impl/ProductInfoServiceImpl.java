package com.kx.system.service.impl;

import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.ProductBusStatistics;
import com.kx.system.domain.ProductBusStatisticsManager;
import com.kx.system.domain.ProductInfo;
import com.kx.system.domain.ProductScanLog;
import com.kx.system.mapper.ProductBusStatisticsManagerMapper;
import com.kx.system.mapper.ProductBusStatisticsMapper;
import com.kx.system.mapper.ProductInfoMapper;
import com.kx.system.mapper.ProductScanLogMapper;
import com.kx.system.service.ProductInfoService;
import com.kx.system.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private UserUtil userUtil;
    @Resource
    private ProductBusStatisticsMapper productBusStatisticsMapper;
    @Resource
    private ProductScanLogMapper productScanLogMapper;
    @Resource
    private ProductBusStatisticsManagerMapper productBusStatisticsManagerMapper;

    @Override
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo) {
        return productInfoMapper.selectPage(productInfo);
    }

    @Override
    public int insertProductInfo(ProductInfo productInfo) {
        //新增业务信息
        SysUser loginUser = userUtil.getLoginUser();
        productInfo.setCreateBy(loginUser.getNickName());
        productInfo.setCreateTime(new Date());
        productInfo.setCreateNum(loginUser.getManagerNum());
        productInfoMapper.insertSelective(productInfo);
        //新增产品业务统计信息
        ProductBusStatistics productBusStatistics = new ProductBusStatistics();
        productBusStatistics.setProductId(productInfo.getId());
        return productBusStatisticsMapper.insertSelective(productBusStatistics);
    }

    @Override
    public ProductBusStatistics queryBusStatisticsByProductId(Long productId) {
        return productBusStatisticsMapper.selectByProductId(productId);
    }

    @Override
    public List<ProductScanLog> productScanLogList(ProductScanLog productScanLog) {
        return productScanLogMapper.selectByProductId(productScanLog.getProductId());
    }

    @Override
    public List<ProductBusStatistics> productBusStatisticsPage(ProductBusStatistics productBusStatistics) {
        return productBusStatisticsMapper.selectPage(productBusStatistics);
    }

    @Override
    public List<ProductBusStatisticsManager> productBusStatisticsManagerList(ProductBusStatisticsManager productBusStatisticsManager) {
        return productBusStatisticsManagerMapper.selectPage(productBusStatisticsManager);
    }


}
