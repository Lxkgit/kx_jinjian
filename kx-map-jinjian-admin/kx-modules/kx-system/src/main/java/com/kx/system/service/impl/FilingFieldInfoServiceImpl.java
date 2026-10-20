package com.kx.system.service.impl;

import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.filing.FilingFieldInfo;
import com.kx.system.mapper.FilingFieldInfoMapper;
import com.kx.system.service.FilingFieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.util.BeanUtils;
import com.kx.common.core.utils.DateUtils;

import java.util.List;


@Service
public class FilingFieldInfoServiceImpl implements FilingFieldInfoService {

    @Autowired
    private FilingFieldInfoMapper filingFieldInfoMapper;

    @Override
    public List<FilingFieldInfo> list(FilingFieldInfo filingFieldInfo) {
        List<FilingFieldInfo> list = filingFieldInfoMapper.list(filingFieldInfo);
        return list;
    }

    @Override
    public FilingFieldInfo getById(Long id) {
        return filingFieldInfoMapper.getById(id);
    }

    @Override
    public FilingFieldInfo saveFilingFieldInfo(FilingFieldInfo filingFieldInfo) {
        FilingFieldInfo existFilingFieldInfo = null;
        if (filingFieldInfo.getId() != null) {
            existFilingFieldInfo = filingFieldInfoMapper.getById(filingFieldInfo.getId());
        }
        if (existFilingFieldInfo == null) {
            filingFieldInfo.setCreateTime(DateUtils.getNowDate());
            filingFieldInfo.setUpdateTime(DateUtils.getNowDate());
            filingFieldInfoMapper.insertFilingFieldInfo(filingFieldInfo);
        } else {
            BeanUtils.copyBeanIgnoreNull(filingFieldInfo,existFilingFieldInfo);
            existFilingFieldInfo.setUpdateTime(DateUtils.getNowDate());
            filingFieldInfoMapper.updateFilingFieldInfo(existFilingFieldInfo);
        }
        return filingFieldInfo;
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
        filingFieldInfoMapper.deleteById(id);
    }

    @Override
    public List<FilingFieldInfo> getListByModelId(Long modelId) {
        FilingFieldInfo field=new FilingFieldInfo();
        field.setModelId(modelId);
        return filingFieldInfoMapper.list(field);
    }

    @Override
    public FilingFieldInfo getByEnglishNameAndModelId(String englishName, Long modelId) {
        return filingFieldInfoMapper.getByEnglishNameAndModelId(englishName,modelId);
    }

    @Override
    public void deleteByModelId(Long modelId) {
        filingFieldInfoMapper.deleteByModelId(modelId);
    }

    @Override
    public void deleteByModelIdAndEnglishName(Long modelId, String englishName) {
        filingFieldInfoMapper.deleteByModelIdAndEnglishName(modelId,englishName);
    }

}
