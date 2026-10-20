package com.kx.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerParam;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.service.IAnchorPointService;
import com.kx.system.service.es.EsAnchorQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 锚点Controller
 *
 * @author kangxu
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/business/anchorPoint")
@Api(tags = "锚点管理")
public class AnchorPointController extends BaseController {

    @Resource
    private IAnchorPointService anchorPointService;

    @Resource
    private EsAnchorQueryService esAnchorQueryService;



    /**
     * 查询锚点列表
     */
    @PostMapping("/list")
    @ApiOperation("查询锚点列表")
    public AjaxResult list(@RequestBody AnchorPoint anchorPoint) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return anchorPointService.esAnchorPointList(anchorPoint,loginUser);
//        return getDataTable(list);
    }
//    @PostMapping("/list")
//    @ApiOperation("查询锚点列表")
//    public TableDataInfo list(@RequestBody AnchorPoint anchorPoint) {
//        List<AnchorPoint> list = anchorPointService.selectAnchorPointList(anchorPoint);
//        return getDataTable(list);
//    }

    /**
     * 导出锚点列表
     */
    @Log(title = "锚点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出锚点列表")
    public void export(HttpServletResponse response, AnchorPoint anchorPoint) {
        List<AnchorPoint> list = anchorPointService.selectAnchorPointList(anchorPoint);
        ExcelUtil<AnchorPoint> util = new ExcelUtil<AnchorPoint>(AnchorPoint.class);
        util.exportExcel(response, list, "锚点数据");
    }

    /**
     * 获取锚点详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取锚点详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(anchorPointService.selectAnchorPointById(id));
    }

    /**
     * 新增锚点
     */
    @Log(title = "锚点", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增锚点")
    public AjaxResult add(@RequestBody AnchorPoint anchorPoint) {
        return toAjax(anchorPointService.insertAnchorPoint(anchorPoint));
    }

    /**
     * 修改锚点
     */
    @Log(title = "锚点", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ApiOperation("修改锚点")
    public AjaxResult edit(@RequestBody AnchorPoint anchorPoint) {
        return toAjax(anchorPointService.updateAnchorPoint(anchorPoint));
    }

    /**
     * 批量修改锚点
     */
    @Log(title = "批量修改锚点", businessType = BusinessType.UPDATE)
    @PostMapping("/editBatch")
    @ApiOperation("批量修改锚点")
    public AjaxResult editBatch(@RequestBody List<AnchorPoint> anchorPointList) {
//        anchorPointService.updateBatchAnchorPoint(anchorPointList);
        return toAjax(anchorPointList.size());
    }

    /**
     * 删除锚点
     */
    @Log(title = "锚点", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    @ApiOperation("删除锚点")
    public AjaxResult delete(@RequestBody AnchorPoint anchorPoint) {
        return toAjax(anchorPointService.deleteAnchorPointById(anchorPoint.getId()));
    }

    @PostMapping("/selectCustomByAnchorId")
    @ApiOperation("根据锚点id或身份证获取360视图基本信息")
    public AjaxResult selectCustomByAnchorId(@RequestBody CustomInfoDTO customInfoDTO) {
        return anchorPointService.selectAnchorPointDetail(customInfoDTO);
    }

    @PostMapping("/selectCustomVisitByAnchorId")
    @ApiOperation("根据锚点id或身份证获取360视图客户旅程信息")
    public AjaxResult selectCustomVisitByAnchorId(@RequestBody CustomInfoDTO customInfoDTO) {
        return anchorPointService.selectAnchorPointVisit(customInfoDTO);
    }


    @PostMapping("/newQueryBySelect")
    @ApiOperation("小微视图筛选客户锚点")
    public AjaxResult query(@RequestBody CustomerSelect select) {
        if (StringUtils.isEmpty(select.getType())) {
            throw new BusinessValidationException("请选择筛选类型");
        }
        if (select.getType().equals("2")&& CollectionUtils.isNotEmpty(select.getGroupIdList())) {
            //客群筛选
            return AjaxResult.success(anchorPointService.queryGroup(select));
        } else {
            //客户筛选
            return AjaxResult.success(anchorPointService.queryByCustomerType(select));

        }
    }

    @PostMapping("/queryBySelect")
    @ApiOperation("小微视图筛选客户锚点")
    public AjaxResult Esquery(@RequestBody CustomerSelect select) throws IOException {
        if (StringUtils.isEmpty(select.getType())) {
            throw new BusinessValidationException("请选择筛选类型");
        }
        if (select.getType().equals("2")&& CollectionUtils.isNotEmpty(select.getGroupIdList())) {
            //客群筛选
            return AjaxResult.success(anchorPointService.queryGroup(select));
        } else {
            //客户筛选
            return AjaxResult.success(esAnchorQueryService.selectByCustomerType(select));

        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @PostMapping("/syncData")
    @ApiOperation("同步大屏视图锚点数据")
    public void syncDataToEs() {
        esAnchorQueryService.syncDataToEs();
    }

    @PostMapping("/queryBySelectMini")
    @ApiOperation("小微视图筛选热力图")
    public AjaxResult queryBySelectMini(@RequestBody CustomerSelect select) {
        if (StringUtils.isEmpty(select.getType())) {
            throw new BusinessValidationException("请选择筛选类型");
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (select.getType().equals("2")&& CollectionUtils.isNotEmpty(select.getGroupIdList())) {
            //客群筛选
            return AjaxResult.success(anchorPointService.queryGroupMini(select));
        } else {
            //客户筛选
            return AjaxResult.success(anchorPointService.queryByCustomerTypeMini(select));
        }
    }

    @PostMapping("/selectLabelByAnchorId")
    @ApiOperation("根据锚点id或身份证获取360视图客户标签")
    public AjaxResult selectLabelByAnchorId(@RequestBody CustomInfoDTO customInfoDTO) {
        return AjaxResult.success(anchorPointService.selectLabelByAnchorId(customInfoDTO));
    }

    @PostMapping("/selectCreditRecordByAnchorId")
    @ApiOperation("根据锚点id或身份证获取360视图授信记录")
    public AjaxResult selectCreditRecordByAnchorId(@RequestBody CustomInfoDTO customInfoDTO) {
        List<CreditRecord> creditRecords = anchorPointService.selectCreditRecordByAnchorId(customInfoDTO);
        creditRecords.forEach(a->{
            if (StringUtils.isNotEmpty(a.getManager())&& StringUtils.isNotEmpty(a.getManagerNum())){
                String name = ManagerStringUtil.managerNumSplitName(a.getManagerNum(), a.getManager());
                a.setManager(name);
            }
        });
        return AjaxResult.success(creditRecords);
    }


    @PostMapping("/queryCustomerByName")
    @ApiOperation("根据姓名模糊搜索客户信息")
    public AjaxResult queryCustomerByName(@RequestBody CustomerParam customerParam) {
        return AjaxResult.success(anchorPointService.queryCustomerByName(customerParam));
    }

    @PostMapping("/queryBySelect/business/Boundary")
    @ApiOperation("商户锚点获取-边界内")
    public AjaxResult queryBySelectAndBoundary(@RequestBody CustomerSelect select) {
        List<AnchorPoint> anchorPoints = anchorPointService.queryBusinessByBoundary(select);
        return AjaxResult.success(anchorPoints);
    }

    @PostMapping("/totalCount")
    @ApiOperation("小微视图筛选客户网点计数")
    public AjaxResult totalCount(@RequestParam("gridCode") String gridCode,
                                 @RequestParam(value = "groupId", required = false) Integer groupId ) {
            //筛选网点计数
            return AjaxResult.success(anchorPointService.getTotalCount(gridCode,groupId));
    }

    @GetMapping("/allAnchor")
    @ApiOperation("获取根据网点名称获取所有网点列表")
    public AjaxResult allAnchor(String anchorName) {
        //筛选网点计数
        return AjaxResult.success(anchorPointService.allAnchor(anchorName));
    }

    @GetMapping("/equipment")
    @ApiOperation("获取根据网点名称获取网点设备数量")
    public AjaxResult getEquipment(String anchorName) {
        return AjaxResult.success(anchorPointService.getEquipment(anchorName));
    }


}
