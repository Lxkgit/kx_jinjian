//package com.kx.system.controller;
//
//import com.kx.common.core.web.controller.BaseController;
//import com.kx.common.core.web.domain.AjaxResult;
//import com.kx.common.core.web.page.TableDataInfo;
//import com.kx.system.domain.task.TaskTypeParent;
//import com.kx.system.service.TaskInfoService;
//import com.kx.system.service.TaskTypeParentService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型主表 前端控制器
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Api(tags = {"任务类型"})
//@RestController
//@RequestMapping("/taskTypeParent")
//public class TaskTypeParentController extends BaseController {
//
//    // todo 任务类型
//
//    @Autowired
//    private TaskTypeParentService taskTypeParentService;
//
//    /**
//     * 列表查询
//     */
//    @GetMapping(value = "/list")
//    @ApiOperation(value = "任务类型分页")
//    public TableDataInfo list(TaskTypeParent taskTypeParent) {
//        startPage();
//        List<TaskTypeParent> list = taskTypeParentService.list(taskTypeParent);
//        return getDataTable(list);
//    }
//
//    /**
//     * 新增/修改
//     */
//    @ApiOperation(value = "新增任务类型")
//    @PostMapping(value = "/save")
//    public AjaxResult save(@RequestBody TaskTypeParent taskTypeParent) {
//        return AjaxResult.success(taskTypeParentService.save(taskTypeParent));
//    }
//
//    @ApiOperation(value = "编辑")
//    @PostMapping(value = "/edit")
//    public AjaxResult edit(@RequestBody TaskTypeParent taskTypeParent){
//        return AjaxResult.success(taskTypeParentService.edit(taskTypeParent));
//    }
//
//    /**
//     * 删除
//     */
//    @ApiOperation(value = "删除任务类型")
//    @PostMapping(value = "/deleteByIds")
//    public AjaxResult deleteByIds(@RequestParam("ids") String ids) {
//        return AjaxResult.success(taskTypeParentService.deleteByIds(ids));
//    }
//
//    /**
//     * 任务对象列表
//     */
//    @ApiOperation(value = "任务对象下拉")
//    @GetMapping(value = "/listTaskObject")
//    public AjaxResult listTaskObject() {
//        return AjaxResult.success(taskTypeParentService.listTaskObject());
//    }
//
//    /**
//     * 任务群组列表
//     */
//    @ApiOperation(value = "任务群组列表")
//    @GetMapping(value = "/listTaskGroup")
//    public AjaxResult listTaskGroup() {
//        return AjaxResult.success(taskTypeParentService.listTaskGroup());
//    }
//
//    /**
//     * 填写形式
//     */
//    @ApiOperation(value = "填写形式下拉")
//    @GetMapping(value = "/listFillForm")
//    public AjaxResult listFillForm() {
//        return AjaxResult.success(taskTypeParentService.listFillForm());
//    }
//
//    /**
//     * 任务优先级
//     */
//    @ApiOperation(value = "任务优先级")
//    @GetMapping(value = "/listPriority")
//    public AjaxResult listPriority() {
//        return AjaxResult.success(taskTypeParentService.listPriority());
//    }
//
//    /**
//     * 任务周期
//     */
//    @ApiOperation(value = "任务周期")
//    @GetMapping(value = "/listPeriod")
//    public AjaxResult listPeriod() {
//        return AjaxResult.success(taskTypeParentService.listPeriod());
//    }
//
//    @ApiOperation(value = "任务详情")
//    @GetMapping(value = "/getById")
//    public AjaxResult getById(Long id){
//        return AjaxResult.success(taskTypeParentService.getById(id));
//    }
//
//    @ApiOperation(value = "获取所有任务类型")
//    @GetMapping(value = "/selectAll")
//    public AjaxResult selectAll(){
//        return AjaxResult.success(taskTypeParentService.list(new TaskTypeParent()));
//    }
//
//    @ApiOperation(value = "获取有任务的任务类型")
//    @GetMapping(value = "/selectOwnAll")
//    public AjaxResult selectOwnAll(){
//        return AjaxResult.success(taskTypeParentService.selectOwnAll(new TaskTypeParent()));
//    }
//
//    @ApiOperation("任务列表中的任务类型")
//    @GetMapping("/listTaskType")
//    public AjaxResult listTaskType(String name){
//        return AjaxResult.success(taskTypeParentService.listTaskType(name));
//    }
//}
