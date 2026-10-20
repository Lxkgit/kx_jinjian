package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.service.TokenService;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.AnchorTypeEnum;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.config.enums.FilingAddressTypeEnum;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.Location;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.enums.filing.FilingStatuEnum;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingFieldInfo;
import com.kx.system.domain.filing.FilingMessageDetail;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.listener.FilingCustomerInfoDataListener;
import com.kx.system.mapper.*;
import com.kx.system.service.FilingCustomerInfoService;
import com.kx.system.service.FilingMessageDetailService;
import com.kx.system.service.IAnchorPointService;
import com.kx.system.service.ISysDeptService;
import com.kx.system.task.CustomInfoSyncJob;
import com.kx.system.util.ArithmeticOperationUtils;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.GPSUtil;
import com.kx.system.util.GeocodingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 建档客户基础信息表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-01
 */
@Slf4j
@Service
public class FilingCustomerInfoServiceImpl implements FilingCustomerInfoService {

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;
    @Resource
    private FilingMessageDetailMapper filingMessageDetailMapper;
    @Resource
    private FilingModelInfoMapper filingModelInfoMapper;
    @Resource
    private FilingImportRecordMapper filingImportRecordMapper;
    @Resource
    private FilingFieldInfoMapper filingFieldInfoMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private CustomerLabelMapper customerLabelMapper;
    @Resource
    private GeocodingUtil geocodingUtil;
    @Resource
    private CustomInfoSyncJob customInfoSyncJob;

    @Resource
    private FilingMessageDetailService filingMessageDetailService;

    @Resource
    private ISysDeptService sysDeptService;

    @Autowired
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private IAnchorPointService anchorPointService;


    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<FilingCustomerInfo> list(FilingCustomerInfo filingCustomerInfo) {
        List<FilingCustomerInfo> list = filingCustomerInfoMapper.list(filingCustomerInfo);
        if (null == list) list = new ArrayList<>();
        //获取建档客户的锚点数据
        List<String> customerNmList = list.stream().map(FilingCustomerInfo::getCustomerNm).collect(Collectors.toList());
        Map<String, AnchorPoint> anchorPointMap = anchorPointService.getAnchorPointByCustomerNm(customerNmList, String.valueOf(CustomTypeNewEnum.FILING.getCode()));
        BigDecimal decimal = new BigDecimal(0);
        list.forEach(f -> {
            AnchorPoint p = anchorPointMap.get(f.getCustomerNm());
            f.setLon(null != p ? p.getLon() : decimal);
            f.setLat(null != p ? p.getLat() : decimal);
        });
        return list;
    }

    @Override
    public FilingCustomerInfo getById(Long id) {
        return filingCustomerInfoMapper.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FilingCustomerInfo saveFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        //获取当前操作人工号、机构号为建档人、建档机构
        SysUser user = SecurityUtils.getLoginUser().getSysUser();
        filingCustomerInfo.setFilingManagerCode(user.getManagerNum());
        filingCustomerInfo.setFilingManagerName(user.getNickName());
        filingCustomerInfo.setFilingOrgCode(user.getDept().getOrgNum());
        filingCustomerInfo.setFilingOrgName(user.getDept().getDeptName());
        SysDept sysDept = sysDeptMapper.selectParentByNum(user.getDept().getOrgNum());
        filingCustomerInfo.setSeparateBank(sysDept.getDeptName());
        filingCustomerInfo.setFilingSource("1");
        //默认为空字符
        filingCustomerInfo.setBelongGridCode("");
        filingCustomerInfo.setBelongGridName("");
        if ("3".equals(filingCustomerInfo.getCustomerSource())){
            filingCustomerInfo.setCustomerIntroducerIdentity("");
        }

        //第一步暂存功能
        if ("1".equalsIgnoreCase(filingCustomerInfo.getIsTmp()) && filingCustomerInfo.getFilingStatus().equals("1")) {
            return tempSaveSept1(filingCustomerInfo);
        }
        //第一步有效建档
        if (!"1".equalsIgnoreCase(filingCustomerInfo.getIsTmp()) && filingCustomerInfo.getFilingStatus().equals("1")) {
            return saveSept1(filingCustomerInfo);
        }
        if ("1".equalsIgnoreCase(filingCustomerInfo.getIsTmp()) && filingCustomerInfo.getFilingStatus().equals("2")) {
            return tempSaveSept2(filingCustomerInfo);
        }
        if (!"1".equalsIgnoreCase(filingCustomerInfo.getIsTmp()) && filingCustomerInfo.getFilingStatus().equals("2")) {
            return saveSept2(filingCustomerInfo);
        }
        return null;
    }

