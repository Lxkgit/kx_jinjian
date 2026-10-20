package com.kx.system.esUtil;


import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import org.elasticsearch.index.query.*;
import org.springframework.util.ObjectUtils;


public class EsPerformanceCustomerInfo {

    public static BoolQueryBuilder ceratSearchSourceBuilder(PerformanceCustomerInfo performanceCustomerInfo){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        int count = 0;
        //查询条件拼接
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getCustomerNm()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerNm.keyword", "*" + performanceCustomerInfo.getCustomerNm() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getCustomerName()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerName.keyword", "*" + performanceCustomerInfo.getCustomerName() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getCustomerPhone()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerPhone.keyword", "*" + performanceCustomerInfo.getCustomerPhone() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getCustomerIdentity()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + performanceCustomerInfo.getCustomerIdentity() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if(performanceCustomerInfo.getCustomerLevelList()!= null &&  performanceCustomerInfo.getCustomerLevelList().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("customerLevel",performanceCustomerInfo.getCustomerLevelList()));
            count++;
        }

        if(StringUtils.isNotEmpty(performanceCustomerInfo.getWorkUnit()) ){

            WildcardQueryBuilder workUnit = QueryBuilders.wildcardQuery("workUnit.keyword","*" +  performanceCustomerInfo.getWorkUnit() + "*");
            boolQueryBuilder.must(workUnit);
        }

        if(StringUtils.isNotEmpty(performanceCustomerInfo.getJob()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("job.keyword", "*" + performanceCustomerInfo.getJob() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if(StringUtils.isNotEmpty(performanceCustomerInfo.getCreditManager()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("creditManager.keyword", "*" + performanceCustomerInfo.getCreditManager() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if (performanceCustomerInfo.getMinDeposit() != null || performanceCustomerInfo.getMaxDeposit()!= null){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("deposit");

            if(performanceCustomerInfo.getMinDeposit() != null  ){
                rangeQueryBuilder.gte(performanceCustomerInfo.getMinDeposit());
            }
            if(performanceCustomerInfo.getMaxDeposit()!= null ){
                rangeQueryBuilder.lte(performanceCustomerInfo.getMaxDeposit());
            }
            boolQueryBuilder.must(rangeQueryBuilder);
            count++;
        }

        if(performanceCustomerInfo.getMinLoan() != null || performanceCustomerInfo.getMaxLoan() != null ){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("loan");
            if (performanceCustomerInfo.getMinLoan() != null){
                rangeQueryBuilder.gte(performanceCustomerInfo.getMinLoan());
            }
            if (performanceCustomerInfo.getMaxLoan() != null){
                rangeQueryBuilder.lte(performanceCustomerInfo.getMaxLoan());
            }
            boolQueryBuilder.must(rangeQueryBuilder);
            count++;
        }

        if(performanceCustomerInfo.getMinFinance() != null || performanceCustomerInfo.getMaxFinance()!= null){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("finance");
            if (performanceCustomerInfo.getMinFinance() != null){
                rangeQueryBuilder.gte(performanceCustomerInfo.getMinFinance());
            }
            if (performanceCustomerInfo.getMaxFinance() != null){
                rangeQueryBuilder.lte(performanceCustomerInfo.getMaxFinance());
            }
            boolQueryBuilder.must(rangeQueryBuilder);
            count++;
        }
        if(performanceCustomerInfo.getMinProperty() != null || performanceCustomerInfo.getMaxProperty()!= null){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("property");
            if (performanceCustomerInfo.getMinProperty() != null){
                rangeQueryBuilder.gte(performanceCustomerInfo.getMinProperty());
            }
            if (performanceCustomerInfo.getMaxProperty() != null){
                rangeQueryBuilder.lte(performanceCustomerInfo.getMaxProperty());
            }
            boolQueryBuilder.must(rangeQueryBuilder);
            count++;
        }

        if(StringUtils.isNotEmpty(performanceCustomerInfo.getBelongGridCode()) ){

            boolQueryBuilder.must(QueryBuilders.termQuery("belongGridCode",performanceCustomerInfo.getBelongGridCode()));
            count++;
        }
        if(performanceCustomerInfo.getBelongGridCodeArr()!= null && performanceCustomerInfo.getBelongGridCodeArr().size() >0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("belongGridCode",performanceCustomerInfo.getBelongGridCodeArr()));
            count++;
        }
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getGridManager()) ){
            boolQueryBuilder.must(QueryBuilders.termQuery("gridManager",performanceCustomerInfo.getGridManager()));
            count++;
        }
        if(StringUtils.isNotEmpty(performanceCustomerInfo.getQuery()) ){
            BoolQueryBuilder boolQueryBuilder2 = new BoolQueryBuilder();
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerName.keyword", "*" + performanceCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder);

            WildcardQueryBuilder wildcardQueryBuilder2 = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + performanceCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder2);

            WildcardQueryBuilder wildcardQueryBuilder3 = QueryBuilders.wildcardQuery("customerPhone.keyword", "*" + performanceCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder3);
            boolQueryBuilder.must(boolQueryBuilder2);
            count++;
        }
        if(performanceCustomerInfo.getCustomerType() != null ){
            boolQueryBuilder.must(QueryBuilders.termQuery("customerType",performanceCustomerInfo.getCustomerType()));
            count++;
        }
        if(performanceCustomerInfo.getCustomerTypes() != null && performanceCustomerInfo.getCustomerTypes().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("customerType",performanceCustomerInfo.getCustomerTypes()));
            count++;
        }

        //根据客群id查询
        if (performanceCustomerInfo.getGroupIdList() != null && performanceCustomerInfo.getGroupIdList().size() >0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("groupId",performanceCustomerInfo.getGroupIdList()));
            count++;
        }


        //权限控制
        JSONObject queryEsRole = performanceCustomerInfo.getQueryEsRole();
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

}
