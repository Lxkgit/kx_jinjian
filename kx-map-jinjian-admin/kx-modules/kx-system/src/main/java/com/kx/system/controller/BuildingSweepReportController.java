package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.log.annotation.Log;
import com.kx.common.log.enums.BusinessType;
import com.kx.common.security.annotation.RequiresPermissions;
import com.kx.system.domain.buildingsweep.BuildingSweepReport;
import com.kx.system.domain.buildingsweep.BuildingSweepReviewRequest;
import com.kx.system.service.IBuildingSweepReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/building-sweep/report")
@Api(tags = "扫楼上报管理")
public class BuildingSweepReportController extends BaseController {

    @Resource
    private IBuildingSweepReportService buildingSweepReportService;

    @PostMapping("/list")
    @RequiresPermissions("system:buildingSweep:list")
    @ApiOperation("查询扫楼上报列表")
    public TableDataInfo list(@RequestBody BuildingSweepReport query) {
        startPage(query);
        List<BuildingSweepReport> list = buildingSweepReportService.selectBuildingSweepReportList(query);
        return getDataTable(list);
    }

    @GetMapping("/{id:\\d+}")
    @RequiresPermissions("system:buildingSweep:query")
    @ApiOperation("查询扫楼上报详情")
    public AjaxResult detail(@PathVariable Long id) {
        return success(buildingSweepReportService.selectBuildingSweepReportById(id));
    }

    @GetMapping("/related-clues")
    @RequiresPermissions("system:buildingSweep:review")
    @ApiOperation("查询扫楼复核可关联线索")
    public AjaxResult relatedClues(@RequestParam(required = false) String keyword) {
        return success(buildingSweepReportService.selectRelatedClueOptions(keyword));
    }

    @PostMapping("/add")
    @RequiresPermissions("system:buildingSweep:add")
    @Log(title = "扫楼上报", businessType = BusinessType.INSERT)
    @ApiOperation("新增扫楼上报")
    public AjaxResult add(@Validated @RequestBody BuildingSweepReport report) {
        return toAjax(buildingSweepReportService.insertBuildingSweepReport(report));
    }

    @PostMapping("/edit")
    @RequiresPermissions("system:buildingSweep:edit")
    @Log(title = "扫楼上报", businessType = BusinessType.UPDATE)
    @ApiOperation("编辑待复核的扫楼上报")
    public AjaxResult edit(@Validated @RequestBody BuildingSweepReport report) {
        return toAjax(buildingSweepReportService.updateBuildingSweepReport(report));
    }

    @PostMapping("/review")
    @RequiresPermissions("system:buildingSweep:review")
    @Log(title = "扫楼上报复核", businessType = BusinessType.UPDATE)
    @ApiOperation("复核扫楼上报")
    public AjaxResult review(@Validated @RequestBody BuildingSweepReviewRequest request) {
        return toAjax(buildingSweepReportService.reviewBuildingSweepReport(request));
    }

    @PostMapping("/resubmit")
    @RequiresPermissions("system:buildingSweep:edit")
    @Log(title = "扫楼上报补正", businessType = BusinessType.UPDATE)
    @ApiOperation("补正并重新上报")
    public AjaxResult resubmit(@Validated @RequestBody BuildingSweepReport report) {
        return toAjax(buildingSweepReportService.resubmitBuildingSweepReport(report));
    }

    @PostMapping("/delete/{id}")
    @RequiresPermissions("system:buildingSweep:remove")
    @Log(title = "扫楼上报", businessType = BusinessType.DELETE)
    @ApiOperation("删除待复核的扫楼上报")
    public AjaxResult delete(@PathVariable Long id) {
        return toAjax(buildingSweepReportService.deleteBuildingSweepReport(id));
    }
}
