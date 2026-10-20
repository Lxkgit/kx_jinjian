package com.kx.system.mapper;

import com.kx.system.domain.group.CustomerGroupCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86176
* @description 针对表【customer_group_category(客群分类)】的数据库操作Mapper
* @createDate 2024-01-29 16:42:04
* @Entity com.kx.system.domain.group.CustomerGroupCategory
*/
public interface CustomerGroupCategoryMapper extends BaseMapper<CustomerGroupCategory> {

    void updateCustomerGroupCategory(@Param("customerGroupCategory") CustomerGroupCategory customerGroupCategory);

    void insertCustomerGroupCategory(@Param("customerGroupCategory") CustomerGroupCategory customerGroupCategory);

    CustomerGroupCategory selectCustomerGroupCategoryById(@Param("id") Long id);

    void deleteCustomerGroupCategoryById(@Param("id") Long id);

    CustomerGroupCategory selectExist(@Param("categoryName") String categoryName, @Param("parentId") Long parentId);

    List<CustomerGroupCategory> selectAll();

    List<CustomerGroupCategory> selectByParentId(Long parentId);
}




