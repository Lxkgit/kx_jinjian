package com.kx.system.esUtil;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.req.GroupManageReq;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class EsCreditGroupMember {


    public static BoolQueryBuilder ceratSourceBuilder(  CreditCustomerInfo creditCustomerInfo){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        int count = 0;
        //条件拼接
        if(StringUtils.isNotEmpty(creditCustomerInfo.getCustomerIdentity())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + creditCustomerInfo.getCustomerIdentity() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if(StringUtils.isNotEmpty(creditCustomerInfo.getCustomerName())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerName.keyword", "*" + creditCustomerInfo.getCustomerName() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(creditCustomerInfo.getCustomerPhone())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerPhone.keyword", "*" + creditCustomerInfo.getCustomerPhone() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(creditCustomerInfo.getCreditManager())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("creditManager.keyword", "*" + creditCustomerInfo.getCreditManager() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if (creditCustomerInfo.getCustomGroupId() != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery("groupId", creditCustomerInfo.getCustomGroupId()));
            count++;
        }
        if (creditCustomerInfo.getBelongGridCodeArr() != null && creditCustomerInfo.getBelongGridCodeArr().size()>0){
            List<String> belongGridCodeArr = creditCustomerInfo.getBelongGridCodeArr();
            for (String belongGridCode : belongGridCodeArr) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorCodes.keyword", "*," + belongGridCode + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
        }

        //权限控制
        JSONObject queryEsRole = creditCustomerInfo.getQueryEsRole();
        if(!ObjectUtils.isEmpty(queryEsRole)){
            if (queryEsRole.containsKey("userId")){
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", queryEsRole.getString("userId"));
                boolQueryBuilder.must(termQueryBuilder);
                count++;
            }else if (queryEsRole.containsKey("deptId")){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorsId.keyword", "*," + queryEsRole.getString("deptId") + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);
                count++;
            }
        }


        if (count<=0){
            return boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }
        return boolQueryBuilder;
    }


    public static BoolQueryBuilder cerateDeleteBuilder( GroupManageReq req){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        int count= 0;
        if (req.getGroupId() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("groupId",req.getGroupId()));
            count++;
        }
        if (req.getIds()!= null){
            boolQueryBuilder.must(QueryBuilders.termsQuery("id",req.getIds()));
            count++;
        }

        if (count<=0){
            return boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }
        return boolQueryBuilder;
    }
}
