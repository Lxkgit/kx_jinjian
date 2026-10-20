package com.kx.system.controller;

import com.kx.common.core.domain.system.grid.*;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.service.GridInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 网格信息详情表
 * @Author lxk
 * @CreateTime 2025-07-16
 */

@RestController
@RequestMapping("/grid/info")
public class GridInfoController extends BaseController {

    @Resource
    private GridInfoService gridInfoService;

    /**
     * 网格信息数据统计
     *
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("网格信息数据统计")
    public AjaxResult getGridInfoStatistics() {
        return AjaxResult.success();
    }

    /**
     * 网格信息数据统计明细列表
     *
     * @return
     */
    @GetMapping("/statistics/list")
    @ApiOperation("网格信息数据统计明细列表")
    public AjaxResult getGridInfoStatisticsList() {
        return AjaxResult.success();
    }

    /**
     * 网格信息数据统计明细列表数据导出
     *
     * @return
     */
    @GetMapping("/statistics/list/export")
    @ApiOperation("网格信息数据统计明细列表数据导出")
    public AjaxResult exportGridInfoStatisticsList() {
        return AjaxResult.success();
    }

    /**
     * 网格信息列表
     *
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("网格信息列表")
    public TableDataInfo getGridInfoList(@RequestBody GridInfo gridInfo) {
        startPage();
        List<GridInfo> gridInfoList = gridInfoService.getGridInfoList(gridInfo);
        return getDataTable(gridInfoList);
    }

    /**
     * 网格信息导入
     */
    @PostMapping("/import")
    @ApiOperation("网格信息导入")
    public void importGridInfo() {

    }

    /**
     * 网格信息导出
     */
    @PostMapping("/export")
    @ApiOperation("网格信息导出")
    public void exportGridInfo() {

    }

    /**
     * 获取网格详细信息（网格基础信息）
     *
     * @param id
     */
    @GetMapping("/select/{id}")
    @ApiOperation("获取网格详细信息（网格基础信息）")
    public AjaxResult getGridInfoById(@PathVariable("id") Long id) {
        GridInfo gridInfo = gridInfoService.getGridInfoById(id);
        return AjaxResult.success(gridInfo);
    }

    /**
     * 网格详细信息修改
     */
    @PostMapping("/update")
    @ApiOperation("网格详细信息修改")
    public AjaxResult updateGridInfo(@RequestBody GridInfo gridInfo) {
        gridInfoService.updateGridInfo(gridInfo);
        return AjaxResult.success();
    }

    /**
     * 设置网格责任网点
     */
    @PostMapping("/branch")
    @ApiOperation("设置网格责任网点")
    public AjaxResult updateGridInfoBranch(@RequestBody GridInfo gridInfo) {
        gridInfoService.updateGridInfoBranch(gridInfo);
        return AjaxResult.success();
    }

    /**
     * 新增网格重点监管对象
     */
    @PostMapping("/ManageObjects/insert")
    public AjaxResult insertManageObjects(@RequestBody GridInfoManageObjects gridInfoManageObjects) {
        gridInfoService.insertManageObjects(gridInfoManageObjects);
        return AjaxResult.success();
    }

    /**
     * 删除网格重点监管对象
     *
     * @param id
     */
    @DeleteMapping("/ManageObjects/delete/{id}")
    public AjaxResult deleteManageObjects(@PathVariable("id") Long id) {
        gridInfoService.deleteManageObjects(id);
        return AjaxResult.success();
    }

    /**
     * 修改网格重点监管对象
     */
    @PostMapping("/ManageObjects/update")
    public AjaxResult updateManageObjects(@RequestBody GridInfoManageObjects gridInfoManageObjects) {
        gridInfoService.updateManageObjects(gridInfoManageObjects);
        return AjaxResult.success();
    }

    /**
     * 根据网格编码获取网格重点监管对象
     *
     * @param gridInfoManageObjects
     */
    @GetMapping("/ManageObjects/select")
    public AjaxResult selectGridInfoManageObjectsList(GridInfoManageObjects gridInfoManageObjects) {
        return AjaxResult.success(gridInfoService.selectGridInfoManageObjectsList(gridInfoManageObjects));
    }

