package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.exception.ServiceException;
import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.SpringUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.service.TokenService;
import com.kx.common.security.utils.DictUtils;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.AndOrEnum;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.config.enums.GroupSatusEnum;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.customer.CustomGroupDTO;
import com.kx.system.domain.customer.CustomerInfo;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.CascadeLabelDTO;
import com.kx.system.domain.dto.CustomGroupPageDTO;
import com.kx.system.domain.dto.CustomerInfoDTO;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.group.CustomGroup;
import com.kx.system.domain.group.CustomGroupCustomer;
import com.kx.system.domain.label.LabelGroup;
import com.kx.system.domain.label.LabelInfo;
import com.kx.system.domain.req.CustomGroupPageReq;
import com.kx.system.domain.req.CustomGroupReq;
import com.kx.system.domain.req.GroupManageReq;
import com.kx.system.mapper.*;
import com.kx.system.service.CustomGroupService;
import com.kx.system.service.CustomerGroupCategoryService;
import com.kx.system.service.es.EsCreditCustMemberService;
import com.kx.system.service.es.EsCustMemberService;
import com.kx.system.service.es.EsFilingCustMemberService;
import com.kx.system.service.es.EsPerCustGroupMemberService;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 86176
 * @description 针对表【custom_group(客群)】的数据库操作Service实现
 * @createDate 2024-01-18 11:08:03
 */
@Service
public class CustomGroupServiceImpl implements CustomGroupService {

    @Resource
    private CustomGroupMapper mapper;

    @Autowired
    private TokenService tokenService;

    @Resource
    private CustomerInfoMapper customerInfoMapper;

    @Resource
    private PaAddrInfoMapper paAddrInfoMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private LabelGroupMapper labelGroupMapper;

    @Resource
    private CustomerLabelMapper customerLabelMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Resource
    private CustomGroupCustomerMapper customGroupCustomerMapper;

    @Resource
    private CustomGroupCustomerServiceImpl customGroupCustomerService;

    @Resource
    private CustomerGroupCategoryService customerGroupCategoryService;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Autowired
    private UserUtil userUtil;
    @Resource
    private EsCustMemberService esCustMemberService;

    @Resource
    private EsPerCustGroupMemberService esPerCustGroupMemberService;
    @Resource
    private EsCreditCustMemberService esCreditCustMemberService;
    @Resource
    private EsFilingCustMemberService esFilingCustMemberService;

    @Override
    public TableDataInfo listNotPage(CustomGroupPageReq req) {
        TableDataInfo rspData = new TableDataInfo();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        req.setUserName(loginUser.getUsername());
        req.setManagerNum(loginUser.getUser().getManagerNum());
        rspData.setRows(mapper.selectCustomGroupList(req));
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        return rspData;
    }

    @Override
    public TableDataInfo queryPage(CustomGroupPageReq req) {
        if (StringUtils.isNotNull(req.getPageNum()) && StringUtils.isNotNull(req.getPageSize())) {
            PageHelper.startPage(req.getPageNum(), req.getPageSize());
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        req.setManagerNum(loginUser.getUser().getManagerNum());
        List<CustomGroup> customGroups = mapper.selectCustomGroupList(req);

        List<CustomGroupPageDTO> dtoList = customGroups.stream().map(g -> {
            CustomGroupPageDTO dto = new CustomGroupPageDTO();
            BeanUtil.copyProperties(g, dto);
            String createBy = g.getCreateBy();
            if (StringUtils.isNotEmpty(g.getCreateBy()) && StringUtils.isNotEmpty(g.getCreateNum())) {
                createBy = ManagerStringUtil.managerNumSplitName(g.getCreateNum(), g.getCreateBy());
            }
            dto.setCreateBy(createBy);
            dto.setGroupStatusDesc(g.getGroupStatus().equals(0) ? "待生成" : "已生成");
            //查询客群标签
            List<Long> labelIds = JSONUtil.parseArray(g.getLabelIds()).toList(Long.class);
            dto.setLabelIds(labelIds);
            return dto;
        }).collect(Collectors.toList());

        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(dtoList);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(customGroups).getTotal());
        return rspData;
    }

