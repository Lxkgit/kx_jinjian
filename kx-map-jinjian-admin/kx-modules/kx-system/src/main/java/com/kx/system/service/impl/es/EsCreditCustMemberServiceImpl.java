package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.esUtil.EsCreditGroupMember;
import com.kx.system.service.es.EsCreditCustMemberService;
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

/**
 * @author
 */
@Service
@Slf4j
public class EsCreditCustMemberServiceImpl implements EsCreditCustMemberService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public JSONObject esQueryCreditMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
        BeanUtils.copyProperties(req, creditCustomerInfo);
        creditCustomerInfo.setCustomGroupId(req.getGroupId());
        BoolQueryBuilder boolQueryBuilder = EsCreditGroupMember.ceratSourceBuilder(creditCustomerInfo);
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
        JSONObject jsonObject = EsSearchUtil.selectEs("es_credit_customer_group_info", builder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public Long remove(GroupManageReq req) {
        BoolQueryBuilder boolQueryBuilder = EsCreditGroupMember.cerateDeleteBuilder(req);
        return EsSearchUtil.esDeleteData("es_credit_customer_group_info", boolQueryBuilder, restHighLevelClient);
    }

    @Override
    public void insertBatch(List<JSONObject> list) {

        EsSearchUtil.asyncSingleBeatch("es_credit_customer_group_info",list,restHighLevelClient);

    }
}
