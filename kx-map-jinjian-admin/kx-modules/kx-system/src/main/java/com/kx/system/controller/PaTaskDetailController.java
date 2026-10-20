//package com.kx.system.controller;
//
//import com.github.pagehelper.PageHelper;
//import com.kx.common.core.utils.poi.ExcelUtil;
//import com.kx.common.core.web.controller.BaseController;
//import com.kx.common.core.web.domain.AjaxResult;
//import com.kx.common.core.web.page.TableDataInfo;
//import com.kx.common.log.annotation.Log;
//import com.kx.common.log.enums.BusinessType;
//import com.kx.system.config.enums.TaskLevelEnum;
//import com.kx.system.config.enums.TaskStatusChildEnum;
//import com.kx.system.config.enums.TaskStatusEnum;
//import com.kx.system.domain.task.PaTaskDetail;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.service.IPaTaskDetailService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@RestController
//@RequestMapping("/paTaskDetail")
//@Api(tags = "任务详情")
//public class PaTaskDetailController extends BaseController {
//
//    // todo 任务
//
//    @Autowired
//    private IPaTaskDetailService paTaskDetailService;
//
//    @PostMapping("/listTaskClient")
//    @ApiOperation("任务客户名单分页")
//    public TableDataInfo listTaskClient(@RequestBody TaskDetailVO taskDetailVO){
//        startPage(taskDetailVO);
//        List<TaskDetailVO> list = paTaskDetailService.listTaskClient(taskDetailVO);
//        return getDataTable(list);
//    }
//
//    @PostMapping("/deleteByClientIds")
//    @ApiOperation("批量删除任务内客户")
//    public AjaxResult deleteByClientIds(@RequestParam("clientIds") List<Long> clientIds){
//        return toAjax(paTaskDetailService.deleteByClientIds(clientIds));
//    }
//
//    @PostMapping("/listDetail")
//    @ApiOperation("任务详情筛选分页")
//    public TableDataInfo listDetail(@RequestBody TaskDetailVO taskDetailVO){
//        startPage(taskDetailVO);
//        return getDataTable(paTaskDetailService.listDetail(taskDetailVO));
//    }
//
//    @GetMapping("/getDetailById")
//    @ApiOperation("获取任务规划详情")
//    public AjaxResult getDetailById(Integer id){
//        return AjaxResult.success(paTaskDetailService.getDetailById(id));
//    }
//
//    @PostMapping("/editTaskDetail")
//    @ApiOperation("编辑任务规划详情")
//    public AjaxResult editTaskDetail(@RequestBody TaskDetailVO taskDetailVO){
//        return AjaxResult.success(paTaskDetailService.editTaskDetail(taskDetailVO));
//    }
//
//    /**
//     * 导出任务列表
//     */
//    @Log(title = "任务", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ApiOperation("任务明细导出")
//    public void export(HttpServletResponse response,@RequestBody TaskDetailVO taskDetailVO)
//    {
//        List<TaskDetailVO> details = paTaskDetailService.export(taskDetailVO);
//        for (TaskDetailVO detailVO :details) {
//            detailVO.setMasterTaskStatusName(TaskStatusEnum.getNameByCode(detailVO.getMasterTaskStatus()));
//            detailVO.setChildTaskStatusName(TaskStatusChildEnum.getNameByCode(detailVO.getChildTaskStatus()));
//            detailVO.setTaskLevelName(TaskLevelEnum.getNameByCode(detailVO.getTaskLevel()));
//        }
//        ExcelUtil<TaskDetailVO> util = new ExcelUtil<>(TaskDetailVO.class);
//        util.exportExcel(response, details, "任务明细");
//    }
//
//    @PostMapping("/taskView")
//    @ApiOperation("任务视图锚点筛选")
//    public AjaxResult taskView(@RequestBody TaskDetailVO taskDetailVO){
//        return AjaxResult.success(paTaskDetailService.taskView(taskDetailVO));
//    }
//
//    @GetMapping("/taskTrack")
//    @ApiOperation("移动端首页任务足迹")
//    public AjaxResult taskTrack(){
//        return AjaxResult.success(paTaskDetailService.taskTrack());
//    }
//
//    @PostMapping("/listDetailInView")
//    @ApiOperation("任务统计下任务明细")
//    public TableDataInfo listDetailInView(@RequestBody TaskDetailVO taskDetailVO){
//        startPage(taskDetailVO);
//        return getDataTable(paTaskDetailService.listDetailInView(taskDetailVO));
//    }
//
//    @PostMapping("/getAnchorDetail")
//    @ApiOperation("任务锚点点击详情")
//    public TableDataInfo getById(@RequestBody TaskDetailVO taskDetailVO){
//        PageHelper.startPage(taskDetailVO.getPageNum(),taskDetailVO.getPageSize());
//        return getDataTable(paTaskDetailService.getAnchorDetail(taskDetailVO));
//    }
//}
