package com.kx.system.service.es;

import com.kx.system.domain.req.GroupManageReq;

public interface EsCustMemberService {

    void removeMember(GroupManageReq req);


    /**
     * ES异步新增批量新增
     * @param totalNum
     * @param type
     */
    void insertBatchAsync(int totalNum, Integer type, Long groupId);

}
