package com.kx.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.poi.ExcelUtil;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.enums.filing.ModelTypeEnum;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingCustomerInfoVo;
import com.kx.system.domain.filing.FilingFieldInfo;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.domain.vo.FillingVillageTypeVo;
import com.kx.system.mapper.FilingModelInfoMapper;
import com.kx.system.service.FilingFieldInfoService;
import com.kx.system.service.FilingModelInfoService;
import com.kx.system.util.ValidationUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.util.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FilingModelInfoServiceImpl implements FilingModelInfoService {

    private static final String FILING_CUSTOMER_INFO = "{\"customerType\":\"例 个人客户 或 企业客户 或 商户客户\"," +
                                                        "\"customerName\":\"例 张三 或 XX有限公司\"," +
                                                        "\"customerAddress\":\"例 杭州市XX区XX街道XX路XX号 \"," +
                                                        "\"customerAddressType\":\"例 经营地址 或 工作地址 或 家庭地址 或 户籍地址\"," +
                                                        "\"customerPhone\":\"例 13378945612\"," +
                                                        "\"customerIdentity\":\"例 330xxxx 或 9xxxx\"," +
                                                        "\"customerSource\":\"例 客户介绍 或 第三方介绍 或 陌拜\"," +
                                                        "\"customerIntroducerIdentity\":\"例330xxxx 或 9xxxx\"," +
                                                        "\"filingManagerCode\":\"例 0001\"," +
                                                        "\"filingManagerName\":\"例 李四\"," +
                                                        "\"filingOrgCode\":\"例 10000\"," +
                                                        "\"filingOrgName\":\"例 杭州分行\"}";


    @Autowired
    private FilingModelInfoMapper filingModelInfoMapper;
    @Autowired
    private FilingFieldInfoService filingFieldInfoService;

    @Override
    public List<FilingModelInfo> list(FilingModelInfo filingModelInfo) {
        List<FilingModelInfo> list = filingModelInfoMapper.list(filingModelInfo);
        return list;
    }

    @Override
    public FilingModelInfo getById(Long id) {
        return filingModelInfoMapper.getById(id);
    }

    @Override
    public FilingModelInfo saveFilingModelInfo(FilingModelInfo filingModelInfo) {
        FilingModelInfo existFilingModelInfo = null;
        if (filingModelInfo.getId() != null) {
            existFilingModelInfo = filingModelInfoMapper.getById(filingModelInfo.getId());
        }
        if (existFilingModelInfo == null) {
            filingModelInfo.setCreateTime(DateUtils.getNowDate());
            filingModelInfo.setUpdateTime(DateUtils.getNowDate());
            filingModelInfoMapper.insertFilingModelInfo(filingModelInfo);
        } else {
            BeanUtils.copyBeanIgnoreNull(filingModelInfo,existFilingModelInfo);
            existFilingModelInfo.setUpdateTime(DateUtils.getNowDate());
            filingModelInfoMapper.updateFilingModelInfo(existFilingModelInfo);
        }
        return filingModelInfo;
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
        filingModelInfoMapper.deleteById(id);
    }

    @Override
    public FilingModelInfo countModelByCodeAndType(FilingModelInfo model) {
        return filingModelInfoMapper.countModelByCodeAndType(model);
    }

    @Override
    public List<FillingVillageTypeVo> listModelSelect() {
        return ModelTypeEnum.listModelTypeVo();
    }

    @Override
    public String startFilingModelInfo(Long id) {
        FilingModelInfo model = filingModelInfoMapper.getById(id);
        ValidationUtil.notNull(model, "模板不存在!");
        FilingModelInfo open = filingModelInfoMapper.countModelByCodeAndType(model);
        if (model.getStatus().equals("0")) {
            model.setStatus("1");
            if (open != null) {
                open.setStatus("0");
                this.saveFilingModelInfo(open);
            }
        } else {
            model.setStatus("0");
        }
        this.saveFilingModelInfo(model);
        return model.getStatus();
    }

    @Override
    public FilingModelInfo getFilingModelInfoById(Long id,String isFinishParam) {
        ValidationUtil.notNull(id, "请输入参数id");
        FilingModelInfo model = this.getById(id);
        ValidationUtil.notNull(model, "模板不存在!");
        List<FilingFieldInfo> fields = filingFieldInfoService.getListByModelId(model.getId());
        fields = fields.stream().filter(s->s.getIsFinish().equals(isFinishParam)).collect(Collectors.toList());
        model.setFields(fields);
        return model;
    }

    @Override
    public String editFilingModelInfo(FilingModelInfo filingModelInfo) {
        Long modelId = 0L;
        if (filingModelInfo.getId() == null) {
            filingModelInfo.setStatus("0");
            FilingModelInfo modelInfo = this.saveFilingModelInfo(filingModelInfo);
            modelId=modelInfo.getId();
            if (StringUtils.isNotEmpty(filingModelInfo.getFields())) {
                for (FilingFieldInfo field : filingModelInfo.getFields()) {
                    FilingFieldInfo fieldeExist = filingFieldInfoService.getByEnglishNameAndModelId(field.getEnglishName(), filingModelInfo.getId());
                    if (fieldeExist != null) {
                        BeanUtils.copyBeanIgnoreNull(field, fieldeExist);
                        fieldeExist.setUseType(filingModelInfo.getModelType());
                        filingFieldInfoService.saveFilingFieldInfo(fieldeExist);
                    } else {
                        field.setModelId(filingModelInfo.getId());
                        field.setUseType(filingModelInfo.getModelType());
                        filingFieldInfoService.saveFilingFieldInfo(field);
                    }
                }
            }
        } else {
            FilingModelInfo model = this.getById(filingModelInfo.getId());
            ValidationUtil.notNull(model, "模板不存在!");
            modelId=model.getId();
            BeanUtils.copyBeanIgnoreNull(filingModelInfo, model);
            this.saveFilingModelInfo(model);
            if (StringUtils.isNotEmpty(filingModelInfo.getFields())) {
                List<String> newFields = filingModelInfo.getFields().stream().map(FilingFieldInfo::getEnglishName).collect(Collectors.toList());
                List<FilingFieldInfo> oldField = filingFieldInfoService.getListByModelId(model.getId());
                oldField = oldField.stream().filter(s -> s.getIsFinish().equals(filingModelInfo.getIsFinishParam())).collect(Collectors.toList());
                List<String> olds = oldField.stream().map(FilingFieldInfo::getEnglishName).collect(Collectors.toList());
                for (FilingFieldInfo field : filingModelInfo.getFields()) {
                    FilingFieldInfo fieldeExist = filingFieldInfoService.getByEnglishNameAndModelId(field.getEnglishName(), filingModelInfo.getId());
                    if (fieldeExist != null) {
                        BeanUtils.copyBeanIgnoreNull(field, fieldeExist);
                        filingFieldInfoService.saveFilingFieldInfo(fieldeExist);
                    } else {
                        field.setModelId(model.getId());
                        filingFieldInfoService.saveFilingFieldInfo(field);
                    }
                }
                /**删除的字段*/
                olds.removeAll(newFields);
                if (StringUtils.isNotEmpty(olds)) {
                    for (String englishName : olds) {
                        filingFieldInfoService.deleteByModelIdAndEnglishName(model.getId(), englishName);
                    }
                }
            }
        }
        return modelId.toString();
    }

    @Override
    public String deleteFilingModelInfo(Long id) {
        ValidationUtil.notNull(id, "请输入参数id");
        FilingModelInfo model = this.getById(id);
        if ("1".equals(model.getStatus())){
            throw new BusinessValidationException("该模板为开启状态无法删除");
        }
        filingFieldInfoService.deleteById(id);
        deleteById(id);
        return "ok";
    }

    @Override
    public FilingModelInfo getCustomerProfileHeaders(String customType) {
        FilingModelInfo filingModelInfo = new FilingModelInfo();
        filingModelInfo.setStatus("1");
        filingModelInfo.setModelType(customType);
        List<FilingModelInfo> list = filingModelInfoMapper.list(filingModelInfo);
        if (StringUtils.isNotEmpty(list)){
            filingModelInfo = list.get(0);
            List<FilingFieldInfo> fields = filingFieldInfoService.getListByModelId(filingModelInfo.getId());
            filingModelInfo.setFields(fields);
        }
        return filingModelInfo;
    }



    @Override
    public String modelDownload() {
        String fileName = "建档客户信息模板.xlsx";
        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName), FilingCustomerInfoVo.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("建档客户信息")
                .doWrite(Collections.singleton(JSONObject.parseObject(FILING_CUSTOMER_INFO, FilingCustomerInfoVo.class)));
        return fileName;
    }

    @Override
    public Long getModelIdByType(String type) {
        //查询当前客户类型开启的模板类型字段
        FilingModelInfo modelParam = new FilingModelInfo();
        modelParam.setStatus("1");
        modelParam.setModelType(type);
        List<FilingModelInfo> list = filingModelInfoMapper.list(modelParam);
        if (list==null || list.size()==0){
            throw new BusinessValidationException("当前无开启模板");
        }
        return list.get(0).getId();
    }
}
