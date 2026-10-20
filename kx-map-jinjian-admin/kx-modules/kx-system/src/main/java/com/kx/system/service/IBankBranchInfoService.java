package com.kx.system.service;

import com.kx.system.domain.BankBranchInfo;

import java.util.List;

/**
 * 银行网点服务接口
 */
public interface IBankBranchInfoService {

    /**
     * 按行政区划代码查询银行网点
     *
     * @param areaCode        行政区划代码（省330000 / 市330100 / 区330102 / 街道330102001 / 村330102001001）
     * @param includeChildren true=本级及下辖，false=仅本级
     * @param bankType        银行类型枚举code（可为空，为空则不过滤），见 BankTypeEnum
     */
    List<BankBranchInfo> selectByAreaCode(String areaCode, boolean includeChildren, Integer bankType);
}