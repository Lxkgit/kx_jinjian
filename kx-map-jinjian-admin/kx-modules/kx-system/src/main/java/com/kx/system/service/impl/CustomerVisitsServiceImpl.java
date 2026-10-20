package com.kx.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.customer.CustomerVisits;
import com.kx.system.domain.customer.VisitsManager;
import com.kx.system.domain.dto.VisitDTO;
import com.kx.system.domain.label.LabelInfo;
import com.kx.system.domain.param.ListVisitsManagerParam;
import com.kx.system.mapper.CustomerLabelMapper;
import com.kx.system.mapper.CustomerVisitsMapper;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.CustomerVisitsService;
import com.kx.system.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户走访信息表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Slf4j
@Service
public class CustomerVisitsServiceImpl implements CustomerVisitsService {

    @Resource
    private CustomerVisitsMapper customerVisitsMapper;

    @Resource
    private CustomerLabelMapper customerLabelMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<CustomerVisits> list(CustomerVisits customerVisits) {
        return customerVisitsMapper.list(customerVisits);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<VisitsManager> listGroupByManager(ListVisitsManagerParam param) {
        return customerVisitsMapper.listGroupByManager(param);
    }

    @Override
    public List<CustomerVisits> listByMangerCode(ListVisitsManagerParam param) {
        return customerVisitsMapper.listByMangerCode(param);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<VisitsManager> listExportGroupByManager(ListVisitsManagerParam param) {
        param.handlePageExportParam();
        return customerVisitsMapper.listExportGroupByManager(param);
    }

    @Override
    public List<CustomerVisits> listExportByMangerCode(ListVisitsManagerParam param) {
        param.handlePageExportParam();
        return customerVisitsMapper.listExportByMangerCode(param);
    }

    @Override
    public CustomerVisits getById(Long id) {
        return customerVisitsMapper.getById(id);
    }

    @Override
    public CustomerVisits saveCustomerVisits(CustomerVisits customerVisits) {
        CustomerVisits existCustomerVisits = null;
        if (customerVisits.getId() != null) {
            existCustomerVisits = customerVisitsMapper.getById(customerVisits.getId());
        }
        if (existCustomerVisits == null) {
            customerVisits.setCreateTime(DateUtils.getNowDate());
            customerVisits.setUpdateTime(DateUtils.getNowDate());
            customerVisitsMapper.insertCustomerVisits(customerVisits);
        } else {
            BeanUtils.copyBeanIgnoreNull(customerVisits, existCustomerVisits);
            existCustomerVisits.setUpdateTime(DateUtils.getNowDate());
            customerVisitsMapper.updateCustomerVisits(existCustomerVisits);
        }
        return customerVisits;
    }

    @Override
    public String deleteByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessValidationException("参数不能为空");
        }
        String[] idsArr = ids.split(",");
        if (idsArr == null || idsArr.length == 0) {
            throw new BusinessValidationException("参数不能为空");
        }
        for (int i = 0; i < idsArr.length; i++) {
            deleteById(Long.valueOf(idsArr[i]));
        }
        return "ok";
    }

    public void deleteById(Long id) {
        customerVisitsMapper.deleteById(id);
    }

    @Override
    public List<CustomerVisits> queryVisits(VisitDTO visitDTO) {
        CustomerVisits customerVisits = new CustomerVisits();
        customerVisits.setIdentity(visitDTO.getIdentity());
        customerVisits.setCustomerId(visitDTO.getCustomerId());
        customerVisits.setType(visitDTO.getType());
        customerVisits.setCustomerPhone(visitDTO.getPhone());
        List<CustomerVisits> list = customerVisitsMapper.list(customerVisits);
        return list;
    }

    /**
     * 新增客户走访信息
     *
     * @param customerVisits 客户走访信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCustomerVisits(CustomerVisits customerVisits) {

        SysUser sysUser = tokenService.getLoginUser(ServletUtils.getRequest()).getSysUser();
        SysUser user = sysUserMapper.selectUserById(sysUser.getUserId());
        SysDept dept = sysDeptMapper.selectDeptById(sysUser.getDept().getDeptId());

        customerVisits.setVisitsManager(user.getNickName());
        customerVisits.setVisitsManagerCode(user.getManagerNum());
        customerVisits.setVisitsOrg(dept.getDeptName());
        customerVisits.setVisitsOrgCode(dept.getOrgNum());

        customerVisits.setVisitsTime(DateUtils.getNowDate());
        customerVisits.setCreateTime(DateUtils.getNowDate());
        customerVisits.setUpdateTime(DateUtils.getNowDate());

        if (customerVisits.getLabels() != null && customerVisits.getLabels().size() > 0) {
            List<CustomerLabel> existLabels = customerLabelMapper.queryList(customerVisits.getCustomerId());
            Set<Long> existLabelIds = existLabels.stream().map(CustomerLabel::getLabelId).collect(Collectors.toSet());

            List<CustomerLabel> customerLabels = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (LabelInfo label : customerVisits.getLabels()) {
                names.add(label.getLabelName());
                if (existLabelIds.contains(label.getId())) {
                    continue;
                }
                CustomerLabel customerLabel = new CustomerLabel();
                customerLabel.setLabelId(label.getId());
                if (StringUtils.isNotEmpty(customerVisits.getCustomerNm())) {
                    customerLabel.setCustomerNm(customerVisits.getCustomerNm());
                }
                customerLabel.setCustomerId(customerVisits.getCustomerId());
                customerLabel.setType(Integer.valueOf(customerVisits.getType()));
                //网格标签
                customerLabel.setLabelType(0);
                customerLabels.add(customerLabel);

            }
            customerVisits.setVisitLabel(JSON.toJSONString(names));
            //插入客户标签关联表
            if (!CollectionUtils.isEmpty(customerLabels)) {
                customerLabelMapper.insertBatch(customerLabels);
            }
        }
        //根据经纬度解析走访地址
//        JSONObject geoResult = geocodingUtil.reGeoCode(customerVisits.getLocation());
//        String visitAddress = geoResult.getString("formatted_address");
//        customerVisits.setVisitsAddress(visitAddress);
        return customerVisitsMapper.insertCustomerVisits(customerVisits);
    }

}
