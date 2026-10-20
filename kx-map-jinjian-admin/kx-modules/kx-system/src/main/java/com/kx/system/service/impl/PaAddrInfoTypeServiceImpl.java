package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.SpringUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.datascope.domain.enums.DataScopeEnums;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.GridInfoParam;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.boundary.BoundaryData;
import com.kx.system.domain.boundary.Features;
import com.kx.system.domain.boundary.Geometry;
import com.kx.system.domain.boundary.Properties;
import com.kx.system.domain.es.EsAnchorPointDto;
import com.kx.system.domain.grid.GridMessageInfo;
import com.kx.system.domain.grid.GridMessageInfo2;
import com.kx.system.domain.param.SearchAddrInfoUserParam;
import com.kx.system.domain.vo.AreaSplitPointVO;
import com.kx.system.mapper.*;
import com.alibaba.fastjson.JSONArray;
import com.kx.system.service.GridMessageInfoService;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.service.IPaChangeLogService;
import com.kx.system.service.es.EsAnchorPointService;
import com.kx.system.util.UserUtil;
import com.kx.system.util.vgrid.GridUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kx.common.datascope.utils.DataScopeUtil.getHighestRoleLevel;
import static com.kx.system.config.constants.SysDictTypeConstants.ADDR_INFO_TYPE;
import static com.kx.system.domain.enums.LogOperateType.*;
import static com.kx.system.domain.enums.LogType.GRID_MODIFY;
import static com.kx.system.domain.enums.LogType.GRID_USER_MODIFY;

@Service
public class PaAddrInfoTypeServiceImpl implements IPaAddrInfoTypeService {

    private static final Logger log = LoggerFactory.getLogger(PaAddrInfoTypeServiceImpl.class);

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Resource
    private IPaChangeLogService paChangeLogService;

    @Resource
    private SysDictDataMapper sysDictDataMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private GridMessageInfoMapper gridMessageInfoMapper;

    @Resource
    private GridMessageInfoService gridMessageInfoService;

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private EsAnchorPointService esAnchorPointService;
    @Resource
    private UserUtil userUtil;

