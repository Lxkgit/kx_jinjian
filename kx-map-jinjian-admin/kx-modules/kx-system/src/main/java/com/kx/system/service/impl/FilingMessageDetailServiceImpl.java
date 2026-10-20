package com.kx.system.service.impl;


import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.enums.TaskObjectEnum;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.customer.CustomerInfo;
import com.kx.system.domain.filing.FilingMessageDetail;
import com.kx.system.mapper.CustomerInfoMapper;
import com.kx.system.mapper.FilingMessageDetailMapper;
import com.kx.system.service.FilingMessageDetailService;
import com.kx.system.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
@Slf4j
@Service
public class FilingMessageDetailServiceImpl implements FilingMessageDetailService {

    @Resource
    private FilingMessageDetailMapper filingMessageDetailMapper;
    @Resource
    private CustomerInfoMapper customerInfoMapper;

    @Override
    public List<FilingMessageDetail> list(FilingMessageDetail filingMessageDetail) {
        List<FilingMessageDetail> list = filingMessageDetailMapper.list(filingMessageDetail);
        return list;
    }

    @Override
    public FilingMessageDetail getById(Long id) {
        return filingMessageDetailMapper.getById(id);
    }

    @Override
    public FilingMessageDetail saveFilingMessageDetail(FilingMessageDetail filingMessageDetail) {
        FilingMessageDetail existFilingMessageDetail = null;
        if (filingMessageDetail.getId() != null) {
            existFilingMessageDetail = filingMessageDetailMapper.getById(filingMessageDetail.getId());
        } else if (filingMessageDetail.getFilingId() != null && filingMessageDetail.getModelId() != null) {
            //建档id和模版id确定一个唯一值
            existFilingMessageDetail = filingMessageDetailMapper.getByModelIdAndFilingId(filingMessageDetail.getModelId(), filingMessageDetail.getFilingId());
        }
        if (existFilingMessageDetail == null) {
            filingMessageDetailMapper.insertFilingMessageDetail(filingMessageDetail);
        } else {
            BeanUtils.copyBeanIgnoreNull(filingMessageDetail, existFilingMessageDetail);
            filingMessageDetailMapper.updateFilingMessageDetail(existFilingMessageDetail);
        }
        return filingMessageDetail;
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

    @Override
    public String deleteByFilingIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessValidationException("参数不能为空");
        }
        String[] idsArr = ids.split(",");
        if (idsArr == null || idsArr.length == 0) {
            throw new BusinessValidationException("参数不能为空");
        }
        for (int i = 0; i < idsArr.length; i++) {
            deleteByFilingId(Long.valueOf(idsArr[i]));
        }
        return "ok";
    }

    public void deleteByFilingId(Long id) {
        filingMessageDetailMapper.deleteByFilingId(id);
    }


    public void deleteById(Long id) {
        filingMessageDetailMapper.deleteById(id);
    }

    @Override
    public List<String> listByMap(HashMap<String, String> map, String managerCode, String modelId, String networkCode) {
        return filingMessageDetailMapper.listByMap(map, managerCode, modelId, networkCode);
    }

    @Override
    public Object getByCustomerId(Long customerId, Long modelId) {
        //查询客户管理主表客户信息
        CustomerInfo customer = customerInfoMapper.selectCustomerInfoById(customerId);
        if (StringUtils.isNull(customer)) {
            throw new BusinessValidationException("当前客户不存在");
        }
        String identity = "";
        if (Objects.equals(customer.getCustomerType(), TaskObjectEnum.CUSTOM.getCode())) {
            // 个人
            identity = customer.getCardNum();
        } else {
            // 企业、商户
            identity = customer.getCreditCode();
        }
        //查询档案
        FilingMessageDetail message = filingMessageDetailMapper.getByIdentityAndModelId(identity, modelId);
        if (message == null || message.getJsonMessage() == null) {
            return "";
        }
        return message.getJsonMessage();
    }


}
