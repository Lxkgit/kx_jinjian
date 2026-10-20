package com.kx.system.controller;


import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.service.FilingModelInfoService;
//import com.kx.system.service.TaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Api(tags = {"建档管理-建档模板管理"})
@RestController
@RequestMapping("/filingModel")
@Slf4j
public class FilingModelInfoController extends BaseController {
    @Autowired
    private FilingModelInfoService filingModelInfoService;
//    @Autowired
//    private TaskInfoService taskInfoService;

    /**
     * 采集表模板 --查询列表
     */
    @GetMapping(value = "/listModel")
    @ApiOperation(value = "采集表模板 --列表", notes = "必传入小区id")
    public AjaxResult listModel(FilingModelInfo filingModelInfo) {
        return AjaxResult.success(filingModelInfoService.list(filingModelInfo));
    }

    /**
     * 采集表模板 -- 三大类型
     * @return
     */
    @GetMapping(value = "/listModelSelect")
    @ApiOperation(value = "采集表模板 --个人、企业、商户", notes = "")
    public AjaxResult listModelSelect() {
        return AjaxResult.success(filingModelInfoService.listModelSelect());
    }


    /**
     * 采集表模板 --开启关闭
     */
    @GetMapping(value = "/startFilingModelInfo")
    @ApiOperation(value = "采集表模板 --开启关闭", notes = "")
    public AjaxResult startFilingModelInfo(Long id) {
        return AjaxResult.success(filingModelInfoService.startFilingModelInfo(id));
    }


    /**
     * 采集表模板 --回显、预览
     */
    @GetMapping(value = "/getFilingModelInfoById")
    @ApiOperation(value = "采集表模板 --回显、预览", notes = "")
    public AjaxResult getFilingModelInfoById(Long id,String isFinishParam) {
        return AjaxResult.success(filingModelInfoService.getFilingModelInfoById(id,isFinishParam));
    }

    /**
     * 采集表模板 --编辑
     */
    @PostMapping(value = "/editFilingModelInfo")
    @ApiOperation(value = "采集表模板 --编辑", notes = "")
    public AjaxResult editFilingModelInfo(@RequestBody FilingModelInfo filingModelInfo) {
//        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
//        if (loginUser != null) {
//            filingModelInfo.setCreateBy(loginUser.getUser().getUserName());
//            filingModelInfo.setCreateOrg(loginUser.getUser().getNetworkCode());
//        }
        //权限未上线 创建人都用admin代替
        filingModelInfo.setCreateBy("admin");
        filingModelInfo.setCreateOrg("852000");
        return AjaxResult.success(filingModelInfoService.editFilingModelInfo(filingModelInfo));
    }

    @PostMapping(value = "/deleteFilingModelInfo")
    @ApiOperation(value = "采集表模板 --删除", notes = "")
    public AjaxResult deleteFilingModelInfo(Long id) {
        return AjaxResult.success(filingModelInfoService.deleteFilingModelInfo(id));
    }

    /**
     * 获取客户档案动态表头
     * @param customType 1--个人客户 2--企业客户 3--商户客户
     * @return
     */
    @ApiOperation(value = "获取客户档案动态表头")
    @GetMapping(value = "/getCustomerProfileHeaders")
    public AjaxResult getCustomerProfileHeaders(String customType){
        return AjaxResult.success(filingModelInfoService.getCustomerProfileHeaders(customType));
    }

    /**
     * 模板下载
     * @return
     */
    @ApiOperation(value = "excel动态模板下载", notes = "传入客户类型")
    @GetMapping(value = "/modelDownload")
    public AjaxResult modelDownload() {
        return AjaxResult.success(filingModelInfoService.modelDownload());
    }

    /**
     * 根据客户类型获取当前开启的模板id
     */
    @ApiOperation(value = "根据客户类型获取当前开启的模板id", notes = "传入客户类型")
    @GetMapping(value = "/getModelIdByType")
    public AjaxResult getModelIdByType(String type) {
        return AjaxResult.success(filingModelInfoService.getModelIdByType(type));
    }


}
