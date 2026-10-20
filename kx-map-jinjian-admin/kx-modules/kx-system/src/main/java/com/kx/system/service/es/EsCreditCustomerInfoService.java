package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.CreditCustomerInfo;

public interface EsCreditCustomerInfoService {

    JSONObject selectCredCustomerInfoList(CreditCustomerInfo creditCustomerInfo, String indexName, Integer pageSize, Integer pageNum);
}