    @Override
    public void initAddrInfoType(PaAddrInfoType paAddrInfoType) {
        // 判断此类型是否有数据
        if (paAddrInfoTypeMapper.getSum(paAddrInfoType) == 0) {
            // 无数据则进行初始化，传入地区编码进行数据复制
            paAddrInfoType.setParentCode("5201"); // 根据登录人数据权限获取code
            paAddrInfoTypeMapper.initAddrInfoType(paAddrInfoType);
        }
    }

    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<PaAddrInfoType> getAddrInfoTypeBottom(PaAddrInfoType paAddrInfoType) {
        if (StringUtils.isEmpty(paAddrInfoType.getCode())) {
            return paAddrInfoTypeMapper.getPaAddrInfoTypeListByPermission(paAddrInfoType);
        }
        return paAddrInfoTypeMapper.getAddrInfoTypeBottom(paAddrInfoType);
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<SysUser> getPaAddrInfoTypeUser(SearchAddrInfoUserParam param) {
        return paAddrInfoTypeMapper.getPaAddrInfoTypeUser(param);
    }

    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<PaAddrInfoType> getPaAddrInfoTypeList(PaAddrInfoType paAddrInfoType) {

        if (!ObjectUtils.isEmpty(paAddrInfoType.getParentCode())) {
            return getPaAddrInfoTypes(paAddrInfoType);
        }
        //查询获取最小的地图等级
        SysUser loginUser = userUtil.getLoginUser();
        if ( loginUser.isAdmin()) {


        }
        Integer minAddrLevel = paAddrInfoTypeMapper.getMinAddrLevel(paAddrInfoType.getTypeCode(),loginUser.getUserId());
        if (minAddrLevel !=null){
            paAddrInfoType.setAddrLevel(minAddrLevel);
        }

        // 对于没有parentCode的查询，要找到有权限的最小层级数据
        Integer minimumCount = paAddrInfoTypeMapper.getMinimumCount();
        List<PaAddrInfoType> permittedCodes = paAddrInfoTypeMapper.getPaAddrInfoTypeListByPermission(paAddrInfoType);
        permittedCodes.forEach(m->m.setMinimumNum(minimumCount));
        filterChildPaAddrInfoType(permittedCodes);
        return permittedCodes;
    }
    @Override
    @DataScope(deptAlias = "b", userAlias = "b")
    public List<PaAddrInfoType> getPaAddrInfoTypeLists(PaAddrInfoType paAddrInfoType) {
        if (!ObjectUtils.isEmpty(paAddrInfoType.getParentCode())) {
            return getPaAddrInfoTypes(paAddrInfoType);
        }else {
            Object dataScope = paAddrInfoType.getParams().get("dataScope");
            if (dataScope == null || StringUtils.isEmpty(dataScope.toString())) {
                paAddrInfoType.setParentCode("100000");
            }
            List<PaAddrInfoType> permittedCodes = paAddrInfoTypeMapper.getPaAddrInfoTypeListByPermission(paAddrInfoType);
            filterChildPaAddrInfoType(permittedCodes);
            return permittedCodes;
        }

    }

    @NotNull
    private List<PaAddrInfoType> getPaAddrInfoTypes(PaAddrInfoType paAddrInfoType) {
        List<PaAddrInfoType> paAddrInfoTypeList = paAddrInfoTypeMapper.getPaAddrInfoTypeList(paAddrInfoType);
        paAddrInfoTypeList.forEach(type -> {
            if (type.getCode().startsWith("330702") || type.getCode().startsWith("330703") || type.getCode().startsWith("330781")) {
                type.setLightOrGray(true);
            } else {
                type.setLightOrGray(false);
            }
        });

        return paAddrInfoTypeList;
    }

    @Override
    public List<PaAddrInfoType> getPaAddrInfoType(PaAddrInfoType paAddrInfoType) {
        if (ObjectUtils.isEmpty(paAddrInfoType.getCode())) {
            throw new BusinessValidationException("网格Code为空");
        }

        return paAddrInfoTypeMapper.getPaAddrInfoTypeList(paAddrInfoType);
    }

    @Override
    public List<PaAddrInfo> getPaAddrInfoList(PaAddrInfo paAddrInfo) {
        /**
         * 稠州系统不再查询基础网格数据，所有网格数据都从特色网格中获取。
         * 默认特色网格类型获取规则见com.kx.system.service.impl.PaAddrInfoTypeServiceImpl#getDefaultAddrInfoTypeId()
         */
        List<PaAddrInfoType> paAddrInfoTypeList =
                SpringUtils.getAopProxy(this).getPaAddrInfoTypeList(toPaAddrInfoType(paAddrInfo));
        return paAddrInfoTypeList.stream().map(this::toPaAddrInfo).collect(Collectors.toList());
    }

    private PaAddrInfo toPaAddrInfo(PaAddrInfoType paAddrInfoType) {
        PaAddrInfo paAddrInfo = new PaAddrInfo();
        BeanUtils.copyProperties(paAddrInfoType, paAddrInfo);
        return paAddrInfo;
    }

    private PaAddrInfoType toPaAddrInfoType(PaAddrInfo paAddrInfo) {
        PaAddrInfoType paAddrInfoType = new PaAddrInfoType();
        paAddrInfoType.setParentCode(paAddrInfo.getParentCode());
        paAddrInfoType.setTypeCode(getDefaultAddrInfoTypeId());
        paAddrInfoType.setCode(paAddrInfoType.getCode());
        paAddrInfoType.setAddrLevel(paAddrInfoType.getAddrLevel());
        return paAddrInfoType;
    }

    @Override
    @Transactional
    public int update(PaAddrInfoType paAddrInfoType) {
        PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
        int result = paAddrInfoTypeMapper.updateAddrInfo(paAddrInfoType);
        PaAddrInfoType after = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
        paChangeLogService.insertPaChangeLog(GRID_MODIFY, EDIT, before.getName(), before, after,
                "边界更新" + paAddrInfoType.getName());

        //更新网格基础信息-网格类型
//        GridMessageInfo gridMessageInfo = gridMessageInfoMapper.getByGridCode(paAddrInfoType.getCode());
//        if (null != gridMessageInfo) {
//            GridMessageInfo gridInfo = new GridMessageInfo();
//            gridInfo.setId(gridMessageInfo.getId());
//            gridInfo.setRuralGridType(paAddrInfoType.getRuralGridType());
//            gridInfo.setUpdateTime(DateUtils.getNowDate());
//            gridMessageInfoMapper.updateGridMessageInfo(gridInfo);
//        } else {
//            gridMessageInfoService.gridMessageSynchroPaAddrInfo(paAddrInfoType.getCode());
//        }
        return result;
    }


    @Override
    @Transactional
    public void updateBatch(List<PaAddrInfoType> paAddrInfoTypeList) {
        if (!CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            paAddrInfoTypeList.forEach(e -> {
                PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(e.getId());
                paAddrInfoTypeMapper.updateAddrInfo(e);
                PaAddrInfoType after = paAddrInfoTypeMapper.selectAddrInfoById(e.getId());
                paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_COORDINATES_MODIFY, before.getName(), before,
                        after, "边界调整" + before.getName());
            });
        }
    }

