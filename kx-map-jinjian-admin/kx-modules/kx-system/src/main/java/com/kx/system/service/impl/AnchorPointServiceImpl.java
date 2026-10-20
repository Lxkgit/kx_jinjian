package com.kx.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.AnchorPointMini;
import com.kx.system.domain.anchor.PaPocAddress;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerParam;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.AnchorPointDTO;
import com.kx.system.domain.dto.AnchorTypeCountDTO;
import com.kx.system.domain.enums.CustomerTypeEnum;
import com.kx.system.domain.enums.LogOperateType;
import com.kx.system.domain.enums.LogType;
import com.kx.system.domain.es.EsAnchorPointDto;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.vo.CustomerJourneyVO;


import com.kx.system.mapper.*;
import com.kx.system.service.*;
import com.kx.system.service.es.EsAnchorPointGroupService;
import com.kx.system.service.es.EsAnchorPointService;
import com.kx.system.service.es.EsPerformanceCustomerInfoService;
import com.kx.system.task.AnchorPointDropGridJob;
import com.kx.system.task.AnchorPointGrid;
import com.kx.system.task.CustomInfoSyncJob;
import com.kx.system.util.BeanUtils;
import com.kx.system.util.UserUtil;
import com.kx.system.util.vgrid.GridUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kx.system.util.PointToolUtil.checkLot;

/**
 * 锚点Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-03
 */
@Service
@Slf4j
public class AnchorPointServiceImpl implements IAnchorPointService {
    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private UserUtil userUtil;

    @Resource
    private OpeningCardRecordMapper openingCardRecordMapper;

    @Resource
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private ISysDeptService sysDeptService;

    @Resource
    private IPaChangeLogService paChangeLogService;

    @Autowired
    private IPaAddrInfoTypeService paAddrInfoTypeService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private CustomInfoSyncJob customInfoSyncJob;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private EsAnchorPointService esAnchorPointService;
    @Resource
    private EsAnchorPointGroupService esAnchorPointGroupService;
    @Resource
    private EsPerformanceCustomerInfoService esPerformanceCustomerInfoService;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;
    @Resource
    private Executor asyncServiceExecutor;

    /**
     * 查询锚点
     *
     * @param id 锚点主键
     * @return 锚点
     */
    @Override
    public AnchorPoint selectAnchorPointById(Long id) {
        return anchorPointMapper.selectAnchorPointById(id);
    }

    /**
     * 查询锚点列表
     *
     * @param anchorPoint 锚点
     * @return 锚点
     */
    @Override
    @DataScope(deptAlias = "su", userAlias = "su")
    public List<AnchorPoint> selectAnchorPointList(AnchorPoint anchorPoint) {
        if (anchorPoint.getPageNum() != null && anchorPoint.getPageSize() != null) {
            PageHelper.startPage(anchorPoint.getPageNum(), anchorPoint.getPageSize());
        }
        return anchorPointMapper.selectAnchorPointList(anchorPoint);
    }

