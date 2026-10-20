package com.kx.system.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.datascope.utils.DataScopeUtil;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingCustomerTransfer;
import com.kx.system.domain.filing.FilingImportRecord;
import com.kx.system.service.FilingCustomerInfoService;
import com.kx.system.service.FilingCustomerTransferService;
import com.kx.system.service.FilingImportRecordService;
import com.kx.system.service.FilingMessageDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 建档客户基础信息表 前端控制器
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Api(tags = {"建档客户基础信息表"})
@RestController
@Slf4j
@RequestMapping("filing")
public class FilingCustomerInfoController extends BaseController {

    @Resource
    private FilingCustomerInfoService filingCustomerInfoService;
    @Resource
    private FilingMessageDetailService filingMessageDetailService;
    @Resource
    private FilingCustomerTransferService filingCustomerTransferService;
    @Resource
    private TokenService tokenService;
    @Resource
    private FilingImportRecordService filingImportRecordService;

    /**
     * 列表查询
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "分页", notes = "传入建档客户基础信息表")
    public TableDataInfo list(FilingCustomerInfo filingCustomerInfo) {
        startPage();
        List<FilingCustomerInfo> list = filingCustomerInfoService.list(filingCustomerInfo);
        return getDataTable(list);
    }

    /**
     * 列表查询
     */
    @PostMapping(value = "/listForMobile")
    @ApiOperation(value = "分页", notes = "传入建档客户基础信息表")
    public TableDataInfo listForMobile(@RequestBody FilingCustomerInfo filingCustomerInfo) {
        startPage(filingCustomerInfo);
        List<FilingCustomerInfo> list = filingCustomerInfoService.list(filingCustomerInfo);
        return getDataTable(list);
    }

    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询数据", notes = "传入主键")
    @GetMapping(value = "/getById")
    public AjaxResult getById(@RequestParam("id") Long id) {
        return AjaxResult.success(filingCustomerInfoService.getById(id));
    }


    @ApiOperation(value = "根据模板id和建档id查询档案", notes = "传入模板id和建档id")
    @GetMapping(value = "/getByModelIdAndFilingId")
    public AjaxResult getByModelIdAndFilingId(Long modelId, Long filingId) {
        return AjaxResult.success(filingCustomerInfoService.getByModelIdAndFilingId(modelId, filingId));
    }

    @ApiOperation(value = "根据模板id和证件号查询档案", notes = "传入模板id和证件号")
    @GetMapping(value = "/getByModelIdAndIdentity")
    public AjaxResult getByModelIdAndIdentity(Long modelId, String identity,String phone) {
        return AjaxResult.success(filingCustomerInfoService.getByModelIdAndIdentity(modelId, identity, phone));
    }

    @ApiOperation(value = "根据证件号和手机号查询档案对应的最新一个模版id", notes = "传入模板id和证件号")
    @GetMapping(value = "/getTypeByPhoneAndIdentity")
    public AjaxResult getTypeByPhoneAndIdentity(String phone, String identity) {
        return AjaxResult.success(filingCustomerInfoService.getTypeByPhoneAndIdentity(phone, identity));
    }


    @ApiOperation(value = "根据模板id和证件号查询是否存在建档档案", notes = "传入模板id和证件号")
    @GetMapping(value = "/getFilingIsExist")
    public AjaxResult getFilingIsExist(Long modelId, String identity) {
        return AjaxResult.success(filingCustomerInfoService.getFilingIsExist(modelId, identity));
    }


    /**
     * 新增或编辑建档客户
     */
    @ApiOperation(value = "校验建档新增", notes = "传入建档客户基础信息表")
    @PostMapping(value = "/save")
    @Transactional
    public AjaxResult add(@RequestBody FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        return AjaxResult.success(filingCustomerInfoService.saveFilingCustomerInfo(filingCustomerInfo));
    }

    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除", notes = "传入ids,多个以逗号隔开")
    @PostMapping(value = "/deleteByIds")
    public AjaxResult deleteByIds(@RequestParam("ids") String ids) {
        filingMessageDetailService.deleteByFilingIds(ids);
        return AjaxResult.success(filingCustomerInfoService.deleteByIds(ids));
    }

