package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.business.BusinessOpportunity;
import com.kx.system.domain.business.BusinessProductType;
import com.kx.system.service.BusinessOpportunityService;
import com.kx.system.service.BusinessProductTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;


@Api(tags = "商机管理")
@RestController
@RequestMapping("/business")
public class BusinessOpportunityController extends BaseController {
  @Autowired
  private BusinessOpportunityService businessOpportunityService;
  @Autowired
  private BusinessProductTypeService businessProductTypeService;

  /**
  * 列表查询
  */
  @GetMapping(value = "/list")
  @ApiOperation(value = "分页", notes = "传入")
  public TableDataInfo list(BusinessOpportunity businessOpportunity) {
    startPage();
    List<BusinessOpportunity> list = businessOpportunityService.list(businessOpportunity);
    return getDataTable(list);
  }

  /**
  * 根据id查询
  */
  @ApiOperation(value = "根据id查询数据", notes = "传入主键")
  @GetMapping(value = "/getById")
  public AjaxResult getById(@RequestParam("id") Long id){
    return AjaxResult.success(businessOpportunityService.getById(id));
  }

  /**
  * 新增/修改
  */
  @ApiOperation(value = "新增/修改数据", notes = "传入")
  @PostMapping(value = "/save")
  public AjaxResult save(@RequestBody BusinessOpportunity businessOpportunity){
    businessOpportunityService.saveBusinessOpportunity(businessOpportunity);
    return AjaxResult.success(businessOpportunity);
  }

  /**
  * 批量删除
  */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteByIds")
  public AjaxResult deleteByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(businessOpportunityService.deleteByIds(ids));
  }

  /**
   * 商机类型查询
   */
  @GetMapping(value = "/listType")
  @ApiOperation(value = "分页", notes = "传入")
  public TableDataInfo listType(BusinessProductType businessProductType) {
    startPage();
    List<BusinessProductType> list = businessProductTypeService.list(businessProductType);
    return getDataTable(list);
  }

  /**
   * 商机类型新增/修改
   */
  @ApiOperation(value = "新增/修改数据", notes = "传入")
  @PostMapping(value = "/saveType")
  public AjaxResult saveType(@RequestBody BusinessProductType businessProductType){
    businessProductTypeService.saveBusinessProductType(businessProductType);
    return AjaxResult.success(businessProductType);
  }

  /**
   * 商机类型删除
   */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteTypeByIds")
  public AjaxResult deleteTypeByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(businessProductTypeService.deleteByIds(ids));
  }
}
