package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.security.utils.DictUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.customer.*;
import com.kx.system.domain.dto.*;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingMessageDetail;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.domain.label.LabelInfo;
import com.kx.system.domain.req.CustomerReq;
import com.kx.system.domain.req.DeptIdReq;
import com.kx.system.domain.req.TradeRecordReq;
import com.kx.system.domain.vo.TaskManagerVO;
import com.kx.system.mapper.*;
import com.kx.system.service.CustomerInfoService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 86176
* @description 针对表【customer_info(客户信息主表)】的数据库操作Service实现
* @createDate 2024-01-18 11:13:45
*/
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo>
    implements CustomerInfoService{

    @Resource
    private CustomerProductDetailMapper customerProductDetailMapper;

    @Resource
    private CustomerLabelMapper customerLabelMapper;

    @Resource
    private CustomerTradeMapper customerTradeMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Resource
    private CustomerInfoMapper customerInfoMapper;

    @Resource
    private PaAddrInfoMapper paAddrInfoMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private FilingModelInfoMapper filingModelInfoMapper;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private FilingMessageDetailMapper filingMessageDetailMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;



    @Override
    public TableDataInfo queryPage(CustomerReq customerReq) {
        if (StringUtils.isNotNull(customerReq.getPageNum()) && StringUtils.isNotNull(customerReq.getPageSize())) {
            PageHelper.startPage(customerReq.getPageNum(), customerReq.getPageSize());
        }
        CustomerInfo infoReq = new CustomerInfo();
        BeanUtil.copyProperties(customerReq, infoReq);
        List<CustomerInfo> customerInfos = customerInfoMapper.selectCustomerInfoList(infoReq);
        List<SysDictData> sys_custom_type = DictUtils.getDictCache("sys_custom_type");
        List<SysDictData> files_status = DictUtils.getDictCache("files_status");
        List<SysDictData> card_type = DictUtils.getDictCache("card_type");
        List<CustomerInfoDTO> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(customerInfos)) {
            customerInfos.forEach(r -> {
                CustomerInfoDTO dto = new CustomerInfoDTO();
                BeanUtil.copyProperties(r, dto);
                //todo  一些枚举或者字典字段转换
                //网格
                PaAddrInfo paAddrInfo = paAddrInfoMapper.selectByCode(r.getBelongGrid().toString());
                if (null != paAddrInfo) {
                    dto.setBelongGridDesc(paAddrInfo.getName());
                }
                sys_custom_type.forEach(t -> {
                    if (r.getCustomerCategory().equals(t.getDictCode().intValue())) {
                        dto.setCustomerCategoryDesc(t.getDictLabel());
                    }
                });
                //部门
                SysDept sysDept = sysDeptMapper.selectDeptById(r.getDutyDeptId().longValue());
                if (null != sysDept) {
                    dto.setDutyDeptDesc(sysDept.getDeptName());
                }
                files_status.forEach(f -> {
                    if (r.getFilesStatus().equals(f.getDictCode().intValue())) {
                        dto.setFilesStatusDesc(f.getDictLabel());
                    }
                });
                //网格员
                SysUser sysUser = sysUserMapper.selectUserById(r.getGridUserId().longValue());
                if (null != sysUser) {
                    dto.setGridUserDesc(sysUser.getUserName());
                }
                //管户人
                SysUser sysUser1 = sysUserMapper.selectUserById(Long.parseLong(r.getBusinessManageUser()));
                if (null != sysUser1) {
                    dto.setBusinessManageUserDesc(sysUser1.getUserName());
                }
                card_type.forEach(c -> {
                    if (r.getCardType().equals(c.getDictCode().intValue())) {
                        dto.setCardTypeDesc(c.getDictLabel());
                    }
                });
                list.add(dto);
            });
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(customerInfos).getTotal());
        return rspData;
    }

    @Override
    public AjaxResult queryDetail(Long customerId) {
        CustomerInfoDetailDTO detailDTO = new CustomerInfoDetailDTO();
        CustomerInfo customerInfo = baseMapper.selectCustomerInfoById(customerId);
        if (null == customerInfo) {
            return AjaxResult.error("客户不存在！");
        }
        List<SysDictData> sys_custom_type = DictUtils.getDictCache("sys_custom_type");
        List<SysDictData> files_status = DictUtils.getDictCache("files_status");
        List<SysDictData> card_type = DictUtils.getDictCache("card_type");
        sys_custom_type.forEach(t -> {
            if (customerInfo.getCustomerCategory().equals(t.getDictCode().intValue())) {
                customerInfo.setCustomerCategoryDesc(t.getDictLabel());
            }
        });
        files_status.forEach(f -> {
            if (customerInfo.getFilesStatus().equals(f.getDictCode().intValue())) {
                customerInfo.setFilesStatusDesc(f.getDictLabel());
            }
        });
        card_type.forEach(c -> {
            if (customerInfo.getCardType().equals(c.getDictCode().intValue())) {
                customerInfo.setCardTypeDesc(c.getDictLabel());
            }
        });
        detailDTO.setCustomerInfo(customerInfo);
        //持有产品
        List<CustomerProductDetail> productDetails = customerProductDetailMapper.selectListByHoldId(customerId);
        detailDTO.setHoldDetail(productDetails);
        detailDTO.setEndProductList(productDetails);
        //客户标签
        List<CustomerLabelDTO> labels = new ArrayList<>();
        List<CustomerLabel> customerLabels = customerLabelMapper.queryList(customerId);
        if (CollectionUtil.isNotEmpty(customerLabels)) {
            List<Long> collect = customerLabels.stream().map(CustomerLabel::getId).collect(Collectors.toList());
            Map<Long, String> labelMap = labelInfoMapper.queryListByIds(collect).stream().collect(Collectors.toMap(LabelInfo::getId, LabelInfo::getLabelName));
            customerLabels.forEach(l -> {
                CustomerLabelDTO dto = new CustomerLabelDTO();
                BeanUtil.copyProperties(l,dto);
                dto.setLabelName(labelMap.get(l.getLabelId()));
                labels.add(dto);
            });
        }
        detailDTO.setCustomerTradeList(queryTradeList(new TradeRecordReq().setCustomerId(customerId)));
        detailDTO.setLabels(labels);
        return AjaxResult.success(detailDTO);
    }

    @Override
    public AjaxResult tradeRecord(TradeRecordReq tradeRecordReq) {
        if (null == tradeRecordReq.getCustomerId()) {
            return AjaxResult.error("参数异常！");
        }
        return AjaxResult.success(queryTradeList(tradeRecordReq));
    }

    @Override
    public AjaxResult mobilize(CustomerMobilizeDTO customerMobilizeDTO) {
        if (CollectionUtil.isEmpty(customerMobilizeDTO.getCustomerIds())) {
            return AjaxResult.success();
        }
        if ((null != customerMobilizeDTO.getBelongGrid() && null != customerMobilizeDTO.getDutyDeptId()) ||
                (null == customerMobilizeDTO.getBelongGrid() && null == customerMobilizeDTO.getDutyDeptId())) {
            return AjaxResult.error("参数异常！");
        }
        customerInfoMapper.updateCustomerInfos(customerMobilizeDTO.getBelongGrid(), customerMobilizeDTO.getDutyDeptId(), customerMobilizeDTO.getCustomerIds());
        return AjaxResult.success();
    }

    @Override
    public AjaxResult archival(CustomerUpdateInfoReq customerUpdateInfoReq) {
        if (null == customerUpdateInfoReq.getId()) {
            return AjaxResult.error("参数异常！");
        }
        customerUpdateInfoReq.setFilesStatus(1);
        updateInfo1(customerUpdateInfoReq);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult updateInfo(CustomerUpdateInfoReq customerUpdateInfoReq) {
        if (null == customerUpdateInfoReq.getId()) {
            return AjaxResult.error("参数异常！");
        }
        updateInfo1(customerUpdateInfoReq);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult editCustomerLabel(CustomerLabelReq customerLabelReq) {
        if (null == customerLabelReq.getCustomerId()) {
            return AjaxResult.error("参数异常！");
        }
        if (CollectionUtil.isEmpty(customerLabelReq.getAddLabelIds()) && CollectionUtil.isEmpty(customerLabelReq.getDelLabelIds())) {
            return AjaxResult.success();
        }
        if (CollectionUtil.isNotEmpty(customerLabelReq.getAddLabelIds())) {
            customerLabelReq.getAddLabelIds().forEach(c -> {
                CustomerLabel label = new CustomerLabel();
                label.setCustomerId(customerLabelReq.getCustomerId());
                label.setLabelId(c);
                label.setLabelType(0);
                customerLabelMapper.insertCustomerLabel(label);
            });
        }
        if (CollectionUtil.isNotEmpty(customerLabelReq.getDelLabelIds())) {
            customerLabelMapper.delLabels(customerLabelReq.getCustomerId(), customerLabelReq.getDelLabelIds());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult queryBusinessManage(DeptIdReq deptIdReq) {
        List<TaskManagerVO> businessManageList = customerInfoMapper.queryBusinessManage(deptIdReq);
        return AjaxResult.success(businessManageList);
    }

    private List<CustomerTradeDTO> queryTradeList(TradeRecordReq tradeRecordReq) {
        List<CustomerTradeDTO> tradeList = new ArrayList<>();
        List<CustomerTrade> trades = customerTradeMapper.selectCustomerTradeList(tradeRecordReq.getCustomerId(),tradeRecordReq.getTradeCardNum(),tradeRecordReq.getStartDate(),tradeRecordReq.getEndDate());
        if (CollectionUtil.isEmpty(trades)) {
            return tradeList;
        }
        Map<Long, List<CustomerTrade>> map = trades.stream().collect(Collectors.groupingBy(CustomerTrade::getTradeCardNum));
        for (Long tradeCardNum: map.keySet()) {
            CustomerTradeDTO dto = new CustomerTradeDTO();
            dto.setTradeCardNum(tradeCardNum);
            dto.setTradeList(map.get(tradeCardNum));
            tradeList.add(dto);
        }
        return tradeList;
    }

    private void updateInfo1(CustomerUpdateInfoReq customerUpdateInfoReq) {
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtil.copyProperties(customerUpdateInfoReq, customerInfo);
        customerInfoMapper.updateCustomerInfo(customerInfo);
    }

    /**
     * 客户信息基础信息匹配当前模板字段信息，同步至档案
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void customerToFiling(){
        log.error("未建档的客户基础信息同步模板开始");
        //获取客户数据
        CustomerInfo param = new CustomerInfo();
        List<CustomerInfo> customerInfos = customerInfoMapper.selectCustomerInfoList(param);
        //查询开启的模板
        FilingModelInfo modelParam = new FilingModelInfo();
        modelParam.setStatus("1");
        modelParam.setModelType("1");
        Long personModelId = filingModelInfoMapper.list(modelParam).get(0).getId();
        modelParam.setModelType("2");
        Long companyModelId = filingModelInfoMapper.list(modelParam).get(0).getId();
        modelParam.setModelType("3");
        Long merchantModelId = filingModelInfoMapper.list(modelParam).get(0).getId();
        //获取客户模板json数据
        List<FilingMessageDetail> messageDetailList = new ArrayList<>();

        for (CustomerInfo customerInfo : customerInfos) {
            //过滤已存在档案的客户
            if (customerInfo.getCustomerType().equals(1)){
                FilingMessageDetail message = filingMessageDetailMapper.getByIdentityAndModelId(customerInfo.getCardNum(), personModelId);
                if (message!=null){
                    continue;
                }
            }
            if (customerInfo.getCustomerType().equals(2)){
                FilingMessageDetail message = filingMessageDetailMapper.getByIdentityAndModelId(customerInfo.getCreditCode(), companyModelId);
                if (message!=null){
                    continue;
                }
            }
            if (customerInfo.getCustomerType().equals(3)){
                FilingMessageDetail message = filingMessageDetailMapper.getByIdentityAndModelId(customerInfo.getCreditCode(), merchantModelId);
                if (message!=null){
                    continue;
                }
            }
            //列转行存储json数据
            FilingMessageDetail messageDetail = new FilingMessageDetail();
            JSONObject jsonObject = new JSONObject();
            //个人
            if (customerInfo.getCustomerType().equals(1)){
                jsonObject.put("identity", customerInfo.getCardNum()==null?"":customerInfo.getCardNum());
                jsonObject.put("phone", customerInfo.getContactPhone()==null?"":customerInfo.getContactPhone());
                jsonObject.put("name",customerInfo.getCustomerName()==null?"":customerInfo.getCustomerName());
                jsonObject.put("sex",customerInfo.getCustomerSex()==null?"":customerInfo.getCustomerSex());
                jsonObject.put("addr",customerInfo.getCustomerAddress()==null?"":customerInfo.getCustomerAddress());
                jsonObject.put("career",customerInfo.getCareer()==null?"":customerInfo.getCareer());
                jsonObject.put("age",customerInfo.getCustomerAge()==null?"":customerInfo.getCustomerAge());
//                String json = .toString();
//                messageDetail.setJsonMessage(json);
//                messageDetail.setIdentity(customerInfo.getCardNum());
                messageDetail.setModelId(personModelId);
                messageDetail.setCreateTime(DateUtils.getNowDate());
                messageDetail.setUpdateTime(DateUtils.getNowDate());
                messageDetailList.add(messageDetail);
            }
            //企业
            if (customerInfo.getCustomerType().equals(2)){
                jsonObject.put("companyName", customerInfo.getCustomerName()==null?"":customerInfo.getCustomerName());
                jsonObject.put("companyNum", customerInfo.getCreditCode()==null?"":customerInfo.getCreditCode());
                jsonObject.put("legalName",customerInfo.getLegalPerson()==null?"":customerInfo.getLegalPerson());
                jsonObject.put("address",customerInfo.getCustomerAddress()==null?"":customerInfo.getCustomerAddress());
                jsonObject.put("phone",customerInfo.getLegalPersonPhone()==null?"":customerInfo.getLegalPersonPhone());
                String json = jsonObject.toString();
//                messageDetail.setJsonMessage(json);
//                messageDetail.setIdentity(customerInfo.getCreditCode());
                messageDetail.setModelId(companyModelId);
                messageDetail.setCreateTime(DateUtils.getNowDate());
                messageDetail.setUpdateTime(DateUtils.getNowDate());
                messageDetailList.add(messageDetail);
            }
            //商户
            if (customerInfo.getCustomerType().equals(3)){
                jsonObject.put("companyName", customerInfo.getCustomerName()==null?"":customerInfo.getCustomerName());
                jsonObject.put("companyNum", customerInfo.getCreditCode()==null?"":customerInfo.getCreditCode());
                jsonObject.put("legalName",customerInfo.getLegalPerson()==null?"":customerInfo.getLegalPerson());
                jsonObject.put("address",customerInfo.getCustomerAddress()==null?"":customerInfo.getCustomerAddress());
                jsonObject.put("phone",customerInfo.getLegalPersonPhone()==null?"":customerInfo.getLegalPersonPhone());
//                String json = jsonObject.toString();
//                messageDetail.setJsonMessage(json);
//                messageDetail.setIdentity(customerInfo.getCreditCode());
                messageDetail.setModelId(merchantModelId);
                messageDetail.setCreateTime(DateUtils.getNowDate());
                messageDetail.setUpdateTime(DateUtils.getNowDate());
                messageDetailList.add(messageDetail);
            }

        }
        List<List<FilingMessageDetail>> lists = ListUtils.partition(messageDetailList, 200);
        for (List<FilingMessageDetail> list : lists) {
            filingMessageDetailMapper.batchInsert(list);
        }
        log.error("未建档的客户基础信息同步模板完成");
    }

    @Override
    public List<CustomerIntroducer> queryCustomerIntroducer(CustomerIntroducer customerIntroducer) {
        if (customerIntroducer==null){
            throw new BusinessValidationException("查询条件不能为空！！");
        }
        List<CustomerIntroducer> resultList = new ArrayList<>();
        //查询绩效
        PerformanceCustomerInfo performanceParam = new PerformanceCustomerInfo();
        performanceParam.setCustomerName(customerIntroducer.getName());
        performanceParam.setCustomerPhone(customerIntroducer.getPhone());
        performanceParam.setCustomerIdentity(customerIntroducer.getIdentity());
        List<PerformanceCustomerInfo> list = performanceCustomerInfoMapper.list(performanceParam);
        if (list!=null && list.size()>0){
            for (PerformanceCustomerInfo performanceCustomerInfo : list) {
                CustomerIntroducer introducer = new CustomerIntroducer();
                introducer.setPhone(performanceCustomerInfo.getCustomerPhone());
                introducer.setName(performanceCustomerInfo.getCustomerName());
                introducer.setIdentity(performanceCustomerInfo.getCustomerIdentity());
                resultList.add(introducer);
            }
            return listDeduplication(resultList);
        }
        //查询建档
        FilingCustomerInfo filingParam = new FilingCustomerInfo();
        filingParam.setCustomerName(customerIntroducer.getName());
        filingParam.setCustomerPhone(customerIntroducer.getPhone());
        filingParam.setCustomerIdentity(customerIntroducer.getIdentity());
        List<FilingCustomerInfo> filingCustomerInfos = filingCustomerInfoMapper.list(filingParam);
        if (filingCustomerInfos!=null && filingCustomerInfos.size()>0){
            for (FilingCustomerInfo filingCustomerInfo : filingCustomerInfos) {
                if (filingCustomerInfo.getFilingStatus().equals("0")){
                    continue;
                }
                CustomerIntroducer introducer = new CustomerIntroducer();
                introducer.setPhone(filingCustomerInfo.getCustomerPhone());
                introducer.setName(filingCustomerInfo.getCustomerName());
                introducer.setIdentity(filingCustomerInfo.getCustomerIdentity());
                resultList.add(introducer);
            }

        }
        if (resultList==null || resultList.size()==0){
            return resultList;
        }
        return listDeduplication(resultList);
    }

    private List<CustomerIntroducer> listDeduplication(List<CustomerIntroducer> resultList){
        //介绍人根据手机号+证件号去重
        resultList = resultList.stream().collect(
                Collectors.toMap(
                        s->s.getIdentity() + "-" + s.getPhone(),
                        s->s,
                        (existing,replacement) -> existing)
        )
                .values()
                .stream()
                .collect(Collectors.toList());
        return resultList;
    }

    @Override
    public String getIsBankCustomer(String identity, String phone) {
        if(StringUtils.isEmpty(identity)&&StringUtils.isEmpty(phone)){
            return "0";
        }
        PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
        performanceCustomerInfo.setCustomerIdentity(identity);
        performanceCustomerInfo.setCustomerPhone(phone);
        List<PerformanceCustomerInfo> list1 =  performanceCustomerInfoMapper.list(performanceCustomerInfo);
        CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
        creditCustomerInfo.setCustomerIdentity(identity);
        creditCustomerInfo.setCustomerPhone(phone);
        List<CreditCustomerInfo> list2 =  creditCustomerInfoMapper.list(creditCustomerInfo);

        if ((list1==null||list1.size()==0)&&(list2==null||list2.size()==0)){
            return "0";
        }else {
            return "1";
        }

    }

}




