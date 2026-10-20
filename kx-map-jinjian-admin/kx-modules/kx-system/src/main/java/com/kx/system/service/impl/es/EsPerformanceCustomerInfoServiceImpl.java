package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.esUtil.EsPerformanceCustomerInfo;
import com.kx.system.service.es.EsPerformanceCustomerInfoService;
import com.kx.system.util.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author  ligen
 * 绩效客户  ES  业务层
 */
@Slf4j
@Service
public class EsPerformanceCustomerInfoServiceImpl implements EsPerformanceCustomerInfoService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public JSONObject selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo, String indexName, Integer pageSize, Integer pageNum) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = EsPerformanceCustomerInfo.ceratSearchSourceBuilder(performanceCustomerInfo);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.trackTotalHits(true);
        //排序必须指定   不然每页会随机展示数据
        searchSourceBuilder.sort("id", SortOrder.DESC);
        if (pageSize ==null || pageSize <= 0){
            pageSize = 100;
        }
        if (pageNum!=null && pageNum >0){
            searchSourceBuilder.from((pageNum -1)*pageSize);
        }
        searchSourceBuilder.size(pageSize);
        JSONObject jsonObject = EsSearchUtil.selectEs(indexName, searchSourceBuilder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public JSONObject selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo, String indexName) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = EsPerformanceCustomerInfo.ceratSearchSourceBuilder(performanceCustomerInfo);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.trackTotalHits(true);
        //排序必须指定   不然每页会随机展示数据
        searchSourceBuilder.sort("id", SortOrder.DESC);
        JSONObject jsonObject = EsSearchUtil.selectEs(indexName, searchSourceBuilder,restHighLevelClient);
        return jsonObject;
    }
}
