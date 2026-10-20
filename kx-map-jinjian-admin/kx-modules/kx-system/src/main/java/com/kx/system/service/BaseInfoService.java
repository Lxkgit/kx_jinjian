package com.kx.system.service;


import com.kx.system.domain.*;

public interface BaseInfoService {
    PaCustomInfo getCustomBynm(String nm);

    /**
     * 根据内码查询对公客户数据
     * @param nm
     * @return
     */
    PaCompanyInfo getCompanyByNm(String nm);

    /**
     * 根据内码查询商户数据
     * @param nm
     * @return
     */
    PaBusinessInfo getBusinessByNm(String nm);
    PaManagerBaseInfo getManagerByManagerId(String managerId);
    CustomInfoVo queryCustomerInfoByNm(String nm);
}
