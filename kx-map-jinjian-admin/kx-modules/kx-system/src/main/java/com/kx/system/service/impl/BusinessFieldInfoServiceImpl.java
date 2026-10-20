package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.business.BusinessFieldInfo;
import com.kx.system.mapper.BusinessFieldInfoMapper;
import com.kx.system.service.BusinessFieldInfoService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 * 商机产品反馈字段表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
@Slf4j
@Service
public class BusinessFieldInfoServiceImpl implements BusinessFieldInfoService {

  @Autowired
  private BusinessFieldInfoMapper businessFieldInfoMapper;

    @Override
    public List<BusinessFieldInfo> list(BusinessFieldInfo businessFieldInfo) {
        List<BusinessFieldInfo> list = businessFieldInfoMapper.list(businessFieldInfo);
        return list;
    }

    @Override
    public BusinessFieldInfo getById(Long id) {
        return businessFieldInfoMapper.getById(id);
    }

    @Override
    public BusinessFieldInfo saveBusinessFieldInfo(BusinessFieldInfo businessFieldInfo) {
        BusinessFieldInfo existBusinessFieldInfo=null;
        if(businessFieldInfo.getId()!=null){
            existBusinessFieldInfo=businessFieldInfoMapper.getById(businessFieldInfo.getId());
        }
        if(existBusinessFieldInfo ==null){
            businessFieldInfo.setCreateTime(DateUtils.getNowDate());
            businessFieldInfo.setUpdateTime(DateUtils.getNowDate());
            businessFieldInfoMapper.insertBusinessFieldInfo(businessFieldInfo);
        }else{
            BeanUtils.copyBeanIgnoreNull(businessFieldInfo,existBusinessFieldInfo);
            existBusinessFieldInfo.setUpdateTime(DateUtils.getNowDate());
            businessFieldInfoMapper.updateBusinessFieldInfo(existBusinessFieldInfo);
        }
        return businessFieldInfo;
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
        businessFieldInfoMapper.deleteById(id);
    }

}
