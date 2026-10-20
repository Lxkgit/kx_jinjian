//package com.kx.system.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//
//import com.kx.system.domain.vo.TaskBaseInfoVo;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.kx.common.log.annotation.Log;
//import com.kx.common.log.enums.BusinessType;
//import com.kx.system.domain.PaTask;
//import com.kx.system.service.IPaTaskService;
//import com.kx.common.core.web.controller.BaseController;
//import com.kx.common.core.web.domain.AjaxResult;
//import com.kx.common.core.utils.poi.ExcelUtil;
//import com.kx.common.core.web.page.TableDataInfo;
//
///**
// * 任务Controller
// *
// * @author kangxu
// * @date 2024-01-23
// */
//@RestController
//@RequestMapping("/business/paTask")
//@Api(tags = "任务管理")
//public class PaTaskController extends BaseController
//{
//    @Autowired
//    private IPaTaskService paTaskService;
//
//    /**
//     * 查询任务列表
//     */
//    @PostMapping("/list")
//    @ApiOperation("查询任务列表")
//    public TableDataInfo list(@RequestBody PaTask paTask)
//    {
//        List<TaskBaseInfoVo> list = paTaskService.selectPaTaskList(paTask);
//        return getDataTable(list);
//    }
//
//    @PostMapping("/listMobile")
//    @ApiOperation("移动端查询任务列表")
//    public TableDataInfo listMobile(@RequestBody PaTask paTask)
//    {
//        List<TaskBaseInfoVo> list = paTaskService.listMobile(paTask);
//        return getDataTable(list);
//    }
//
//    /**
//     * 导出任务列表
//     */
//    @Log(title = "任务", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ApiOperation("导出任务列表")
//    public void export(HttpServletResponse response, PaTask paTask)
//    {
//        List<TaskBaseInfoVo> taskBaseInfoVos = paTaskService.selectPaTaskList(paTask);
//        List<PaTask> list = new ArrayList<>();
//        taskBaseInfoVos.stream().forEach(taskBaseInfoVo -> {
//            PaTask task = new PaTask();
//            BeanUtils.copyProperties(taskBaseInfoVo,task);
//            list.add(task);
//        });
//        ExcelUtil<PaTask> util = new ExcelUtil<PaTask>(PaTask.class);
//        util.exportExcel(response, list, "任务数据");
//    }
//
//    /**
//     * 获取任务详细信息
//     */
//    @GetMapping(value = "/{id}")
//    @ApiOperation("获取任务详细信息")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        return success(paTaskService.selectPaTaskById(id));
//    }
//
//    /**
//     * 新增任务
//     */
//    @Log(title = "任务", businessType = BusinessType.INSERT)
//    @PostMapping("/add")
//    @ApiOperation("新增/编辑任务")
//    public AjaxResult add(@RequestBody PaTask paTask)
//    {
//        return toAjax(paTaskService.insertPaTask(paTask));
//    }
//
//    /**
//     * 修改任务
//     */
//    @ApiOperation("修改任务")
//    @Log(title = "任务", businessType = BusinessType.UPDATE)
//    @PostMapping("/edit")
//    public AjaxResult edit(@RequestBody PaTask paTask)
//    {
//        return toAjax(paTaskService.updatePaTask(paTask));
//    }
//
//    /**
//     * 删除任务
//     */
//    @Log(title = "任务", businessType = BusinessType.DELETE)
//	@PostMapping("/deleteByIds")
//    @ApiOperation("批量删除任务")
//    public AjaxResult remove(Long[] ids)
//    {
//        return toAjax(paTaskService.deletePaTaskByIds(ids));
//    }
//
//    /**
//     * 主任务状态下拉
//     */
//    @ApiOperation(value = "主任务状态下拉")
//    @GetMapping(value = "/listTaskStatus")
//    public AjaxResult listTaskStatus() {
//        return AjaxResult.success(paTaskService.listTaskStatus());
//    }
//
//    /**
//     * 子任务状态下拉
//     */
//    @ApiOperation(value = "子任务状态下拉")
//    @GetMapping(value = "/listChildTaskStatus")
//    public AjaxResult listChildTaskStatus() {
//        return AjaxResult.success(paTaskService.listChildTaskStatus());
//    }
//
//    @ApiOperation(value = "任务方式下拉")
//    @GetMapping(value = "/listTaskMethod")
//    public AjaxResult listTaskMethod() {
//        return AjaxResult.success(paTaskService.listTaskMethod());
//    }
//
//    @ApiOperation(value = "任务催办")
//    @GetMapping(value = "/taskUrge")
//    public AjaxResult taskUrge(Long[] ids) {
//        paTaskService.taskUrge(ids);
//        return AjaxResult.success("催办消息已发送");
//    }
//
//    @ApiOperation(value = "任务完成率排行统计")
//    @PostMapping(value = "/listTaskRank")
//    public AjaxResult listTaskRank(@RequestBody PaTask paTask) {
//        return AjaxResult.success(paTaskService.listTaskRank(paTask));
//    }
//
//    @ApiOperation(value = "任务名称模糊搜素")
//    @PostMapping(value = "/listPaTask")
//    public AjaxResult listPaTask(@RequestBody PaTask paTask) {
//        return AjaxResult.success(paTaskService.listPaTask(paTask));
//    }
//
//    @ApiOperation(value = "任务进度统计")
//    @PostMapping(value = "/listTaskProgress")
//    public AjaxResult listTaskProgress(@RequestBody PaTask paTask) {
//        return AjaxResult.success(paTaskService.listTaskProgress(paTask));
//    }
//
//    @ApiOperation(value = "当前任务的完成进度统计")
//    @GetMapping(value = "/getOwnCount")
//    public AjaxResult getOwnCount(Long id) {
//        return AjaxResult.success(paTaskService.getOwnCount(id));
//    }
//}
