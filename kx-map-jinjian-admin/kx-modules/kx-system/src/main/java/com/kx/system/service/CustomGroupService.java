package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.customer.CustomGroupDTO;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.CustomGroupPageDTO;
import com.kx.system.domain.dto.CustomerInfoDTO;
import com.kx.system.domain.group.CustomGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kx.system.domain.group.CustomGroupCustomer;
import com.kx.system.domain.group.CustomerGroupCategory;
import com.kx.system.domain.req.CustomGroupPageReq;
import com.kx.system.domain.req.CustomGroupReq;
import com.kx.system.domain.req.GroupManageReq;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
* @author 86176
* @description 针对表【custom_group(客群)】的数据库操作Service
* @createDate 2024-01-18 11:08:03
*/
public interface CustomGroupService {

    TableDataInfo queryPage(CustomGroupPageReq req);

    TableDataInfo listNotPage(CustomGroupPageReq req);

    AjaxResult addOrEditGroup(CustomGroupReq req);

    AjaxResult delGroup(Long id);

    AjaxResult delGroupList(GroupManageReq req);

    TableDataInfo manageCustomerPage(GroupManageReq req);

    List<CustomerInfoDTO> managePage(GroupManageReq req);

    AjaxResult removeMember(GroupManageReq req);

    AjaxResult addMember(GroupManageReq req);

    AjaxResult cascadeLabel(Long id);

    List<CustomGroup> getChildCustomGroup(Integer type);

    /**
     * 保存客群对应的客户
     * @param customGroupDTO
     * @return
     */
    AjaxResult saveCustomGroup(CustomGroupDTO customGroupDTO);

    /**
     * 根据客群 IdList 取出其中客户的 IdList
     * @param groupIdList
     * @return
     */
    List<Long> getCustomerIdList(List<Long> groupIdList);

    AjaxResult saveGroup(PerformanceCustomerInfo customerInfo);
}
