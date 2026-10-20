package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.customer.CustomGroupDTO;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.CustomerGroupCategoryReq;
import com.kx.system.domain.dto.CustomerInfoDTO;
import com.kx.system.domain.req.CustomGroupPageReq;
import com.kx.system.domain.req.CustomGroupReq;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.service.CustomGroupService;
import com.kx.system.service.CustomerGroupCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customer/group")
@Api(tags = "客群管理")
public class CustomGroupController extends BaseController {

    @Resource
    private CustomerGroupCategoryService customerGroupCategoryService;

    @Resource
    private CustomGroupService customGroupService;

    @GetMapping("/category/get")
    @ApiOperation("获取群分类目录")
    public AjaxResult getCategory() {
        return customerGroupCategoryService.getCategory();
    }

    @PostMapping("/addOrEditCategory")
    @ApiOperation("客群分类新增、编辑")
    public AjaxResult addOrEditCategory(@RequestBody CustomerGroupCategoryReq req) {
        return customerGroupCategoryService.addOrEdit(req);
    }


    @GetMapping("/delCategory/{id}")
    @ApiOperation("删除客群分类")
    public AjaxResult delCategory(@PathVariable("id")Long id) {
        return customerGroupCategoryService.delCategory(id);
    }

    @PostMapping("/page")
    @ApiOperation("客群分页列表")
    public TableDataInfo page(@RequestBody CustomGroupPageReq req) {
        return customGroupService.queryPage(req);
    }

    @PostMapping("/listNotPage")
    @ApiOperation("客群列表不分页")
    public TableDataInfo listNotPage(@RequestBody CustomGroupPageReq req) {
        return customGroupService.listNotPage(req);
    }

    @PostMapping("/addOrEditGroup")
    @ApiOperation("客群管理-新增、编辑客群信息（含标签）")
    public AjaxResult addOrEditGroup(@RequestBody CustomGroupReq req) {
        return customGroupService.addOrEditGroup(req);
    }

    @GetMapping("/delGroup/{id}")
    @ApiOperation("删除客群")
    public AjaxResult delGroup(@PathVariable("id")Long id) {
        return customGroupService.delGroup(id);
    }

    @PostMapping("/delGroupList")
    @ApiOperation("批量删除客群")
    public AjaxResult delGroupList(@RequestBody GroupManageReq req) {
        return customGroupService.delGroupList(req);
    }

    @PostMapping("/manage/page")
    @ApiOperation("成员管理列表")
    public TableDataInfo managePage(@RequestBody GroupManageReq req) {
        if (null == req.getGroupId()) {
            return new TableDataInfo();
        }
        return customGroupService.manageCustomerPage(req);
    }

    @PostMapping("/removeMember")
    @ApiOperation("移除群成员")
    public AjaxResult removeMember(@RequestBody GroupManageReq req) {
        return customGroupService.removeMember(req);
    }

    @PostMapping("/addMember")
    @ApiOperation("新增群成员")
    public AjaxResult addMember(@RequestBody GroupManageReq req) {
        return customGroupService.addMember(req);
    }


    @GetMapping("/cascadeLabel/{id}")
    @ApiOperation("级联获取标签")
    public AjaxResult cascadeLabel(@PathVariable("id")Long id) {
        return customGroupService.cascadeLabel(id);
    }

    @GetMapping("/getChildCustomGroup")
    @ApiOperation("获取客群类型下子客群")
    public AjaxResult getChildCustomGroup(Integer customType) {
        return AjaxResult.success(customGroupService.getChildCustomGroup(customType));
    }

    @PostMapping("/saveCustomGroup")
    @ApiOperation("客户列表-保存客群（不含标签）")
    public AjaxResult saveCustomGroup(@RequestBody CustomGroupDTO customGroupDTO){
        if (customGroupDTO == null) {
            return AjaxResult.warn("参数为空");
        }
        return customGroupService.saveCustomGroup(customGroupDTO);
    }

    @PostMapping("/saveGroup")
    @ApiOperation("网点列表-保存客群（不含标签）")
    public AjaxResult saveGroup(@RequestBody PerformanceCustomerInfo customerInfo){
        if (customerInfo == null) {
            return AjaxResult.warn("参数为空");
        }
        return customGroupService.saveGroup(customerInfo);
    }

}