    /**
     * 批量移交客户申请
     */
    @ApiOperation(value = "批量新增移交客户申请", notes = "传入建档客户移交信息")
    @PostMapping(value = "/moveBatch")
    public AjaxResult moveBatch(@RequestBody List<FilingCustomerTransfer> list) {
        return AjaxResult.success(filingCustomerTransferService.moveBatch(list));
    }

    /**
     * 批量审批客户申请
     */
    @ApiOperation(value = "批量审批客户申请", notes = "传入建档客户移交信息")
    @PostMapping(value = "/judgeBatch")
    public AjaxResult judgeBatch(@RequestBody List<FilingCustomerTransfer> list) {
        return AjaxResult.success(filingCustomerTransferService.judgeBatch(list));
    }

    /**
     * @param transfer 1-我的审批 2-我的申请 3-我的接收
     * @return
     */
    @PostMapping(value = "/pageList")
    @ApiOperation(value = "审批、申请、接收", notes = "传入建档客户移交审批表")
    public TableDataInfo pageList(@RequestBody FilingCustomerTransfer transfer) {
        startPage(transfer);
        SysUser sysUser = tokenService.getLoginUser(ServletUtils.getRequest()).getSysUser();
        switch (transfer.getPageType()) {
            case 1:
                //客户经理没有权限查看审批列表
                Long highestLevelDeptId = DataScopeUtil.getHighestLevelDeptId(sysUser);
                if (highestLevelDeptId == null) {
                    return new TableDataInfo();
                }
                transfer.setMoveJudgeDeptId(DataScopeUtil.getHighestLevelDeptId(sysUser));
                break;
            case 2:
                transfer.setMoveOutManagerCode(sysUser.getManagerNum());
                break;
            case 3:
                transfer.setMoveInManagerCode(sysUser.getManagerNum());
                break;
            default:
                break;
        }
        List<FilingCustomerTransfer> list = filingCustomerTransferService.list(transfer);
        return getDataTable(list);
    }


    @GetMapping(value = "/moveList")
    @ApiOperation(value = "分页", notes = "传入建档客户移交审批表")
    public TableDataInfo moveList(FilingCustomerTransfer filingCustomerTransfer) {
        startPage();
        List<FilingCustomerTransfer> list = filingCustomerTransferService.list(filingCustomerTransfer);
        return getDataTable(list);
    }

    @GetMapping(value = "/recordList")
    @ApiOperation(value = "获取建档客户导入记录", notes = "传入建档客户导入记录表")
    public TableDataInfo recordList(FilingImportRecord filingImportRecord) {
        startPage();
        List<FilingImportRecord> list = filingImportRecordService.list(filingImportRecord);
        return getDataTable(list);
    }

    @ApiOperation(value = "批量导入建档客户")
    @PostMapping(value = "/importBatch")
    public AjaxResult importBatch(MultipartFile file, HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        filingCustomerInfoService.importBatch(inputStream, request);
        return AjaxResult.success("ok");
    }

    /**
     * 建档完成，建档客户数据绑定网格编码，信息完整度
     * 基础信息同步至档案、建档信息同步锚点网格编码、同步网格名称
     */
    @Scheduled(cron = "0 0 1 * * ?")
    @PostMapping(value = "/synfilingStatistics")
    public AjaxResult filingStatistics() throws JsonProcessingException {
        filingCustomerInfoService.filingStatistics();
        return AjaxResult.success("ok");
    }


    @ApiOperation(value = "根据客户id获取当前开启的模板id")
    @GetMapping(value = "/getModelIdByCustomId")
    public AjaxResult getModelIdByCustomId(Long clientId) {
        return AjaxResult.success(filingCustomerInfoService.getModelIdByCustomId(clientId));
    }

}
