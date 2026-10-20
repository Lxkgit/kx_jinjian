package com.kx.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.kx.system.domain.group.CustomGroup;
import com.kx.system.domain.group.CustomGroupCustomer;
import java.util.List;

/**
 * 客群客户对应Service接口
 * 
 * @author kangxu
 * @date 2024-04-09
 */
public interface ICustomGroupCustomerService
{
    /**
     * 查询客群客户对应
     * 
     * @param id 客群客户对应主键
     * @return 客群客户对应
     */
    public CustomGroupCustomer selectCustomGroupCustomerById(Long id);

    /**
     * 查询客群客户对应列表
     * 
     * @param customGroupCustomer 客群客户对应
     * @return 客群客户对应集合
     */
    public List<CustomGroupCustomer> selectCustomGroupCustomerList(CustomGroupCustomer customGroupCustomer);

    public int selectCustomGroupCustomerCount(CustomGroupCustomer customGroupCustomer);

    /**
     * 修改客群客户对应
     * 
     * @param customGroupCustomer 客群客户对应
     * @return 结果
     */
    public int updateCustomGroupCustomer(CustomGroupCustomer customGroupCustomer);

    /**
     * 修改客群客户对应
     *
     * @param customGroupCustomer 客群客户对应
     * @return 结果
     */
    public void deleteAlll(CustomGroupCustomer customGroupCustomer);

    /**
     * 批量插入
     *
     * @param customGroupCustomer 客群客户对应
     * @return 结果
     */
    public void saveBatch(List<CustomGroupCustomer> customGroupCustomer);

    /**
     * 批量删除客群客户对应
     * 
     * @param ids 需要删除的客群客户对应主键集合
     * @return 结果
     */
    public int deleteCustomGroupCustomerByIds(Long[] ids);

    /**
     * 删除客群客户对应信息
     * 
     * @param id 客群客户对应主键
     * @return 结果
     */
    public int deleteCustomGroupCustomerById(Long id);
}
