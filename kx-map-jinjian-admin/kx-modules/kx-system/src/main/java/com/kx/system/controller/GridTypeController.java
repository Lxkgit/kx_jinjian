package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.service.ISysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.kx.system.config.constants.SysDictTypeConstants.ADDR_INFO_TYPE;

/**
 * 网格分类信息
 *
 * @author kangxu
 */
@RestController
@RequestMapping("/business/gridType")
@Api(tags = "网格管理-网格体系管理")
public class GridTypeController extends BaseController {
    @Autowired
    private ISysDictDataService dictDataService;

    @GetMapping("/list")
    @ApiOperation("获取网格体系列表")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        dictData.setDictType(ADDR_INFO_TYPE);
        dictData.setStatus("0");
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @Log(title = "网格分类数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出网格体系列表")
    public void export(HttpServletResponse response, SysDictData dictData) {
        dictData.setDictType(ADDR_INFO_TYPE);
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "网格分类数据");
    }

    /**
     * 查询网格分类数据详细
     */
    @GetMapping(value = "/{dictCode}")
    @ApiOperation("获取网格体系详情")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 新增网格分类
     */
    @Log(title = "网格分类数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("添加网格体系数据")
    public AjaxResult add(@RequestBody SysDictData dict) {
        dict.setDictType(ADDR_INFO_TYPE);
        dict.setDictValue(ADDR_INFO_TYPE + "_" + LocalDateTime.now());
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存网格分类
     */
    @Log(title = "网格分类数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ApiOperation("编辑网格体系数据")
    public AjaxResult edit(@RequestBody SysDictData dict) {
        dict.setDictType(ADDR_INFO_TYPE);
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除网格分类
     */
    @Log(title = "删除网格分类", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ApiOperation("删除网格体系数据")
    public AjaxResult remove(@RequestBody SysDictData dict) {
        List<Long> dictCodes = new ArrayList<>();
        dictCodes.add(dict.getDictCode());
        dictDataService.deleteDictDataByIds(dictCodes.stream().toArray(Long[]::new));
        return success();
    }
}
