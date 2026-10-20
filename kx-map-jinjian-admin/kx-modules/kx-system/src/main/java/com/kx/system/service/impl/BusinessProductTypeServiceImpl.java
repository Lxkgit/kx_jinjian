package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.business.BusinessProductType;
import com.kx.system.mapper.BusinessProductTypeMapper;
import com.kx.system.service.BusinessProductTypeService;
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
 * @since 2024-03-26
 */
@Slf4j
@Service
public class BusinessProductTypeServiceImpl implements BusinessProductTypeService {

  @Autowired
  private BusinessProductTypeMapper businessProductTypeMapper;

    @Override
    public List<BusinessProductType> list(BusinessProductType businessProductType) {
        List<BusinessProductType> list = businessProductTypeMapper.list(businessProductType);
        return list;
    }

    @Override
    public BusinessProductType getById(Long id) {
        return businessProductTypeMapper.getById(id);
    }

    @Override
    public BusinessProductType saveBusinessProductType(BusinessProductType businessProductType) {
        BusinessProductType existBusinessProductType=null;
        if(businessProductType.getId()!=null){
            existBusinessProductType=businessProductTypeMapper.getById(businessProductType.getId());
        }
        if(existBusinessProductType ==null){
            businessProductType.setCreateTime(DateUtils.getNowDate());
            businessProductType.setUpdateTime(DateUtils.getNowDate());
            businessProductTypeMapper.insertBusinessProductType(businessProductType);
        }else{
            BeanUtils.copyBeanIgnoreNull(businessProductType,existBusinessProductType);
            existBusinessProductType.setUpdateTime(DateUtils.getNowDate());
            businessProductTypeMapper.updateBusinessProductType(existBusinessProductType);
        }
        return businessProductType;
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
        businessProductTypeMapper.deleteById(id);
    }

}
