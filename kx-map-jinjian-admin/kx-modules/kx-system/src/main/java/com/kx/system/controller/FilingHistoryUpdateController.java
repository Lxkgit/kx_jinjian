package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.filing.FilingHistoryUpdate;
import com.kx.system.service.FilingHistoryUpdateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
* <p>
*  前端控制器
* </p>
*
* @author LiuBoWu
* @since 2024-03-15
*/
@Api(tags = {"建档管理-档案记录管理"})
@RestController
@RequestMapping("filingHistory")
public class FilingHistoryUpdateController extends BaseController {
  @Autowired
  private FilingHistoryUpdateService filingHistoryUpdateService;

  /**
  * 列表查询
  */
  @GetMapping(value = "/list")
  @ApiOperation(value = "分页", notes = "传入")
  public TableDataInfo list(FilingHistoryUpdate filingHistoryUpdate) {
    startPage();
    List<FilingHistoryUpdate> list = filingHistoryUpdateService.list(filingHistoryUpdate);
    return getDataTable(list);
  }

  /**
  * 根据id查询
  */
  @ApiOperation(value = "根据id查询数据", notes = "传入主键")
  @GetMapping(value = "/getById")
  public AjaxResult getById(@RequestParam("id") Long id){
    return AjaxResult.success(filingHistoryUpdateService.getById(id));
  }

  /**
  * 新增/修改
  */
  @ApiOperation(value = "新增/修改数据", notes = "传入")
  @PostMapping(value = "/save")
  public AjaxResult save(@RequestBody FilingHistoryUpdate filingHistoryUpdate){
    filingHistoryUpdateService.saveFilingHistoryUpdate(filingHistoryUpdate);
    return AjaxResult.success(filingHistoryUpdate);
  }

  /**
  * 批量删除
  */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteByIds")
  public AjaxResult deleteByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(filingHistoryUpdateService.deleteByIds(ids));
  }

}
