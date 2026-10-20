package com.kx.system.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class EsSearchUtil {

    public static JSONObject selectEs(String indexName , SearchSourceBuilder searchSourceBuilder,RestHighLevelClient client){
        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray();
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indexName);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            hits.forEach(p-> {
                String sourceAsString = p.getSourceAsString();
                jsonArray.add(JSONObject.parseObject(sourceAsString));
            });

            jsonObject.put("total",hits.getTotalHits().value);
            jsonObject.put("data",jsonArray);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 单条异步新增
     * indexName 索引名称
     * jsonObject 数据
     */
    public static void asyncSingle(String indexName,JSONObject jsonObject,RestHighLevelClient client){

        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(jsonObject.toJSONString(), XContentType.JSON);
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        indexRequest.create(false);
        client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getFailed()>0){
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()){
                        log.error("将ID {} 的数据存入ES时存在失败的分片，原因为：｛｝",indexRequest.id(),failure.getCause());
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                log.error("{}:存储es时异常，数据信息为{}",indexRequest.id(),e);
            }
        });


    }
    //更新
    public static void esUpdateQuery(String indexName, BoolQueryBuilder boolQueryBuilder,Map<String, Object> params,RestHighLevelClient client){

        UpdateByQueryRequest request = new UpdateByQueryRequest();
        request.indices(indexName);
        request.setQuery(boolQueryBuilder);
        //使用Script更新数据 这里使用了painless脚本语言

        Set<String> keySet = params.keySet();

        StringBuffer sb = new StringBuffer();

        for (String key : keySet) {
            String idOrCode =  "ctx._source."+key+" = params."+key;
            sb.append(idOrCode+";");
        }

        request.setScript(new Script(ScriptType.INLINE,"painless",sb.toString(),params));

        try {
            client.updateByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //批量新增
    public static void asyncSingleBeatch(String indexName, List<JSONObject> list, RestHighLevelClient client){
        BulkRequest request = new BulkRequest();
        for (JSONObject jsonObject : list) {
            IndexRequest indexRequest = new IndexRequest(indexName);
            indexRequest.source(jsonObject.toJSONString(), XContentType.JSON);
//            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
            indexRequest.create(false);
            request.add(indexRequest);
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        }

        try {
            BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
            if (bulk.hasFailures()){
                log.error("批量新增失败 ：{}",bulk.buildFailureMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //数据删除
    public static Long esDeleteData(String indexName, BoolQueryBuilder boolQueryBuilder,RestHighLevelClient client){
        DeleteByQueryRequest request = new DeleteByQueryRequest();
        request.indices(indexName);
        request.setQuery(boolQueryBuilder);
        try {
            BulkByScrollResponse bulkByScrollResponse = client.deleteByQuery(request, RequestOptions.DEFAULT);
            //实际删除条数
            long deleted = bulkByScrollResponse.getDeleted();
            //符合条件的数据总量
            long total = bulkByScrollResponse.getTotal();
            log.info("符合删除的条件的 数据条数：{}",total);
            log.info("实际删除的数据条数 {}",deleted);
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //批量更新

    /**
     * 根据行政区划编码计算 LIKE 前缀
     * <ul>
     *   <li>省级（6位，后4位全0，如 330000）→ 取前2位（33）</li>
     *   <li>市级（6位，后2位为00，如 330100）→ 取前4位（3301）</li>
     *   <li>区县/街道/村（其余情况）→ 取完整编码</li>
     * </ul>
     */
    public static String resolvePrefix(String areaCode) {
        if (areaCode == null || areaCode.length() < 6) {
            return areaCode;
        }
        if (areaCode.length() == 6) {
            if (areaCode.endsWith("0000")) {
                return areaCode.substring(0, 2);
            }
            if (areaCode.endsWith("00")) {
                return areaCode.substring(0, 4);
            }
        }
        return areaCode;
    }

    /**
     * 根据行政区划编码计算 LIKE 前缀
     * <ul>
     *   <li>省级（6位，后4位全0，如 330000）→ 取前2位（33）</li>
     *   <li>市级（6位，后2位为00，如 330100）→ 取前4位（3301）</li>
     *   <li>区县/街道/村（其余情况）→ 取完整编码</li>
     * </ul>
     */
    public static Integer getLevel(String areaCode) {
        if (areaCode == null || areaCode.length() < 6) {
            return 0;
        }
        if (areaCode.length() == 6) {
            if (areaCode.endsWith("0000")) {
                return 1;
            }
            if (areaCode.endsWith("00")) {
                return 2;
            }
        }else{
            return 2;
        }
        return 2;
    }


}
