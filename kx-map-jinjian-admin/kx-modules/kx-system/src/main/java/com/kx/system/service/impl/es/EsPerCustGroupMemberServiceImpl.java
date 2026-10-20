package com.kx.system.service.impl.es;


import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.esUtil.EsPerGroupMember;
import com.kx.system.service.es.EsPerCustGroupMemberService;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class EsPerCustGroupMemberServiceImpl implements EsPerCustGroupMemberService {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public JSONObject esQueryPerMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
        BeanUtils.copyProperties(req, performanceCustomerInfo);
        performanceCustomerInfo.setCustomGroupId(req.getGroupId());
        BoolQueryBuilder boolQueryBuilder = EsPerGroupMember.ceratSourceBuilder(performanceCustomerInfo);
        builder.query(boolQueryBuilder);
        builder.trackTotalHits(true);
        //排序必须指定   不然每页会随机展示数据
        builder.sort("id", SortOrder.DESC);

        builder.query(boolQueryBuilder);
        builder.trackTotalHits(true);
        //排序必须指定   不然每页会随机展示数据
        builder.sort("id", SortOrder.DESC);
        if (pageSize ==null || pageSize <= 0){
            pageSize = 100;
        }
        if (pageNum!=null && pageNum >0){
            builder.from((pageNum -1)*pageSize);
        }
        builder.size(pageSize);
        JSONObject jsonObject = EsSearchUtil.selectEs("es_performance_customer_group_info", builder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public Long remove(GroupManageReq req) {
        BoolQueryBuilder boolQueryBuilder = EsPerGroupMember.cerateDeleteBuilder(req);
        return EsSearchUtil.esDeleteData("es_performance_customer_group_info", boolQueryBuilder, restHighLevelClient);
    }

    @Override
    public void insertBatch(List<JSONObject> list) {
        EsSearchUtil.asyncSingleBeatch("es_performance_customer_group_info",list,restHighLevelClient);
    }
}