    @Override
    public AjaxResult esAnchorPointList(AnchorPoint anchorPoint, LoginUser loginUser) {
        // 权限
        // 获取当前登陆人信息
        JSONObject esUserInfo = sysUserService.getESUserInfo(loginUser);
        anchorPoint.setQueryEsRole(esUserInfo);

        if (StringUtils.isEmpty(anchorPoint.getType())) {
            throw new BusinessValidationException("锚点类型为空");
        }
        String indexName = "es_anchor_point";

        JSONObject jsonObject = esAnchorPointService.selectAnchorPointList(anchorPoint, indexName, anchorPoint.getPageSize(), anchorPoint.getPageNum());
        // 生成searchSourceBuilder
        List<AnchorPoint> anchorPoints = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), AnchorPoint.class);
        Integer total = jsonObject.getInteger("total");
        AjaxResult success = AjaxResult.success();
        success.put("rows", anchorPoints);
        success.put("total", total);
        return success;
    }

    /**
     * 新增锚点
     *
     * @param anchorPoint 锚点
     * @return 结果
     */
    @Override
    public int insertAnchorPoint(AnchorPoint anchorPoint) {
        int result = anchorPointMapper.insertAnchorPoint(anchorPoint);
        paChangeLogService.insertPaChangeLog(LogType.ANCHOR_MODIFY, LogOperateType.ADD, anchorPoint.getAnchorName(),
                anchorPoint, null, "新增锚点[" + anchorPoint.getAnchorName() + "]");
        return result;
    }

    /**
     * 修改锚点
     *
     * @param anchorPoint 锚点
     * @return 结果
     */
    @Transactional
    @Override
    public int updateAnchorPoint(AnchorPoint anchorPoint) {
        // 更新前锚点
        AnchorPoint before = anchorPointMapper.selectAnchorPointById(anchorPoint.getId());
        // 更新锚点信息，相同客户号的一块更新
        int result = anchorPointMapper.updateAnchorPointByNm(anchorPoint);


        AnchorPoint after = anchorPointMapper.selectAnchorPointById(anchorPoint.getId());
        // 更新关联客户信息
        updateCustomerAddr(after);
        paChangeLogService.insertPaChangeLog(LogType.ANCHOR_MODIFY, LogOperateType.EDIT, before.getAnchorName(), before,
                after, "更新锚点[" + anchorPoint.getAnchorName() + "]");
        // 坐标不变则不重新落格
        if (!before.getLon().equals(after.getLon())) {
            CompletableFuture.runAsync(() -> {
                List<AnchorPoint> anchorPoints = new ArrayList<>();
                anchorPoints.add(after);
                customInfoSyncJob.initAnchor(anchorPoints);
            });
        } else {
            // 更新ES
            EsAnchorPointDto esAnchorPoint = new EsAnchorPointDto();
            esAnchorPoint.setAddressDesc(anchorPoint.getAddressDesc());
            BeanUtils.copyBeanProperties(anchorPoint, esAnchorPoint);
            esAnchorPointService.updateAnchorPoint(esAnchorPoint, "es_anchor_point");
        }
        return result;
    }

    private void updateCustomerAddr(AnchorPoint anchorPoint) {
        // 1-绩效客户
        if (anchorPoint.getType().equals("1")) {
            PerformanceCustomerInfo performanceCustomerInfo =
                    performanceCustomerInfoMapper.selectPerformanceCustomerInfoById(anchorPoint.getCustomerId());
            if (performanceCustomerInfo == null) {
                return;
            }
            performanceCustomerInfo.setCustomerAddress(anchorPoint.getCustomerAddress());
            performanceCustomerInfoMapper.updatePerformanceCustomerInfo(performanceCustomerInfo);
        }
        // 2-授信客户
        if (anchorPoint.getType().equals("2")) {
            CreditCustomerInfo creditCustomerInfo =
                    creditCustomerInfoMapper.selectCreditCustomerInfoByCustomNm(anchorPoint.getCustomerNm());
            if (creditCustomerInfo == null) {
                return;
            }
            creditCustomerInfo.setCustomerAddress(anchorPoint.getCustomerAddress());
            creditCustomerInfoMapper.updateCreditCustomerInfo(creditCustomerInfo);
        }
        // 3-建档客户
        if (anchorPoint.getType().equals("3")) {
            FilingCustomerInfo filingCustomerInfo = filingCustomerInfoMapper.getById(anchorPoint.getCustomerId());
            if (filingCustomerInfo == null) {
                return;
            }
            filingCustomerInfo.setCustomerAddress(anchorPoint.getCustomerAddress());
            filingCustomerInfoMapper.updateFilingCustomerInfo(filingCustomerInfo);
        }
    }

    /**
     * 批量删除锚点
     *
     * @param ids 需要删除的锚点主键
     * @return 结果
     */
    @Override
    public int deleteAnchorPointByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<AnchorPoint> anchorPoints = anchorPointMapper.selectAnchorPointByIds(Arrays.asList(ids));
        String anchorNames = Arrays.toString(anchorPoints.stream().map(AnchorPoint::getAnchorName).toArray());
        int result = anchorPointMapper.deleteAnchorPointByIds(ids);
        paChangeLogService.insertPaChangeLog(LogType.ANCHOR_MODIFY, LogOperateType.DELETE, anchorNames, anchorPoints,
                null, "删除锚点[" + anchorNames + "]");
        return result;
    }

    /**
     * 删除锚点信息
     *
     * @param id 锚点主键
     * @return 结果
     */
    @Override
    public int deleteAnchorPointById(Long id) {
        AnchorPoint anchorPoint = anchorPointMapper.selectAnchorPointById(id);
        paChangeLogService.insertPaChangeLog(LogType.ANCHOR_MODIFY, LogOperateType.DELETE, anchorPoint.getAnchorName(),
                anchorPoint, null, "删除锚点[" + anchorPoint.getAnchorName() + "]");
        return anchorPointMapper.deleteAnchorPointById(id);
    }


    /**
     * 360-基础信息
     *
     * @param customInfoDTO
     * @return
     */
    @Override
    public AjaxResult selectAnchorPointDetail(CustomInfoDTO customInfoDTO) {
        // 通过首页点击锚点进入的
        PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
        BeanUtils.copyProperties(customInfoDTO, performanceCustomerInfo);

        // 贵阳试用的演示系统 无绩效客户 特殊处理 后续标品迭代使用下面被注释的代码
        JSONObject jsonObject = esPerformanceCustomerInfoService.selectPerformanceCustomerInfoList(performanceCustomerInfo, "es_credit_customer_info");
//      JSONObject jsonObject = esPerformanceCustomerInfoService.selectPerformanceCustomerInfoList(performanceCustomerInfo, "es_performance_customer_info");

        List<PerformanceCustomerInfo> infos = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), PerformanceCustomerInfo.class);
        if (!CollectionUtils.isEmpty(infos)) {
            performanceCustomerInfo = infos.get(0);

            if (StringUtils.isNotEmpty(performanceCustomerInfo.getGridManager()) && StringUtils.isNotEmpty(performanceCustomerInfo.getGridManagerName())) {
                String gridManager = ManagerStringUtil.managerNumSplitName(performanceCustomerInfo.getGridManager(), performanceCustomerInfo.getGridManagerName());
                performanceCustomerInfo.setGridManager(gridManager);
            }
        }
        return AjaxResult.success(performanceCustomerInfo);
    }

    @Override
    public List<AnchorPointMini> queryByCustomerTypeMini(CustomerSelect select) {
        List<String> typeList = select.getTypeList();
        if (CollectionUtils.isEmpty(typeList)) {
            throw new BusinessValidationException("请选择客户类型");
        }
        if (select.getOrgCodes() != null && select.getOrgCodes().size() > 0) {
            List<Long> deptIds = sysDeptService.selectDeptIdByCodes(select.getOrgCodes());
            select.setDeptIds(deptIds);
        }
        // 除授信客户外的所有客户类型查询、合并
        return randomSample(anchorPointMapper.selectByCustomerTypeMini(select), MINI_SAMPLE_SIZE);
    }

    @Override
    public List<AnchorPointMini> esByCustomerTypeMini(CustomerSelect select, LoginUser loginUser) {
        JSONObject esUserInfo = sysUserService.getESUserInfo(loginUser);
        select.setQueryEsRole(esUserInfo);
        JSONObject jsonObject = esAnchorPointService.esQueryGroupMini(select, "es_anchor_point");
        List<AnchorPointMini> anchorPointMini = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), AnchorPointMini.class);
        return anchorPointMini;
    }

    @Override
    public List<AnchorPoint> queryByCustomerType(CustomerSelect select) {
             List<String> typeList = select.getTypeList();
        if (CollectionUtils.isEmpty(typeList)) {
            throw new BusinessValidationException("请选择客户类型");
        }
        // 根据当前机构编码获取，本级以及下辖所有机构
        if (select.getOrgCodes() != null && select.getOrgCodes().size() > 0) {
            List<Long> deptIds = sysDeptService.selectDeptIdByCodes(select.getOrgCodes());
            select.setDeptIds(deptIds);
        }
        // 除授信客户外的所有客户类型查询、合并
        return anchorPointMapper.selectByCustomerType(select);
    }

    @Override
    public List<AnchorPointMini> queryGroupMini(CustomerSelect select) {
        // 根据当前网格获取，本级以及下辖所有网格
        if (StringUtils.isNotEmpty(select.getGridCode())) {
            List<PaAddrInfoType> gridList = paAddrInfoTypeService.getSelfAndChild(select.getGridCode());
            List<String> gridCodes = gridList.stream().map(PaAddrInfoType::getCode).collect(Collectors.toList());
            select.setGridChildCodes(gridCodes);
        }
        // 根据当前机构编码获取，本级以及下辖所有机构
        if (select.getOrgCodes() != null && select.getOrgCodes().size() > 0) {
            List<SysDept> orgCodes = sysDeptService.getSelfAndChild(select.getOrgCodes());
            List<String> orgList = orgCodes.stream().map(SysDept::getOrgNum).collect(Collectors.toList());
            select.setOrgCodes(orgList);
        }
        return randomSample(anchorPointMapper.queryGroupMini(select), MINI_SAMPLE_SIZE);
    }

    @Override
    public List<AnchorPointMini> esGroupMini(CustomerSelect select) {

        JSONObject jsonObject = esAnchorPointGroupService.esQueryGroupMini(select, "es_anchor_point_group");
        List<AnchorPointMini> anchorPointMini = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), AnchorPointMini.class);
        return anchorPointMini;
    }

    @Override
    public List<AnchorPoint> queryGroup(CustomerSelect select) {
        // 根据当前机构编码获取，本级以及下辖所有机构
        if (select.getOrgCodes() != null && select.getOrgCodes().size() > 0) {
            List<SysDept> orgCodes = sysDeptService.getSelfAndChild(select.getOrgCodes());
            List<String> orgList = orgCodes.stream().map(SysDept::getOrgNum).collect(Collectors.toList());
            select.setOrgCodes(orgList);
        }

        return anchorPointMapper.queryGroup(select);
    }

    /**
     * 360-客户旅程
     *
     * @param customInfoDTO
     * @return
     */
    @Override
    public AjaxResult selectAnchorPointVisit(CustomInfoDTO customInfoDTO) {
        // 通过首页点击锚点进入的
        if (null == customInfoDTO.getCustomerId() && customInfoDTO.getAnchorPointId() != null) {
            // 根据锚点Id 去查询客户身份证号
            String identity = anchorPointMapper.selectCustomerId(customInfoDTO);
            if (StringUtils.isEmpty(identity)) {
                throw new BusinessValidationException("未查询到该用户信息");
            }
            customInfoDTO.setCustomerIdentity(identity);
        }
        if (userUtil.getLoginUser() == null) {
            throw new BusinessValidationException("用户信息有误");
        }
        customInfoDTO.setManagerNum(userUtil.getLoginUser().getManagerNum());
        List<CustomerJourneyVO> customerJourneyVO = openingCardRecordMapper.listInfo(customInfoDTO);

        // 修改走访信息中走访人的名称  修改后:客户经理(客户经理工号)
        customerJourneyVO = updateCustomerJourneyVOZfManagerName(customerJourneyVO);

        return AjaxResult.success(customerJourneyVO);
    }

    @Override
    public List<String> selectLabelByAnchorId(CustomInfoDTO customInfoDTO) {
        // 通过首页点击锚点进入的
        if (customInfoDTO.getAnchorPointId() != null) {
            // 根据锚点Id 去查询客户身份证号
            String identity = anchorPointMapper.selectCustomerId(customInfoDTO);
            if (StringUtils.isEmpty(identity)) {
                throw new BusinessValidationException("未查询到该用户信息");
            }
            customInfoDTO.setCustomerIdentity(identity);
        }
        // 绩效客户
        if (customInfoDTO.getType().equals(CustomerTypeEnum.performance_customer.getCode())) {
            PerformanceCustomerInfo performanceCustomerInfo = new PerformanceCustomerInfo();
            List<PerformanceCustomerInfo> infos = performanceCustomerInfoMapper.selectCustomerInfo(customInfoDTO);
            if (!CollectionUtils.isEmpty(infos)) {
                performanceCustomerInfo = infos.get(0);
                customInfoDTO.setCustomerNm(performanceCustomerInfo.getCustomerNm());
                customInfoDTO.setDeptId(performanceCustomerInfo.getDeptId());
            }
        }
        // 授信客户
        if (customInfoDTO.getType().equals(CustomerTypeEnum.credit_customer.getCode())) {
            List<CreditCustomerInfo> creditCustomerInfos = creditCustomerInfoMapper.selectCustomerInfo(customInfoDTO);
            CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
            if (!CollectionUtils.isEmpty(creditCustomerInfos)) {
                creditCustomerInfo = creditCustomerInfos.get(0);
                customInfoDTO.setCustomerNm(creditCustomerInfo.getCustomerNm());
                customInfoDTO.setDeptId(creditCustomerInfo.getDeptId());
            }
        }
        // 建档客户
        if (customInfoDTO.getType().equals(CustomerTypeEnum.filing_customer.getCode())) {
            FilingCustomerInfo filingCustomerInfo = filingCustomerInfoMapper.selectCustomerInfo(customInfoDTO);
            customInfoDTO.setCustomerNm(filingCustomerInfo.getCustomerNm());
            customInfoDTO.setDeptId(filingCustomerInfo.getDeptId());
            customInfoDTO.setCustomerId(filingCustomerInfo.getId());
        }

        return labelInfoMapper.selectCustomerLabels(customInfoDTO);
    }

    @Override
    public List<CreditRecord> selectCreditRecordByAnchorId(CustomInfoDTO customInfoDTO) {
        // 通过首页点击锚点进入的
        if (customInfoDTO.getAnchorPointId() != null) {
            // 根据锚点Id 去查询客户 Id
            String identity = anchorPointMapper.selectCustomerId(customInfoDTO);
            if (StringUtils.isEmpty(identity)) {
                throw new BusinessValidationException("未查询到该用户信息");
            }
            customInfoDTO.setCustomerIdentity(identity);
        }
//        String managerNum = userUtil.getLoginUser().getManagerNum();
        CreditRecord creditRecord = new CreditRecord();
        creditRecord.setIdentity(customInfoDTO.getCustomerIdentity());
//        creditRecord.setManagerNum(managerNum);
        return creditRecordMapper.selectCreditRecordList(creditRecord);
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<AnchorPoint> queryCustomerByName(CustomerParam param) {
        return anchorPointMapper.queryCustomerByName(param);
    }

    /**
     * 根据地址查询网格信息
     *
     * @param addressDesc 搜索地址信息
     * @return
     */
    @Override
    public List<AnchorPoint> queryCustomerByAddr(String addressDesc) {
        if (StringUtils.isNull(addressDesc))
            throw new BusinessValidationException("地址不能为空");
        return anchorPointMapper.queryCustomerByAddr(addressDesc);
    }

    /**
     * 根据网点名称查询网点信息
     *
     * @param anchorName
     * @return
     */
    @Override
    public List<AnchorPoint> queryCustomerByAnchor(String anchorName) {
        if (StringUtils.isNull(anchorName))
            throw new BusinessValidationException("网点名称不能为空");

        return anchorPointMapper.queryCustomerByAnchor(anchorName);
    }

    /**
     * 根据网点名称查询网点信息
     *
     * @param branchName
     * @return
     */
    @Override
    public List<PaAddrInfoType> queryCustomerByBranch(String branchName) {
        if (StringUtils.isNull(branchName))
            throw new BusinessValidationException("网格不能为空");
        List<PaAddrInfoType> paAddrInfoTypes = anchorPointMapper.queryCustomerByBranch(branchName);
        paAddrInfoTypes.stream().forEach(paAddrInfoType -> {
            // 拼接 threeGrid, fourGrid 和 name，加入分隔符 /
            String fullName = (paAddrInfoType.getThreeGrid() != null ? paAddrInfoType.getThreeGrid() : "") +
                    (paAddrInfoType.getThreeGrid() != null && paAddrInfoType.getFourGrid() != null ? "/" : "") +
                    (paAddrInfoType.getFourGrid() != null ? paAddrInfoType.getFourGrid() : "") +
                    (paAddrInfoType.getFourGrid() != null && paAddrInfoType.getName() != null ? "/" : "") +
                    (paAddrInfoType.getName() != null ? paAddrInfoType.getName() : "");
            paAddrInfoType.setName(fullName);
        });
        return paAddrInfoTypes;
    }

    /**
     * 匹配五级边界地址跑批
     * @return
     */
    @Override
    public String tests() {

        //获取所有
        List<PaAddrInfoType> list = paAddrInfoTypeMapper.testSelect();
        List<PaPocAddress> addressList = paAddrInfoTypeMapper.address();

        for (PaAddrInfoType paAddrInfoType : list) {
            for (PaPocAddress paPocAddress : addressList) {
                if(checkLot(paAddrInfoType.getCoordinates(),paPocAddress.getLat(),paPocAddress.getLon())){
                    paPocAddress.setCode(paAddrInfoType.getCode());
                }
            }
        }
        List<List<PaPocAddress>> partition = ListUtils.partition(addressList, 500);
        for (List<PaPocAddress> paPocAddresses : partition) {
            paAddrInfoTypeMapper.addressUpdateByLonLat(paPocAddresses);

        }

        return "ok";
    }




    @Override
    @Async("asyncServiceExecutor")
    public void insertBatch(List<AnchorPoint> anchorPoints, CountDownLatch countDownLatch, int length, int i) {
        try {
            anchorPointMapper.insertBatch(anchorPoints);
            log.info("================锚点跑批进度" + (i + 1) + "/" + length);
        } finally {
            countDownLatch.countDown();
        }
    }

    /**
     * 获取绩效客户的锚点数据
     *
     * @param customerNmList
     * @param customerType
     * @return
     */
    @Override
    public Map<String, AnchorPoint> getAnchorPointByCustomerNm(List<String> customerNmList, String customerType) {
        Map<String, AnchorPoint> anchorPointMap = new HashMap<>();
        if (StringUtils.isNotEmpty(customerNmList) && StringUtils.isNotEmpty(customerType)) {
//            List<AnchorPoint> anchorPointList = anchorPointMapper.selectAnchorPointByCustomerNms(customerNmList, customerType);

            List<AnchorPoint> anchorPointList = esAnchorPointService.selectAnchorPointList(customerNmList, customerType);
            anchorPointMap.putAll(anchorPointList.stream().collect(Collectors.toMap(AnchorPoint::getCustomerNm, Function.identity(), (key1, key2) -> key2)));
        }
        return anchorPointMap;
    }

    @Override
    public List<AnchorPoint> queryBusinessByBoundary(CustomerSelect select) {
        double[][] doubles = JSONObject.parseObject(select.getBoundary(), double[][].class);

        // 获取边界最大最小值
        Map<String, Double> maxAndMin = getMaxAndMin(doubles);
        double maxLon = maxAndMin.get("maxLon");
        double minLon = maxAndMin.get("minLon");
        double maxLat = maxAndMin.get("maxLat");
        double minLat = maxAndMin.get("minLat");

        List<AnchorPoint> anchorPoints = anchorPointMapper.selectBusinessAll(maxLon, minLon, maxLat, minLat);
        List<AnchorPoint> anchorPointsList = new ArrayList<>();

        if (org.apache.commons.lang3.StringUtils.isNotEmpty(select.getBoundary())) {
            // 判断点位是否在边界中
            int limit = 0;
            for (AnchorPoint anchorPoint : anchorPoints) {
                if (GridUtil.isPointInMultiPolygon(anchorPoint.getLon().doubleValue(),
                        anchorPoint.getLat().doubleValue(), doubles)) {
                    anchorPointsList.add(anchorPoint);
                    limit++;
                }
                if (limit > select.getSize()) {
                    break;
                }
            }

        }
        return anchorPointsList;
    }


    private List<CustomerJourneyVO> updateCustomerJourneyVOZfManagerName(List<CustomerJourneyVO> customerJourneyVOS) {
        // 获取所有客户经理工号
        Set<String> managers = new HashSet<>();
        for (CustomerJourneyVO customerJourneyVO : customerJourneyVOS) {
            if (customerJourneyVO.getType().intValue() == 3) {
                managers.add(customerJourneyVO.getFirstField());
            }
        }
        List<String> managerList = new ArrayList<>();
        for (String manager : managers) {
            managerList.add(manager);
        }

        if (managerList.size() <= 0) {
            return customerJourneyVOS;
        }
        // 查询客户经理信息
        List<SysUser> sysUsers = sysUserMapper.selectUserByManagerNumList(managerList);

        for (SysUser sysUser : sysUsers) {


            for (CustomerJourneyVO customerJourneyVO : customerJourneyVOS) {
                if (customerJourneyVO.getType().intValue() != 3) {
                    continue;
                }

                if (sysUser.getManagerNum().equals(customerJourneyVO.getFirstField())) {
                    String name = ManagerStringUtil.managerNumSplitName(sysUser.getManagerNum(), sysUser.getNickName());
                    customerJourneyVO.setFirstField(name);
                }
            }

        }
        return customerJourneyVOS;

    }


    private Map<String, Double> getMaxAndMin(double[][] doubles) {

        double maxLon = doubles[0][0];
        double minLon = doubles[0][0];
        double maxLat = doubles[0][1];
        double minLat = doubles[0][1];
        Map<String, Double> result = new HashMap<>();
        for (double[] aDouble : doubles) {

            // 纬度
            if (aDouble[1] > maxLat) {
                maxLat = aDouble[1];
            }
            if (aDouble[1] < minLat) {
                minLat = aDouble[1];
            }
            // 经度
            if (aDouble[0] > maxLon) {
                maxLon = aDouble[0];
            }
            if (aDouble[0] < minLon) {
                minLon = aDouble[0];
            }
        }
        result.put("maxLon", maxLon);
        result.put("minLon", minLon);
        result.put("maxLat", maxLat);
        result.put("minLat", minLat);


        return result;
    }


    /**
     * 筛选网点计数
     * @param gridCode
     * @param groupId
     * @return
     */
    @Override
    public AnchorTypeCountDTO getTotalCount(String gridCode, Integer groupId) {
        AnchorTypeCountDTO totalCount;
        if (ObjectUtil.isNull(groupId)){
            totalCount = anchorPointMapper.getAnchorTotal(gridCode);

        }else {
            totalCount = anchorPointMapper.getTotalCount(gridCode, groupId);
        }

        Map<Integer, String> groupIdMapping = new HashMap<>();
        if (!ObjectUtil.isNull(groupId)){
            groupIdMapping.put(groupId, String.valueOf(groupId));
        }else {
            groupIdMapping.put(groupId, "0");
        }

        String customGroupId = groupIdMapping.getOrDefault(groupId, "0");
            return AnchorTypeCountDTO.builder()
                    .count(totalCount.getCount())
                    .customGroupId(customGroupId)
                    .build();
    }

    @Override
    public List<AnchorPoint> allAnchor(String anchorName) {
        return anchorPointMapper.selectAllAnchor(anchorName);
    }

    @Override
    public List<AnchorPoint> getEquipment(String anchorName) {
        return anchorPointMapper.selectEquipment(anchorName);
    }


    @Autowired
    AnchorPointDropGridJob anchorPointDropGridJob;
    @Override
    public void anchCode() {
        HashMap<String, List<PaAddrInfoType>> child = anchorPointDropGridJob.getChild();
        int count = anchorPointMapper.selectPaPocAddressCount();
//        int pageSize = 10000;//194312
        int pageSize = 1000;//200524
        int maxPage = count / pageSize + 1;
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(maxPage);

        for (int i = 1; i <= maxPage; i++){
            int from = (i - 1) * pageSize;
            asyncServiceExecutor.execute(() -> {
                try {
                    List<AnchorPointGrid> anchorPointGrids = anchorPointMapper.selectPaPocAddress(from, pageSize);
                    //更新网格
                    if (!anchorPointGrids.isEmpty()){
                        anchorPointDropGridJob.initAnchor(anchorPointGrids,child,"330700");
                        anchorPointMapper.updatePaPocAddressCode(anchorPointGrids);
                    }
                }finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
            log.info("################耗时##############：{}", System.currentTimeMillis() - startTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int MINI_SAMPLE_SIZE = 1000;

    /**
     * 蓄水池抽样：从 list 中均匀随机抽取 sampleSize 条，不足时原样返回。
     * 时间复杂度 O(n)，空间复杂度 O(sampleSize)，无需全量 shuffle。
     */
    private <T> List<T> randomSample(List<T> list, int sampleSize) {
        if (list.size() <= sampleSize) {
            return list;
        }
        List<T> reservoir = new ArrayList<>(list.subList(0, sampleSize));
        Random random = new Random();
        for (int i = sampleSize; i < list.size(); i++) {
            int j = random.nextInt(i + 1);
            if (j < sampleSize) {
                reservoir.set(j, list.get(i));
            }
        }
        return reservoir;
    }

}
