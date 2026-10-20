package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.*;
import com.kx.system.service.IPaWorkBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 工作看板Controller
 *
 * @author kangxu
 * @date 2024-01-29
 */
@RestController
@RequestMapping("/business/workBoard")
@Api(tags = "工作看板")
public class PaWorkBoardController extends BaseController {

    @Autowired
    private IPaWorkBoardService paWorkBoardService;

    /**
     * 业务导航列表
     */
    @PostMapping("/businessNavigation")
    @ApiOperation("业务导航列表")
    public AjaxResult businessNavigation() {
        return AjaxResult.success(paWorkBoardService.businessNavigation());
    }

    /**
     * 数据概览
     */
    @PostMapping("/dataOverview")
    @ApiOperation("数据概览")
    public AjaxResult dataOverview() {
        return AjaxResult.success(paWorkBoardService.dataOverview());
    }

    /**
     * 本周日程
     */
    @PostMapping("/weekSchedule")
    @ApiOperation("本周日程")
    public AjaxResult weekSchedule(@RequestBody PaSchedule paSchedule) {
        return AjaxResult.success(paWorkBoardService.weekSchedule(paSchedule));
    }

    /**
     * 待办事项
     */
    @PostMapping("/toDoList")
    @ApiOperation("待办事项")
    public TableDataInfo toDoList() {
        startPage();
        List<PaBacklog> list = paWorkBoardService.toDoList();
        return getDataTable(list);
    }

    /**
     * 工作足迹概览
     */
    @GetMapping("/workTrackOverview")
    @ApiOperation("工作足迹概览")
    public AjaxResult workTrackOverview() {
        return AjaxResult.success(paWorkBoardService.workTrackOverview());
    }

    /**
     * 工作足迹列表
     */
    @PostMapping("/workTrackList")
    @ApiOperation("工作足迹列表")
    public TableDataInfo workTrackList(@RequestBody PaTask paTask) {
        startPage(paTask);
        List<TaskOverviewEntity> list = paWorkBoardService.workTrackList(paTask);
        return getDataTable(list);
    }

}