    /**
     * 第二步有效建档
     */
    private FilingCustomerInfo saveSept2(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        FilingCustomerInfo existFilingCustomerInfo = filingCustomerInfoMapper.getById(filingCustomerInfo.getId());
        if (existFilingCustomerInfo == null) {
            throw new BusinessValidationException("建档记录不存在");
        }

        checkFilingTwo(filingCustomerInfo, false);
        filingCustomerInfo.setFilingStatus(FilingStatuEnum.VALID.getCode());
        filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
        saveLableAndDetail(filingCustomerInfo, existFilingCustomerInfo,false);
        return filingCustomerInfo;
    }

    /**
     * 第二步暂存
     */
    private FilingCustomerInfo tempSaveSept2(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        FilingCustomerInfo existFilingCustomerInfo = filingCustomerInfoMapper.getById(filingCustomerInfo.getId());
        if (existFilingCustomerInfo == null) {
            throw new BusinessValidationException("建档记录不存在");
        }
        //状态保持不变
        filingCustomerInfo.setFilingStatus(existFilingCustomerInfo.getFilingStatus());
        if (FilingStatuEnum.BASE.getCode().equalsIgnoreCase(existFilingCustomerInfo.getFilingStatus())) {
            try {
                checkFilingTwo(filingCustomerInfo, true);
                //校验通过，变为有效建档
                filingCustomerInfo.setFilingStatus(FilingStatuEnum.VALID.getCode());
                filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
            } catch (BusinessValidationException e) {
                //校验不通过，只暂存
                filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
            }
        }
        saveLableAndDetail(filingCustomerInfo, existFilingCustomerInfo,false);
        return filingCustomerInfo;
    }

    /**
     * 第一步基础建档
     */
    private FilingCustomerInfo saveSept1(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        checkFilingOne(filingCustomerInfo);
        //确认是否重复建档
        checkRepeatFiling(filingCustomerInfo);
        filingCustomerInfo.setFilingStatus(FilingStatuEnum.BASE.getCode());

        FilingCustomerInfo existFilingCustomerInfo = null;
        if (filingCustomerInfo.getId() == null) {
            filingCustomerInfo.setCustomerNm(filingCustomerInfo.getCustomerIdentity());
            //初次基础建档下一步（状态转变为基础建档）
            filingCustomerInfoMapper.insertFilingCustomerInfo(filingCustomerInfo);
        } else {
            existFilingCustomerInfo = filingCustomerInfoMapper.getById(filingCustomerInfo.getId());
            if (existFilingCustomerInfo == null) {
                throw new BusinessValidationException("建档记录不存在");
            }
            //临时建档->基础建档
            filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
        }
        saveLableAndDetail(filingCustomerInfo, existFilingCustomerInfo,true);
        //生成锚点
        parseAddressAndSaveAnchor(filingCustomerInfo);
        return filingCustomerInfo;
    }

