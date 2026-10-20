package com.kx.system.esUtil;


import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.es.EsAnchorPointDto;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EsAnchorPoint {

    public static BoolQueryBuilder ceratSearchSourceBuilder(EsAnchorPointDto anchorPoint){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        int count = 0;
        //查询条件拼接
        if(StringUtils.isNotEmpty(anchorPoint.getCustomerNm()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerNm.keyword", "*" + anchorPoint.getCustomerNm() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(anchorPoint.getAnchorName()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("anchorName.keyword", "*" + anchorPoint.getAnchorName() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(anchorPoint.getGridManager()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("gridManager.keyword", "*" + anchorPoint.getGridManager() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(anchorPoint.getCustomerIdentity()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + anchorPoint.getCustomerIdentity() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(anchorPoint.getCreditManager()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("creditManager.keyword", "*" + anchorPoint.getCreditManager() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if (StringUtils.isNotEmpty(anchorPoint.getType())){
            boolQueryBuilder.must(QueryBuilders.termQuery("type",anchorPoint.getType()));
            count++;
        }
        if (anchorPoint.getDeptIds()!= null && anchorPoint.getDeptIds().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("deptId",anchorPoint.getDeptIds()));
            count++;
        }

        if (anchorPoint.getBelongGrids() != null && anchorPoint.getBelongGrids().size()>0){
            for (String belongGrid : anchorPoint.getBelongGrids()){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorCodes.keyword", "*," + belongGrid + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            count++;
        }


        //权限控制
        JSONObject queryEsRole = anchorPoint.getQueryEsRole();
        if(!ObjectUtils.isEmpty(queryEsRole)){
            if (queryEsRole.containsKey("userId")){
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", queryEsRole.getString("userId"));
                boolQueryBuilder.must(termQueryBuilder);
                count++;
            }else if (queryEsRole.containsKey("deptId")){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorsDeptIds.keyword", "*," + queryEsRole.getString("deptId") + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);
                count++;
            }

        }

        if (count<=0){
            return boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }
        return boolQueryBuilder;
    }


    public static Map<String,Object> createEsAnchorPoint(EsAnchorPointDto esAnchorPoint){
        Map<String,Object> map = new HashMap<>();
        //修改名称
        if (StringUtils.isNotEmpty(esAnchorPoint.getAnchorName())){
            map.put("anchorName",esAnchorPoint.getAnchorName());
        }
        //修改类型
        if (esAnchorPoint.getAddressType()!= null){
           map.put("addressType",esAnchorPoint.getAddressType());
        }
        //修改地址
       if (StringUtils.isNotEmpty(esAnchorPoint.getAddrInfo())){
           map.put("addrInfo",esAnchorPoint.getAddrInfo());
       }
        //经纬度
       if (esAnchorPoint.getLon()!=null){
           map.put("lon",esAnchorPoint.getLon());
       }
       if (esAnchorPoint.getLat()!=null){
           map.put("lat",esAnchorPoint.getLat());
       }
       //网格员
        if (StringUtils.isNotEmpty(esAnchorPoint.getGridManager())){
            map.put("gridManager",esAnchorPoint.getGridManager());
        }
       //归属网格
        if (StringUtils.isNotEmpty(esAnchorPoint.getBelongGrid())){
            map.put("belongGrid",esAnchorPoint.getBelongGrid());
        }
        if (StringUtils.isNotEmpty(esAnchorPoint.getBelongGridName())){
            map.put("belongGridName",esAnchorPoint.getBelongGridName());
        }
        //网格管理员信息更新
         if(StringUtils.isNotEmpty(esAnchorPoint.getBelongManagerCode())){
             map.put("belongManagerCode",esAnchorPoint.getBelongManagerCode());
         }
        if(StringUtils.isNotEmpty(esAnchorPoint.getBelongManagerName())){
            map.put("belongManagerName",esAnchorPoint.getBelongManagerName());
        }
        //网格父级列表
        if (StringUtils.isNotEmpty(esAnchorPoint.getAncestorCodes())){
            map.put("ancestorCodes",esAnchorPoint.getAncestorCodes());
        }
        return map;
   }


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
        //类型列表查询
        if (select.getTypeList()!= null&&select.getTypeList().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("type",select.getTypeList()));

        }
        boolQueryBuilder.must(QueryBuilders.existsQuery("lon"));

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

    public static BoolQueryBuilder ceratBuilderQueryByNmAndType(List<String> customerNmList , String type){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("type",type));
        boolQueryBuilder.must(QueryBuilders.termsQuery("customerNm",customerNmList));
        return boolQueryBuilder;
    }



}
