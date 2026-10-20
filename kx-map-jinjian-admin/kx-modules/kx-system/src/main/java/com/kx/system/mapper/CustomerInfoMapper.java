package com.kx.system.mapper;

import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.DeptList;
import com.kx.system.domain.customer.CustomerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kx.system.domain.req.DeptIdReq;
import com.kx.system.domain.vo.TaskManagerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86176
* @description 针对表【customer_info(客户信息主表)】的数据库操作Mapper
* @createDate 2024-01-18 11:13:45
* @Entity com.kx.system.domain.customer.CustomerInfo
*/
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo> {

    List<CustomerInfo> selectCustomerInfoList(CustomerInfo customerInfo);

    List<CustomerInfo> selectCustomerGroupInfoList(@Param("customerInfo") CustomerInfo customerInfo, @Param("ids") List<Long> ids);

    CustomerInfo selectCustomerInfoById(@Param("customerId") Long customerId);

    void updateCustomerInfos(@Param("belongGrid") Integer belongGrid, @Param("dutyDeptId") Integer dutyDeptId, @Param("customerIds") List<Long> customerIds);

    void updateCustomerInfo(CustomerInfo customerInfo);

    List<DeptList> selectDeptIdByCustomerIds(@Param("customerIdList") List<Long> customerIdList);

    List<TaskManagerVO> queryBusinessManage(@Param("deptIdReq") DeptIdReq deptIdReq);

    String selectBelongGridName(@Param("belongGridCode") String belongGridCode);
}




