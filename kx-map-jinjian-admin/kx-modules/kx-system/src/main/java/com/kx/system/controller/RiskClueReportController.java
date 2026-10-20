package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.annotation.RequiresPermissions;
import com.kx.system.domain.riskclue.RiskClueFeedbackRecord;
import com.kx.system.domain.riskclue.RiskClueReport;
import com.kx.system.domain.riskclue.RiskClueReviewRequest;
import com.kx.system.domain.riskclue.RiskClueTransferRecord;
import com.kx.system.service.IRiskClueReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/risk-clue/report")
@Api(tags = "线索上报管理")
public class RiskClueReportController extends BaseController {
    @Resource
    private IRiskClueReportService riskClueReportService;

    @PostMapping("/list")
    @RequiresPermissions("system:riskClue:list")
    @ApiOperation("查询线索列表")
    public TableDataInfo list(@RequestBody RiskClueReport query) {
        startPage(query);
        List<RiskClueReport> list = riskClueReportService.selectRiskClueReportList(query);
        return getDataTable(list);
    }

    @GetMapping("/{id}")
    @RequiresPermissions("system:riskClue:query")
    @ApiOperation("查询线索详情")
    public AjaxResult detail(@PathVariable Long id) {
        return success(riskClueReportService.selectRiskClueReportById(id));
    }

    @PostMapping("/add")
    @RequiresPermissions("system:riskClue:add")
    @Log(title = "线索上报", businessType = BusinessType.INSERT)
    @ApiOperation("新增并提交线索")
    public AjaxResult add(@RequestBody RiskClueReport report) {
        return AjaxResult.success(riskClueReportService.addRiskClueReport(report));
    }

    @PostMapping("/save-draft")
    @RequiresPermissions("system:riskClue:add")
    @Log(title = "线索草稿", businessType = BusinessType.INSERT)
    @ApiOperation("保存线索草稿或修改暂存")
    public AjaxResult saveDraft(@RequestBody RiskClueReport report) {
        return AjaxResult.success(riskClueReportService.saveRiskClueDraft(report));
    }

    @PostMapping("/edit")
    @RequiresPermissions("system:riskClue:edit")
    @Log(title = "线索修改", businessType = BusinessType.UPDATE)
    @ApiOperation("修改草稿或待复核线索")
    public AjaxResult edit(@RequestBody RiskClueReport report) {
        return toAjax(riskClueReportService.updateRiskClueReport(report));
    }

    @PostMapping("/submit")
    @RequiresPermissions("system:riskClue:edit")
    @Log(title = "线索提交", businessType = BusinessType.UPDATE)
    @ApiOperation("提交已有草稿")
    public AjaxResult submit(@RequestBody RiskClueReport report) {
        return toAjax(riskClueReportService.submitDraftReport(report));
    }

    @PostMapping("/resubmit")
    @RequiresPermissions("system:riskClue:edit")
    @Log(title = "线索补正重提", businessType = BusinessType.UPDATE)
    @ApiOperation("补正并重新提交线索")
    public AjaxResult resubmit(@RequestBody RiskClueReport report) {
        return toAjax(riskClueReportService.resubmitRiskClueReport(report));
    }

    @PostMapping("/review")
    @RequiresPermissions("system:riskClue:review")
    @Log(title = "线索复核", businessType = BusinessType.UPDATE)
    @ApiOperation("复核线索")
    public AjaxResult review(@Validated @RequestBody RiskClueReviewRequest request) {
        return toAjax(riskClueReportService.reviewRiskClueReport(request));
    }

    @PostMapping("/transfer")
    @RequiresPermissions("system:riskClue:transfer")
    @Log(title = "线索移送", businessType = BusinessType.UPDATE)
    @ApiOperation("移送线索")
    public AjaxResult transfer(@Validated @RequestBody RiskClueTransferRecord record) {
        return toAjax(riskClueReportService.transferRiskClueReport(record));
    }

    @PostMapping("/feedback")
    @RequiresPermissions("system:riskClue:feedback")
    @Log(title = "线索反馈", businessType = BusinessType.UPDATE)
    @ApiOperation("登记线索反馈")
    public AjaxResult feedback(@Validated @RequestBody RiskClueFeedbackRecord record) {
        return toAjax(riskClueReportService.feedbackRiskClueReport(record));
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions("system:riskClue:remove")
    @Log(title = "线索删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除草稿或待复核线索")
    public AjaxResult delete(@PathVariable Long id) {
        return toAjax(riskClueReportService.deleteRiskClueReport(id));
    }
}