    @Override
    public AjaxResult updateGridUser(PaAddrInfoType paAddrInfoType) {
//        if (!StringUtils.isEmpty(paAddrInfoType.getUserManagerNum())) {
//            List<String> secondManagerNumList = paAddrInfoType.getSecondManagerNum();
//            for (String  secondManagerNum : secondManagerNumList) {
//                if ( StringUtils.equals(paAddrInfoType.getUserManagerNum(), secondManagerNum)){
//                    return AjaxResult.warn("主网格员和副网格员不能为同一人");
//                }
//            }
//
//        }
        PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
        PaAddrInfoType update = new PaAddrInfoType();
        update.setId(before.getId());
        // 网格员变更
        String logContent = "[" + before.getName() + "]网格员变更";
        if (!Objects.equals(before.getUserManagerNum(), paAddrInfoType.getUserManagerNum())) {
            logContent += "[网格主管员工号：" + before.getUserManagerNum() + "->" + paAddrInfoType.getUserManagerNum() + "]";
            SysUser user = sysUserMapper.selectUserByManagerNum2(paAddrInfoType.getUserManagerNum());

            update.setUserManagerNum(paAddrInfoType.getUserManagerNum());
            update.setUserId(user.getUserId());

        }

        //list先排序在比较
        if (before.getSecondManagerNum()!=null && before.getSecondManagerNum().size() >0 ){
            Collections.sort(before.getSecondManagerNum());
        }
        if (paAddrInfoType.getSecondManagerNum()!=null && paAddrInfoType.getSecondManagerNum().size() >0 ){
            Collections.sort(paAddrInfoType.getSecondManagerNum());
        }
        if ( (before.getSecondManagerNum()== null
                && paAddrInfoType.getSecondManagerNum()!=null
                && paAddrInfoType.getSecondManagerNum().size()>0)
                ||( !before.getSecondManagerNum().containsAll(paAddrInfoType.getSecondManagerNum()))) {
            logContent += "[网格副管员工号：" + before.getSecondManagerNum() + "->" + paAddrInfoType.getSecondManagerNum() + "]";
            List<String> userIds = new ArrayList<>();
            List<String> userNameList = new ArrayList<>();
            for (String s : paAddrInfoType.getSecondManagerNum()) {

                SysUser secUser = sysUserMapper.selectUserByManagerNum2(s);
                userIds.add(secUser.getUserId().toString());
                userNameList.add(secUser.getNickName().toString());
            }
            update.setSecondUserName(userNameList);
            update.setSecondManagerNum(paAddrInfoType.getSecondManagerNum());
            update.setSecondUserId(userIds);
        }

        //网格员变更不做检查
        if (before.getDeptId() == null && update.getUserId() != null) {
            SysUser user = sysUserMapper.selectUserById(update.getUserId());
            update.setDeptId(user.getDeptId());
            update.setDeptName(user.getDept().getDeptName());
        }
        update.setManagerNum(update.getUserManagerNum());
        paAddrInfoTypeMapper.updateAddrInfo2(update);
        PaAddrInfoType after = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
        paChangeLogService.insertPaChangeLog(GRID_USER_MODIFY, EDIT, before.getName(), before, after,
                logContent);

        //更新网格基础信息-管理员
        updateGridAdminInfo(before.getCode(), after.getDeptId(), paAddrInfoType);
        //客户，锚点网格员变更
        CompletableFuture.runAsync(() -> {
            anchorPointMapper.updateGridManagerByBelongGrid(before.getCode(), paAddrInfoType.getUserManagerNum());
            performanceCustomerInfoMapper.updateGridManager(before.getCode());
            creditCustomerInfoMapper.updateGridManager(before.getCode());
            filingCustomerInfoMapper.updateGridManager(before.getCode());
            //更新ES
            EsAnchorPointDto esAnchorPoint = new EsAnchorPointDto();
            esAnchorPoint.setBelongGrid(before.getCode());
            esAnchorPoint.setGridManager(paAddrInfoType.getUserManagerNum());
            esAnchorPointService.updateAnchorPointByCode(esAnchorPoint,"es_anchor_point");
        });
        return AjaxResult.success();
    }

    private void updateGridAdminInfo(String code, Long deptId, PaAddrInfoType paAddrInfoType) {
        if (StringUtils.isNotEmpty(code) && null != paAddrInfoType) {
//            GridMessageInfo gridMessageInfo = gridMessageInfoMapper.getByGridCode(code);
//            if (null != gridMessageInfo) {
//                SysUser user = sysUserMapper.selectUserByManagerNum2(paAddrInfoType.getUserManagerNum());
//
////                SysUser secUser = sysUserMapper.selectUserByManagerNum2(paAddrInfoType.getSecondManagerNum());
//
//                SysDept sysDept = sysDeptMapper.selectDeptById(deptId);
//
//                GridMessageInfo updateGridInfo = new GridMessageInfo();
//                updateGridInfo.setId(gridMessageInfo.getId());
//                updateGridInfo.setGridMainManagerCode(user.getManagerNum());
//                updateGridInfo.setGridMainManagerName(user.getNickName());
//
//                updateGridInfo.setGridSideManagerCode(paAddrInfoType.getSecondManagerNum());
//                updateGridInfo.setGridSideManagerName(paAddrInfoType.getSecondUserName());
//
//                if (null != sysDept) {
//                    updateGridInfo.setBranch(sysDept.getDeptName());
//                }
//                updateGridInfo.setUpdateTime(DateUtils.getNowDate());
//                gridMessageInfoMapper.updateGridMessageInfo(updateGridInfo);
//            } else {
//                // 同步更新网格基础信息维护表
//                gridMessageInfoService.gridMessageSynchroPaAddrInfo(code);
//            }
        }
    }

