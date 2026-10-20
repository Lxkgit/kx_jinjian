package com.kx.system.esUtil;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.CreditCustomerInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class EsCreditCustomerInfo {

    public static BoolQueryBuilder ceratSearchSourceBuilder(CreditCustomerInfo creditCustomerInfo){

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        int count = 0;

        //条件拼接
        if (StringUtils.isNotEmpty(creditCustomerInfo.getCustomerName())) {
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerName.keyword", "*" + creditCustomerInfo.getCustomerName() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if (StringUtils.isNotEmpty(creditCustomerInfo.getCustomerIdentity())){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + creditCustomerInfo.getCustomerIdentity() + "*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }

        if (creditCustomerInfo.getCustomerTypes() != null && creditCustomerInfo.getCustomerTypes().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("customerType",creditCustomerInfo.getCustomerTypes()));
            count++;
        }


        if (creditCustomerInfo.getBelongGridCodes() != null && creditCustomerInfo.getBelongGridCodes().size()>0){
            List<String> belongGridCodes = creditCustomerInfo.getBelongGridCodes();
            for (String belongGridCode : belongGridCodes) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorCodes.keyword", "*," + belongGridCode + ",*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            count++;
        }
        if (StringUtils.isNotEmpty(creditCustomerInfo.getBelongGridCode()) ){
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestorCodes.keyword", "*," + creditCustomerInfo.getBelongGridCode() + ",*");
            boolQueryBuilder.must(wildcardQueryBuilder);
            count++;
        }
        if (creditCustomerInfo.getBranchBanks() != null && creditCustomerInfo.getBranchBanks().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("deptId",creditCustomerInfo.getBranchBanks()));
            count++;
        }
        if (creditCustomerInfo.getGuaranteeMethods() != null && creditCustomerInfo.getGuaranteeMethods().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("guaranteeMethod",creditCustomerInfo.getGuaranteeMethods()));
            count++;
        }

        if(creditCustomerInfo.getUsingType() !=null ){
            boolQueryBuilder.must(QueryBuilders.termQuery("usingType",creditCustomerInfo.getUsingType()));
            count++;
        }
        if(creditCustomerInfo.getIsProduct() !=null ){
            boolQueryBuilder.must(QueryBuilders.termQuery("isProduct",creditCustomerInfo.getIsProduct()));
            count++;
        }
        if (creditCustomerInfo.getFiveLevels() != null && creditCustomerInfo.getFiveLevels().size()>0){
            for (String fiveLevel : creditCustomerInfo.getFiveLevels()) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("fiveLevel.keyword", "*" + fiveLevel + "*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            count++;
        }
        if (creditCustomerInfo.getProductLabels() != null && creditCustomerInfo.getProductLabels().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("productLabel",creditCustomerInfo.getProductLabels()));
            count++;
        }

        if (creditCustomerInfo.getOfflineAttracts() != null && creditCustomerInfo.getOfflineAttracts().size()>0){
            for (String offlineAttract : creditCustomerInfo.getOfflineAttracts()) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("offlineAttract.keyword", "*" + offlineAttract + "*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            count++;
        }

        if (creditCustomerInfo.getExchangeMethod() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("exchangeMethod",creditCustomerInfo.getExchangeMethod()));
            count++;
        }
        if (creditCustomerInfo.getCreditPromotions() != null && creditCustomerInfo.getCreditPromotions().size()>0){
            for (String creditPromotion : creditCustomerInfo.getCreditPromotions()) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("creditPromotion.keyword", "*" + creditPromotion + "*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            count++;
        }
        if (creditCustomerInfo.getOtherLabel() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("otherLabel",creditCustomerInfo.getOtherLabel()));
            count++;
        }

        if (creditCustomerInfo.getIsInArrears() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("isInArrears",creditCustomerInfo.getIsInArrears()));
            count++;
        }
        if (creditCustomerInfo.getIsProduct() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("isProduct",creditCustomerInfo.getIsProduct()));
            count++;
        }
        if (StringUtils.isNotEmpty(creditCustomerInfo.getCustomerPhone())){
            boolQueryBuilder.must(QueryBuilders.termQuery("customerPhone",creditCustomerInfo.getCustomerPhone()));
            count++;
        }
        if (StringUtils.isNotEmpty(creditCustomerInfo.getCustomerAddress())){
            boolQueryBuilder.must(QueryBuilders.termQuery("customerAddress",creditCustomerInfo.getCustomerAddress()));
            count++;
        }
        if (creditCustomerInfo.getCreditAmount() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("creditAmount",creditCustomerInfo.getCreditAmount()));
            count++;
        }
        if (creditCustomerInfo.getUsingCreditAmount() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("usingCreditAmount",creditCustomerInfo.getUsingCreditAmount()));
            count++;
        }
        if (StringUtils.isNotEmpty(creditCustomerInfo.getGridManager())){
            boolQueryBuilder.must(QueryBuilders.termQuery("gridManager",creditCustomerInfo.getGridManager()));
            count++;
        }
        if (StringUtils.isNotEmpty(creditCustomerInfo.getCreditManager())){
            boolQueryBuilder.must(QueryBuilders.termQuery("creditManager",creditCustomerInfo.getCreditManager()));
            count++;
        }

        if (StringUtils.isNotEmpty(creditCustomerInfo.getCreditManagerJobNumber())){
            boolQueryBuilder.must(QueryBuilders.termQuery("creditManagerJobNumber",creditCustomerInfo.getCreditManagerJobNumber()));
            count++;
        }

        if (creditCustomerInfo.getDeleteFlag() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("deleteFlag",creditCustomerInfo.getDeleteFlag()));
            count++;
        }


        if(StringUtils.isNotEmpty(creditCustomerInfo.getQuery()) ){


            BoolQueryBuilder boolQueryBuilder2 = new BoolQueryBuilder();
            WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("customerName.keyword", "*" + creditCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder);

            WildcardQueryBuilder wildcardQueryBuilder2 = QueryBuilders.wildcardQuery("customerIdentity.keyword", "*" + creditCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder2);

            WildcardQueryBuilder wildcardQueryBuilder3 = QueryBuilders.wildcardQuery("customerPhone.keyword", "*" + creditCustomerInfo.getQuery() + "*");
            boolQueryBuilder2.should(wildcardQueryBuilder3);

            boolQueryBuilder.must(boolQueryBuilder2);
            count++;
        }
        if (creditCustomerInfo.getBelongGridCodeArr() != null && creditCustomerInfo.getBelongGridCodeArr().size()>0){
            boolQueryBuilder.must(QueryBuilders.termQuery("belongGridCode",creditCustomerInfo.getBelongGridCodeArr()));
            count++;
        }
        if (creditCustomerInfo.getGroupIdList() != null && creditCustomerInfo.getGroupIdList().size()>0){
            boolQueryBuilder.must(QueryBuilders.termsQuery("groupId",creditCustomerInfo.getGroupIdList()));
            count++;
        }
        //权限控制
        JSONObject queryEsRole = creditCustomerInfo.getQueryEsRole();
        if(!ObjectUtils.isEmpty(queryEsRole)){
            if (queryEsRole.containsKey("userId")){
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("userId", queryEsRole.getString("userId"));
                boolQueryBuilder.must(termQueryBuilder);
                count++;
            }else if (queryEsRole.containsKey("deptId")){
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("ancestors.keyword", "*," + queryEsRole.getString("deptId") + ",*");
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