    @Override
    public AjaxResult addOrEditGroup(CustomGroupReq req) {
        if (StrUtil.isEmpty(req.getCustomGroupName()) || null == req.getCustomType()) {
            return AjaxResult.error("参数异常！");
        }
        if (null != req.getId()) {
            //编辑
            CustomGroup group = mapper.selectCustomGroupById(req.getId());
            if (null == group) {
                return AjaxResult.error("参数异常！");
            }
            BeanUtil.copyProperties(req, group);
            group.setUpdateBy(SecurityUtils.getUsername());
            group.setUpdateTime(new Date());
            group.setLabelIds(JSONUtil.toJsonStr(req.getLabelIds()));
            group.setUpdateFlag(req.getUpdateFlag());
            group.setGroupStatus(GroupSatusEnum.IS_NOT_FINISH.getCode());
            if (!CollectionUtils.isEmpty(req.getLabelIds())) {
                SpringUtils.getAopProxy(this).initCustomer(group, req);
            } else {
                group.setGroupStatus(GroupSatusEnum.IS_FINISH.getCode());
            }
            mapper.updateCustomGroup(group);
        } else {
            //新增
            CustomGroup group = new CustomGroup();
            BeanUtil.copyProperties(req, group);
            group.setCustomNum(0);
            group.setCustomGroupSource("标签圈选");
            group.setGroupStatus(GroupSatusEnum.IS_NOT_FINISH.getCode());
            group.setCreateBy(SecurityUtils.getUsername());
            group.setCreateNum(SecurityUtils.getLoginUser().getUser().getManagerNum());
            group.setCreateTime(new Date());
            group.setLabelIds(JSONUtil.toJsonStr(req.getLabelIds()));
            group.setUpdateFlag(req.getUpdateFlag());
            mapper.insertCustomGroup(group);
            SpringUtils.getAopProxy(this).initCustomer(group, req);
        }
        return AjaxResult.success();
    }

    /**
     * 初始化客群下的客户数据
     *
     * @param group
     * @param req
     */
    @Async("asyncServiceExecutor")
    public void initCustomer(CustomGroup group, CustomGroupReq req) {
        //异步保存客户信息
        List<CustomerLabel> customerIdList;
        req.setSize(req.getLabelIds().size());
        if (req.getUpdateFlag() == AndOrEnum.OR.getCode()) {
            //全或
            customerIdList = customerLabelMapper.queryListOrLabel(req);
        } else {
            //全且
            customerIdList = customerLabelMapper.queryListAndLabel(req);
        }
        //清除此客群下的客户
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        customGroupCustomer.setCustomGroupId(group.getId());
        customGroupCustomerService.deleteAlll(customGroupCustomer);
        group.setCustomNum(0);
        mapper.clearNum(group);
        //保存客群与客户关系表数据
        if (!CollectionUtils.isEmpty(customerIdList)) {
            List<CustomGroupCustomer> result = new ArrayList<>();
            customerIdList.stream().forEach(e -> {
                if (null !=e.getCustomerId() && null != group.getId()) {
                    CustomGroupCustomer temp = new CustomGroupCustomer();
                    temp.setCustomerId(e.getCustomerId());
                    temp.setCustomGroupId(group.getId());
                    result.add(temp);
                }
            });
            if (result.size()>0) {
                customGroupCustomerService.saveBatch(result);
                group.setCustomNum(result.size());
            }
        }
        group.setGroupStatus(GroupSatusEnum.IS_FINISH.getCode());
        mapper.updateCustomGroup(group);
        //初始化ES
        //清空原客群群数据
        GroupManageReq groupManageReq = new GroupManageReq();
        groupManageReq.setGroupId(group.getId());
        esCustMemberService.removeMember(groupManageReq);
        CustomGroup customGroup = mapper.selectCustomGroupById(group.getId());
        esCustMemberService.insertBatchAsync(group.getCustomNum(),customGroup.getCustomType(),group.getId());

    }

