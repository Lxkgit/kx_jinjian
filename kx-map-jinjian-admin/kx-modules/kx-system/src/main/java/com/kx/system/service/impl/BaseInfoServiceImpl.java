package com.kx.system.service.impl;

import com.kx.system.domain.*;
import com.kx.system.mapper.*;
import com.kx.system.service.BaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("baseInfoService")
public class BaseInfoServiceImpl implements BaseInfoService {
    @Autowired
    private PaCustomInfoMapper paCustomInfoMapper;
    @Autowired
    private PaCompanyInfoMapper paCompanyInfoMapper;
    @Autowired
    private PaBusinessInfoMapper paBusinessInfoMapper;
    @Autowired
    private PaManagerBaseInfoMapper paManagerBaseInfoMapper;
    @Autowired
    private CustomInfoMapper customInfoMapper;

    @Override
    public PaCustomInfo getCustomBynm(String nm) {
        return paCustomInfoMapper.selectByNm(nm);
    }

    @Override
    public PaCompanyInfo getCompanyByNm(String nm) {
        return paCompanyInfoMapper.selectPaCompanyInfoAndBelongToByNm(nm);
    }

    @Override
    public PaBusinessInfo getBusinessByNm(String nm) {
        return paBusinessInfoMapper.selectByNm(nm);
    }

    @Override
    public PaManagerBaseInfo getManagerByManagerId(String managerId) {
        return paManagerBaseInfoMapper.selectBaseInfoByManagerCode(managerId);
    }

    @Override
    public CustomInfoVo queryCustomerInfoByNm(String nm) {
        CustomInfoVo vo=new CustomInfoVo();
        List<CustomInfoVo> list= customInfoMapper.queryCustomerInfoByNm(nm);
        if (list!=null && list.size()>0){
            vo=list.get(0);
        }
        return vo;
    }
}
