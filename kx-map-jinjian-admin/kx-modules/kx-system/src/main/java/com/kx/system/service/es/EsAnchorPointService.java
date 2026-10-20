package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.es.EsAnchorPointDto;

import java.util.List;

public interface EsAnchorPointService {


    JSONObject selectAnchorPointList(AnchorPoint anchorPoint, String indexName, Integer pageSize, Integer pageNum);
    void updateAnchorPointByCode(EsAnchorPointDto esAnchorPoint, String indexName);
    void updateAnchorPoint(EsAnchorPointDto esAnchorPoint, String indexName);
    void insertAnchorPoint(String indexName, EsAnchorPointDto esAnchorPoint);
    JSONObject esQueryGroupMini(CustomerSelect select, String indexName);

    List<AnchorPoint> selectAnchorPointList(List<String> customerNmList , String type);


}
