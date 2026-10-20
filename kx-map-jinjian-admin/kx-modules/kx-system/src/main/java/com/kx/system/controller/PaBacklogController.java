package com.kx.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.kx.system.domain.PaBacklog;
import com.kx.system.service.IPaBacklogService;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.page.TableDataInfo;

/**
 * 待办事项Controller
 *
 * @author kangxu
 * @date 2024-01-31
 */
@RestController
@RequestMapping("/backlog")
public class PaBacklogController extends BaseController
{
    @Autowired
    private IPaBacklogService paBacklogService;

    /**
     * 查询待办事项列表
     */
    @PostMapping("/list")
    @ApiOperation("查询待办事项列表")
    public TableDataInfo list(@RequestBody PaBacklog paBacklog)
    {
        startPage(paBacklog);
        List<PaBacklog> list = paBacklogService.selectPaBacklogList(paBacklog);
        return getDataTable(list);
    }

    /**
     * 导出待办事项列表
     */
    @Log(title = "待办事项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出待办事项列表")
    public void export(HttpServletResponse response, PaBacklog paBacklog)
    {
        List<PaBacklog> list = paBacklogService.selectPaBacklogList(paBacklog);
        ExcelUtil<PaBacklog> util = new ExcelUtil<PaBacklog>(PaBacklog.class);
        util.exportExcel(response, list, "待办事项数据");
    }

    /**
     * 获取待办事项详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取待办事项详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(paBacklogService.selectPaBacklogById(id));
    }

    /**
     * 新增待办事项
     */
    @Log(title = "待办事项", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增待办事项")
    public AjaxResult add(@RequestBody PaBacklog paBacklog)
    {
        return toAjax(paBacklogService.insertPaBacklog(paBacklog));
    }

    /**
     * 修改待办事项
     */
    @ApiOperation("修改待办事项")
    @Log(title = "待办事项", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody PaBacklog paBacklog)
    {
        return toAjax(paBacklogService.updatePaBacklog(paBacklog));
    }

    /**
     * 删除待办事项
     */
    @Log(title = "待办事项", businessType = BusinessType.DELETE)
	@PostMapping("/delete")
    @ApiOperation("删除待办事项")
    public AjaxResult remove(@RequestBody PaBacklog paBacklog)
    {
        return toAjax(paBacklogService.deletePaBacklogById(paBacklog.getId()));
    }
}
