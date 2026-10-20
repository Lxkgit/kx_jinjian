package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.customer.CustomGroupDTO;
import com.kx.system.domain.dto.CustomerGroupCategoryReq;
import com.kx.system.domain.group.CustomGroupCustomer;
import com.kx.system.domain.group.CustomerGroupCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86176
* @description 针对表【customer_group_category(客群分类)】的数据库操作Service
* @createDate 2024-01-29 16:42:04
*/
public interface CustomerGroupCategoryService extends IService<CustomerGroupCategory> {

    AjaxResult addOrEdit(CustomerGroupCategoryReq req);

    AjaxResult delCategory(Long id);

    AjaxResult getCategory();

    /**
     * 异步保存客群对应客户的方法
     * @param CustomGroupDTO
     */
    void asyncSaveCustomGroup(CustomGroupDTO CustomGroupDTO, LoginUser loginUser);

    void saveGroup(List<Long> idList, Long id);
}
