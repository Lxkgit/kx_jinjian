package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.domain.AnchorChangeLogVo;
import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.param.SearchAnchorChangeLogParam;
import com.kx.system.domain.param.SearchPaChangeLogListParam;
import com.kx.system.service.IPaChangeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 变更记录Controller
 *
 * @author kangxu
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/business/paChangeLog")
@Api(tags = "变更记录")
public class PaChangeLogController extends BaseController {
    @Autowired
    private IPaChangeLogService paChangeLogService;

    /**
     * 查询变更记录列表
     */
    @PostMapping("/list")
    @ApiOperation("查询变更记录列表")
    public TableDataInfo list(@RequestBody SearchPaChangeLogListParam param) {
        startPage(param);
        List<PaChangeLog> list = paChangeLogService.selectPaChangeLogList(param);
        return getDataTable(list);
    }

    /**
     * 导出变更记录列表，根据页码导出
     */
    @Log(title = "变更记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出变更记录列表")
    public void export(HttpServletResponse response, SearchPaChangeLogListParam paChangeLog) {
        List<PaChangeLog> list = paChangeLogService.selectPaChangeLogExportList(paChangeLog);
        new ExcelUtil<>(PaChangeLog.class).exportExcel(response, list, "变更记录数据");
    }

    /**
     * 获取变更记录详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取变更记录详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(paChangeLogService.selectPaChangeLogById(id));
    }

    @PostMapping("/listAnchorChangeLog")
    @ApiOperation("查询锚点变更记录列表")
    public TableDataInfo list(@RequestBody SearchAnchorChangeLogParam param) {
        startPage(param);
        List<AnchorChangeLogVo> list = paChangeLogService.selectAnchorChangeLogList(param);
        return getDataTable(list);
    }

    /**
     * 导出变更记录列表，根据页码导出
     */
    @Log(title = "变更记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportAnchorChangeLog")
    @ApiOperation("导出锚点变更记录列表")
    public void exportAnchorChangeLog(HttpServletResponse response, SearchAnchorChangeLogParam paChangeLog) {
        List<PaChangeLog> list = paChangeLogService.selectAnchorChangeLogExportList(paChangeLog);
        new ExcelUtil<>(PaChangeLog.class).exportExcel(response, list, "变更记录数据");
    }
}