    //todo 后期修改
    @Transactional
    @Override
    public void updateGridDepts(List<PaAddrInfoType> paAddrInfoTypeList) {
        if (CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            return;
        }
        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypeList) {
            PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
            AnchorPoint anchorPoint = anchorPointMapper.selectAnchorPointById(paAddrInfoType.getAnchorId());
            if (before == null || anchorPoint == null) {
                throw new BusinessValidationException("网格或责任网点不存在，请重新选择");
            }

            // 老数据仍使用 user_id 关联绩效客户；新导入的银行网点可能没有绩效客户，
            // 此时也必须保存责任网点到网格基础信息表，供网格列表、详情和大屏共同读取。
            if (paAddrInfoType.getUserId() != null) {
                PaAddrInfoType update = new PaAddrInfoType();
                update.setId(paAddrInfoType.getId());
                update.setUserId(paAddrInfoType.getUserId());
                paAddrInfoTypeMapper.updateAddrInfo(update);
            }

            String dotTapy = sysDeptMapper.selectDotTapy(anchorPoint.getAnchorName());
            String[] split = StringUtils.isEmpty(dotTapy) ? new String[0] : dotTapy.split(",");
            // 检查split数组中是否包含元素"2"
            boolean containsTwo = false;
            for (String s : split) {
                if ("2".equals(s.trim())) {
                    containsTwo = true;
                    break;
                }
            }
            if(!containsTwo){
                // 如果split不包含2，则添加一个新元素"2"并与原split的所有元素进行拼接
                // 创建一个新的StringBuilder用于拼接字符串
                StringBuilder newDotTapy = new StringBuilder();
                for (int i = 0; i < split.length; i++) {
                    newDotTapy.append(split[i]);
                    if (i < split.length - 1) {
                        newDotTapy.append(",");
                    }
                }
                if (newDotTapy.length() > 0) {
                    newDotTapy.append(",");
                }
                newDotTapy.append("2");

                // 更新原表
                sysDeptMapper.updateDotTapy(anchorPoint.getAnchorName(), newDotTapy.toString());
            }
            // 更新网格基础信息表
            GridMessageInfo2 gridMessageInfo = gridMessageInfoMapper.getByGridCode(before.getCode());
            String beforeDeptName = gridMessageInfo == null || StringUtils.isEmpty(gridMessageInfo.getDutyNetworkName())
                    ? "未设置" : gridMessageInfo.getDutyNetworkName();
            GridMessageInfo2 updateGridInfo = new GridMessageInfo2();
            updateGridInfo.setGridCode(before.getCode());
            updateGridInfo.setGridName(before.getName());
            updateGridInfo.setRuralGridType(before.getRuralGridType());
            updateGridInfo.setDutyNetworkName(anchorPoint.getAnchorName());
            updateGridInfo.setDutyNetworkCode(anchorPoint.getCustomerNm());
            updateGridInfo.setDutyNetworkAddress(anchorPoint.getAddressDesc());
            updateGridInfo.setBankAgencyName(anchorPoint.getBankAgencyName());
            updateGridInfo.setDutyName(anchorPoint.getDutyName());
            updateGridInfo.setDutyPhone(anchorPoint.getDutyPhone());
            updateGridInfo.setDutyTelephone(anchorPoint.getDutyTelephone());
            updateGridInfo.setUpdateTime(DateUtils.getNowDate());
            if (gridMessageInfo == null) {
                updateGridInfo.setCreateTime(DateUtils.getNowDate());
                gridMessageInfoMapper.insertGridMessageInfo(updateGridInfo);
            } else {
                updateGridInfo.setId(gridMessageInfo.getId());
                gridMessageInfoMapper.updateGridMessageInfo(updateGridInfo);
            }

            // if (before.getUserId() != null) {
            //     paAddrInfoTypeMapper.cleanUser(before.getId());
            // }
            PaAddrInfoType after = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_DEPT_EDIT, before.getName(), before, after,
                    "辖区分配[" + before.getName() + ":" + beforeDeptName + "->" + anchorPoint.getAnchorName() + "]");
        }
    }

    @Override
    public List<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(OrgGridInfoParam orgGridInfoParam) {
        return paAddrInfoTypeMapper.getPaAddrInfoTypeByOrgCode(orgGridInfoParam);
    }

    @Override
    public List<GridInfoDto> getGridInfo(GridInfoParam gridInfoParam) {
        return paAddrInfoTypeMapper.getGridInfo();
    }

    @Override
    public GridInfoDto getGridInfoByCode(String gridCode) {
        return paAddrInfoTypeMapper.getGridInfoByCode(gridCode);
    }

    // 辖区分配
    @Override
    public void updateGridDept(List<PaAddrInfoType> paAddrInfoTypeList) {
        if (CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            return;
        }
        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypeList) {
            PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());

            //机构有无变更，不做检查
//            if (Objects.equals(before.getDeptId(), paAddrInfoType.getDeptId())) {
//                continue;
//            }
            SysDept sysDept = sysDeptMapper.selectDeptById(paAddrInfoType.getDeptId());
            PaAddrInfoType update = new PaAddrInfoType();
            update.setId(before.getId());
            update.setDeptId(paAddrInfoType.getDeptId());
            update.setDeptCode(sysDept == null ? null : sysDept.getOrgNum());
//            update.setDeptName(paAddrInfoType.getDeptName());
            paAddrInfoTypeMapper.updateAddrInfo(update);

            // 更新网格基础信息表
            String beforeDeptName = "null";
