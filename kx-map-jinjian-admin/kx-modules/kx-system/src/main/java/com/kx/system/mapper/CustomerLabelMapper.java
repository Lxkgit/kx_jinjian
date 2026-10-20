package com.kx.system.mapper;

import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.domain.customer.CustomerLabel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kx.system.domain.req.CustomGroupReq;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* @author 86176
* @description 针对表【customer_label(用户标签)】的数据库操作Mapper
* @createDate 2024-01-18 11:13:45
* @Entity com.kx.system.domain.customer.CustomerLabel
*/
public interface CustomerLabelMapper extends BaseMapper<CustomerLabel> {

    List<CustomerLabel> queryList(@Param("customerId") Long customerId);

    @DataScope(deptAlias = "u", userAlias = "u")
    List<CustomerLabel> queryListAndLabel(@Param("req") CustomGroupReq req);

    @DataScope(deptAlias = "u", userAlias = "u")
    List<CustomerLabel> queryListOrLabel(@Param("req") CustomGroupReq req);

    void insertCustomerLabel(@Param("customerLabel") CustomerLabel customerLabel);

    void delLabels(@Param("customerId") Long customerId, @Param("delLabelIds") List<Long> delLabelIds);

    void insertBatch(@Param("customerLabels") List<CustomerLabel> customerLabels);

    void deleteByCustomerIdAndCustomerType(@Param("customerId") Long customerId,@Param("customerType")String customerType);

    long getUsedCount(@Param("ids") List<Long> ids);

    void deleteByNmsAndType(@Param("nms") List<String> nms, @Param("type") String type);

    void updatePerCustomerIdByNmAndType();

    void updateCreCustomerIdByNmAndType();
}




