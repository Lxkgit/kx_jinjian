package com.kx.system.service;

import java.util.List;
import com.kx.system.domain.customer.AcquiringCustomerInfo;

/**
 * 收单客户信息Service接口
 * 
 * @author kangxu
 * @date 2024-04-01
 */
public interface IAcquiringCustomerInfoService 
{
    /**
     * 查询收单客户信息
     * 
     * @param id 收单客户信息主键
     * @return 收单客户信息
     */
    public AcquiringCustomerInfo selectAcquiringCustomerInfoById(Long id);

    /**
     * 查询收单客户信息列表
     * 
     * @param acquiringCustomerInfo 收单客户信息
     * @return 收单客户信息集合
     */
    public List<AcquiringCustomerInfo> selectAcquiringCustomerInfoList(AcquiringCustomerInfo acquiringCustomerInfo);

    /**
     * 新增收单客户信息
     * 
     * @param acquiringCustomerInfo 收单客户信息
     * @return 结果
     */
    public int insertAcquiringCustomerInfo(AcquiringCustomerInfo acquiringCustomerInfo);

    /**
     * 修改收单客户信息
     * 
     * @param acquiringCustomerInfo 收单客户信息
     * @return 结果
     */
    public int updateAcquiringCustomerInfo(AcquiringCustomerInfo acquiringCustomerInfo);

    /**
     * 批量删除收单客户信息
     * 
     * @param ids 需要删除的收单客户信息主键集合
     * @return 结果
     */
    public int deleteAcquiringCustomerInfoByIds(Long[] ids);

    /**
     * 删除收单客户信息信息
     * 
     * @param id 收单客户信息主键
     * @return 结果
     */
    public int deleteAcquiringCustomerInfoById(Long id);
}
