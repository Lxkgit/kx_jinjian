package com.kx.system.controller;



import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.VisitLabelVO;
import com.kx.system.domain.dto.LabelDTO;
import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.req.LabelDelReq;
import com.kx.system.domain.req.LabelReq;
import com.kx.system.service.LabelGroupService;
import com.kx.system.service.LabelInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/label")
@Api(tags = "标签管理")
public class LabelController extends BaseController {

    @Resource
    private LabelGroupService labelGroupService;

    @Resource
    private LabelInfoService labelInfoService;

    @PostMapping("/group/addOrEdit")
    @ApiOperation("新增、编辑标签目录")
    public AjaxResult addOrEditGroup(@RequestBody LabelGroupDTO labelGroupDTO) {
        return labelGroupService.addOrEditGroup(labelGroupDTO);
    }

    @GetMapping("/group/get")
    @ApiOperation("获取标签目录")
    public AjaxResult labelDirectory() {
        return labelGroupService.labelDirectory();
    }

    @PostMapping("/group/deleteByIds")
    @ApiOperation("删除、批量删除标签目录")
    public AjaxResult deleteDirectoryByIds(@RequestBody LabelDelReq req) {
        return labelGroupService.deleteDirectoryByIds(req.getIds());
    }

    @PostMapping("/addOrEdit")
    @ApiOperation("新增、编辑标签")
    public AjaxResult addOrEditLabel(@RequestBody LabelDTO labelDTO) {
        return labelInfoService.addOrEditLabel(labelDTO);
    }

    @PostMapping("/deleteByIds")
    @ApiOperation("删除、批量删除标签")
    public AjaxResult deleteLabelByIds(@RequestBody LabelDelReq req) {
        return labelInfoService.deleteLabelByIds(req.getIds());
    }

    @PostMapping("/page")
    @ApiOperation("标签分页查询")
    public TableDataInfo page(@RequestBody LabelReq labelReq) {
        return labelInfoService.queryPage(labelReq);
    }

    @GetMapping("/selectChildLabel")
    @ApiOperation("查询根标签下所有子标签")
    public AjaxResult selectChildLabel(Long id) {
        List<LabelGroupDTO> labels = labelInfoService.selectChildLabel(id);
        return AjaxResult.success(labels);
    }

    @GetMapping("/selectSysAndGridLabel")
    @ApiOperation("查询客户类型下系统和网格标签")
    public AjaxResult selectSysAndGridLabel(Long id) {
        VisitLabelVO visitLabelVO = labelInfoService.selectSysAndGridLabel(id);
        return AjaxResult.success(visitLabelVO);
    }
}
