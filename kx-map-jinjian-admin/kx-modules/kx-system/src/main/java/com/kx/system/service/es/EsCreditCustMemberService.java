package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.req.GroupManageReq;

import java.util.List;

/**
 * @author  ligen
 * 授信客群 成员查询
 */
public interface EsCreditCustMemberService {

    JSONObject esQueryCreditMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum);
    Long remove(GroupManageReq req);
    void insertBatch(List<JSONObject> list);
}
