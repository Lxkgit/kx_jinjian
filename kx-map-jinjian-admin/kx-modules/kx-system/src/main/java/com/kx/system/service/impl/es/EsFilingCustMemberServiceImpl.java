package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.esUtil.EsFilingGroupMember;
import com.kx.system.service.es.EsFilingCustMemberService;
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
 * @author ligen
 *
 */
@Service
@Slf4j
public class EsFilingCustMemberServiceImpl implements EsFilingCustMemberService {
    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public JSONObject esQueryFilingMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum) {

        SearchSourceBuilder builder = new SearchSourceBuilder();
        FilingCustomerInfo filingCustomerInfo  = new FilingCustomerInfo();
        BeanUtils.copyProperties(req, filingCustomerInfo);
        filingCustomerInfo.setCustomGroupId(req.getGroupId());
        BoolQueryBuilder boolQueryBuilder = EsFilingGroupMember.ceratSourceBuilder(filingCustomerInfo);
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
        JSONObject jsonObject = EsSearchUtil.selectEs("es_filing_customer_group_info", builder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public Long remove(GroupManageReq req) {
        BoolQueryBuilder boolQueryBuilder = EsFilingGroupMember.cerateDeleteBuilder(req);
        return EsSearchUtil.esDeleteData("es_filing_customer_group_info", boolQueryBuilder, restHighLevelClient);

    }

    @Override
    public void insertBatch(List<JSONObject> list) {

        EsSearchUtil.asyncSingleBeatch("es_filing_customer_group_info",list,restHighLevelClient);
    }
}
