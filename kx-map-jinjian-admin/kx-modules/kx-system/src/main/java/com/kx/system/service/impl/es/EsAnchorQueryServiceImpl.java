package com.kx.system.service.impl.es;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.service.ISysDeptService;
import com.kx.system.service.es.EsAnchorQueryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EsAnchorQueryServiceImpl implements EsAnchorQueryService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private AnchorPointMapper anchorPointMapper;

    @Autowired
    private IPaAddrInfoTypeService paAddrInfoTypeService;
    @Override
    public List<AnchorPoint> selectByCustomerType(CustomerSelect select) throws IOException {
        // 处理机构条件
        if (select.getOrgCodes() != null && !select.getOrgCodes().isEmpty()) {
            List<Long> deptIds = sysDeptService.selectDeptIdByCodes(select.getOrgCodes());
            select.setDeptIds(deptIds);
        }

        // 获取 belongGrids
        List<String> belongGrids = anchorPointMapper.codeByIds(select.getDeptIds());
        select.setBelongOrgGrid(belongGrids);

        // 创建搜索请求
        SearchRequest searchRequest = new SearchRequest("es_renhang_anchor_point");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 网格条件
        if (select.getGridChildCodes() != null && !select.getGridChildCodes().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belongGrid", select.getGridChildCodes()));
        }

        // 机构祖先条件
        if (select.getDeptIds() != null && !select.getDeptIds().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belongOrgCode", select.getBelongOrgGrid()));
        }

        // 经理条件
        if (select.getManagerCodes() != null && !select.getManagerCodes().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belongManagerCode", select.getManagerCodes()));
        }

        // 客户类型
        if (select.getTypeList() != null && !select.getTypeList().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("type", select.getTypeList()));
        }

        // 网格编码模糊匹配
        if (StringUtils.isNotEmpty(select.getGridCode())) {
            String wildcardValue = "*," + select.getGridCode() + ",*";
            boolQuery.filter(QueryBuilders.wildcardQuery("ancestorCodes", wildcardValue));
        }

        // 设置查询
        sourceBuilder.query(boolQuery);
        sourceBuilder.size(20000);
        searchRequest.source(sourceBuilder);

        // 执行查询并解析结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 解析结果
        SearchHits hits = response.getHits();
        List<AnchorPoint> anchorPoints = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            AnchorPoint anchorPoint = JSON.parseObject(hit.getSourceAsString(), AnchorPoint.class);
            anchorPoints.add(anchorPoint);
        }

        return anchorPoints;
    }

    @Override
    public List<AnchorPoint> queryGroup(CustomerSelect select) throws IOException {
        // 处理网格条件（若需要）
        if (StringUtils.isNotEmpty(select.getGridCode())) {
            List<PaAddrInfoType> gridList = paAddrInfoTypeService.getSelfAndChild(select.getGridCode());
            List<String> gridCodes = gridList.stream().map(PaAddrInfoType::getCode).collect(Collectors.toList());
            select.setGridChildCodes(gridCodes);
        }

        // 处理机构条件
        if (select.getOrgCodes() != null && !select.getOrgCodes().isEmpty()) {
            List<SysDept> orgCodes = sysDeptService.getSelfAndChild(select.getOrgCodes());
            List<String> orgList = orgCodes.stream().map(SysDept::getOrgNum).collect(Collectors.toList());
            select.setOrgCodes(orgList);
        }

        // 构建ES查询请求
        SearchRequest searchRequest = new SearchRequest("anchor_point_index");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 网格条件
        if (select.getGridChildCodes() != null && !select.getGridChildCodes().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belong_grid", select.getGridChildCodes()));
        }

        // 机构条件
        if (select.getOrgCodes() != null && !select.getOrgCodes().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belong_org_code", select.getOrgCodes()));
        }

        // 经理条件
        if (select.getManagerCodes() != null && !select.getManagerCodes().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("belong_manager_code", select.getManagerCodes()));
        }

        // 客群条件
        if (select.getGroupIdList() != null && !select.getGroupIdList().isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("custom_group_ids", select.getGroupIdList()));
        }

        sourceBuilder.query(boolQuery);
        sourceBuilder.size(20000); // 设置返回结果数量
        searchRequest.source(sourceBuilder);

        // 执行ES查询并转换结果
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 解析结果
        SearchHits hits = response.getHits();
        List<AnchorPoint> anchorPoints = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            AnchorPoint anchorPoint = JSON.parseObject(hit.getSourceAsString(), AnchorPoint.class);
            anchorPoints.add(anchorPoint);
        }
        return anchorPoints;
    }
    @Override
    public List<AnchorPoint> queryByCustomerType(CustomerSelect select) {
        // 构建 Elasticsearch 查询
        SearchRequest searchRequest = new SearchRequest("es_renhang_anchor_point");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 添加 dataScope 条件
        String dataScope = getDataScope(select);
        if (!StringUtils.isEmpty(dataScope)) {
            boolQuery.must(QueryBuilders.queryStringQuery(dataScope));
        }

        // 添加 gridChildCodes 条件
        if (select.getGridChildCodes() != null && !select.getGridChildCodes().isEmpty()) {
            boolQuery.must(QueryBuilders.termsQuery("belong_grid", select.getGridChildCodes()));
        }

        // 添加 deptIds 条件
        if (select.getDeptIds() != null && !select.getDeptIds().isEmpty()) {
            boolQuery.must(QueryBuilders.termsQuery("belong_org_code", select.getDeptIds()));
        }

        // 添加 managerCodes 条件
        if (select.getManagerCodes() != null && !select.getManagerCodes().isEmpty()) {
            boolQuery.must(QueryBuilders.termsQuery("belong_manager_code", select.getManagerCodes()));
        }

        // 添加 typeList 条件
        if (select.getTypeList() != null && !select.getTypeList().isEmpty()) {
            boolQuery.must(QueryBuilders.termsQuery("type", select.getTypeList()));
        }

        // 添加 gridCode 条件
        if (select.getGridCode() != null && !select.getGridCode().isEmpty()) {
            boolQuery.must(QueryBuilders.wildcardQuery("belong_grid", "*" + select.getGridCode() + "*"));
        }

        // 设置查询
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.size(10000); // 设置返回结果数量

        // 执行查询
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return parseSearchResponse(searchResponse);
        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch 查询失败", e);
        }
    }

    private String getDataScope(CustomerSelect select) {
        // 获取当前用户
        // SysUser user = getCurrentUser();

        // 获取数据范围 SQL
        // DataScope dataScope = DataScopeUtil.getDataScope(user);
        // return dataScope;
        return "";
    }

    private List<AnchorPoint> parseSearchResponse(SearchResponse searchResponse) {
        List<AnchorPoint> anchorPoints = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            AnchorPoint anchorPoint = JSON.parseObject(hit.getSourceAsString(), AnchorPoint.class);
            anchorPoints.add(anchorPoint);
        }
        return anchorPoints;
    }
    @Override
    public void syncDataToEs() {
        // 查询数据库中的数据
        List<AnchorPoint> anchorPoints = anchorPointMapper.selectEsAnchorPoints();

        // 批量插入到 Elasticsearch
        BulkRequest bulkRequest = new BulkRequest();
        for (AnchorPoint anchorPoint : anchorPoints) {
            bulkRequest.add(new IndexRequest("es_renhang_anchor_point")
                    .id(String.valueOf(anchorPoint.getId()))
                    .source(JSON.toJSONString(anchorPoint), XContentType.JSON));
        }

        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                throw new RuntimeException("同步数据到 Elasticsearch 失败: " + bulkResponse.buildFailureMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("同步数据到 Elasticsearch 失败", e);
        }
    }
}