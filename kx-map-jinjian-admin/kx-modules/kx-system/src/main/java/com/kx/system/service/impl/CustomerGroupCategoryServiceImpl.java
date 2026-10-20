package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.customer.CustomGroupDTO;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.CustomerGroupCategoryDTO;
import com.kx.system.domain.dto.CustomerGroupCategoryReq;
import com.kx.system.domain.enums.RoleEnum;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.group.CustomGroup;
import com.kx.system.domain.group.CustomGroupCustomer;
import com.kx.system.domain.group.CustomerGroupCategory;
import com.kx.system.mapper.*;
import com.kx.system.service.CustomerGroupCategoryService;
import com.kx.system.service.ISysDeptService;
import com.kx.system.service.es.EsCustMemberService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 86176
 * @description 针对表【customer_group_category(客群分类)】的数据库操作Service实现
 * @createDate 2024-01-29 16:42:04
 */
@Service
public class CustomerGroupCategoryServiceImpl extends ServiceImpl<CustomerGroupCategoryMapper, CustomerGroupCategory>
        implements CustomerGroupCategoryService {

    @Resource
    private CustomerGroupCategoryMapper mapper;

    @Resource
    private CustomGroupMapper customGroupMapper;

    @Resource
    private CustomGroupCustomerMapper customGroupCustomerMapper;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;


    @Resource
    private EsCustMemberService esCustMemberService;
    @Override
    public AjaxResult addOrEdit(CustomerGroupCategoryReq req) {
        if (StrUtil.isEmpty(req.getCategoryName()) || null == req.getParentId()) {
            return AjaxResult.error("参数异常！");
        }
        CustomerGroupCategory customerGroupCategory1 = mapper.selectExist(req.getCategoryName(), req.getParentId());
        if (null != customerGroupCategory1) {
            return AjaxResult.error("该分类下已存在相同名称分类！");
        }
        CustomerGroupCategory customerGroupCategory = new CustomerGroupCategory();
        BeanUtil.copyProperties(req, customerGroupCategory);
        if (null == req.getId()) {
            mapper.insertCustomerGroupCategory(customerGroupCategory);
        } else {
            mapper.updateCustomerGroupCategory(customerGroupCategory);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult delCategory(Long id) {
        if (null == id) {
            return AjaxResult.error("参数异常!");
        }
        //基础分类不能删、有客群不能删
        CustomerGroupCategory customerGroupCategory = mapper.selectCustomerGroupCategoryById(id);
        if (null == customerGroupCategory) {
            return AjaxResult.error("参数异常!");
        }
        if (customerGroupCategory.getParentId().equals(0L)) {
            return AjaxResult.error("基础分类不能删除!");
        }
        CustomGroup customGroup = customGroupMapper.selectOneByCategoryId(id);
        if (null != customGroup) {
            return AjaxResult.error("该分类下存在群组，不能删除!");
        }
        mapper.deleteCustomerGroupCategoryById(id);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getCategory() {
        List<CustomerGroupCategoryDTO> dtoList = new ArrayList<>();
        List<CustomerGroupCategory> list = mapper.selectAll();
        if (CollectionUtil.isEmpty(list)) {
            return AjaxResult.success();
        }
        list.forEach(d -> {
            if (d.getParentId().equals(0L)) {
                CustomerGroupCategoryDTO dto = new CustomerGroupCategoryDTO();
                BeanUtil.copyProperties(d, dto);
                List<CustomerGroupCategory> collect = list.stream().filter(c -> c.getParentId().equals(d.getId())).collect(Collectors.toList());
                dto.setChild(collect);
                dtoList.add(dto);
            }
        });
        return AjaxResult.success(dtoList);
    }

    /**
     * 异步保存客群中客户的方法
     *
     * @param customGroupDTO
     */
    @Override
    @Async
    //异步+自定义AOP注解?
//    @DataScope(deptAlias = "u", userAlias = "u")
    public void asyncSaveCustomGroup(CustomGroupDTO customGroupDTO,LoginUser loginUser) {
        //客户 id 集合
        List<Long> idList = new ArrayList<>();
        CustomGroup customGroup = new CustomGroup();
        customGroup.setId(customGroupDTO.getCustomGroupId());
        //绩效客户
        if (customGroupDTO.getType().equals(1)) {
            PerformanceCustomerInfo customerInfo = new PerformanceCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            idList = performanceCustomerInfoMapper.selectPerformanceCustomerInfoIdList(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.PERFORMANCE.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.PERFORMANCE.getCode() + ""));
        }
        //授信客户
        if (customGroupDTO.getType().equals(2)) {
            CreditCustomerInfo customerInfo = new CreditCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            idList = creditCustomerInfoMapper.selectCreditCustomerInfoIdList(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.CREDIT.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.CREDIT.getCode() + ""));
        }
        //建档客户
        if (customGroupDTO.getType().equals(3)) {
            FilingCustomerInfo customerInfo = new FilingCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            idList = filingCustomerInfoMapper.listIds(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.FILING.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.FILING.getCode() + ""));
        }
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        BeanUtil.copyProperties(customGroupDTO, customGroupCustomer);
        customGroupCustomer.setCustomerIds(idList);
        try {
            batchInsertCustomGroupCustomer(customGroupCustomer);
            customGroup.setGroupStatus(1);
            customGroupMapper.updateStatusByGroupId(customGroup);
            esCustMemberService.insertBatchAsync(idList.size(),customGroup.getCustomType(),customGroup.getId());

        } catch (Exception e) {
            log.error("插入数据异常", e);
        }
    }

    @Override
    @Async
    public void saveGroup(List<Long> idList, Long id) {
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        customGroupCustomer.setCustomGroupId(id);
        customGroupCustomer.setCustomerIds(idList);
        batchInsertCustomGroupCustomer(customGroupCustomer);
        CustomGroup customGroup = new CustomGroup();
        customGroup.setGroupStatus(1);
        customGroup.setId(id);
        customGroupMapper.updateStatusByGroupId(customGroup);
    }

    private void batchInsertCustomGroupCustomer(CustomGroupCustomer customGroupCustomer) {
        CustomGroupCustomer temp = new CustomGroupCustomer();
        temp.setCustomGroupId(customGroupCustomer.getCustomGroupId());
        temp.setCustomerIds(new ArrayList<>());
        for (Long customerId : customGroupCustomer.getCustomerIds()) {
            temp.getCustomerIds().add(customerId);
            if (temp.getCustomerIds().size() == 100) {
                customGroupCustomerMapper.insertCustomGroupCustomer(temp);
                temp.getCustomerIds().clear();
            }
        }
        if (!CollectionUtils.isEmpty(temp.getCustomerIds())) {
            customGroupCustomerMapper.insertCustomGroupCustomer(temp);
        }
    }
}




