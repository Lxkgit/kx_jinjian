package com.kx.system.controller;

import javax.servlet.http.HttpServletResponse;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.system.domain.anchor.BuildingAnchorPoint;
import com.kx.system.service.IBuildingAnchorPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;

import java.util.List;

/**
 * 楼宇锚点Controller
 *
 * @author kangxu
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/business/buildingAnchorPoint")
@Api(tags = "楼宇锚点管理")
public class BuildingAnchorPointController extends BaseController
{
    @Autowired
    private IBuildingAnchorPointService buildingAnchorPointService;

    /**
     * 查询楼宇锚点列表
     */
    @PostMapping("/list")
    @ApiOperation("查询楼宇锚点列表")
    public TableDataInfo list(@RequestBody BuildingAnchorPoint buildingAnchorPoint)
    {
        List<BuildingAnchorPoint> list = buildingAnchorPointService.selectBuildingAnchorPointList(buildingAnchorPoint);
        return getDataTable(list);
    }

    /**
     * 导出楼宇锚点列表
     */
    @Log(title = "楼宇锚点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出楼宇锚点列表")
    public void export(HttpServletResponse response, BuildingAnchorPoint buildingAnchorPoint)
    {
        List<BuildingAnchorPoint> list = buildingAnchorPointService.selectBuildingAnchorPointList(buildingAnchorPoint);
        ExcelUtil<BuildingAnchorPoint> util = new ExcelUtil<BuildingAnchorPoint>(BuildingAnchorPoint.class);
        util.exportExcel(response, list, "楼宇锚点数据");
    }

    /**
     * 获取楼宇锚点详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取楼宇锚点详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(buildingAnchorPointService.selectBuildingAnchorPointById(id));
    }

    /**
     * 新增楼宇锚点
     */
    @Log(title = "楼宇锚点", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增楼宇锚点")
    public AjaxResult add(@RequestBody BuildingAnchorPoint buildingAnchorPoint)
    {
        return toAjax(buildingAnchorPointService.insertBuildingAnchorPoint(buildingAnchorPoint));
    }

    /**
     * 修改楼宇锚点
     */
    @ApiOperation("修改楼宇锚点")
    @Log(title = "楼宇锚点", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody BuildingAnchorPoint buildingAnchorPoint)
    {
        return toAjax(buildingAnchorPointService.updateBuildingAnchorPoint(buildingAnchorPoint));
    }

    /**
     * 删除楼宇锚点
     */
    @Log(title = "楼宇锚点", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除楼宇锚点")
    public AjaxResult remove(@RequestBody BuildingAnchorPoint buildingAnchorPoint)
    {
        return toAjax(buildingAnchorPointService.deleteBuildingAnchorPointById(buildingAnchorPoint.getId()));
    }
}
