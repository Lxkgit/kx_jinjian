package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.business.BusinessDetailInfo;
import com.kx.system.mapper.BusinessDetailInfoMapper;
import com.kx.system.service.BusinessDetailInfoService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
/**
 * <p>
 * 反馈详情表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-26
 */
@Slf4j
@Service
public class BusinessDetailInfoServiceImpl implements BusinessDetailInfoService {

  @Autowired
  private BusinessDetailInfoMapper businessDetailInfoMapper;

    @Override
    public List<BusinessDetailInfo> list(BusinessDetailInfo businessDetailInfo) {
        List<BusinessDetailInfo> list = businessDetailInfoMapper.list(businessDetailInfo);
        return list;
    }

    @Override
    public BusinessDetailInfo getById(Long id) {
        return businessDetailInfoMapper.getById(id);
    }

    @Override
    public BusinessDetailInfo saveBusinessDetailInfo(BusinessDetailInfo businessDetailInfo) {
        BusinessDetailInfo existBusinessDetailInfo=null;
        if(businessDetailInfo.getId()!=null){
            existBusinessDetailInfo=businessDetailInfoMapper.getById(businessDetailInfo.getId());
        }
        if(existBusinessDetailInfo ==null){
            businessDetailInfo.setCreateTime(DateUtils.getNowDate());
            businessDetailInfo.setUpdateTime(DateUtils.getNowDate());
            businessDetailInfoMapper.insertBusinessDetailInfo(businessDetailInfo);
        }else{
            BeanUtils.copyBeanIgnoreNull(businessDetailInfo,existBusinessDetailInfo);
            existBusinessDetailInfo.setUpdateTime(DateUtils.getNowDate());
            businessDetailInfoMapper.updateBusinessDetailInfo(existBusinessDetailInfo);
        }
        return businessDetailInfo;
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
        businessDetailInfoMapper.deleteById(id);
    }

}
