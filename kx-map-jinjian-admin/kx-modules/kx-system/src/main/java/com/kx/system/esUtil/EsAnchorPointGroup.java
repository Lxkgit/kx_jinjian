package com.kx.system.esUtil;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.req.GroupManageReq;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.util.ObjectUtils;

public class EsAnchorPointGroup {


    public static BoolQueryBuilder ceratQueryGroupMiniSourceBuilder(CustomerSelect select){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //机构以及下级机构
        if (select.getDeptIds() != null ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorsDeptIds.keyword", "*," + select.getDeptIds() + ",*");
            boolQueryBuilder.must(wildcardQueryBuilder);
        }
        //网格及下级网格
        if (StringUtils.isNotEmpty(select.getGridCode())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorCodes.keyword", "*," + select.getGridCode() + ",*");
            boolQueryBuilder.must(wildcardQueryBuilder);

        }

        //网格管理经理列表查询
        if (select.getManagerCodes()!= null && select.getManagerCodes().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("belongManagerCode",select.getManagerCodes()));
        }

        if (select.getGroupIdList()!= null && select.getGroupIdList().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("customGroupId",select.getGroupIdList()));
        }

        boolQueryBuilder.must(QueryBuilders.termQuery("customType",select.getGroupCategory()));


        //权限控制
        JSONObject queryEsRole = select.getQueryEsRole();
        if(!ObjectUtils.isEmpty(queryEsRole)){
            if (queryEsRole.containsKey("userId")){
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", queryEsRole.getString("userId"));
                boolQueryBuilder.must(termQueryBuilder);
            }else if (queryEsRole.containsKey("deptId")){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorsDeptIds.keyword", "*," + queryEsRole.getString("deptId") + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);

            }

        }

        return boolQueryBuilder;

    }

    public static BoolQueryBuilder cerateDeleteBuilder( GroupManageReq req){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("customGroupId",req.getGroupId()));
        boolQueryBuilder.must(QueryBuilders.termsQuery("customerId",req.getIds()));

        return boolQueryBuilder;
    }
}
