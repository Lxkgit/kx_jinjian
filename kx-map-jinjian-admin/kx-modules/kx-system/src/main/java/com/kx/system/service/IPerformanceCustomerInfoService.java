package com.kx.system.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.customer.BankType;
import com.kx.system.domain.customer.PerformanceCustomerInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 绩效客户信息Service接口
 *
 * @author kangxu
 * @date 2024-04-01
 */
public interface IPerformanceCustomerInfoService
{
    /**
     * 查询绩效客户信息
     *
     * @param id 绩效客户信息主键
     * @return 绩效客户信息
     */
    public PerformanceCustomerInfo selectPerformanceCustomerInfoById(Long id);

    /**
     * 查询绩效客户信息列表
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 绩效客户信息集合
     */
    public List<PerformanceCustomerInfo> selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo);
    TableDataInfo esQueryPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo , LoginUser loginUser);

    /**
     * 新增绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    public int insertPerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo);

    /**
     * 修改绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    public int updatePerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo);

    /**
     * 批量删除绩效客户信息
     *
     * @param ids 需要删除的绩效客户信息主键集合
     * @return 结果
     */
    public int deletePerformanceCustomerInfoByIds(Long[] ids);

    /**
     * 删除绩效客户信息信息
     *
     * @param id 绩效客户信息主键
     * @return 结果
     */
    public int deletePerformanceCustomerInfoById(Long id);

    void insertBatch(List<PerformanceCustomerInfo> customerInfos, CountDownLatch countDownLatch, int length, int i);

    List<PerformanceCustomerInfo> list(PerformanceCustomerInfo performanceCustomerInfo);


    List<BankType> listByBank(Long groupId);

    String modelDownload();

    void importBatch(InputStream inputStream, HttpServletRequest request);

    List<AnchorExport> selectAllAnchor( PerformanceCustomerInfo anchorExport);
}
