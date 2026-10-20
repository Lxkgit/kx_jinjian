package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.business.BusinessOpportunity;
import com.kx.system.mapper.BusinessOpportunityMapper;
import com.kx.system.service.BusinessOpportunityService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-12
 */
@Slf4j
@Service
public class BusinessOpportunityServiceImpl implements BusinessOpportunityService {

  @Autowired
  private BusinessOpportunityMapper businessOpportunityMapper;

    @Override
    public List<BusinessOpportunity> list(BusinessOpportunity businessOpportunity) {
        List<BusinessOpportunity> list = businessOpportunityMapper.list(businessOpportunity);
        return list;
    }

    @Override
    public BusinessOpportunity getById(Long id) {
        return businessOpportunityMapper.getById(id);
    }

    @Override
    public BusinessOpportunity saveBusinessOpportunity(BusinessOpportunity businessOpportunity) {
        BusinessOpportunity existBusinessOpportunity=null;
        if(businessOpportunity.getId()!=null){
            existBusinessOpportunity=businessOpportunityMapper.getById(businessOpportunity.getId());
        }
        if(existBusinessOpportunity ==null){
            businessOpportunity.setCreateTime(DateUtils.getNowDate());
            businessOpportunity.setUpdateTime(DateUtils.getNowDate());
            businessOpportunityMapper.insertBusinessOpportunity(businessOpportunity);
        }else{
            BeanUtils.copyBeanIgnoreNull(businessOpportunity,existBusinessOpportunity);
            existBusinessOpportunity.setUpdateTime(DateUtils.getNowDate());
            businessOpportunityMapper.updateBusinessOpportunity(existBusinessOpportunity);
        }
        return businessOpportunity;
    }

    @Override
    public String deleteByIds(String ids) {
        if (StringUtils.isEmpty(ids)){
            throw new BusinessValidationException("参数不能为空");
        }
        String[] idsArr=ids.split(",");
        if (idsArr==null || idsArr.length==0){
            throw new BusinessValidationException("参数不能为空");
        }
        for (int i = 0; i < idsArr.length; i++) {
            deleteById(Long.valueOf(idsArr[i]));
        }
        return "ok";
    }

    public void deleteById(Long id) {
        businessOpportunityMapper.deleteById(id);
    }

}
