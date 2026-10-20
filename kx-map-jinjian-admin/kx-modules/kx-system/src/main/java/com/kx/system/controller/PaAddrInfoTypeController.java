package com.kx.system.controller;

import com.github.pagehelper.Page;
import com.kx.common.core.domain.R;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataBody;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.GridInfoParam;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.param.SearchAddrInfoUserParam;
import com.kx.system.service.IPaAddrInfoTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kx.system.domain.vo.AreaSplitPointVO;

import java.util.List;

/**
 * 网格维护-网格管理Controller
 *
 * @author gary
 * @date 2021-12-07
 */


@RestController
@RequestMapping("/business/addrInfo")
@Api(tags = "网格管理-网格管理")
public class PaAddrInfoTypeController extends BaseController {

    @Autowired
    private IPaAddrInfoTypeService paAddrInfoService;

    /**
     * 初始化网格体系下的网格
     */
    @Log(title = "初始化网格体系下的网格", businessType = BusinessType.INSERT)
    @PostMapping("/initAddrInfoType")
    @ApiOperation("初始化网格体系下的网格")
    public AjaxResult initAddrInfoType(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.initAddrInfoType(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 获取5级行政区划
     */
    @Log(title = "获取所有行政区划", businessType = BusinessType.INSERT)
    @PostMapping("/getAddrInfoTypeBottom")
    @ApiOperation("获取所有行政区划")
    public TableDataInfo getAddrInfoTypeBottom(@RequestBody PaAddrInfoType paAddrInfoType) {
        startPage(paAddrInfoType);
        List<PaAddrInfoType> list = paAddrInfoService.getAddrInfoTypeBottom(paAddrInfoType);
        //转为前端拼接
//        list.forEach(a->{
//            if (StringUtils.isNotEmpty(a.getUserName())){
//                String userName = ManagerStringUtil.managerNumSplitName(a.getUserManagerNum(), a.getUserName());
//                a.setUserName(userName);
//            }
//            if (StringUtils.isNotEmpty(a.getSecondUserName())) {
//                String secondUserName = ManagerStringUtil.managerNumSplitName(a.getSecondManagerNum(), a.getSecondUserName());
//                a.setSecondUserName(secondUserName);
//            }
//        });

        return getDataTable(list);
    }

    /**
     * 获取网格下级数据-特色网格
     */
    @Log(title = "获取网格下级数据", businessType = BusinessType.INSERT)
    @PostMapping("/getPaAddrInfoTypeList")
    @ApiOperation("获取网格下级数据")
    public AjaxResult getPaAddrInfoTypeList(@RequestBody PaAddrInfoType paAddrInfoType) {
        return AjaxResult.success(paAddrInfoService.getPaAddrInfoTypeLists(paAddrInfoType));
    }

    /**
     * 获取网格数据-特色网格
     */
    @Log(title = "获取网格数据", businessType = BusinessType.INSERT)
    @PostMapping("/getPaAddrInfoType")
    @ApiOperation("获取网格数据")
    public AjaxResult getPaAddrInfoType(@RequestBody PaAddrInfoType paAddrInfoType) {
        return AjaxResult.success(paAddrInfoService.getPaAddrInfoType(paAddrInfoType));
    }

    /**
     * 获取网格下级数据-基础网格
     */
    @Log(title = "获取网格下级数据", businessType = BusinessType.INSERT)
    @PostMapping("/getPaAddrInfoList")
    @ApiOperation("获取网格下级数据")
    public AjaxResult getPaAddrInfoList(@RequestBody PaAddrInfo paAddrInfo) {
        return AjaxResult.success(paAddrInfoService.getPaAddrInfoList(paAddrInfo));
    }

    /**
     * 边界调整
     */
    @Log(title = "边界调整", businessType = BusinessType.INSERT)
    @PostMapping("/update")
    @ApiOperation("边界调整")
    public AjaxResult update(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.update(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 边界批量调整
     */
    @Log(title = "边界批量调整", businessType = BusinessType.UPDATE)
    @PostMapping("/updateBatch")
    @ApiOperation("边界批量调整")
    public AjaxResult updateBatch(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
        paAddrInfoService.updateBatch(paAddrInfoTypeList);
        return AjaxResult.success();
    }

    /**
     * 修改网格员
     */
    @Log(title = "修改网格员", businessType = BusinessType.UPDATE)
    @PostMapping("dateGridUser")
    @ApiOperation("修改网格员")
    public AjaxResult updateGridUser(@RequestBody PaAddrInfoType paAddrInfoType) {
        return paAddrInfoService.updateGridUser(paAddrInfoType);
    }

    /**
     * 辖区分配
     */
    // @Log(title = "批量辖区分配", businessType = BusinessType.UPDATE)
    // //@PostMapping("/updateGridDept")
    // @ApiOperation("批量辖区分配")
    // public AjaxResult updateGridDept(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
    //     paAddrInfoService.updateGridDept(paAddrInfoTypeList);
    //     return AjaxResult.success();
    // }
    @Log(title = "批量辖区分配", businessType = BusinessType.UPDATE)
    @PostMapping("/updateGridDept")
    @ApiOperation("批量辖区分配")
    public AjaxResult updateGridDepts(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
        paAddrInfoService.updateGridDepts(paAddrInfoTypeList);
        return AjaxResult.success();
    }

    /**
     * 新增网格
     */
    @Log(title = "新增网格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增网格")
    public AjaxResult add(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.add(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 删除网格
     */
    @Log(title = "删除网格", businessType = BusinessType.INSERT)
    @PostMapping("/delete")
    @ApiOperation("删除网格")
    public AjaxResult delete(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.delete(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 新建子网格
     */
    @Log(title = "新建子网格", businessType = BusinessType.INSERT)
    @PostMapping("/addChild")
    @ApiOperation("新建子网格")
    public AjaxResult addChild(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
        paAddrInfoService.addChild(paAddrInfoTypeList);
        return AjaxResult.success();
    }

    /**
     * 拆分网格
     */
    @Log(title = "拆分网格", businessType = BusinessType.INSERT)
    @PostMapping("/splitGrid")
    @ApiOperation("拆分网格")
    public AjaxResult splitGrid(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
        paAddrInfoService.splitGrid(paAddrInfoTypeList);
        return AjaxResult.success();
    }

    /**
     * 切分网格
     */
    @Log(title = "切分网格", businessType = BusinessType.INSERT)
    @PostMapping("/cutGrid")
    @ApiOperation("切分网格")
    public AjaxResult cutGrid(@RequestBody List<PaAddrInfoType> paAddrInfoTypeList) {
        paAddrInfoService.cutGrid(paAddrInfoTypeList);
        return AjaxResult.success();
    }

    /**
     * 合并子网格
     */
    @Log(title = "合并子网格", businessType = BusinessType.INSERT)
    @PostMapping("/mergeChildGrid")
    @ApiOperation("合并子网格")
    public AjaxResult mergeChildGrid(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.mergeChildGrid(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 清空子网格
     */
    @Log(title = "清空子网格", businessType = BusinessType.INSERT)
    @PostMapping("/cleanChildGrid")
    @ApiOperation("清空子网格")
    public AjaxResult cleanChildGrid(@RequestBody PaAddrInfoType paAddrInfoType) {
        paAddrInfoService.cleanChildGrid(paAddrInfoType);
        return AjaxResult.success();
    }

    /**
     * 根据网格编码获取边界数据
     *
     * @param areaCode
     * @param jurisdiction
     * @return
     */
    @GetMapping("/getCoordinatesByCode")
    @ApiOperation("根据网格编码获取边界数据")
    public AjaxResult getCoordinatesByCode(@RequestParam(name = "areaCode", required = false) String areaCode, @RequestParam(name = "typeCode", required = false) String typeCode, @RequestParam(name = "jurisdiction", required = false) String jurisdiction) {
        return AjaxResult.success(paAddrInfoService.getCoordinatesByCode(areaCode, typeCode, jurisdiction));
    }

    /**
     * 根据网格编码获取边界数据
     *
     * @param areaCode
     * @param jurisdiction
     * @return
     */
    @GetMapping("/getBaseCoordinatesByCode")
    @ApiOperation("根据网格编码获取边界数据")
    public AjaxResult getBaseCoordinatesByCode(@RequestParam(name = "areaCode", required = false) String areaCode, @RequestParam(name = "jurisdiction", required = false) String jurisdiction) {
        return AjaxResult.success(paAddrInfoService.getBaseCoordinatesByCode(areaCode, jurisdiction));
    }

    /*
     */
/**
 * 根据网格编码获取一级网格数量
 *//*

    @GetMapping("/getMinGridCount")
    @ApiOperation("根据网格编码获取一级网格数量")
    public AjaxResult getMinGridCount(@RequestParam(name = "gridCode",required = false) String gridCode) {
        return AjaxResult.success(paAddrInfoService.getMinGridCount(gridCode));
    }
*/

    /**
     * 获取所有网格管理员
     */
    @Log(title = "获取所有网格管理员", businessType = BusinessType.INSERT)
    @PostMapping("/getPaAddrInfoTypeUser")
    @ApiOperation("获取所有网格管理员")
    public AjaxResult getPaAddrInfoTypeUser(@RequestBody SearchAddrInfoUserParam param) {
        return AjaxResult.success(paAddrInfoService.getPaAddrInfoTypeUser(param));
    }

    @PostMapping("/reOrgAncestorCodes")
    @ApiOperation("重新生成祖先字段")
    public AjaxResult reOrgAncestorCodes() {
        paAddrInfoService.reOrgAncestorCodes();
        return AjaxResult.success();
    }

    @PostMapping("/getBaseCoordinatesByCode")
    @ApiOperation("获取指定orgCode下的网格信息")
    public TableDataBody<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(@RequestBody OrgGridInfoParam orgGridInfoParam) {
        Page<?> page = startPages(orgGridInfoParam.getPageNum(), orgGridInfoParam.getPageSize());
        List<OrgGridInfoDto> orgGridInfoDtoList = paAddrInfoService.getPaAddrInfoTypeByOrgCode(orgGridInfoParam);
        return getDataTableBody(orgGridInfoDtoList, page);
    }

    @PostMapping("/getGridInfo")
    @ApiOperation("获取网格信息基本信息")
    public TableDataBody<GridInfoDto> getGridInfo(@RequestBody GridInfoParam gridInfoParam) {
        Page<?> page = startPages(gridInfoParam.getPageNum(), gridInfoParam.getPageSize());
        List<GridInfoDto> gridInfoDtoList = paAddrInfoService.getGridInfo(gridInfoParam);
        return getDataTableBody(gridInfoDtoList, page);
    }

    @GetMapping("/getGridInfoByCode")
    @ApiOperation("获取指定网格信息基本信息")
    public GridInfoDto getGridInfoByCode(@RequestParam(name = "gridCode") String gridCode) {
        return paAddrInfoService.getGridInfoByCode(gridCode);
    }

    /**
     * 根据行政区划编码，将区域边界垂直平分，返回左右两块的中心点位
     *
     * @param areaCode  行政区划编码
     * @param isCurrent true-查询本级，false-查询下辖
     */
    @GetMapping("/getAreaSplitPoints")
    @ApiOperation("获取区域边界垂直分割的两个中心点位")
    public AjaxResult getAreaSplitPoints(
            @RequestParam(name = "areaCode") String areaCode,
            @RequestParam(name = "isCurrent") Boolean isCurrent) {
        return AjaxResult.success(paAddrInfoService.getAreaSplitPoints(areaCode, isCurrent));
    }
}