    /**
     * 新增社会网格员
     */
    @PostMapping("/community/supervisor/insert")
    public AjaxResult insertCommunitySupervisor(@RequestBody GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        gridInfoService.insertCommunitySupervisor(gridInfoCommunitySupervisor);
        return AjaxResult.success();
    }

    /**
     * 删除社会网格员
     *
     * @param id
     */
    @DeleteMapping("/community/supervisor/delete/{id}")
    public AjaxResult deleteCommunitySupervisor(@PathVariable("id") Long id) {
        gridInfoService.deleteCommunitySupervisor(id);
        return AjaxResult.success();
    }

    /**
     * 修改社会网格员
     */
    @PostMapping("/community/supervisor/update")
    public AjaxResult updateCommunitySupervisor(@RequestBody GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        gridInfoService.updateCommunitySupervisor(gridInfoCommunitySupervisor);
        return AjaxResult.success();
    }

    /**
     * 根据网格编码查询社会网格员
     *
     * @param gridInfoCommunitySupervisor
     */
    @GetMapping("/community/supervisor/select")
    public AjaxResult selectGridInfoCommunitySupervisorList(GridInfoCommunitySupervisor gridInfoCommunitySupervisor) {
        return AjaxResult.success(gridInfoService.selectGridInfoCommunitySupervisorList(gridInfoCommunitySupervisor));
    }

    /**
     * 新增基层组织
     */
    @PostMapping("/grassrootsInfo/insert")
    public AjaxResult insertGrassrootsInfo(@RequestBody GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        gridInfoService.insertGrassrootsInfo(gridInfoGrassrootsInfo);
        return AjaxResult.success();
    }

    /**
     * 删除基层组织
     *
     * @param id
     */
    @DeleteMapping("/grassrootsInfo/delete/{id}")
    public AjaxResult deleteGrassrootsInfo(@PathVariable("id") Long id) {
        gridInfoService.deleteGrassrootsInfo(id);
        return AjaxResult.success();
    }

    /**
     * 修改基层组织
     */
    @PostMapping("/grassrootsInfo/update")
    public AjaxResult updateGrassrootsInfo(@RequestBody GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        gridInfoService.updateGrassrootsInfo(gridInfoGrassrootsInfo);
        return AjaxResult.success();
    }

    /**
     * 根据网格编码查询基层组织
     *
     * @param gridInfoGrassrootsInfo
     */
    @GetMapping("/grassrootsInfo/select")
    public AjaxResult getGrassrootsInfo(@RequestBody GridInfoGrassrootsInfo gridInfoGrassrootsInfo) {
        return AjaxResult.success(gridInfoService.selectGridInfoGrassrootsInfoList(gridInfoGrassrootsInfo));
    }

    /**
     * 新增派出所信息
     */
    @PostMapping("/police/insert")
    public AjaxResult insertGridInfoPolice(@RequestBody GridInfoPolice gridInfoPolice) {
        gridInfoService.insertGridInfoPolice(gridInfoPolice);
        return AjaxResult.success();
    }

    /**
     * 删除派出所信息
     *
     * @param id
     */
    @DeleteMapping("/police/delete/{id}")
    public AjaxResult deleteGridInfoPoliceById(@PathVariable("id") Long id) {
        gridInfoService.deleteGridInfoPoliceById(id);
        return AjaxResult.success();
    }

    /**
     * 修改派出所信息
     */
    @PostMapping("/police/update")
    public AjaxResult updateGridInfoPolice(@RequestBody GridInfoPolice gridInfoPolice) {
        gridInfoService.updateGridInfoPolice(gridInfoPolice);
        return AjaxResult.success();
    }

    /**
     * 根据网格编码查询派出所信息
     */
    @GetMapping("/police/select/{gridCode}")
    public AjaxResult selectGridInfoPoliceList(GridInfoPolice gridInfoPolice) {
        return AjaxResult.success(gridInfoService.selectGridInfoPoliceList(gridInfoPolice));
    }


}
