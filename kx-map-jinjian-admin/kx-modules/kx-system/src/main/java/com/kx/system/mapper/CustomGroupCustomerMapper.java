package com.kx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kx.system.api.domain.SysDept;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerInfo;
import com.kx.system.domain.group.CustomGroup;
import com.kx.system.domain.group.CustomGroupCustomer;
//import com.kx.system.domain.task.PaTaskDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客群客户对应Mapper接口
 *
 * @author kangxu
 * @date 2024-04-09
 */
public interface CustomGroupCustomerMapper extends BaseMapper<CustomGroupCustomer>
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
     * 删除客群客户对应
     *
     * @param customGroupCustomer 客群客户对应
     * @return 结果
     */
    public void deleteAlll(@Param("customGroupCustomer") CustomGroupCustomer customGroupCustomer);

    /**
     * 客群客户对应数据集合
     *
     * @param customGroupCustomerList 客群客户对应数据集合
     * @return 结果
     */
    public int saveBatch(@Param("customGroupCustomerList") List<CustomGroupCustomer> customGroupCustomerList);

    /**
     * 删除客群客户对应
     *
     * @param id 客群客户对应主键
     * @return 结果
     */
    public int deleteCustomGroupCustomerById(Long id);

    /**
     * 批量删除客群客户对应
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomGroupCustomerByIds(Long[] ids);

	/**
     * 为某个客群新增客户
     * @param customGroupCustomer
     */
    void insertCustomGroupCustomer(@Param("customGroupCustomer")CustomGroupCustomer customGroupCustomer);

    /**
     * 根据客群id取出对应的客户ids
     * @param customGroupCustomer
     * @return
     */
    List<Long> selectCustomer(CustomGroupCustomer customGroupCustomer);

    /**
     * 根据客户ids删除对应的客群中的客户
     * @param customGroupCustomer
     */
    void deleteCustomGroupCustomer(CustomGroupCustomer customGroupCustomer);

    List<SysDept> selectCustomerByCustomGroups(@Param("customGroups") List<String> customGroups, @Param("type") Integer taskObjectId);

//    List<PaTaskDetail> selectCustomIdByGroupIds(@Param("customGroupIds") List<Long> customGroupIds, @Param("type") Integer taskObjectId);
}
