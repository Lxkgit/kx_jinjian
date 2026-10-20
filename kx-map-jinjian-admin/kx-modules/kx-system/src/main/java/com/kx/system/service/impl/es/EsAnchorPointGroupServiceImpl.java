package com.kx.system.service.impl.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.esUtil.EsAnchorPointGroup;
import com.kx.system.service.es.EsAnchorPointGroupService;
import com.kx.system.util.EsSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author  ligen
 * 锚点客群Es  业务层
 */

@Service
@Slf4j
public class EsAnchorPointGroupServiceImpl implements EsAnchorPointGroupService {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    @Override
    public JSONObject esQueryGroupMini(CustomerSelect select, String indexName) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder =  EsAnchorPointGroup.ceratQueryGroupMiniSourceBuilder(select);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.trackTotalHits(true);

        String[] includes = new String[]{"lon","lat"};
        String[] excludes = new String[]{"id","customerId",
                "customType", "userId", "deptId", "ancestorsDeptIds",
                "belongManagerCode", "ancestorCodes", "customGroupId"};

        FetchSourceContext sourceContext = new FetchSourceContext(true,includes,excludes);
        searchSourceBuilder.fetchSource(sourceContext);
        //排序必须指定   不然每页会随机展示数据
        searchSourceBuilder.size(5000);
        JSONObject jsonObject = EsSearchUtil.selectEs(indexName, searchSourceBuilder,restHighLevelClient);
        return jsonObject;
    }

    @Override
    public Long remove(GroupManageReq req) {
        BoolQueryBuilder boolQueryBuilder = EsAnchorPointGroup.cerateDeleteBuilder(req);
        return EsSearchUtil.esDeleteData("es_anchor_point_group", boolQueryBuilder, restHighLevelClient);
    }

    @Override
    public void insertBatch(List<JSONObject> list) {
        EsSearchUtil.asyncSingleBeatch("es_anchor_point_group",list,restHighLevelClient);

    }


}
