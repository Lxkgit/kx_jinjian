package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.PaViewDeptMemberVo;
import com.kx.system.domain.param.ListPaViewDeptMembersParam;
import com.kx.system.service.IPaViewDeptMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Api(tags = {"小微视图-部门用户查询"})
@RestController
@RequestMapping("small/deptMember")
public class PaViewDeptController extends BaseController {

    @Resource
    private IPaViewDeptMemberService paViewDeptService;

    @ApiOperation(value = "查询部门机构树及机构下的用户")
    @GetMapping(value = "/listDeptMember")
    public AjaxResult listDeptMember(@RequestParam(value = "deptId", required = false) Long deptId) {
        PaViewDeptMemberVo paViewDeptMemberVo = paViewDeptService.listPaViewDeptMembers(deptId);
        return AjaxResult.success(paViewDeptMemberVo);
    }

}
