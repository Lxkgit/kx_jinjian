package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.req.GroupManageReq;

import java.util.List;

/**
 * @author ligen
 */
public interface EsFilingCustMemberService {

    JSONObject esQueryFilingMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum);
    Long remove(GroupManageReq req);
    void insertBatch(List<JSONObject> list);

}
