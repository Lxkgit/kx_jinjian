package com.kx.system.controller;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.domain.MaintainNetwork;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.common.AjaxResultModel;
import com.kx.system.domain.dto.GridBoundaryDTO;
import com.kx.system.domain.grid.AddrInfoDto;
import com.kx.system.domain.grid.AddrInfoParam;
import com.kx.system.domain.vo.UserInfoVo;
import com.kx.system.service.IPaManageAreaService;
//import com.kx.system.service.TaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网格维护-辖区管理Controller
 *
 * @author gary
 * @date 2021-12-07
 */


@RestController
@RequestMapping("/system/manageArea")
@Api(tags = "网格维护-辖区管理")
public class PaManageAreaController extends BaseController {

    @Autowired
    private IPaManageAreaService paManageAreaService;

//    @Autowired
//    private TaskInfoService taskInfoService;

    /**
     * 待维护行政村/社区
     */
    @GetMapping("/unMaintainedArea")
    @ApiOperation("待维护行政村/社区")
    public AjaxResult unMaintainedArea() {
        return paManageAreaService.unMaintainedArea();
    }

    /**
     * 网点所辖区域列表
     */
    @GetMapping("/manageAreaList")
    @ApiOperation("网点所辖区域列表")
    public TableDataInfo manageAreaList(@RequestParam(required = false) String networkName,
                                        @RequestParam(required = false) String villageName) {
        startPage();
        List<MaintainNetwork> list = paManageAreaService.manageAreaList(networkName, villageName);
        return getDataTable(list);
    }

    /**
     * 行政区分配
     */
    @Log(title = "辖区管理", businessType = BusinessType.UPDATE)
    @PostMapping("areaDistribution")
    @ApiOperation("行政区分配")
    public AjaxResult areaDistribution(@RequestBody PaAddrInfo paAddrInfo) {
        return toAjax(paManageAreaService.areaDistribution(paAddrInfo));
    }

    /**
     * 删除辖区
     */
    @Log(title = "辖区管理", businessType = BusinessType.DELETE)
    @PostMapping("deleteAreaDistribution")
    @ApiOperation("删除辖区")
    public AjaxResult deleteAreaDistribution(@RequestBody PaAddrInfo paAddrInfo) {
        return toAjax(paManageAreaService.deleteAreaDistribution(paAddrInfo));
    }

    /**
     * 村网格列表查询
     */
    @PostMapping("listAreaGrid")
    @ApiOperation("村网格列表查询")
    public TableDataInfo listAreaGrid(@RequestBody AddrInfoParam addrInfoParam) {
//        if (StringUtils.isNotEmpty(addrInfoParam.getNetworkId())) {
//            addrInfoParam.setQueryList(taskInfoService.getQueryListByNetworkIds(addrInfoParam.getNetworkId()));
//        }
//        Boolean headOffice = taskInfoService.isHeadOffice();
//        if (!headOffice) {
//            UserInfoVo userInfoVo = taskInfoService.getUserInfo();
//            addrInfoParam.setUserList(userInfoVo.getNetworkLCodeList());
//        }
        startPage(addrInfoParam);
        List<AddrInfoDto> list = paManageAreaService.listAreaGrid(addrInfoParam);
        return getDataTable(list);
    }

    @ApiOperation(value = "获取网格员信息")
    @PostMapping(value = "/getAreaManagers")
    public AjaxResult getAreaManagers(@RequestBody AddrInfoParam addrInfoParam) {
//        if (StringUtils.isNotEmpty(addrInfoParam.getNetworkId())) {
//            addrInfoParam.setQueryList(taskInfoService.getQueryListByNetworkIds(addrInfoParam.getNetworkId()));
//        }
//        Boolean headOffice = taskInfoService.isHeadOffice();
//        if (!headOffice) {
//            UserInfoVo userInfoVo = taskInfoService.getUserInfo();
//            addrInfoParam.setUserList(userInfoVo.getNetworkLCodeList());
//        }
        return AjaxResult.success(paManageAreaService.getAreaManagers(addrInfoParam));
    }

    /**
     * 编辑三级网格行政分配
     */
    @PostMapping("editAreaDistribution")
    @ApiOperation("编辑三级网格行政分配")
    public AjaxResult editAreaDistribution(@RequestBody AddrInfoParam addrInfoParam) {
        return toAjax(paManageAreaService.editAreaDistribution(addrInfoParam));
    }

    /**
     * 编辑四级网格
     */
    @PostMapping("editFourAreaDistribution")
    @ApiOperation("编辑四级网格")
    public AjaxResult editFourAreaDistribution(@RequestBody AddrInfoParam addrInfoParam) {
        return toAjax(paManageAreaService.editFourAreaDistribution(addrInfoParam));
    }

    /**
     * 删除四级网格
     */
    @PostMapping("deleteFourGrid")
    @ApiOperation("删除四级网格")
    public AjaxResult deleteFourGrid(@RequestBody AddrInfoParam addrInfoParam) {
        return toAjax(paManageAreaService.deleteFourGrid(addrInfoParam));
    }

    /**
     * 新增四级网格
     */
    @PostMapping("addFourGrid")
    @ApiOperation("新增四级网格")
    public AjaxResult addFourGrid(@RequestBody AddrInfoParam addrInfoParam) {
        return toAjax(paManageAreaService.addFourGrid(addrInfoParam));
    }

    /**
     * 获取四级网格集
     */
    @GetMapping("getFourGridList")
    @ApiOperation("获取四级网格集")
    public AjaxResult getFourGridList(String addrCode) {
        return AjaxResult.success(paManageAreaService.getFourGridList(addrCode));
    }

    @GetMapping("getSonGridBoundary")
    @ApiOperation("前台 - 获取下沉子网格")
    public AjaxResultModel<List<GridBoundaryDTO>> getSonGridBoundary(String addrCode) {
        return AjaxResultModel.success(paManageAreaService.getSonGridBoundary(addrCode));
    }

    @GetMapping("getGridBoundary")
    @ApiOperation("前台 - 获取网格边界")
    public AjaxResultModel<GridBoundaryDTO> getGridBoundary(String addrCode) {
        return AjaxResultModel.success(paManageAreaService.getGridBoundary(addrCode));
    }

    /**
     * 合并四级网格
     */
    @PostMapping("mergeFourGrid")
    @ApiOperation("合并四级网格")
    public AjaxResult mergeFourGrid(@RequestBody AddrInfoParam addrInfoParam) {
        return toAjax(paManageAreaService.mergeFourGrid(addrInfoParam));
    }

    @GetMapping("/listAddrTree")
    @ApiOperation("获取网格树状图")
    public AjaxResult listAddrTree() {


        /**
         * poc 或 演示  特殊处理
         */

        return AjaxResult.success(paManageAreaService.listAddrTreeWeight());


//        return AjaxResult.success(paManageAreaService.listAddrTree());
    }

    @GetMapping("/listAddrChildren")
    @ApiOperation("逐级获取行政区划")
    public AjaxResult listAddrChildren(@RequestParam(required = false) String parentCode) {
        return AjaxResult.success(paManageAreaService.listAddrChildren(parentCode));
    }
}
