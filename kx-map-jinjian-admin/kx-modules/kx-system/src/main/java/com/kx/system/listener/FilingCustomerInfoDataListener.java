package com.kx.system.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.AnchorTypeEnum;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.config.enums.FilingAddressTypeEnum;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.Location;
import com.kx.system.domain.enums.filing.AddressTypeEnum;
import com.kx.system.domain.enums.filing.ModelTypeEnum;
import com.kx.system.domain.enums.filing.SourceTypeEnum;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingImportRecord;
import com.kx.system.domain.filing.FilingMessageDetail;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.mapper.FilingCustomerInfoMapper;
import com.kx.system.mapper.FilingImportRecordMapper;
import com.kx.system.mapper.FilingMessageDetailMapper;
import com.kx.system.task.CustomInfoSyncJob;
import com.kx.system.util.EasyExcelUtil;
import com.kx.system.util.GPSUtil;
import com.kx.system.util.GeocodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FilingCustomerInfoDataListener implements ReadListener<FilingCustomerInfo> {

    private FilingImportRecordMapper filingImportRecordMapper;

    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    private FilingMessageDetailMapper filingMessageDetailMapper;

    private LoginUser loginUser;

    private List<FilingModelInfo> list;

    private GeocodingUtil geocodingUtil;

    private CustomInfoSyncJob customInfoSyncJob;

    /**
     * 标准表头
     */
    private static final List<String> HEAD_LIST = Arrays.asList("客户类型", "客户名称", "联系地址", "地址类型", "手机号码", "证件号(身份证号/统一社会信用代码)", "客户来源", "介绍人证件号(身份证号/统一社会信用代码)", "建档人工号", "建档人", "建档机构号", "建档机构");

    /**
     * 数据存储阈值
     */
    private static final int BATCH_COUNT = 2000;

    /**
     * 数据新增阈值
     */
    private static final int ADD_COUNT = 50;

    /**
     * 失败原因记录
     */
    private static String FAIL_REASON = "";

    /**
     * 记录表id
     */
    private static Long id = 0L;


    /**
     * 设置集合阈值
     */
    private List<FilingCustomerInfo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        //校验表头
        String reason = EasyExcelUtil.verifyHeadRe(HEAD_LIST, headMap);
        ReadListener.super.invokeHead(headMap, context);
        //初始化导入记录，校验表头无出错则为导入中状态，
        FilingImportRecord filingImportRecord = new FilingImportRecord();
        if (StringUtils.isEmpty(reason)) {
            filingImportRecord.setImportStatus("0");
        } else {
            filingImportRecord.setImportStatus("2");
            filingImportRecord.setFailReason(reason);
        }
        //数据条数
        filingImportRecord.setImportNum(context.readSheetHolder().getApproximateTotalRowNumber() - 1);
        filingImportRecord.setCreateTime(DateUtils.getNowDate());
        filingImportRecord.setUserId(loginUser.getSysUser().getUserId());
        filingImportRecord.setUserName(loginUser.getSysUser().getUserName());
        filingImportRecordMapper.insertFilingImportRecord(filingImportRecord);
        id = filingImportRecord.getId();
        if(StringUtils.isNotEmpty(reason)) {
            context.interrupt();
        }
        System.out.println(id);
    }

    public FilingCustomerInfoDataListener(FilingCustomerInfoMapper filingCustomerInfoMapper, List<FilingModelInfo> list, LoginUser loginUser, FilingImportRecordMapper filingImportRecordMapper, FilingMessageDetailMapper filingMessageDetailMapper,GeocodingUtil geocodingUtil,CustomInfoSyncJob customInfoSyncJob) {
        this.filingCustomerInfoMapper = filingCustomerInfoMapper;
        this.loginUser = loginUser;
        this.list = list;
        this.filingImportRecordMapper = filingImportRecordMapper;
        this.filingMessageDetailMapper = filingMessageDetailMapper;
        this.geocodingUtil = geocodingUtil;
        this.customInfoSyncJob = customInfoSyncJob;
    }

    @Override
    public void invoke(FilingCustomerInfo data, AnalysisContext context) {
        Integer total = context.readSheetHolder().getApproximateTotalRowNumber();
        // 校验数据量
        if (total - 1 > BATCH_COUNT) {
            FAIL_REASON = "超出最大导入数据量\n";
            failRecord(FAIL_REASON, id);
        }
        if (total - 1 <= 0) {
            FAIL_REASON = "无数据导入\n";
            failRecord(FAIL_REASON, id);
        }

        //数据空值检查
        checkDataNull(data.getFilingManagerCode(), "建档人工号", context);
        checkDataNull(data.getCustomerName(), "客户名称", context);
        checkDataNull(data.getCustomerPhone(), "手机号码", context);
        checkDataNull(data.getCustomerIdentity(), "证件号", context);

        //校验 每条数据与数据库对比是否存在 证件号、客户名称、手机号 其中两条重复数据，以及校验 手机号和证件号是否二选一必填
        List<FilingCustomerInfo> checkFiling = filingCustomerInfoMapper.checkFiling(null, data.getCustomerIdentity(), data.getCustomerName(), data.getCustomerPhone());
        if (!CollectionUtils.isEmpty(checkFiling)) {
            FAIL_REASON = "第" + context.getCurrentRowNum() + "行数据已存在建档 \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        if (StringUtils.isEmpty(data.getCustomerIdentity()) && StringUtils.isEmpty(data.getCustomerPhone())) {
            FAIL_REASON = "第" + context.getCurrentRowNum() + "行出现错误，证件号和手机号二选一必填! \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //校验 手机号长度为11位数字组成
        if (!data.getCustomerPhone().matches("(\\d{11})?")) {
//            FAIL_REASON = FAIL_REASON + "第" + context.getCurrentRowNum() + "行出现错误：手机号码应为数字组成的11位" + data.getCustomerPhone() + "\n";
            FAIL_REASON =   "第" + context.getCurrentRowNum() + "行出现错误：手机号码应为数字组成的11位" + data.getCustomerPhone() + "\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //客户类型，模板绑定
        FilingModelInfo customer = list.stream().filter(s -> s.getModelType().equals(ModelTypeEnum.family.getCode())).findFirst().orElse(null);
        if (customer == null) {
//            FAIL_REASON = FAIL_REASON + "个人客户存在未开启的模板 \n";
            FAIL_REASON =   "个人客户存在未开启的模板 \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        FilingModelInfo company = list.stream().filter(s -> s.getModelType().equals(ModelTypeEnum.company.getCode())).findFirst().orElse(null);
        if (company == null) {
            FAIL_REASON = "企业客户不存在开启的模板 \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        FilingModelInfo business = list.stream().filter(s -> s.getModelType().equals(ModelTypeEnum.business.getCode())).findFirst().orElse(null);
        if (business == null) {
            FAIL_REASON = "商户客户不存在开启的模板 \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        Long customerModelId = customer.getId();
        Long companyModelId = company.getId();
        Long businessModelId = business.getId();
        if (StringUtils.isNotEmpty(data.getCustomerType())&&data.getCustomerType().equals(ModelTypeEnum.family.getName())) {
            data.setCustomerType(ModelTypeEnum.family.getCode());
            data.setModelId(customerModelId);
        } else if (StringUtils.isNotEmpty(data.getCustomerType())&&data.getCustomerType().equals(ModelTypeEnum.company.getName())) {
            data.setCustomerType(ModelTypeEnum.company.getCode());
            data.setModelId(companyModelId);
        } else if (StringUtils.isNotEmpty(data.getCustomerType())&&data.getCustomerType().equals(ModelTypeEnum.business.getName())) {
            data.setCustomerType(ModelTypeEnum.business.getCode());
            data.setModelId(businessModelId);
        } else if (StringUtils.isNotEmpty(data.getCustomerType())&&data.getCustomerType().equals(ModelTypeEnum.other.getName())) {
            data.setCustomerType(ModelTypeEnum.other.getCode());
        } else {
            FAIL_REASON = "第" + context.getCurrentRowNum() + "行出现错误：不存在该客户类型" + data.getCustomerType() +" \n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //校验 证件号字段长度为18位数，字母或数字组成
        if (data.getCustomerType().equals(ModelTypeEnum.family.getCode()) && !data.getCustomerIdentity().matches("^[0-9a-zA-Z]{18}$|^$")) {
//            FAIL_REASON = FAIL_REASON + "第" + context.getCurrentRowNum() + "行出现错误：个人客户证件号应为字母或数字组成的18位或为空：" + data.getCustomerIdentity() + "\n";
            FAIL_REASON =  "第" + context.getCurrentRowNum() + "行出现错误：个人客户证件号应为字母或数字组成的18位或为空：" + data.getCustomerIdentity() + "\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        if (!data.getCustomerType().equals(ModelTypeEnum.family.getCode()) && !data.getCustomerIdentity().matches("^[0-9a-zA-Z]{1,50}$|^$")) {
//            FAIL_REASON = FAIL_REASON + "第" + context.getCurrentRowNum() + "行出现错误：该客户类型证件号应为字母或数字组成的不超过50位的数据，或数据为空：" + data.getCustomerIdentity() + "\n";
            FAIL_REASON =  "第" + context.getCurrentRowNum() + "行出现错误：该客户类型证件号应为字母或数字组成的不超过50位的数据，或数据为空：" + data.getCustomerIdentity() + "\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //客户来源
        if (StringUtils.isNotEmpty(data.getCustomerSource())&&data.getCustomerSource().equals(SourceTypeEnum.customer.getName())) {
            data.setCustomerSource(SourceTypeEnum.customer.getType());
        } else if (StringUtils.isNotEmpty(data.getCustomerSource())&&data.getCustomerSource().equals(SourceTypeEnum.other.getName())) {
            data.setCustomerSource(SourceTypeEnum.other.getType());
        } else if (StringUtils.isNotEmpty(data.getCustomerSource())&&data.getCustomerSource().equals(SourceTypeEnum.mobai.getName())) {
            data.setCustomerSource(SourceTypeEnum.mobai.getType());
        } else {
            FAIL_REASON = "第" + context.getCurrentRowNum() + "行出现错误：不存在该客户介绍类型" + data.getCustomerSource() + "\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //客户经营地址
        if (StringUtils.isNotEmpty(data.getCustomerAddressType())) {
            data.setCustomerAddressType(AddressTypeEnum.getTypeByName(data.getCustomerAddressType()).getType());
        } else {
            FAIL_REASON = "第" + context.getCurrentRowNum() + "行出现错误：不存在该客户地址类型" + data.getCustomerAddressType()+ "\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
        //介绍人证件号，如果不为空，则查询是否在系统内，如果是，则保存成功，否则提示未在系统库中匹配到介绍人信息
        if (StringUtils.isNotEmpty(data.getCustomerIntroducerIdentity())) {
            //取绩效和建档客户中匹配
            if (CollectionUtils.isEmpty(filingCustomerInfoMapper.getInfoByIdentity(data))) {
                //提示错误
//                FAIL_REASON = FAIL_REASON + "第" + context.getCurrentRowNum() + "行出现错误：未在系统库中匹配到介绍人信息：" + data.getCustomerIntroducerIdentity() + "\n";
                FAIL_REASON =  "第" + context.getCurrentRowNum() + "行出现错误：未在系统库中匹配到介绍人信息：" + data.getCustomerIntroducerIdentity() + "\n";
                failRecord(FAIL_REASON, id);
                context.interrupt();
            }
        }

        //默认设置
        data.setFilingStatus("1");
        data.setFilingSource("2");
        data.setFilingProgress("0%");
        data.setUpdateTime(DateUtils.getNowDate());
        data.setCreateTime(DateUtils.getNowDate());
        data.setFilingDate(DateUtils.getNowDate());
        cachedDataList.add(data);
        if (cachedDataList.size() == total - 1) {
            //校验表单中数据是否存在重复
            Map<List<String>, List<FilingCustomerInfo>> listMapNameAndIdentity = cachedDataList.stream().collect(Collectors.groupingBy(obj -> Arrays.asList(obj.getCustomerName(), obj.getCustomerIdentity())));
            for (Map.Entry<List<String>, List<FilingCustomerInfo>> listListEntry : listMapNameAndIdentity.entrySet()) {
                if (listListEntry.getValue().size() > 1) {
                    FAIL_REASON = "表格中姓名和证件号出现重复：" + listListEntry.getValue().get(0).getCustomerName()+ "\n";
                    failRecord(FAIL_REASON, id);
                    context.interrupt();
                }
            }
            Map<List<String>, List<FilingCustomerInfo>> listMapNameAndPhone = cachedDataList.stream().collect(Collectors.groupingBy(obj -> Arrays.asList(obj.getCustomerName(), obj.getCustomerPhone())));
            for (Map.Entry<List<String>, List<FilingCustomerInfo>> listListEntry : listMapNameAndPhone.entrySet()) {
                if (listListEntry.getValue().size() > 1) {
                    FAIL_REASON = "姓名和手机号出现重复：" + listListEntry.getValue().get(0).getCustomerName()+ "\n";
                    failRecord(FAIL_REASON, id);
                    context.interrupt();
                }
            }
            Map<List<String>, List<FilingCustomerInfo>> listMapIdentityAndPhone = cachedDataList.stream().collect(Collectors.groupingBy(obj -> Arrays.asList(obj.getCustomerIdentity(), obj.getCustomerPhone())));
            for (Map.Entry<List<String>, List<FilingCustomerInfo>> listListEntry : listMapIdentityAndPhone.entrySet()) {
                if (listListEntry.getValue().size() > 1) {
                    FAIL_REASON = "手机号和证件号出现重复：" + listListEntry.getValue().get(0).getCustomerName()+ "\n";
                    failRecord(FAIL_REASON, id);
                    context.interrupt();
                }
            }
            List<List<FilingCustomerInfo>> partition = org.apache.commons.collections4.ListUtils.partition(cachedDataList, ADD_COUNT);

            for (List<FilingCustomerInfo> list : partition) {
                for (FilingCustomerInfo filingCustomerInfo : list) {
                    filingCustomerInfoMapper.insertFilingCustomerInfo(filingCustomerInfo);
                    //新增json子表数据
                    FilingMessageDetail messageDetail = new FilingMessageDetail();
                    messageDetail.setFilingId(filingCustomerInfo.getId());
                    if (filingCustomerInfo.getCustomerType().equals("1")) {
                        messageDetail.setModelId(customerModelId);
                    } else if (filingCustomerInfo.getCustomerType().equals("2")) {
                        messageDetail.setModelId(companyModelId);
                    } else if (filingCustomerInfo.getCustomerType().equals("3")) {
                        messageDetail.setModelId(businessModelId);
                    }
                    messageDetail.setUpdateTime(DateUtils.getNowDate());
                    messageDetail.setCreateTime(DateUtils.getNowDate());
                    String json = JSON.toJSONString(filingCustomerInfo);
                    messageDetail.setJsonMessage(json);
                    filingMessageDetailMapper.insertFilingMessageDetail(messageDetail);
                    String customerAddress = filingCustomerInfo.getCustomerAddress();
                    List<AnchorPoint> anchorPoints = new ArrayList<>();
                    if(StringUtils.isNotEmpty(customerAddress)){
                        JSONObject jsonObject = geocodingUtil.geoCode(customerAddress);
                        Location location = JSON.parseObject(jsonObject.getString("location"), Location.class);
                        if (location != null) {
                            double[] locationArr = GPSUtil.bd09_To_gps84(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
                            AnchorPoint anchorPoint = new AnchorPoint();
                            anchorPoint.setCustomerId(filingCustomerInfo.getId());
                            anchorPoint.setCustomerNm(filingCustomerInfo.getCustomerNm());
                            anchorPoint.setIdentity(filingCustomerInfo.getCustomerIdentity());
                            anchorPoint.setType(String.valueOf(CustomTypeNewEnum.FILING.getCode()));
                            anchorPoint.setAnchorName(filingCustomerInfo.getCustomerName());
                            anchorPoint.setAnchorTypeCode(Integer.parseInt(filingCustomerInfo.getCustomerType()));
                            anchorPoint.setAnchorType(AnchorTypeEnum.getNameByCode(Integer.parseInt(filingCustomerInfo.getCustomerType())));
                            anchorPoint.setAddressDesc(filingCustomerInfo.getCustomerAddress());
                            anchorPoint.setUpdateTime(new Date());
                            anchorPoint.setBelongOrgCode(filingCustomerInfo.getFilingOrgCode());
                            anchorPoint.setBelongOrgName(filingCustomerInfo.getFilingOrgName());
                            anchorPoint.setCustomerPhone(filingCustomerInfo.getCustomerPhone());
                            anchorPoint.setCreateTime(new Date());
                            anchorPoint.setBelongManagerCode(filingCustomerInfo.getFilingManagerCode());
                            anchorPoint.setBelongManagerName(filingCustomerInfo.getFilingManagerName());
                            anchorPoint.setAddressType(Integer.parseInt(FilingAddressTypeEnum.getNameByCode(filingCustomerInfo.getCustomerAddressType())));
                            anchorPoint.setLon(BigDecimal.valueOf(locationArr[1]));
                            anchorPoint.setLat(BigDecimal.valueOf(locationArr[0]));
                            anchorPoints.add(anchorPoint);
                        }
                    }
                    customInfoSyncJob.initAnchor(anchorPoints);
                }
            }

        }
    }

    private void checkDataNull(String fieldVal,String errorMsg, AnalysisContext context) {
        if(StringUtils.isEmpty(fieldVal)){
            //提示错误
            FAIL_REASON =  "第" + context.getCurrentRowNum() + "行出现错误：" +errorMsg +"缺失\n";
            failRecord(FAIL_REASON, id);
            context.interrupt();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //导入成功
        FilingImportRecord record = new FilingImportRecord();
        record.setImportStatus("1");
        record.setId(id);
        filingImportRecordMapper.updateFilingImportRecord(record);
    }

    public void failRecord(String fail, Long id) {
        //导入失败
        FilingImportRecord record = new FilingImportRecord();
        record.setFailReason(fail);
        record.setImportStatus("2");
        record.setId(id);
        filingImportRecordMapper.updateFilingImportRecord(record);

    }
}
