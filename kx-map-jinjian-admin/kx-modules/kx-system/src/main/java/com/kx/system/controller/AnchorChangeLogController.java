package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.domain.AnchorChangeLog;
import com.kx.system.service.IAnchorChangeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 锚点变更记录Controller
 *
 * @author kangxu
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/anchorChangeLog")
@Api(tags = "锚点变更记录")
public class AnchorChangeLogController extends BaseController
{
    @Autowired
    private IAnchorChangeLogService anchorChangeLogService;

    /**
     * 查询锚点变更记录列表
     */
    @PostMapping("/list")
    @ApiOperation("查询锚点变更记录列表")
    public TableDataInfo list(@RequestBody AnchorChangeLog anchorChangeLog)
    {
        startPage(anchorChangeLog);
        List<AnchorChangeLog> list = anchorChangeLogService.selectAnchorChangeLogList(anchorChangeLog);
        return getDataTable(list);
    }

    /**
     * 导出锚点变更记录列表
     */
    @Log(title = "锚点变更记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出锚点变更记录列表")
    public void export(HttpServletResponse response, AnchorChangeLog anchorChangeLog)
    {
        List<AnchorChangeLog> list = anchorChangeLogService.selectAnchorChangeLogList(anchorChangeLog);
        ExcelUtil<AnchorChangeLog> util = new ExcelUtil<AnchorChangeLog>(AnchorChangeLog.class);
        util.exportExcel(response, list, "锚点变更记录数据");
    }

    /**
     * 获取锚点变更记录详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取锚点变更记录详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(anchorChangeLogService.selectAnchorChangeLogById(id));
    }

    /**
     * 新增锚点变更记录
     */
    @Log(title = "锚点变更记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增锚点变更记录")
    public AjaxResult add(@RequestBody AnchorChangeLog anchorChangeLog)
    {
        return toAjax(anchorChangeLogService.insertAnchorChangeLog(anchorChangeLog));
    }

    /**
     * 修改锚点变更记录
     */
    @ApiOperation("修改锚点变更记录")
    @Log(title = "锚点变更记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody AnchorChangeLog anchorChangeLog)
    {
        return toAjax(anchorChangeLogService.updateAnchorChangeLog(anchorChangeLog));
    }

    /**
     * 删除锚点变更记录
     */
    @Log(title = "锚点变更记录", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除锚点变更记录")
    public AjaxResult remove(@RequestBody AnchorChangeLog anchorChangeLog)
    {
        return toAjax(anchorChangeLogService.deleteAnchorChangeLogById(anchorChangeLog.getId()));
    }
}
