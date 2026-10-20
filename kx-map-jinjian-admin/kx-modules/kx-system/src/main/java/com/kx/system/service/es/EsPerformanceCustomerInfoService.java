package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.customer.PerformanceCustomerInfo;


public interface EsPerformanceCustomerInfoService {
    JSONObject selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo, String indexName, Integer pageSize, Integer pageNum);
    JSONObject selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo, String indexName);
}