    @Override
    public AjaxResult delGroupList(GroupManageReq req) {
        CustomGroup group = new CustomGroup();
        group.setUpdateBy(SecurityUtils.getUsername());
        group.setUpdateTime(new Date());
        group.setDelFlag(1);
        group.setIds(req.getIds());
        mapper.updateCustomGroupList(group);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult delGroup(Long id) {
        CustomGroup group = mapper.selectCustomGroupById(id);
        if (null == group) {
            return AjaxResult.error("参数异常！");
        }
        group.setUpdateBy(SecurityUtils.getUsername());
        group.setUpdateTime(new Date());
        group.setDelFlag(1);
        mapper.updateCustomGroup(group);
        return AjaxResult.success();
    }

    @Override
    public TableDataInfo manageCustomerPage(GroupManageReq req) {
        int idNum = 0;
        TableDataInfo rspData = new TableDataInfo();
        //根据不同类型获取客户数据
//        if (StringUtils.isNotNull(req.getPageNum()) && StringUtils.isNotNull(req.getPageSize())) {
//            PageHelper.startPage(req.getPageNum(), req.getPageSize());
//        }
//        if (req.getCustomType().intValue() == CustomTypeNewEnum.PERFORMANCE.getCode()) {
//            JSONObject jsonObject =  esPerCustGroupMemberService.esQueryPerMemberPage(req, req.getPageSize(), req.getPageNum());
//            List<PerformanceCustomerInfo> result = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), PerformanceCustomerInfo.class);
//            idNum = jsonObject.getInteger("total");
//            rspData.setRows(result);
//        } else if (req.getCustomType().intValue() == CustomTypeNewEnum.CREDIT.getCode()) {
//            JSONObject jsonObject = esCreditCustMemberService.esQueryCreditMemberPage(req, req.getPageSize(), req.getPageNum());
//            List<CreditCustomerInfo> result = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), CreditCustomerInfo.class);
//            idNum = jsonObject.getInteger("total");
//            rspData.setRows(result);
//        } else if (req.getCustomType().intValue() == CustomTypeNewEnum.FILING.getCode()) {
//            JSONObject jsonObject = esFilingCustMemberService.esQueryFilingMemberPage(req, req.getPageSize(), req.getPageNum());
//            List<FilingCustomerInfo> result = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), FilingCustomerInfo.class);
//            idNum = jsonObject.getInteger("total");
//            rspData.setRows(result);
//
//        }
        PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
        performanceCustomerInfo.setGroupId(req.getGroupId());
        List<PerformanceCustomerInfo> list = performanceCustomerInfoMapper.listByRenHang(performanceCustomerInfo);
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(idNum);
        rspData.setCode(HttpStatus.SUCCESS);
        return rspData;
    }

    @Override
    public List<CustomerInfoDTO> managePage(GroupManageReq req) {
        List<CustomerInfoDTO> list0 = new ArrayList<>();
        CustomGroup group = mapper.selectCustomGroupById(req.getGroupId());
        if (null == group) {
            return list0;
        }
        //取出客群对应客户的ids
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        customGroupCustomer.setCustomGroupId(req.getGroupId());
        List<Long> customerIds = customGroupCustomerMapper.selectCustomer(customGroupCustomer);
        if (customerIds.isEmpty()) {
            return list0;
        }
//        List<Long> list = JSONUtil.parseArray(group.getCustomMember()).toList(Long.class);
        CustomerInfo infoReq = new CustomerInfo();
        BeanUtil.copyProperties(req, infoReq);
        if (StringUtils.isNotNull(req.getPageNum()) && StringUtils.isNotNull(req.getPageSize())) {
            PageHelper.startPage(req.getPageNum(), req.getPageSize());
        }
        List<CustomerInfo> customerInfos = customerInfoMapper.selectCustomerGroupInfoList(infoReq, customerIds);
        if (CollectionUtil.isNotEmpty(customerInfos)) {
            List<SysDictData> card_type = DictUtils.getDictCache("card_type");
            List<SysDictData> sys_custom_type = DictUtils.getDictCache("sys_custom_type");
            List<SysDictData> files_status = DictUtils.getDictCache("files_status");
            customerInfos.forEach(r -> {
                CustomerInfoDTO dto = new CustomerInfoDTO();
                BeanUtil.copyProperties(r, dto);
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
                //网格员
                SysUser sysUser = sysUserMapper.selectUserById(r.getGridUserId().longValue());
                if (null != sysUser) {
                    dto.setGridUserDesc(sysUser.getUserName());
                }
                list0.add(dto);
            });
        }
        return list0;
    }

    @Override
    public AjaxResult removeMember(GroupManageReq req) {
        if (null == req.getGroupId()) {
            return AjaxResult.error("参数异常！");
        }
        if (CollectionUtil.isEmpty(req.getIds())) {
            return AjaxResult.success();
        }
        CustomGroup group = mapper.selectCustomGroupById(req.getGroupId());
        if (null == group) {
            return AjaxResult.error("参数异常！");
        }
//        List<Long> list = JSONUtil.parseArray(group.getCustomMember()).toList(Long.class);
        //取出客群对应客户的ids
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
//        customGroupCustomer.setCustomGroupId(req.getGroupId());
//        List<Long> customerIds  = customGroupCustomerMapper.selectCustomer(customGroupCustomer);
//        customerIds.removeAll(req.getIds());
//        CustomGroup customGroup = new CustomGroup();
//        customGroup.setId(group.getId());
//        customGroup.setCustomMember(JSONUtil.toJsonStr(customerIds));
//        customGroup.setCustomNum(customerIds.size());
//        mapper.updateCustomGroup(customGroup);
        customGroupCustomer.setCustomGroupId(req.getGroupId());
        customGroupCustomer.setCustomerIds(req.getIds());
        customGroupCustomerMapper.deleteCustomGroupCustomer(customGroupCustomer);

        List<Long> longs = customGroupCustomerMapper.selectCustomer(customGroupCustomer);
        CustomGroup customGroup = mapper.selectCustomGroupById(req.getGroupId());
        customGroup.setCustomNum(longs.size());
        mapper.updateCustomGroup(customGroup);

//        if (customGroup.getCustomNum() == 1) {
//            mapper.clearNum(customGroup);
//        } else if (customGroup.getCustomNum() > 1) {
//            customGroup.setCustomNum(customGroup.getCustomNum() - 1);
//            mapper.updateCustomGroup(customGroup);
//        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult addMember(GroupManageReq req) {
        if (null == req.getGroupId() || CollectionUtil.isEmpty(req.getIds())) {
            return AjaxResult.error("参数异常！");
        }
        CustomGroup group = mapper.selectCustomGroupById(req.getGroupId());
        if (null == group) {
            return AjaxResult.error("参数异常！");
        }
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        customGroupCustomer.setCustomGroupId(req.getGroupId());
        customGroupCustomer.setCustomerIds(req.getIds());
//        CustomGroup customGroup = new CustomGroup();
//        customGroup.setId(group.getId());
//        List<Long> list = JSONUtil.parseArray(group.getCustomMember()).toList(Long.class);
//        list.addAll(req.getIds());
//        Set<Long> set = new HashSet<>(list);
//        List<Long> longs = new ArrayList<>(set);
//        customGroup.setCustomMember(JSONUtil.toJsonStr(longs));
//        customGroup.setCustomNum(longs.size());
//        mapper.updateCustomGroup(customGroup);
        customGroupCustomerMapper.insertCustomGroupCustomer(customGroupCustomer);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult cascadeLabel(Long id) {
        if (null == id) {
            return AjaxResult.error("参数异常！");
        }
        List<CascadeLabelDTO> dtoList = new ArrayList<>();
        List<LabelGroup> labelGroups;
        //如果是建档客户，只展示网格标签
        if(id != 3){
            labelGroups = labelGroupMapper.selectAll();
        }else {
            labelGroups = labelGroupMapper.selectGridLabel();
        }

        labelGroups.forEach(g -> {
            if (g.getId().equals(id)) {
                CascadeLabelDTO dto = new CascadeLabelDTO();
                dto.setId(g.getId());
                dto.setName(g.getLabelGroupName());
                //二级标签目录
                List<CascadeLabelDTO> dtoList1 = new ArrayList<>();
                labelGroups.forEach(labelGroup -> {
                    if (labelGroup.getParentId().equals(id)) {
                        CascadeLabelDTO dto1 = new CascadeLabelDTO();
                        dto1.setId(labelGroup.getId());
                        dto1.setName(labelGroup.getLabelGroupName());
                        //三级   标签
                        List<LabelInfo> labelInfos = labelInfoMapper.selectLabelInfoByGroupId(labelGroup.getId());
                        if (CollectionUtil.isNotEmpty(labelInfos)) {
                            List<CascadeLabelDTO> dtoList2 = new ArrayList<>();
                            labelInfos.forEach(l -> {
                                CascadeLabelDTO dto2 = new CascadeLabelDTO();
                                dto2.setId(l.getId());
                                dto2.setName(l.getLabelName());
                                dtoList2.add(dto2);
                            });
                            dto1.setChild(dtoList2);
                        }
                        dtoList1.add(dto1);
                    }
                });
                dto.setChild(dtoList1);
                dtoList.add(dto);
            }
        });
        return AjaxResult.success(dtoList);
    }

    @Override
    public List<CustomGroup> getChildCustomGroup(Integer type) {
        if (type == null) {
            throw new BusinessValidationException("请先选择任务类型");
        }
        //工号，只能看自己创建的
//        String jobNo = userUtil.getLoginUser().getUserName();
        String jobNo = userUtil.getLoginUser().getManagerNum();
        return mapper.selectGroupByType(type, jobNo);
    }


    private CascadeLabelDTO getLabels(Long id, List<Long> groupIds, List<Long> labelIds) {
        CascadeLabelDTO cascadeLabelDTO = new CascadeLabelDTO();
        if (CollectionUtil.isEmpty(labelIds) || CollectionUtil.isEmpty(groupIds) || null == id) {
            return cascadeLabelDTO;
        }
        List<LabelGroup> labelGroups = labelGroupMapper.selectAll();
        labelGroups.forEach(g -> {
            if (g.getId().equals(id)) {
                cascadeLabelDTO.setId(g.getId());
                cascadeLabelDTO.setName(g.getLabelGroupName());
                //二级标签目录
                List<CascadeLabelDTO> dtoList1 = new ArrayList<>();
                labelGroups.forEach(labelGroup -> {
                    if (groupIds.contains(labelGroup.getId()) && labelGroup.getParentId().equals(id)) {
                        CascadeLabelDTO dto1 = new CascadeLabelDTO();
                        dto1.setId(labelGroup.getId());
                        dto1.setName(labelGroup.getLabelGroupName());
                        //三级   标签
                        List<LabelInfo> labelInfos = labelInfoMapper.selectLabelInfoByGroupId(labelGroup.getId());
                        if (CollectionUtil.isNotEmpty(labelInfos)) {
                            List<CascadeLabelDTO> dtoList2 = new ArrayList<>();
                            labelInfos.forEach(l -> {
                                if (labelIds.contains(l.getId())) {
                                    CascadeLabelDTO dto2 = new CascadeLabelDTO();
                                    dto2.setId(l.getId());
                                    dto2.setName(l.getLabelName());
                                    dtoList2.add(dto2);
                                }
                            });
                            dto1.setChild(dtoList2);
                        }
                        dtoList1.add(dto1);
                    }
                });
                cascadeLabelDTO.setChild(dtoList1);
            }
        });
        return cascadeLabelDTO;
    }

    /**
     * 保存客群中客户的方法
     *
     * @param
     * @return
     */
    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public AjaxResult saveCustomGroup(CustomGroupDTO customGroupDTO) {
        if (customGroupDTO.getType() == null) {
            return AjaxResult.warn("请选择客户的类型");
        }
        CustomGroup customGroup = new CustomGroup();
        BeanUtil.copyProperties(customGroupDTO, customGroup);
        int customNum = 0;
        LoginUser loginUser = new LoginUser();
        //绩效客户
        if (customGroupDTO.getType().equals(1)) {
            PerformanceCustomerInfo customerInfo = new PerformanceCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            customNum = performanceCustomerInfoMapper.selectPerformanceCustomerInfoIdCount(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.PERFORMANCE.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.PERFORMANCE.getCode() + ""));
        }
        //授信客户
        if (customGroupDTO.getType().equals(2)) {
            CreditCustomerInfo customerInfo = new CreditCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            customNum = creditCustomerInfoMapper.selectCreditCustomerInfoIdCount(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.CREDIT.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.CREDIT.getCode() + ""));
        }
        //建档客户
        if (customGroupDTO.getType().equals(3)) {
            FilingCustomerInfo customerInfo = new FilingCustomerInfo();
            BeanUtil.copyProperties(customGroupDTO, customerInfo);
            loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            customNum = filingCustomerInfoMapper.listIdCount(customerInfo);
            customGroup.setCustomType(CustomTypeNewEnum.FILING.getCode());
            customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.FILING.getCode() + ""));
        }

        if (customNum == 0) {
            return AjaxResult.error("未选择客户,无法保存客群");
        }
        customGroup.setCustomNum(customNum);
        customGroup.setLabelIds(JSONUtil.toJsonStr(customGroupDTO.getLabelIds()));
        customGroup.setCustomGroupSource("条件筛选");
        customGroup.setCreateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        customGroup.setCreateNum(SecurityUtils.getLoginUser().getUser().getManagerNum());
        customGroup.setCreateTime(new Date());
        mapper.insertCustomGroup(customGroup);
        //获取新增客群的 Id
        customGroupDTO.setCustomGroupId(customGroup.getId());
        customerGroupCategoryService.asyncSaveCustomGroup(customGroupDTO, loginUser);
        return AjaxResult.success("保存成功");
    }


    @Override
    public AjaxResult saveGroup(PerformanceCustomerInfo customerInfo) {
        //新增客群目录
        CustomGroup customGroup = new CustomGroup();
        customGroup.setCustomGroupName(customerInfo.getCustomGroupName());
        customGroup.setGroupDesc(customerInfo.getGroupDesc());
        List<PerformanceCustomerInfo> list = performanceCustomerInfoMapper.listByRenHang(customerInfo);
        if (list==null || list.size()==0){
            return AjaxResult.error("未选择客户,无法保存客群");
        }
        customGroup.setCustomNum(list.size());
        customGroup.setCustomType(CustomTypeNewEnum.PERFORMANCE.getCode());
        customGroup.setCategoryId(Long.parseLong(CustomTypeNewEnum.PERFORMANCE.getCode() + ""));
        customGroup.setCustomGroupSource("条件筛选");
        customGroup.setCreateBy(SecurityUtils.getLoginUser().getUser().getNickName());
        customGroup.setCreateNum(SecurityUtils.getLoginUser().getUser().getManagerNum());
        customGroup.setCreateTime(new Date());
        mapper.insertCustomGroup(customGroup);
        //新增客群
        List<Long> idList = list.stream().map(PerformanceCustomerInfo::getId).collect(Collectors.toList());
        Long id = customGroup.getId();
        customerGroupCategoryService.saveGroup(idList,id);
        return AjaxResult.success("保存成功");
    }

    /**
     * 根据客群 IdList 取出其中客户的 IdList
     *
     * @param groupIdList
     * @return
     */
    @Override
    public List<Long> getCustomerIdList(List<Long> groupIdList) {
        CustomGroupCustomer customGroupCustomer = new CustomGroupCustomer();
        //根据客群Id 取出该客群下客户的Ids
        HashSet<Long> customerIds = new HashSet<>();
        for (Long groupId : groupIdList) {
            customGroupCustomer.setCustomGroupId(groupId);
            List<Long> ids = customGroupCustomerMapper.selectCustomer(customGroupCustomer);
            customerIds.addAll(ids);
        }
        if (customerIds.isEmpty()) {
            throw new ServiceException("该客群下面没有客户,请换一个客群");
        }
        List<Long> customerIdList = new ArrayList<>(customerIds);
        return customerIdList;
    }
}




