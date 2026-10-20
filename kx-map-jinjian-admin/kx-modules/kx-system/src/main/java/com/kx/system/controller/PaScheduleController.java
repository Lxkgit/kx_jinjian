package com.kx.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.annotation.RequiresPermissions;
import com.kx.system.domain.PaSchedule;
import com.kx.system.service.IPaScheduleService;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.page.TableDataInfo;

/**
 * 日程Controller
 *
 * @author kangxu
 * @date 2024-01-25
 */
@RestController
@RequestMapping("/schedule")
@Api(tags = "日程管理")
public class PaScheduleController extends BaseController
{
    @Autowired
    private IPaScheduleService paScheduleService;

    /**
     * 查询日程列表
     */
    @PostMapping("/list")
    @ApiOperation("查询日程列表")
    public TableDataInfo list(@RequestBody PaSchedule paSchedule)
    {
        List<PaSchedule> list = paScheduleService.selectPaScheduleList(paSchedule);
        return getDataTable(list);
    }

    /**
     * 导出日程列表
     */
    @Log(title = "日程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出日程列表")
    public void export(HttpServletResponse response, PaSchedule paSchedule)
    {
        List<PaSchedule> list = paScheduleService.selectPaScheduleList(paSchedule);
        ExcelUtil<PaSchedule> util = new ExcelUtil<PaSchedule>(PaSchedule.class);
        util.exportExcel(response, list, "日程数据");
    }

    /**
     * 获取日程详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取日程详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(paScheduleService.selectPaScheduleById(id));
    }

    /**
     * 新增日程
     */
    @Log(title = "日程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增日程")
    public AjaxResult add(@RequestBody PaSchedule paSchedule)
    {
        return toAjax(paScheduleService.insertPaSchedule(paSchedule));
    }

    /**
     * 修改日程
     */
    @ApiOperation("修改日程")
    @Log(title = "日程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PaSchedule paSchedule)
    {
        return toAjax(paScheduleService.updatePaSchedule(paSchedule));
    }

    /**
     * 删除日程
     */
    @Log(title = "日程", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除日程")
    public AjaxResult remove(@RequestBody PaSchedule paSchedule)
    {
        return toAjax(paScheduleService.deletePaScheduleById(paSchedule.getId()));
    }
}
