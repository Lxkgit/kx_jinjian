package com.kx.system.service;

import com.kx.system.domain.PaViewDeptMemberVo;
import com.kx.system.domain.param.ListPaViewDeptMembersParam;

/**
 * 小微视图-部门机构树查询 服务类
 */
public interface IPaViewDeptMemberService {
    PaViewDeptMemberVo listPaViewDeptMembers(Long deptId);
}
