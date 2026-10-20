package com.kx.system.controller;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.AnchorComplaintEvents;
import com.kx.system.service.ComplaintInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
@Api(tags = "网点投诉事件信息管理")
public class ComplaintInfoController {
    @Autowired
    ComplaintInfoService complaintInfoService;

    @GetMapping("/list")
    @ApiOperation("查询网点投诉事件信息列表")
    public AjaxResult  getByAnchorName(@RequestParam String anchorName) {
        return AjaxResult.success(complaintInfoService.findByAnchorName(anchorName));
    }

    @GetMapping("/getComplaint")
    @ApiOperation("查询最近一次网点投诉事件信息")
    public AjaxResult getLatestByAnchorName(@RequestParam String anchorName) {
        return AjaxResult.success(complaintInfoService.findLatestByAnchorName(anchorName));
    }

    @GetMapping("/getById/{id}")
    @ApiOperation("根据id查询网点投诉事件信息详情")
    public AjaxResult getById(@PathVariable Long id) {
        return AjaxResult.success(complaintInfoService.findById(id));
    }

    @PostMapping("/add")
    @ApiOperation("新增网点投诉事件信息")
    public AjaxResult addComplaint(@RequestBody AnchorComplaintEvents anchorComplaintEvents) {
        return AjaxResult.success(complaintInfoService.addComplaint(anchorComplaintEvents));
    }
}