//            GridMessageInfo gridMessageInfo = gridMessageInfoMapper.getByGridCode(before.getCode());
//            if (null != gridMessageInfo) {
//                beforeDeptName = gridMessageInfo.getBranch();
//                GridMessageInfo updateGridInfo = new GridMessageInfo();
//                updateGridInfo.setId(gridMessageInfo.getId());
////                updateGridInfo.setBranch(sysDept.getDeptName());
//                updateGridInfo.setDutyNetworkName(sysDept.getDeptName());
//                updateGridInfo.setDutyNetworkAddress(sysDept.getAddress());
//                updateGridInfo.setBankAgencyName(sysDept.getBankAgencyName());
//                updateGridInfo.setUpdateTime(DateUtils.getNowDate());
//                updateGridInfo.setDutyName(sysDept.getDutyName());
//                updateGridInfo.setDutyPhone(sysDept.getDutyPhone());
//                updateGridInfo.setDutyTelephone(sysDept.getDutyTelephone());
//                gridMessageInfoMapper.updateGridMessageInfo(updateGridInfo);
//            }

            if (before.getUserId() != null) {
                paAddrInfoTypeMapper.cleanUser(before.getId());
            }
            PaAddrInfoType after = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_DEPT_EDIT, before.getName(), before, after,
                    "辖区分配[" + before.getName() + ":" + beforeDeptName + "->" + sysDept.getDeptName() + "]");
        }
    }

    @Override
    public Set<String> getPaAddrInfoTypeListByUser(SysUser user) {
        DataScopeEnums dataScope = getHighestRoleLevel(user);
        List<PaAddrInfoType> paAddrInfoTypes;
        //只查看本人管辖的网格
        if (dataScope == DataScopeEnums.DATA_SCOPE_SELF) {
            paAddrInfoTypes = paAddrInfoTypeMapper.selectByUserId(user.getUserId());
        } else {
            paAddrInfoTypes = paAddrInfoTypeMapper.selectByDeptId(dataScope.getHighestLevelDeptId(user.getDept()));
        }
        filterChildPaAddrInfoType(paAddrInfoTypes);
        return paAddrInfoTypes.stream().map(PaAddrInfoType::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPaAddrInfoTypeListByDeptCode(String deptCode) {
        List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectByDeptCode(deptCode);
        filterChildPaAddrInfoType(paAddrInfoTypes);
        return paAddrInfoTypes.stream().map(PaAddrInfoType::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getPaAddrInfoTypeListByManagerCode(String managerCode) {
        SysUser sysUser = sysUserMapper.selectUserByManagerNum2(managerCode);
        List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectByUserId(sysUser.getUserId());
        filterChildPaAddrInfoType(paAddrInfoTypes);
        return paAddrInfoTypes.stream().map(PaAddrInfoType::getCode).collect(Collectors.toSet());
    }

    //网格去重，如果网格存在父子关系，则过滤掉子网格
    public void filterChildPaAddrInfoType(List<PaAddrInfoType> paAddrInfoTypes) {
        if (CollectionUtils.isEmpty(paAddrInfoTypes) || paAddrInfoTypes.size() == 1) {
            return;
        }
        Set<String> ancestorCodes = paAddrInfoTypes.stream().map(PaAddrInfoType::getAncestorCodes).collect(Collectors.toSet());
        Iterator<PaAddrInfoType> iterator = paAddrInfoTypes.iterator();
        while (iterator.hasNext()) {
            PaAddrInfoType next = iterator.next();
            if(Objects.isNull(next)){
                continue;
            }
            String nextAncestorCodes = next.getAncestorCodes();
            if (nextAncestorCodes == null) {
                continue;
            }
            for (String ancestorCode : ancestorCodes) {
                if (ancestorCode != null && !nextAncestorCodes.equals(ancestorCode) && nextAncestorCodes.startsWith(ancestorCode)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public List<PaAddrInfoType> getSelfAndChild(String gridCode) {
        PaAddrInfoType grid = paAddrInfoTypeMapper.getByGridCode(gridCode);
        List<PaAddrInfoType> list = paAddrInfoTypeMapper.selectByParentCode(gridCode);
        list.add(grid);
        if (list.size() > 1) {
            //递归查询 获取网格树
            int flag = 0;
            List<String> parentCodes = list.stream().map(PaAddrInfoType::getCode).collect(Collectors.toList());
            while (flag == 0) {
                if (parentCodes.isEmpty()) {
                    break;
                }
                List<PaAddrInfoType> paAddrInfoTypeList = paAddrInfoTypeMapper.selectByParentCodeList(parentCodes);
                if (paAddrInfoTypeList == null || paAddrInfoTypeList.size() == 0) {
                    break;
                }
                list.addAll(paAddrInfoTypeList);
                if (paAddrInfoTypeList.get(0).getAddrLevel() == 6) {
                    flag = 1;
                }
                parentCodes = paAddrInfoTypeList.stream().map(PaAddrInfoType::getCode).collect(Collectors.toList());

            }
        }
        return list;
    }

    @Override
    public List<PaAddrInfoType> listAllAddrInfoWithOutCoordinates() {
        PaAddrInfoType query = new PaAddrInfoType();
        query.setTypeCode(getDefaultAddrInfoTypeId());
        return paAddrInfoTypeMapper.getPaAddrInfoTypeList(query);
    }

    @Override
    public int add(PaAddrInfoType paAddrInfoType) {
        if (StringUtils.isNotEmpty(paAddrInfoType.getParentCode())) {
            // 先去数据库查询最大code在做处理
            int maxId = getAddrCodeMax(paAddrInfoType.getParentCode(), "ad");
//            String  code = paAddrInfoType.getParentCode() + "ad" + String.format("%02d", maxId + 1);
            String code = creatByGridCode(paAddrInfoType.getParentCode(), maxId);
            paAddrInfoType.setCode(code);
            paAddrInfoType.setAncestorCodes(paAddrInfoType.getAncestorCodes() + paAddrInfoType.getCode() + ",");
            paAddrInfoType.setId(null);
        } else {
            paAddrInfoType.setAncestorCodes("," + paAddrInfoType.getCode() + ",");
        }
        paAddrInfoType.setManagerNum(paAddrInfoType.getUserManagerNum());
        int result = paAddrInfoTypeMapper.insertAddrInfo(paAddrInfoType);

        //新增网格基础信息表数据
//        GridMessageInfo gridMessageInfo = new GridMessageInfo();
//        gridMessageInfo.setRuralGridType(paAddrInfoType.getRuralGridType());
//        gridMessageInfo.setGridCode(paAddrInfoType.getCode());
//        gridMessageInfo.setGridName(paAddrInfoType.getName());
        // 主管员
        // if (StringUtils.isNotEmpty(paAddrInfoType.getName()) & paAddrInfoType.getUserId() != null) {
        //     SysUser sysUser = sysUserMapper.selectUserById(paAddrInfoType.getUserId());
        //     gridMessageInfo.setGridMainManagerCode(sysUser.getManagerNum());
        //     gridMessageInfo.setGridMainManagerName(paAddrInfoType.getUserName());
        // }
        // //副管员
        // if ((paAddrInfoType.getSecondUserName() !=null && paAddrInfoType.getSecondUserName().size()>0)
        //         && (paAddrInfoType.getSecondUserId() != null && paAddrInfoType.getSecondUserId().size()>0)) {
        //
        //     List<String> secUserIds = new ArrayList<>();
        //
        //     for (String userIdStr : paAddrInfoType.getSecondUserId()) {
        //         SysUser sysUser = sysUserMapper.selectUserById(new Long(userIdStr));
        //         secUserIds.add(sysUser.getUserId().toString());
        //     }
        //
        //     gridMessageInfo.setGridSideManagerCode(secUserIds);
        //     gridMessageInfo.setGridSideManagerName(paAddrInfoType.getSecondUserName());
        // }\
        //todo 等后续确定需求更改数据
//        gridMessageInfo.setGridMainManagerCode(null);
//        gridMessageInfo.setGridMainManagerName(paAddrInfoType.getUserName());
//        gridMessageInfo.setGridSideManagerCode(null);
//        gridMessageInfo.setGridSideManagerName(paAddrInfoType.getSecondUserName());
//        gridMessageInfo.setCreateTime(DateUtils.getNowDate());
//        gridMessageInfo.setUpdateTime(DateUtils.getNowDate());
//        gridMessageInfoMapper.insertGridMessageInfo(gridMessageInfo);
        paChangeLogService.insertPaChangeLog(GRID_MODIFY, ADD, paAddrInfoType.getName(), null, paAddrInfoType,
                "新增网格[" + paAddrInfoType.getName() + "]");
        return result;
    }

    @Override
    public void reOrgAncestorCodes() {
        List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectCodeAndParentCodeAll();
        Map<String, PaAddrInfoType> map = paAddrInfoTypes.stream()
                .collect(Collectors.toMap(PaAddrInfoType::getCode, Function.identity()));

        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
            setAncestorCode(map, paAddrInfoType);
        }

        for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
            paAddrInfoTypeMapper.updateAddrInfoByCode(paAddrInfoType);
        }
    }

    @Override
    public HashMap<String, String> getMinGridCount(String gridCode) {
        HashMap<String,String> map = new HashMap<>();
        if (Objects.isNull(gridCode)|| gridCode.isEmpty()|| "330700".equals(gridCode)){
            map.put("minimumCount",paAddrInfoTypeMapper.getMinimumCount().toString());
            return map;
        }
        map.put("minGridCount",paAddrInfoTypeMapper.getMinimumCountByParentCode(gridCode).toString());
        return map;
    }

    private void setAncestorCode(Map<String, PaAddrInfoType> map, PaAddrInfoType paAddrInfoType) {
        if (StringUtils.isEmpty(paAddrInfoType.getAncestorCodes())) {
            PaAddrInfoType parent = map.get(paAddrInfoType.getParentCode());
            if (parent == null) {
                paAddrInfoType.setAncestorCodes("," + paAddrInfoType.getCode() + ",");
                return;
            }
            if (StringUtils.isEmpty(parent.getAncestorCodes())) {
                setAncestorCode(map, parent);
            }
            paAddrInfoType.setAncestorCodes(parent.getAncestorCodes() + paAddrInfoType.getCode() + ",");
        }
    }

    /**
     * 查询某网格下子网格的ID最大值
     *
     * @param parentCode parentCode
     * @param type       cf|qf|ad
     * @return
     */
    private int getAddrCodeMax(String parentCode, String type) {
        List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectByParentCode(parentCode);
        return paAddrInfoTypes.stream()
                // 获取code截取掉parentCode后的字符
                .map(info -> info.getCode().substring(info.getParentCode().length()))
                // 获取手动id最大值
                .filter(code -> code.startsWith(type)).map(this::parseCode).max(Integer::compareTo).orElse(0);
    }

    private int parseCode(String code) {
        try {
            return Integer.parseInt(code.substring(2));
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    @Transactional
    public void delete(PaAddrInfoType paAddrInfoType) {
        if (!ObjectUtils.isEmpty(paAddrInfoType.getId())) {
            PaAddrInfoType before = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoType.getId());
            paAddrInfoTypeMapper.deleteAddrInfoById(paAddrInfoType.getId());
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, DELETE, before.getAnotherName(), before, null,
                    "删除网格[" + before.getName() + "]");
        }
    }

    @Override
    @Transactional
    public int addChild(List<PaAddrInfoType> paAddrInfoTypeList) {
        if (!CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            for (int i = 0; i < paAddrInfoTypeList.size(); i++) {
                paAddrInfoTypeList.get(i).setId(null);
                paAddrInfoTypeList.get(i).setManagerNum(paAddrInfoTypeList.get(i).getUserManagerNum());
                paAddrInfoTypeMapper.insertAddrInfo(paAddrInfoTypeList.get(i));
            }
        }
        return paAddrInfoTypeList.size();
    }

    /**
     * 拆分网格
     *
     * @param paAddrInfoTypeList
     */
    @Override
    @Transactional
    public void splitGrid(List<PaAddrInfoType> paAddrInfoTypeList) {
        // 不保留父级
        if (!CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            //被拆的网格
            PaAddrInfoType parent = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoTypeList.get(0).getId());
            //被拆分网格的父级
            PaAddrInfoType ancestor = paAddrInfoTypeMapper.selectByCode(parent.getParentCode(), parent.getTypeCode());
            if (!ObjectUtils.isEmpty(parent)) {
                // 先删除原有边界
                paAddrInfoTypeMapper.deleteAddrInfoById(paAddrInfoTypeList.get(0).getId());
                // 清空子网格
                paAddrInfoTypeMapper.cleanChildGrid(parent.getCode(), parent.getTypeCode());
                // 查询此网格详情
//                GridMessageInfo detail = gridMessageInfoMapper.getByGridCode(parent.getCode());
                // 清空id,重新生成code,保存边界
                int maxId = getAddrCodeMax(parent.getParentCode(), "cf");
                for (PaAddrInfoType e : paAddrInfoTypeList) {
                    e.setId(null);
                    e.setCode(parent.getParentCode() + "cf" + String.format("%02d", ++maxId));
                    e.setParentCode(parent.getParentCode());
                    //设置祖先路径
                    e.setAncestorCodes(ancestor.getAncestorCodes() + e.getCode() + ",");
                    e.setUpdateTime(new Date());
                    e.setCreateTime(new Date());
                    e.setManagerNum(e.getUserManagerNum());
                    paAddrInfoTypeMapper.insertAddrInfo(e);
                    //新增网格详情
                    GridMessageInfo temp = new GridMessageInfo();
//                    BeanUtils.copyProperties(detail,temp);
//                    temp.setGridCode(e.getCode());
//                    temp.setGridName(e.getName());
//                    temp.setRuralGridType(e.getRuralGridType());
//                    gridMessageInfoMapper.insertGridMessageInfo(temp);
                }
                // 删除此网格详情
//                gridMessageInfoMapper.deleteById(detail.getId());
            }
            String logContent = "拆分网格[" + parent.getName() + "->"
                    + Arrays.toString(paAddrInfoTypeList.stream().map(PaAddrInfoType::getName).toArray()) + "]";
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_SPLIT, parent.getName(), parent, paAddrInfoTypeList,
                    logContent);
        }
    }

    /**
     * 切分网格
     *
     * @param paAddrInfoTypeList
     */
    @Override
    @Transactional
    public void cutGrid(List<PaAddrInfoType> paAddrInfoTypeList) {
        // 保留父级
        if (!CollectionUtils.isEmpty(paAddrInfoTypeList)) {
            PaAddrInfoType parent = paAddrInfoTypeMapper.selectAddrInfoById(paAddrInfoTypeList.get(0).getId());
            // 清空id保存边界
            for (int i = 0; i < paAddrInfoTypeList.size(); i++) {
                PaAddrInfoType e = paAddrInfoTypeList.get(i);
                e.setId(null);
                e.setCode(e.getParentCode() + "qf" + String.format("%02d", i));
                e.setAncestorCodes(parent.getAncestorCodes() + e.getCode() + ",");
                e.setManagerNum(e.getUserManagerNum());
                paAddrInfoTypeMapper.insertAddrInfo(e);
                //同步数据
//                gridMessageInfoService.gridMessageSynchroPaAddrInfo(e.getCode());
                //新增网格详情
//                GridMessageInfo temp = new GridMessageInfo();
//                BeanUtils.copyProperties(parent,temp);
//                temp.setGridCode(e.getCode());
//                temp.setGridName(e.getName());
//                gridMessageInfoMapper.insertGridMessageInfo(temp);
            }
            String logContent = "切分网格[" + parent.getName() + "->"
                    + Arrays.toString(paAddrInfoTypeList.stream().map(PaAddrInfoType::getName).toArray()) + "]";
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_CUT, parent.getName(), parent, paAddrInfoTypeList,
                    logContent);
        }

    }

    @Override
    @Transactional
    public void mergeChildGrid(PaAddrInfoType paAddrInfoType) {
        // 获取所有应该删除的网格
        if (StringUtils.isNotEmpty(paAddrInfoType.getCode())) {
            List<String> codes = Arrays.asList(paAddrInfoType.getCode().split(","));
            List<PaAddrInfoType> configBefore = paAddrInfoTypeMapper.listByIds(codes, paAddrInfoType.getTypeCode());
            // 需要合并网格的父网格
            PaAddrInfoType parentAddr = paAddrInfoTypeMapper.selectByCode(configBefore.get(0).getParentCode(), paAddrInfoType.getTypeCode());
            // 删除需要合并的网格
            for (String code : codes) {
                paAddrInfoTypeMapper.deleteAddrInfoByCode(code, paAddrInfoType.getTypeCode());
                paAddrInfoTypeMapper.cleanChildGrid(code, paAddrInfoType.getTypeCode());
            }
            int maxId = getAddrCodeMax(paAddrInfoType.getParentCode(), "hb");
            // 新增本网格
            String newCode = paAddrInfoType.getParentCode() + "hb" + String.format("%02d", maxId + 1);
            paAddrInfoType.setCode(newCode);
            paAddrInfoType.setAncestorCodes(parentAddr.getAncestorCodes() + newCode + ",");
            paAddrInfoType.setId(null);
            paAddrInfoType.setManagerNum(paAddrInfoType.getUserManagerNum());
            paAddrInfoTypeMapper.insertAddrInfo(paAddrInfoType);

            String subject = Arrays.toString(configBefore.stream().map(PaAddrInfoType::getName).toArray());
            String logContent = "合并子网[" + subject + "->" + paAddrInfoType.getName() + "]";
            paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_MERGE, subject, configBefore, paAddrInfoType,
                    logContent);

            // 同步更新网格基础信息维护表
//            gridMessageInfoService.gridMessageSynchroPaAddrInfo(newCode);
        }
    }

    @Override
    @Transactional
    public void cleanChildGrid(PaAddrInfoType paAddrInfoType) {
        List<PaAddrInfoType> before =
                paAddrInfoTypeMapper.listAllChildGrid(paAddrInfoType.getParentCode(), paAddrInfoType.getTypeCode());
        if (CollectionUtils.isEmpty(before)) {
            return;
        }
        paAddrInfoTypeMapper.cleanChildGrid(paAddrInfoType.getParentCode(), paAddrInfoType.getTypeCode());

        PaAddrInfoType parent =
                paAddrInfoTypeMapper.selectByCode(paAddrInfoType.getParentCode(), paAddrInfoType.getTypeCode());
        paChangeLogService.insertPaChangeLog(GRID_MODIFY, GRID_CLEAN_CHILD, parent.getName(), before, null,
                "清除子网格[" + parent.getName() + "]");
    }

    @Override
    public BoundaryData getCoordinatesByCode(String areaCode, String typeCode, String jurisdiction) {
        return paAddrInfosTOBoundaryData(
                paAddrInfoTypeMapper.selectByCodeAndJurisdiction(areaCode, typeCode, jurisdiction));
    }

    @Override
    @Cacheable(value = "getBaseCoordinatesByCode", key = "#areaCode + '_' + #jurisdiction")
    public BoundaryData getBaseCoordinatesByCode(String areaCode, String jurisdiction) {
        BoundaryData boundaryData =
                paAddrInfosTOBoundaryData(paAddrInfoTypeMapper.selectBaseByCodeAndJurisdiction(areaCode, jurisdiction));
        return boundaryData;
    }

    @Override
    public Long getDefaultAddrInfoTypeId() {
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType(ADDR_INFO_TYPE);
        if (CollectionUtils.isEmpty(sysDictDataList)) {
            // 没有网格数据
            return null;
        }
        return Collections.min(sysDictDataList, Comparator.comparing(SysDictData::getCreateTime)).getDictCode();
    }

    private BoundaryData paAddrInfosTOBoundaryData(List<PaAddrInfoType> paAddrInfos) {
        if (paAddrInfos == null || paAddrInfos.size() == 0) {
            return null;
        }
        BoundaryData boundaryData = new BoundaryData();
        List<Features> features = new ArrayList<>();
        paAddrInfos.stream().forEach(a -> {
            //过滤掉宁波的边界数据
            if("330200".equals(a.getCode())){
                return;
            }
            Features feature = new Features();
            Properties properties = new Properties();
            properties.setCode(a.getCode());
            properties.setName(a.getName());
            properties.setParentCode(a.getParentCode());
            properties.setLevel(a.getAddrLevel());
            properties.setCenter(JSONObject.parse(a.getCenterPoint()));
            properties.setType(a.getType());
            properties.setIsCash(a.getIsCash());
            if (StringUtils.isNotBlank(a.getBoundaryPoints()) && StringUtils.isBlank(a.getCoordinates())) {
                a.setCoordinates(GridUtil.convertCoordinates(a.getBoundaryPoints()));
            }
            Geometry geometry = new Geometry();
            geometry.setCoordinates(JSONObject.parse(a.getCoordinates()));
            feature.setGeometry(geometry);
            feature.setProperties(properties);
            features.add(feature);
        });
        boundaryData.setFeatures(features);
        return boundaryData;
    }

    private String creatByGridCode(String parentCode, int maxId) {
        String code = parentCode + "ad" + String.format("%02d", maxId + 1);
//        GridMessageInfo byGridCode = gridMessageInfoMapper.getByGridCode(code);
//        if (byGridCode != null) {
//            return parentCode + "ad" + String.format("%02d", maxId + 2);
//        }

        return code;
    }

    @Override
    public List<AreaSplitPointVO> getAreaSplitPoints(String areaCode, Boolean isCurrent) {
        List<PaAddrInfoType> list = paAddrInfoTypeMapper.selectCoordinatesByAreaCode(areaCode, isCurrent);
        List<AreaSplitPointVO> result = new ArrayList<>();
        for (PaAddrInfoType item : list) {
            if (StringUtils.isEmpty(item.getCoordinates())) {
                continue;
            }
            try {
                // coordinates格式: [[[[lon, lat], ...]]] (GeoJSON MultiPolygon/Polygon)
                // 递归取出所有 [lon, lat] 坐标点
                List<double[]> points = extractPoints(JSONArray.parseArray(item.getCoordinates()));
                if (points.isEmpty()) {
                    continue;
                }
                double minLon = points.stream().mapToDouble(p -> p[0]).min().getAsDouble();
                double maxLon = points.stream().mapToDouble(p -> p[0]).max().getAsDouble();
                double centerLon = (minLon + maxLon) / 2.0;

                List<double[]> leftPoints = points.stream().filter(p -> p[0] <= centerLon).collect(Collectors.toList());
                List<double[]> rightPoints = points.stream().filter(p -> p[0] > centerLon).collect(Collectors.toList());

                if (!leftPoints.isEmpty()) {
                    double avgLon = leftPoints.stream().mapToDouble(p -> p[0]).average().getAsDouble();
                    double avgLat = leftPoints.stream().mapToDouble(p -> p[1]).average().getAsDouble();
                    result.add(new AreaSplitPointVO(item.getCode(), 1, avgLon, avgLat));
                }
                if (!rightPoints.isEmpty()) {
                    double avgLon = rightPoints.stream().mapToDouble(p -> p[0]).average().getAsDouble();
                    double avgLat = rightPoints.stream().mapToDouble(p -> p[1]).average().getAsDouble();
                    result.add(new AreaSplitPointVO(item.getCode(), 2, avgLon, avgLat));
                }
            } catch (Exception e) {
                log.warn("解析coordinates失败, code={}, error={}", item.getCode(), e.getMessage());
            }
        }
        return result;
    }

    /**
     * 递归从嵌套JSONArray中提取所有 [lon, lat] 坐标点
     */
    private List<double[]> extractPoints(JSONArray array) {
        List<double[]> points = new ArrayList<>();
        if (array == null || array.isEmpty()) {
            return points;
        }
        Object first = array.get(0);
        if (first instanceof Number) {
            // 叶节点：[lon, lat]
            if (array.size() >= 2) {
                points.add(new double[]{array.getDoubleValue(0), array.getDoubleValue(1)});
            }
        } else {
            for (int i = 0; i < array.size(); i++) {
                Object element = array.get(i);
                if (element instanceof JSONArray) {
                    points.addAll(extractPoints((JSONArray) element));
                }
            }
        }
        return points;
    }
}
