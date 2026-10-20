package com.kx.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.branch.BranchArticleInfo;
import com.kx.system.domain.branch.BranchExcel;
import com.kx.system.domain.branch.BranchInfo;
import com.kx.system.domain.branch.vo.BranchArticleInfoVo;
import com.kx.system.domain.branch.vo.BranchInfoVo;
import com.kx.system.domain.customer.AcquiringCustomerInfo;
import com.kx.system.service.BranchInfoService;
import com.kx.system.util.excel.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/branch")
@Api(tags = "网点管理")
public class BranchInfoController extends BaseController {

    // todo 网点

    @Resource
    private BranchInfoService branchViewService;

    @GetMapping("/statistics")
    @ApiOperation("网点数据统计")
    public AjaxResult getBranchStatistics() {
        return AjaxResult.success();
    }

    @GetMapping("/statistics/list")
    @ApiOperation("网点数据统计明细列表")
    public AjaxResult getBranchStatisticsList() {
        return AjaxResult.success();
    }

    @GetMapping("/statistics/list/export")
    @ApiOperation("网点数据统计明细列表数据导出")
    public AjaxResult exportBranchStatisticsList() {
        return AjaxResult.success();
    }

    @PostMapping("/list")
    @ApiOperation("网点详情列表")
    public TableDataInfo getBranchList(@RequestBody BranchInfoVo branchInfoVo) {
        startPage();
        List<BranchInfo> list = branchViewService.selectBranchList(branchInfoVo);
        return getDataTable(list);
    }

    @GetMapping("/import")
    @ApiOperation("网点数据导入")
    public void importBranchData(@RequestParam("file") MultipartFile file) {
        branchViewService.importBranchData(file);
    }

    @GetMapping("/export")
    @ApiOperation("网点数据导出")
    public void exportBranchData(HttpServletResponse response, @RequestBody BranchInfoVo branchInfoVo) {
        branchViewService.exportBranchData(response, branchInfoVo);
    }

    @GetMapping("/select/{id}")
    @ApiOperation("根据id查询网点详细信息")
    public AjaxResult selectBranchById(@PathVariable("id") Long id) {
        return AjaxResult.success(branchViewService.selectBranchById(id));
    }

    @GetMapping("/update")
    @ApiOperation("修改网点信息")
    public AjaxResult updateBranch(@RequestBody BranchInfoVo branchInfo) {
        branchViewService.updateBranch(branchInfo);
        return AjaxResult.success();
    }

    @GetMapping("/article/list")
    @ApiOperation("查询网点文章列表")
    public TableDataInfo selectBranchArticleList(String orgNum) {
        startPage();
        List<BranchArticleInfoVo> list = branchViewService.selectBranchArticleList(orgNum);
        return getDataTable(list);
    }

    @GetMapping("/article/select/{id}")
    @ApiOperation("根据id查询文章")
    public AjaxResult selectBranchArticleById(@PathVariable("id") Long id) {
        return AjaxResult.success(branchViewService.selectBranchArticleById(id));
    }

    @GetMapping("/article/insert")
    @ApiOperation("新增文章")
    public AjaxResult insertBranchArticle(@RequestBody BranchArticleInfo branchArticleInfo) {
        branchViewService.insertBranchArticle(branchArticleInfo);
        return AjaxResult.success();
    }

    @GetMapping("/article/update")
    @ApiOperation("修改文章信息")
    public AjaxResult updateBranchArticle(@RequestBody BranchArticleInfo branchArticleInfo) {
        branchViewService.updateBranchArticle(branchArticleInfo);
        return AjaxResult.success();
    }

    @GetMapping("/article/delete/{id}")
    @ApiOperation("删除文章信息")
    public AjaxResult deleteBranchArticleById(@PathVariable("id") Long id) {
        branchViewService.deleteBranchArticleById(id);
        return AjaxResult.success();
    }


}
