package com.kx.system.controller;

import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.system.domain.CreditRecord;
import com.kx.system.service.ICreditRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 授信Controller
 *
 * @author kangxu
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/record")
public class CreditRecordController extends BaseController
{
    @Autowired
    private ICreditRecordService creditRecordService;

    /**
     * 查询授信列表
     */
    @PostMapping("/list")
    @ApiOperation("查询授信列表")
    public TableDataInfo list(@RequestBody CreditRecord creditRecord)
    {
        startPage(creditRecord);
        List<CreditRecord> list = creditRecordService.selectCreditRecordList(creditRecord);
        return getDataTable(list);
    }

    /**
     * 导出授信列表
     */
    @Log(title = "授信", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出授信列表")
    public void export(HttpServletResponse response, CreditRecord creditRecord)
    {
        List<CreditRecord> list = creditRecordService.selectCreditRecordList(creditRecord);
        ExcelUtil<CreditRecord> util = new ExcelUtil<CreditRecord>(CreditRecord.class);
        util.exportExcel(response, list, "授信数据");
    }

    /**
     * 获取授信详细信息
     */
    @GetMapping(value = "/{customId}")
    @ApiOperation("获取授信详细信息")
    public AjaxResult getInfo(@PathVariable("customId") Long customId)
    {
        return success(creditRecordService.selectCreditRecordByCustomId(customId));
    }

    /**
     * 新增授信
     */
    @Log(title = "授信", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiOperation("新增授信")
    public AjaxResult add(@RequestBody CreditRecord creditRecord)
    {
        return toAjax(creditRecordService.insertCreditRecord(creditRecord));
    }

    /**
     * 修改授信
     */
    @ApiOperation("修改授信")
    @Log(title = "授信", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody CreditRecord creditRecord)
    {
        return toAjax(creditRecordService.updateCreditRecord(creditRecord));
    }
}
