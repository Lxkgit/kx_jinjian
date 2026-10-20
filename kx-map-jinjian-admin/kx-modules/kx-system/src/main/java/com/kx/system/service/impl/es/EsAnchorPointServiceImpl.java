package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.es.EsAnchorPointDto;
import com.kx.system.esUtil.EsAnchorPoint;
import com.kx.system.service.es.EsAnchorPointService;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author  ligen
 * 锚点  ES操作 业务层
 */
@Service
@Slf4j
public class EsAnchorPointServiceImpl implements EsAnchorPointService {


    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public JSONObject selectAnchorPointList(AnchorPoint anchorPoint, String indexName, Integer pageSize, Integer pageNum) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        EsAnchorPointDto anchorPointDomain = new EsAnchorPointDto();
        BeanUtils.copyBeanProperties(anchorPoint,anchorPointDomain);
        BoolQueryBuilder boolQueryBuilder = EsAnchorPoint.ceratSearchSourceBuilder(anchorPointDomain);
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

    @Override
    public void updateAnchorPointByCode(EsAnchorPointDto esAnchorPoint, String indexName) {
        //以id进行修改
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("belongGrid",esAnchorPoint.getBelongGrid()));
        //生成参数
        Map<String, Object> params = EsAnchorPoint.createEsAnchorPoint(esAnchorPoint);
        EsSearchUtil.esUpdateQuery(indexName,boolQueryBuilder,params,restHighLevelClient);

    }

    @Override
    public void updateAnchorPoint(EsAnchorPointDto esAnchorPoint, String indexName) {
        //以id进行修改
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("id",esAnchorPoint.getId()));
        //生成参数
        Map<String, Object> params = EsAnchorPoint.createEsAnchorPoint(esAnchorPoint);
        EsSearchUtil.esUpdateQuery(indexName,boolQueryBuilder,params,restHighLevelClient);

    }

    @Override
    public void insertAnchorPoint(String indexName, EsAnchorPointDto esAnchorPoint) {
        EsSearchUtil.asyncSingle(indexName, JSONObject.parseObject(JSONObject.toJSONString(esAnchorPoint)),restHighLevelClient);

    }

    @Override
    public JSONObject esQueryGroupMini(CustomerSelect select, String indexName) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = EsAnchorPoint.ceratQueryGroupMiniSourceBuilder(select);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.trackTotalHits(true);
        String[] includes = new String[]{"lon","lat"};
        String[] excludes = new String[]{
                "id", "customerId", "customerNm", "customerIdentity",
                "type", "anchorName", "anchorType", "addressDesc", "updateTime", "addressType",
                "belongGridName", "belongGrid", "areaCode",
                "belongOrgCode", "branchBank", "customerPhone",
                "creditManagerJobNumber", "creditManager",
                "gridManager", "ancestorsDeptIds",
                "ancestorCodes", "deptId", "userId"};

        FetchSourceContext sourceContext = new FetchSourceContext(true,includes,excludes);
        searchSourceBuilder.fetchSource(sourceContext);
        //排序必须指定   不然每页会随机展示数据
//       searchSourceBuilder.sort("id", SortOrder.DESC);
        searchSourceBuilder.size(5000);
        JSONObject jsonObject = EsSearchUtil.selectEs(indexName, searchSourceBuilder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public List<AnchorPoint> selectAnchorPointList(List<String> customerNmList , String type) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = EsAnchorPoint.ceratBuilderQueryByNmAndType(customerNmList, type);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.trackTotalHits(true);
        int pageSize = 1000;
        searchSourceBuilder.size(pageSize);
        JSONObject jsonObject = EsSearchUtil.selectEs("es_anchor_point", searchSourceBuilder,restHighLevelClient);
        List<AnchorPoint> anchorPoints = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), AnchorPoint.class);
        return anchorPoints;

    }


}
