package com.kx.system.controller;


import cn.hutool.json.JSONObject;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.GridExport;
import com.kx.system.domain.grid.GridCruxInfo;
import com.kx.system.domain.grid.GridRatingInfo;
import com.kx.system.service.GridCruxInfoService;
import com.kx.system.service.GridMessageInfoService;
import com.kx.system.service.GridRatingInfoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.kx.system.domain.grid.GridMessageInfo2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 网格基础信息维护表 前端控制器
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Api(tags = {"网格基础信息维护表"})
@RestController
@RequestMapping("/gridMessage")
public class GridMessageInfoController extends BaseController {
  @Autowired
  private GridMessageInfoService gridMessageInfoService;
  @Autowired
  private GridCruxInfoService gridCruxInfoService;
  @Autowired
  private GridRatingInfoService gridRatingInfoService;

  /**
   * 列表查询
   */
  @PostMapping(value = "/list")
  @ApiOperation(value = "分页", notes = "传入网格基础信息维护表")
  public TableDataInfo list(@RequestBody GridMessageInfo2 gridMessageInfo) {
    startPage(gridMessageInfo);
    List<GridMessageInfo2> list = gridMessageInfoService.list(gridMessageInfo);
    return getDataTable(list);
  }

  /**
   * 根据id查询
   */
  @ApiOperation(value = "根据id查询数据", notes = "传入主键")
  @GetMapping(value = "/getById")
  public AjaxResult getById(@RequestParam("id") Long id){
    return AjaxResult.success(gridMessageInfoService.getById(id));
  }

  /**
   * 新增/修改
   */
  @ApiOperation(value = "新增/修改数据", notes = "传入网格基础信息维护表")
  @PostMapping(value = "/save")
  public AjaxResult save(@RequestBody GridMessageInfo2 gridMessageInfo){
    gridMessageInfoService.saveGridMessageInfo(gridMessageInfo);
    return AjaxResult.success(gridMessageInfo);
  }

  /**
   * 批量删除
   */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteByIds")
  public AjaxResult deleteByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(gridMessageInfoService.deleteByIds(ids));
  }

  /**
   * 根据网格编码查询，网格维护信息
   */
  @ApiOperation(value = "根据网格编码查询数据", notes = "传入网格编码")
  @GetMapping(value = "/getByGridCode")
  public AjaxResult getByGridCode(String gridCode){
    return AjaxResult.success(gridMessageInfoService.getByGridCode(gridCode));
  }


  /**
   * 查询其他网点
   */
  @ApiOperation(value = "根据网格编码查询数据", notes = "传入网格编码")
  @GetMapping(value = "/getGridCruxInfo")
  public AjaxResult getGridCruxInfo(String gridCode){
    GridCruxInfo param = new GridCruxInfo();
    param.setGridCode(gridCode);
    return AjaxResult.success(gridCruxInfoService.list(param));
  }


  /**
   * 根据网格编码查询，网格评级信息
   */
  @ApiOperation(value = "根据网格编码查询数据", notes = "传入网格编码")
  @GetMapping(value = "/getGridRatingInfo")
  public AjaxResult getGridRatingInfo(String gridCode){
    GridRatingInfo param = new GridRatingInfo();
    param.setGridCode(gridCode);
    return AjaxResult.success(gridRatingInfoService.list(param));
  }


  /**
   * 新增或修改网格关键人信息
   */
  @ApiOperation(value = "新增或修改网格关键信息",notes = "传入网格关键信息")
  @PostMapping(value = "/saveGridCruxInfo")
  public AjaxResult saveGridCruxInfo(@RequestBody GridCruxInfo gridCruxInfo){
    return AjaxResult.success(gridCruxInfoService.saveGridCruxInfo(gridCruxInfo));
  }

  /**
   * 新增或修改网格评级
   */
  @ApiOperation(value = "新增或修改网格评级信息",notes = "传入网格评级信息")
  @PostMapping("/saveGridRatingInfo")
  public AjaxResult saveGridRatingInfo(@RequestBody GridRatingInfo gridRatingInfo){
    return AjaxResult.success(gridRatingInfoService.saveGridRatingInfo(gridRatingInfo));
  }


  /**
   * 批量删除网格关键信息
   */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteGridCruxByIds")
  public AjaxResult deleteGridCruxByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(gridCruxInfoService.deleteByIds(ids));
  }


  /**
   * 批量删除网格评级信息
   */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteGridRatingByIds")
  public AjaxResult deleteGridRatingByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(gridRatingInfoService.deleteByIds(ids));
  }

  /**
   *     @Scheduled(cron = "0 0 1 * * ?")
   *     @PostConstruct
   *     同步网格基础信息
   */
  @Scheduled(cron = "0 0 2 * * ?")
  @GetMapping(value = "/synGridMsgInfo")
  public void gridMessageSynchroPaAddrInfo(){
    gridMessageInfoService.gridMessageSynchroPaAddrInfo(null);
  }



  @ApiOperation(value = "获取当前层级服务网点信息")
  @GetMapping(value = "/gridInfo")
  public TableDataInfo getGridInfo(@RequestParam("gridCode") String gridCode){
    return getDataTable(gridMessageInfoService.getGridInfo(gridCode));
  }


  @ApiOperation(value = "获取当前网格下所有网点")
  @GetMapping(value = "/gridInfoByGridCode")
  public AjaxResult getGridInfoByGridCode(@RequestParam("gridCode") String gridCode){
    return AjaxResult.success(gridMessageInfoService.getAllByGridCode(gridCode));
  }


  @PostMapping("/gridExport")
  @ApiOperation("网格信息导出")
  public AjaxResult anchorExport(HttpServletResponse response, @RequestBody GridMessageInfo2 gridExport) throws UnsupportedEncodingException {
    gridMessageInfoService.selectAllGrid(response,gridExport);
    return AjaxResult.success("导出成功");
  }

  /**
   * 列表查询
   */
  @PostMapping(value = "/pageList")
  @ApiOperation(value = "分页")
  public JSONObject pageLsit(@RequestBody GridMessageInfo2 gridMessageInfo) {
    JSONObject jsonObject = gridMessageInfoService.pageLsit(gridMessageInfo);
    return jsonObject;
  }
}
