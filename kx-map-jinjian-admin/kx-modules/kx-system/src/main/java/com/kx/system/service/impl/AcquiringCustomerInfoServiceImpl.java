package com.kx.system.service.impl;

import java.util.List;

import com.kx.system.domain.customer.AcquiringCustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.AcquiringCustomerInfoMapper;
import com.kx.system.service.IAcquiringCustomerInfoService;

/**
 * 收单客户信息Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Service
public class AcquiringCustomerInfoServiceImpl implements IAcquiringCustomerInfoService
{
    @Autowired
    private AcquiringCustomerInfoMapper acquiringCustomerInfoMapper;

    /**
     * 查询收单客户信息
     *
     * @param id 收单客户信息主键
     * @return 收单客户信息
     */
    @Override
    public AcquiringCustomerInfo selectAcquiringCustomerInfoById(Long id)
    {
        return acquiringCustomerInfoMapper.selectAcquiringCustomerInfoById(id);
    }

    /**
     * 查询收单客户信息列表
     *
     * @param acquiringCustomerInfo 收单客户信息
     * @return 收单客户信息
     */
    @Override
    public List<AcquiringCustomerInfo> selectAcquiringCustomerInfoList(AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return acquiringCustomerInfoMapper.selectAcquiringCustomerInfoList(acquiringCustomerInfo);
    }

    /**
     * 新增收单客户信息
     *
     * @param acquiringCustomerInfo 收单客户信息
     * @return 结果
     */
    @Override
    public int insertAcquiringCustomerInfo(AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return acquiringCustomerInfoMapper.insertAcquiringCustomerInfo(acquiringCustomerInfo);
    }

    /**
     * 修改收单客户信息
     *
     * @param acquiringCustomerInfo 收单客户信息
     * @return 结果
     */
    @Override
    public int updateAcquiringCustomerInfo(AcquiringCustomerInfo acquiringCustomerInfo)
    {
        return acquiringCustomerInfoMapper.updateAcquiringCustomerInfo(acquiringCustomerInfo);
    }

    /**
     * 批量删除收单客户信息
     *
     * @param ids 需要删除的收单客户信息主键
     * @return 结果
     */
    @Override
    public int deleteAcquiringCustomerInfoByIds(Long[] ids)
    {
        return acquiringCustomerInfoMapper.deleteAcquiringCustomerInfoByIds(ids);
    }

    /**
     * 删除收单客户信息信息
     *
     * @param id 收单客户信息主键
     * @return 结果
     */
    @Override
    public int deleteAcquiringCustomerInfoById(Long id)
    {
        return acquiringCustomerInfoMapper.deleteAcquiringCustomerInfoById(id);
    }
}
