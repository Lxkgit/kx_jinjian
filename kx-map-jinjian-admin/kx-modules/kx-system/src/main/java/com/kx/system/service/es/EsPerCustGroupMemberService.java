package com.kx.system.service.es;

import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.req.GroupManageReq;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ligen
 *  绩效客群 ES 操作
 */

@Service
public interface EsPerCustGroupMemberService {

    JSONObject esQueryPerMemberPage(GroupManageReq req, Integer pageSize, Integer pageNum);
    Long remove(GroupManageReq req);
    void insertBatch(List<JSONObject> list);



}
