package com.kx.system.service.impl;

import com.kx.system.domain.group.CustomGroupCustomer;
import com.kx.system.mapper.CustomGroupCustomerMapper;
import com.kx.system.service.ICustomGroupCustomerService;
import com.kx.system.util.ListSplitUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客群客户对应Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-09
 */
@Service
public class CustomGroupCustomerServiceImpl implements ICustomGroupCustomerService {
    @Resource
    private CustomGroupCustomerMapper customGroupCustomerMapper;

    /**
     * 查询客群客户对应
     *
     * @param id 客群客户对应主键
     * @return 客群客户对应
     */
    @Override
    public CustomGroupCustomer selectCustomGroupCustomerById(Long id) {
        return customGroupCustomerMapper.selectCustomGroupCustomerById(id);
    }

    /**
     * 查询客群客户对应列表
     *
     * @param customGroupCustomer 客群客户对应
     * @return 客群客户对应
     */
    @Override
    public List<CustomGroupCustomer> selectCustomGroupCustomerList(CustomGroupCustomer customGroupCustomer) {
        return customGroupCustomerMapper.selectCustomGroupCustomerList(customGroupCustomer);
    }

    @Override
    public int selectCustomGroupCustomerCount(CustomGroupCustomer customGroupCustomer) {
        return customGroupCustomerMapper.selectCustomGroupCustomerCount(customGroupCustomer);
    }

    /**
     * 修改客群客户对应
     *
     * @param customGroupCustomer 客群客户对应
     * @return 结果
     */
    @Override
    public int updateCustomGroupCustomer(CustomGroupCustomer customGroupCustomer) {
        return customGroupCustomerMapper.updateCustomGroupCustomer(customGroupCustomer);
    }

    @Override
    public void deleteAlll(CustomGroupCustomer customGroupCustomer) {
        customGroupCustomerMapper.deleteAlll(customGroupCustomer);
    }

    @Override
    public void saveBatch(List<CustomGroupCustomer> customGroupCustomer) {
        ListSplitUtils.split(customGroupCustomer, 1000, customGroupCustomerMapper::saveBatch);
    }

    /**
     * 批量删除客群客户对应
     *
     * @param ids 需要删除的客群客户对应主键
     * @return 结果
     */
    @Override
    public int deleteCustomGroupCustomerByIds(Long[] ids) {
        return customGroupCustomerMapper.deleteCustomGroupCustomerByIds(ids);
    }

    /**
     * 删除客群客户对应信息
     *
     * @param id 客群客户对应主键
     * @return 结果
     */
    @Override
    public int deleteCustomGroupCustomerById(Long id) {
        return customGroupCustomerMapper.deleteCustomGroupCustomerById(id);
    }
}
