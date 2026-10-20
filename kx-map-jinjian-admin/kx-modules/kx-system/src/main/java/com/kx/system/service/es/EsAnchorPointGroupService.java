package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.req.GroupManageReq;

import java.util.List;

public interface EsAnchorPointGroupService {

    JSONObject esQueryGroupMini(CustomerSelect select, String indexName);

    Long remove(GroupManageReq req);

    void insertBatch(List<JSONObject> list);
}
