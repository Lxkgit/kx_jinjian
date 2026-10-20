package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.QuestionRecord;
import com.kx.system.service.IQuestionRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description question_record
 * @author BEJSON
 * @date 2024-11-30
 */
@RestController
@RequestMapping(value = "/questionRecord")
public class QuestionRecordController extends BaseController {

    @Resource
    private IQuestionRecordService questionRecordService;

    @PostMapping("/list")
    @ApiOperation("查询列表")
    public TableDataInfo list(@RequestBody QuestionRecord questionRecord){
        return getDataTable(questionRecordService.list(questionRecord));
    }

    @PostMapping("/insert")
    @ApiOperation("新增")
    public AjaxResult insert(@RequestBody QuestionRecord questionRecord){
        questionRecordService.insert(questionRecord);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public AjaxResult update(@RequestBody QuestionRecord questionRecord){
        questionRecordService.update(questionRecord);
        return AjaxResult.success("修改成功");
    }

    @GetMapping("/delete")
    @ApiOperation("删除")
    public AjaxResult delete(String id){
        questionRecordService.delete(id);
        return AjaxResult.success("删除成功");
    }



}