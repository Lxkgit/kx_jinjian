package com.kx.system.controller;


import com.alibaba.fastjson.JSON;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.service.FilingHistoryUpdateService;
import com.kx.system.service.FilingMessageDetailService;
import com.kx.system.domain.filing.FilingMessageDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
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
@Api(tags = {"建档管理-档案客户管理"})
@RestController
@RequestMapping("filingMessage")
public class FilingMessageDetailController extends BaseController{
  @Autowired
  private FilingMessageDetailService filingMessageDetailService;
  @Autowired
  private FilingHistoryUpdateService filingHistoryUpdateService;

  /**
  * 列表查询
  */
  @GetMapping(value = "/list")
  @ApiOperation(value = "分页", notes = "传入")
  public TableDataInfo list(FilingMessageDetail filingMessageDetail) {
    startPage();
    List<FilingMessageDetail> list = filingMessageDetailService.list(filingMessageDetail);
    return getDataTable(list);
  }

  /**
  * 根据id查询
  */
  @ApiOperation(value = "根据id查询数据", notes = "传入主键")
  @GetMapping(value = "/getById")
  public AjaxResult getById(@RequestParam("id") Long id){
    return AjaxResult.success(filingMessageDetailService.getById(id));
  }

  /**
   * 根据客户id查询
   */
  @ApiOperation(value = "根据客户id查询", notes = "传入主键")
  @GetMapping(value = "/getByCustomerId")
  public AjaxResult getByCustomerId(@RequestParam("customerId") Long customerId,@RequestParam("modelId") Long modelId){
    return AjaxResult.success(filingMessageDetailService.getByCustomerId(customerId,modelId));
  }

//  /**
//  * 新增/修改
//  */
//  @ApiOperation(value = "新增/修改数据", notes = "传入")
//  @PostMapping(value = "/save")
//  public AjaxResult save(@RequestBody FilingMessageDetail filingMessageDetail){
//    String json = JSON.toJSONString(filingMessageDetail.getJsonMessage());
//    filingMessageDetail.setJsonMessage(json);
//    filingMessageDetailService.saveFilingMessageDetail(filingMessageDetail);
//    return AjaxResult.success(filingMessageDetail);
//  }

  /**
  * 批量删除
  */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/deleteByIds")
  public AjaxResult deleteByIds(@RequestParam("ids") String ids){
    return AjaxResult.success(filingMessageDetailService.deleteByIds(ids));
  }

  /**
   * 根据配置Map
   * 列表查询
   * 分页查询
   */
  @ApiOperation(value = "配置查询", notes = "传入Map")
  @PostMapping(value = "/listByMap")
  public TableDataInfo listByMap(@RequestBody HashMap<String, String> map){
    //获取权限
//        Boolean headOffice = taskInfoService.isHeadOffice();
    String managerCode= "";
    String networkCode= "";
//        if (!headOffice) {
//          UserInfoVo userInfoVo = taskInfoService.getUserInfo();
//          managerCode = userInfoVo.getManagerCode();
//        }
    //获取模板id
    String modelId = map.get("modelId");
    if (modelId==null || modelId.equals("")){
      throw new BusinessValidationException("没有开启的模板");
    }
    map.remove("modelId");
    //获取分页参数
    String pageSize = map.get("pageSize");
    map.remove("pageSize");
    String pageNum = map.get("pageNum");
    map.remove("pageNum");
    map.remove("");
    //分页查询
    startPages(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
    List<String> list =  filingMessageDetailService.listByMap(map,managerCode,modelId,networkCode);
    return  getDataTable(list);

  }


  /**
   * 批量删除
   */
  @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
  @PostMapping(value = "/getFilingHistory")
  public AjaxResult getFilingHistory(@RequestParam("identity") Long identity,@RequestParam("modelId") Long modelId){
    return AjaxResult.success(filingHistoryUpdateService.getByIdentityAndModelId(identity,modelId));
  }


}