    /**
     * 第一步暂存
     */
    private FilingCustomerInfo tempSaveSept1(FilingCustomerInfo filingCustomerInfo) throws JsonProcessingException {
        FilingCustomerInfo existFilingCustomerInfo = null;
        if (filingCustomerInfo.getId() == null) {
            filingCustomerInfo.setFilingStatus(FilingStatuEnum.TEMP.getCode());
            filingCustomerInfo.setCustomerNm(filingCustomerInfo.getCustomerIdentity());
            filingCustomerInfoMapper.insertFilingCustomerInfo(filingCustomerInfo);
        } else {
            existFilingCustomerInfo = filingCustomerInfoMapper.getById(filingCustomerInfo.getId());
            if (existFilingCustomerInfo == null) {
                throw new BusinessValidationException("建档记录不存在");
            }
            if (FilingStatuEnum.TEMP.getCode().equalsIgnoreCase(existFilingCustomerInfo.getFilingStatus())) {
                //继续暂存
                filingCustomerInfo.setFilingStatus(FilingStatuEnum.TEMP.getCode());
                filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
            } else if (FilingStatuEnum.BASE.getCode().equalsIgnoreCase(existFilingCustomerInfo.getFilingStatus())
                    || FilingStatuEnum.VALID.getCode().equalsIgnoreCase(existFilingCustomerInfo.getFilingStatus())) {
                checkFilingOne(filingCustomerInfo);
                //状态不变
                filingCustomerInfo.setFilingStatus(existFilingCustomerInfo.getFilingStatus());
                filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);

                if (!StringUtils.isEmpty(filingCustomerInfo.getCustomerAddress()) &&
                        !filingCustomerInfo.getCustomerAddress().equalsIgnoreCase(existFilingCustomerInfo.getCustomerAddress())) {
                    //如果地址修改了，重新落格
                    parseAddressAndSaveAnchor(filingCustomerInfo);
                } else {
                    //更新锚点信息
                    saveFilingAnchor(filingCustomerInfo);
                }
            }
        }
        saveLableAndDetail(filingCustomerInfo, existFilingCustomerInfo,true);
        return filingCustomerInfo;
    }

    private void saveLableAndDetail(FilingCustomerInfo filingCustomerInfo, FilingCustomerInfo existInfo,boolean deleteHistoryLabel) throws
            JsonProcessingException {
        //保存标签
        addCustomerLabel(filingCustomerInfo, existInfo,deleteHistoryLabel);
        //保存建档字段
        FilingMessageDetail filingMessageDetail = filingCustomerInfo.getFilingMessageDetail();
        filingMessageDetail.setModelId(filingCustomerInfo.getModelId());
        filingMessageDetail.setFilingId(filingCustomerInfo.getId());
        filingMessageDetailService.saveFilingMessageDetail(filingMessageDetail);
    }


    private void parseAddressAndSaveAnchor(FilingCustomerInfo filingCustomerInfo) {
        //校验地址
        JSONObject jsonObject = geocodingUtil.geoCode(filingCustomerInfo.getCustomerAddress());
        Location location = JSON.parseObject(jsonObject.getString("location"), Location.class);
        if (location == null) {
            throw new BusinessValidationException("该地址异常，请输入详细地址！");
        }
        double[] locationArr = GPSUtil.bd09_To_gps84(Double.parseDouble(location.getLat()), Double.parseDouble(location.getLng()));
        location.setLat(String.valueOf(locationArr[0]));
        location.setLng(String.valueOf(locationArr[1]));
        //异步插入锚点
        CompletableFuture.runAsync(() -> saveFilingAnchor(filingCustomerInfo, location));
    }

    private void saveFilingAnchor(FilingCustomerInfo filingCustomerInfo, Location location) {
        AnchorPoint anchorPoint = buildAnchorPoint(filingCustomerInfo);
        anchorPoint.setLon(BigDecimal.valueOf(Double.parseDouble(location.getLng())));
        anchorPoint.setLat(BigDecimal.valueOf(Double.parseDouble(location.getLat())));
        anchorPoint.setCreditManagerJobNumber(filingCustomerInfo.getFilingManagerCode());
        anchorPoint.setCreditManager(filingCustomerInfo.getFilingManagerName());
        customInfoSyncJob.initAnchor(Arrays.asList(anchorPoint));
    }

    private void saveFilingAnchor(FilingCustomerInfo filingCustomerInfo) {
        customInfoSyncJob.initAnchor(Arrays.asList(buildAnchorPoint(filingCustomerInfo)));
    }

    private AnchorPoint buildAnchorPoint(FilingCustomerInfo filingCustomerInfo) {
        AnchorPoint anchorPoint = new AnchorPoint();
        //查询是否已存在该建档客户锚点
        AnchorPoint existAnchorPoint = anchorPointMapper.selectExistFilingAnchor(filingCustomerInfo.getId());
        if (existAnchorPoint != null) {
            BeanUtil.copyProperties(existAnchorPoint, anchorPoint);
        }
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
        return anchorPoint;
    }

    private void addCustomerLabel(FilingCustomerInfo updateInfo, FilingCustomerInfo existInfo,boolean deleteHistoryLabel) throws
            JsonProcessingException {
        FilingCustomerInfo filingCustomerInfo = new FilingCustomerInfo();
        if (existInfo != null) {
            BeanUtils.copyProperties(existInfo, filingCustomerInfo);
        }
        BeanUtils.copyBeanIgnoreNull(updateInfo, filingCustomerInfo);

        if(deleteHistoryLabel){
            //修改标签关联,先删除后新增 无论暂存还是保存标签
            customerLabelMapper.deleteByCustomerIdAndCustomerType(filingCustomerInfo.getId(), "3");
        }


        List<String> labelName = filingFieldInfoMapper.list(new FilingFieldInfo().setModelId(filingCustomerInfo.getModelId())).stream().filter(s -> s.getFieldType().equals("10")).map(FilingFieldInfo::getEnglishName).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> customerLabelIds = new ArrayList<>();
        //收集第一步标签
        if (filingCustomerInfo.getFilingMessageDetail().getJsonMessage() != null) {
            Map jsonMessage = mapper.readValue(filingCustomerInfo.getFilingMessageDetail().getJsonMessage().toString(), Map.class);
            for (String label : labelName) {
                if (jsonMessage.get(label) != null && jsonMessage.get(label) != "") {
                    List<Integer> longs = (List<Integer>) jsonMessage.get(label);
                    if (longs != null && longs.size() != 0) {
                        customerLabelIds.addAll(longs);
                    }
                }
            }
        }
        //收集第二步标签
        if (filingCustomerInfo.getFilingMessageDetail().getJsonFinish() != null) {
            Map jsonFinish = mapper.readValue(filingCustomerInfo.getFilingMessageDetail().getJsonFinish().toString(), Map.class);
            for (String label : labelName) {
                if (jsonFinish.get(label) != null && jsonFinish.get(label) != "") {
                    List<Integer> longs = (List<Integer>) jsonFinish.get(label);
                    if (longs != null && longs.size() != 0) {
                        customerLabelIds.addAll(longs);
                    }
                }
            }
        }
        //新增标签客户关联表
        List<CustomerLabel> customerLabels = new ArrayList<>();
        if (customerLabelIds.size() > 0) {
            //去重
            customerLabelIds = customerLabelIds.stream().distinct().collect(Collectors.toList());
            for (Integer labelId : customerLabelIds) {
                CustomerLabel customerLabel = new CustomerLabel();
                customerLabel.setLabelId(labelId.longValue());
                customerLabel.setCustomerId(filingCustomerInfo.getId());
                customerLabel.setLabelType(0);
                customerLabel.setType(3);
                customerLabels.add(customerLabel);
            }
            customerLabelMapper.insertBatch(customerLabels);
        }
    }

    @Override
    public void updateFilingCustomerInfo(FilingCustomerInfo filingCustomerInfo) {
        filingCustomerInfo.setUpdateTime(DateUtils.getNowDate());
        filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
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
        filingCustomerInfoMapper.deleteById(id);
    }

    @Override
    public void checkFilingOne(FilingCustomerInfo filingCustomerInfo) {
        //校验证件号、手机号是否为必填
        String identity = filingCustomerInfo.getCustomerIdentity();
        String phone = filingCustomerInfo.getCustomerPhone();
        if (StringUtils.isEmpty(identity) && StringUtils.isEmpty(phone)) {
            throw new BusinessValidationException("手机号和证件号不能全为空");
        }
        //个人校验证件号为18位或者空
        if (filingCustomerInfo.getCustomerType().equals("1") && !identity.matches("^[0-9a-zA-Z]{18}$|^$")) {
            throw new BusinessValidationException("证件号应为18位字母或数字");
        }
        //其他类型校验证件号为不超过50位或者空
//        if (!filingCustomerInfo.getCustomerType().equals("1") && !identity.matches("^[0-9a-zA-Z]{1,50}$|^$")) {
//            throw new BusinessValidationException("证件号长度不能超过50位");
//        }


        if (!filingCustomerInfo.getCustomerType().equals("1") && !identity.matches("^[0-9a-zA-Z]+$|^$")) {
            throw new BusinessValidationException("请输入正确证件号");
        }

        if (!filingCustomerInfo.getCustomerType().equals("1") && !identity.matches("^[0-9a-zA-Z]{1,50}$|^$")) {
            throw new BusinessValidationException("证件号长度不能超过50位");
        }

        //校验手机号为11位或者空
        if (!phone.matches("(\\d{11})?")) {
            throw new BusinessValidationException("手机号码应为数字组成的11位");
        }
    }

    public void checkRepeatFiling(FilingCustomerInfo filingCustomerInfo) {
        //校验三个字段中超两个为重复
        List<FilingCustomerInfo> customers = filingCustomerInfoMapper.checkFiling(
                filingCustomerInfo.getId(),
                filingCustomerInfo.getCustomerIdentity(),
                filingCustomerInfo.getCustomerName(),
                filingCustomerInfo.getCustomerPhone());

        if (!CollectionUtils.isEmpty(customers)) {
            throw new BusinessValidationException("重复建档！该客户已存在，\n" +
                    "客户名称:" + customers.get(0).getCustomerName() + "\n" +
                    "手机号码:" + customers.get(0).getCustomerPhone() + "\n" +
                    "证件号码:" + customers.get(0).getCustomerIdentity());
        }
    }

    private void checkFilingTwo(FilingCustomerInfo filingCustomerInfo, boolean isTemp) {
        //校验打卡
        if (StringUtils.isEmpty(filingCustomerInfo.getClockIn()) || "{}".equalsIgnoreCase(filingCustomerInfo.getClockIn())) {
            throw new BusinessValidationException("无法完成,无打卡数据!请前往移动端打卡");
        }
        if (StringUtils.isEmpty(filingCustomerInfo.getCustomerPhoto()) || "[]".equalsIgnoreCase(filingCustomerInfo.getCustomerPhone())) {
            throw new BusinessValidationException("未上传图片");
        }
        if (!isTemp) {
            //校验是否有身份证
            FilingCustomerInfo filingCustomerInfoTemp = filingCustomerInfoMapper.getById(filingCustomerInfo.getId());
            if (StringUtils.isEmpty(filingCustomerInfoTemp.getCustomerIdentity()) && StringUtils.isEmpty(filingCustomerInfo.getCustomerIdentity())) {
                throw new BusinessValidationException("证件号未填写，请补充！");
            }
        }
    }

    @Override
    public FilingCustomerInfo getByModelIdAndFilingId(Long modelId, Long filingId) {
        FilingCustomerInfo filingCustomerInfo = filingCustomerInfoMapper.getById(filingId);
        FilingMessageDetail filingMessageDetail = filingMessageDetailMapper.getByModelIdAndFilingId(modelId, filingId);
        if (filingCustomerInfo != null) {
            filingCustomerInfo.setFilingMessageDetail(filingMessageDetail);
        }
        return filingCustomerInfo;
    }


    @Override
    public FilingCustomerInfo getByModelIdAndIdentity(Long modelId, String identity, String phone) {
        FilingCustomerInfo param = new FilingCustomerInfo();
        param.setCustomerIdentity(identity);
        param.setModelId(modelId);
        param.setCustomerPhone(phone);
        List<FilingCustomerInfo> list = filingCustomerInfoMapper.listAllIdentity(param);
        if (list == null || list.size() == 0) {
            throw new BusinessValidationException("对应建档数据模板未开启！");
        }
        FilingCustomerInfo filingCustomerInfo = list.get(0);
        FilingMessageDetail filingMessageDetail = filingMessageDetailMapper.getByModelIdAndFilingId(modelId, filingCustomerInfo.getId());
        filingCustomerInfo.setFilingMessageDetail(filingMessageDetail);
        return filingCustomerInfo;
    }

    @Override
    public String getFilingIsExist(Long modelId, String identity) {
        FilingCustomerInfo param = new FilingCustomerInfo();
        param.setCustomerIdentity(identity);
        param.setModelId(modelId);
        List<FilingCustomerInfo> list = filingCustomerInfoMapper.list(param);
        if (list == null || list.size() == 0) {
            return "0";
        } else {
            return "1";
        }
    }

    @Override
    @Async
    public void importBatch(InputStream file, HttpServletRequest request) {
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            FilingModelInfo param = new FilingModelInfo();
            //查询当前为开启状态的所有模板
            param.setStatus("1");
            List<FilingModelInfo> list = filingModelInfoMapper.list(param);
            //使用监听器读取并写表
            EasyExcel.read(file, FilingCustomerInfo.class,
                    new FilingCustomerInfoDataListener(filingCustomerInfoMapper, list, loginUser, filingImportRecordMapper, filingMessageDetailMapper,geocodingUtil,customInfoSyncJob)).sheet().doRead();
        } catch (DuplicateKeyException dke) {

        }
    }

    @Override
    public void filingStatistics() throws JsonProcessingException {
        /**
         * 跑批更新建档客户建档进度
         * 规则：取建档第二步，除固定字段外 填写字段数/字段数*100%
         */
        log.info("建档进度更新开始");
        List<FilingMessageDetail> list = filingMessageDetailMapper.list(new FilingMessageDetail());
        List<FilingCustomerInfo> filingList = new ArrayList<>();
        //根据模板id分组
        Map<Long, List<FilingMessageDetail>> listMap = list.stream().collect(Collectors.groupingBy(FilingMessageDetail::getModelId));
        for (Map.Entry<Long, List<FilingMessageDetail>> entry : listMap.entrySet()) {
            //查询当前模板对应的完成建档字段集
            FilingFieldInfo param = new FilingFieldInfo();
            param.setModelId(entry.getKey());
            param.setIsFinish("1");
            List<FilingFieldInfo> fieldList = filingFieldInfoMapper.list(param);
            //去调第二步固定的两个字段
            List<FilingFieldInfo> remove = fieldList.stream().filter(s -> s.getEnglishName().equals("customerPhoto") || s.getEnglishName().equals("clockIn")).collect(Collectors.toList());
            fieldList.removeAll(remove);
            //根据对应模板json数据统计完成度
            List<FilingMessageDetail> messageList = entry.getValue();

            for (FilingMessageDetail filingMessageDetail : messageList) {
                FilingCustomerInfo filingCustomerInfo = new FilingCustomerInfo();
                if (filingMessageDetail.getJsonFinish() == null || filingMessageDetail.getJsonFinish() == "") {
                    filingCustomerInfo.setId(filingMessageDetail.getFilingId());
                    filingCustomerInfo.setFilingProgress("0%");
                    filingList.add(filingCustomerInfo);
                    continue;
                }
                Integer i = 0;
                ObjectMapper mapper = new ObjectMapper();
                Map jsonFinish = mapper.readValue(filingMessageDetail.getJsonFinish().toString(), Map.class);
                for (FilingFieldInfo filingFieldInfo : fieldList) {
                    if (jsonFinish.get(filingFieldInfo.getEnglishName()) != null && jsonFinish.get(filingFieldInfo.getEnglishName()) != "") {
                        i++;
                    }
                }
                StringBuilder builder = new StringBuilder();
                if (fieldList.size() == 0) {
                    builder.append("0/1");
                } else {
                    builder.append(i).append("/").append(fieldList.size()).append("*100");
                }
                double arithmetic = ArithmeticOperationUtils.arithmetic(builder.toString(), 2);
                filingCustomerInfo.setId(filingMessageDetail.getFilingId());
                filingCustomerInfo.setFilingProgress(arithmetic + "%");
                filingList.add(filingCustomerInfo);
            }


        }
        //更新完整度
        filingCustomerInfoMapper.updateBatchById(filingList); //此sql版本dm不兼容
//        for (FilingCustomerInfo filingCustomerInfo : filingList) {
//            filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
//        }
        log.info("建档进度更新完成");
        /**
         * 跑批更新建档客户，网格编码，网格名称
         */
        log.info("网格编码更新开始");
        filingCustomerInfoMapper.updateFilingCustomerGrid();
        log.info("网格编码更新完成");
    }

    /**
     * 根据手机号，证件号获取最新的客户模版id
     *
     * @param phone
     * @param identity
     * @return
     */
    @Override
    public Long getTypeByPhoneAndIdentity(String phone, String identity) {
        return filingCustomerInfoMapper.getTypeByPhoneAndIdentity(phone, identity);
    }

    @Override
    public Integer countByGrid(String gridCode) {
        return filingCustomerInfoMapper.countByGrid(gridCode);
    }

    @Override
    public Long getModelIdByCustomId(Long clientId) {
        FilingCustomerInfo filingCustomerInfo = filingCustomerInfoMapper.getById(clientId);
        Long modelId = null;
        if(filingCustomerInfo != null){
            modelId = filingCustomerInfo.getModelId();
            return modelId;
        }
        return modelId;
    }


}
