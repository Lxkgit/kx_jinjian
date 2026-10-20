package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.esUtil.EsCreditCustomerInfo;
import com.kx.system.service.es.EsCreditCustomerInfoService;
import com.kx.system.util.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author ligen
 * 绩效客户 ES 业务层
 */

@Slf4j
@Service
public class EsCreditCustomerInfoServiceImpl implements EsCreditCustomerInfoService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public JSONObject selectCredCustomerInfoList(CreditCustomerInfo creditCustomerInfo, String indexName, Integer pageSize, Integer pageNum) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = EsCreditCustomerInfo.ceratSearchSourceBuilder(creditCustomerInfo);
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
}
