package com.kx.system.service;

import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.domain.param.CreditHeatMapParam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 授信客户信息Service接口
 *
 * @author kangxu
 * @date 2024-04-01
 */
public interface ICreditCustomerInfoService {
    /**
     * 查询授信客户信息
     *
     * @param customNm 授信客户信息主键
     * @return 授信客户信息
     */
    CreditCustomerInfo selectCreditCustomerInfoByCustomNm(String customNm);

    /**
     * 查询授信客户信息列表
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 授信客户信息集合
     */
    List<CreditCustomerInfo> selectCreditCustomerInfoList(CreditCustomerInfo creditCustomerInfo);

    TableDataInfo esCreditCustomerInfoList(CreditCustomerInfo creditCustomerInfo, LoginUser loginUser);

    /**
     * 新增授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    int insertCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo);

    /**
     * 修改授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    int updateCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo);

    void insertBatch(List<CreditCustomerInfo> creditCustomerInfos, CountDownLatch countDownLatch);

    /**
     * 查询授信客户热力图
     *
     * @param param
     * @return
     */
    Map<String, Integer> heatMap(CreditHeatMapParam param);
}
