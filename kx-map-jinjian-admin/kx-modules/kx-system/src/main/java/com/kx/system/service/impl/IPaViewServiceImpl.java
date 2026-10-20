package com.kx.system.service.impl;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.utils.DataScopeUtil;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.enums.OverviewGroupType;
import com.kx.system.domain.enums.OverviewType;
import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewDepositModule;
import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.domain.small.PaViewLoan;
import com.kx.system.domain.small.PaViewProsperousLoan;
import com.kx.system.mapper.*;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.service.IPaViewService;
import com.kx.system.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

import static com.kx.system.domain.enums.OverviewGroupType.*;
import static com.kx.system.domain.enums.OverviewType.*;

@Service
public class IPaViewServiceImpl implements IPaViewService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private IPaAddrInfoTypeService paAddrInfoTypeService;
    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Resource
    private PaViewDepositModuleManagerGridMapper paViewDepositModuleManagerGridMapper;
    @Resource
    private PaViewDepositModuleManagerMapper paViewDepositModuleManagerMapper;
    @Resource
    private PaViewDepositModuleOrgGridMapper paViewDepositModuleOrgGridMapper;
    @Resource
    private PaViewDepositModuleOrgMapper paViewDepositModuleOrgMapper;

    @Resource
    private PaViewGridOverviewMapper paViewGridOverviewMapper;
    @Resource
    private PaViewGridOverviewManagerGridMapper paViewGridOverviewManagerGridMapper;
    @Resource
    private PaViewGridOverviewManagerMapper paViewGridOverviewManagerMapper;
    @Resource
    private PaViewGridOverviewOrgGridMapper paViewGridOverviewOrgGridMapper;
    @Resource
    private PaViewGridOverviewOrgMapper paViewGridOverviewOrgMapper;

    @Resource
    private PaViewLoanManagerMapper paViewLoanManagerMapper;
    @Resource
    private PaViewLoanManagerGridMapper paViewLoanManagerGridMapper;
    @Resource
    private PaViewLoanOrgGridMapper paViewLoanOrgGridMapper;
    @Resource
    private PaViewLoanOrgMapper paViewLoanOrgMapper;

    @Resource
    private PaViewProsperousLoanManagerMapper paViewProsperousLoanManagerMapper;
    @Resource
    private PaViewProsperousLoanManagerGridMapper paViewProsperousLoanManagerGridMapper;
    @Resource
    private PaViewProsperousLoanOrgMapper paViewProsperousLoanOrgMapper;
    @Resource
    private PaViewProsperousLoanOrgGridMapper paViewProsperousLoanOrgGridMapper;

    private final Map<OverviewType, Map<OverviewGroupType, OverviewQueryMapperHolder>> mapperHolder = new HashMap<>();

    @PostConstruct
    public void init() throws NoSuchMethodException {
        mapperHolder.put(DEPOSIT_MODULE, new HashMap<>());
        mapperHolder.put(GRID_OVERVIEW, new HashMap<>());
        mapperHolder.put(LOAN, new HashMap<>());
        mapperHolder.put(PROSPEROUS_LOAN, new HashMap<>());

        mapperHolder.get(DEPOSIT_MODULE).put(MANAGER, new OverviewQueryMapperHolder(paViewDepositModuleManagerMapper, PaViewDepositModule.class));
        mapperHolder.get(DEPOSIT_MODULE).put(MANAGER_GRID, new OverviewQueryMapperHolder(paViewDepositModuleManagerGridMapper, PaViewDepositModule.class));
        mapperHolder.get(DEPOSIT_MODULE).put(ORG, new OverviewQueryMapperHolder(paViewDepositModuleOrgMapper, PaViewDepositModule.class));
        mapperHolder.get(DEPOSIT_MODULE).put(ORG_GRID, new OverviewQueryMapperHolder(paViewDepositModuleOrgGridMapper, PaViewDepositModule.class));

        mapperHolder.get(GRID_OVERVIEW).put(MANAGER, new OverviewQueryMapperHolder(paViewGridOverviewManagerMapper, PaViewGridOverview.class));
        mapperHolder.get(GRID_OVERVIEW).put(MANAGER_GRID, new OverviewQueryMapperHolder(paViewGridOverviewManagerGridMapper, PaViewGridOverview.class));
        mapperHolder.get(GRID_OVERVIEW).put(ORG, new OverviewQueryMapperHolder(paViewGridOverviewOrgMapper, PaViewGridOverview.class));
        mapperHolder.get(GRID_OVERVIEW).put(ORG_GRID, new OverviewQueryMapperHolder(paViewGridOverviewOrgGridMapper, PaViewGridOverview.class));

        mapperHolder.get(LOAN).put(MANAGER, new OverviewQueryMapperHolder(paViewLoanManagerMapper, PaViewLoan.class));
        mapperHolder.get(LOAN).put(MANAGER_GRID, new OverviewQueryMapperHolder(paViewLoanManagerGridMapper, PaViewLoan.class));
        mapperHolder.get(LOAN).put(ORG, new OverviewQueryMapperHolder(paViewLoanOrgMapper, PaViewLoan.class));
        mapperHolder.get(LOAN).put(ORG_GRID, new OverviewQueryMapperHolder(paViewLoanOrgGridMapper, PaViewLoan.class));

        mapperHolder.get(PROSPEROUS_LOAN).put(MANAGER, new OverviewQueryMapperHolder(paViewProsperousLoanManagerMapper, PaViewProsperousLoan.class));
        mapperHolder.get(PROSPEROUS_LOAN).put(MANAGER_GRID, new OverviewQueryMapperHolder(paViewProsperousLoanManagerGridMapper, PaViewProsperousLoan.class));
        mapperHolder.get(PROSPEROUS_LOAN).put(ORG, new OverviewQueryMapperHolder(paViewProsperousLoanOrgMapper, PaViewProsperousLoan.class));
        mapperHolder.get(PROSPEROUS_LOAN).put(ORG_GRID, new OverviewQueryMapperHolder(paViewProsperousLoanOrgGridMapper, PaViewProsperousLoan.class));
    }

    @Override
    public Object query(PaViewSmallParam paViewSmallParam, OverviewType type) {
        if("4".equalsIgnoreCase(paViewSmallParam.getCustomerType())){
            paViewSmallParam.setCustomerType("3");
        }
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (StringUtils.isEmpty(paViewSmallParam.getCustomerType())) {
            try {
                return mapperHolder.get(type).get(ORG).resultType.newInstance();
            } catch (Exception ignore) {
            }
            return null;
        }

        boolean managerCodeEmpty = CollectionUtils.isEmpty(paViewSmallParam.getManagerCodes());
        boolean orgCodesEmpty = StringUtils.isEmpty(paViewSmallParam.getOrgCodes());
        boolean gridCodeEmpty = StringUtils.isEmpty(paViewSmallParam.getGridCode());
        if (managerCodeEmpty && orgCodesEmpty) {
            Long highestLevelDeptId = DataScopeUtil.getHighestLevelDeptId(sysUser);
            //如果是客户经理,只察看本人数据
            if (highestLevelDeptId == null) {
                paViewSmallParam.setManagerCodes(Arrays.asList(sysUser.getManagerNum()));
            } else {
                SysDept sysDept = sysDeptMapper.selectDeptById(highestLevelDeptId);
                paViewSmallParam.setOrgCodes(sysDept.getOrgNum());
                orgCodesEmpty = false;
            }
        }

        if (!orgCodesEmpty) {
            if (!gridCodeEmpty) {
                // 网格+机构,数金只统计1-5级网格+5级机构数据
                // 如需要查看1-4级机构数据，需要自己汇总
                return mapperHolder.get(type).get(ORG_GRID).query(paViewSmallParam);
            } else {
                // 机构
                return mapperHolder.get(type).get(ORG).query(paViewSmallParam);
            }
        } else {
            if (!gridCodeEmpty) {
                // 网格+客户经理
                return mapperHolder.get(type).get(MANAGER_GRID).query(paViewSmallParam);
            } else {
                // 客户经理
                return mapperHolder.get(type).get(MANAGER).query(paViewSmallParam);
            }
        }
    }

    @Override
    public PaViewGridOverview queryOverview(PaViewSmallParam paViewSmallParam) {
        if("4".equalsIgnoreCase(paViewSmallParam.getCustomerType())){
            paViewSmallParam.setCustomerType("3");
        }
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();

        boolean managerCodeEmpty = CollectionUtils.isEmpty(paViewSmallParam.getManagerCodes());
        boolean orgCodesEmpty = StringUtils.isEmpty(paViewSmallParam.getOrgCodes());
        boolean gridCodeEmpty = StringUtils.isEmpty(paViewSmallParam.getGridCode());
        Long highestLevelDeptId = DataScopeUtil.getHighestLevelDeptId(sysUser);
        if (managerCodeEmpty && orgCodesEmpty) {
            //如果是客户经理,只察看本人数据
            if (highestLevelDeptId == null) {
                paViewSmallParam.setManagerCodes(Arrays.asList(sysUser.getManagerNum()));
            } else {
                SysDept sysDept = sysDeptMapper.selectDeptById(highestLevelDeptId);
                paViewSmallParam.setOrgCodes(sysDept.getOrgNum());
                orgCodesEmpty = false;
            }
        }
        /**
         *  网格概况查询逻辑：
         *  概况结果分两部分：网格基本信息+客户统计信息
         *  机构维度：查看机构管辖网格范围内所有网格基本信息数据总和，客户信息按照机构维度统计
         *  机构+网格维度：查看指定网格基本信息，客户信息按照机构+网格维度统计
         *  客户经理维度：逻辑同机构维度
         *  客户经理+网格维度：逻辑同机构+网格维度
         */
        if (gridCodeEmpty) {
            Set<String> gridList;
            if (!orgCodesEmpty) {
                //查询机构管辖的网格
                gridList = paAddrInfoTypeService.getPaAddrInfoTypeListByDeptCode(paViewSmallParam.getOrgCodes());
                paViewSmallParam.setGridCodes(new ArrayList<>(gridList));
            } else if (!managerCodeEmpty) {
                //查询客户经理管辖的网格
                gridList = paAddrInfoTypeService.getPaAddrInfoTypeListByManagerCode(paViewSmallParam.getManagerCodes().get(0));
                paViewSmallParam.setGridCodes(new ArrayList<>(gridList));
            } else {
                gridList = paAddrInfoTypeService.getPaAddrInfoTypeListByUser(sysUser);
            }
            if (CollectionUtils.isEmpty(gridList)) {
                return new PaViewGridOverview();
            }
            paViewSmallParam.setGridCodes(new ArrayList<>(gridList));
        } else {
            paViewSmallParam.setGridCodes(Arrays.asList(paViewSmallParam.getGridCode()));
        }
        //先查询网格概况
        PaViewGridOverview overview;
        if (paViewSmallParam.getGridCodes().size() == 1) {
            overview = paViewGridOverviewMapper.selectByCode(paViewSmallParam.getGridCodes().get(0));
        } else {
            overview = paViewGridOverviewMapper.groupByCodes(paViewSmallParam.getGridCodes());
        }
        PaViewGridOverview result;
        if (!orgCodesEmpty) {
            if (!gridCodeEmpty) {
                result = paViewGridOverviewOrgGridMapper.getByQuery(paViewSmallParam);
            } else {
                // 机构
                result = paViewGridOverviewOrgMapper.getByQuery(paViewSmallParam);
            }
        } else {
            if (!gridCodeEmpty) {
                // 网格+客户经理
                result = paViewGridOverviewManagerGridMapper.getByQuery(paViewSmallParam);
            } else {
                // 客户经理
                result = paViewGridOverviewManagerMapper.getByQuery(paViewSmallParam);
            }
        }
        return merge(overview, result);
    }

    private PaViewGridOverview merge(PaViewGridOverview source, PaViewGridOverview target) {
        if (source == null) {
            return target;
        }
        if (target == null) {
            return source;
        }
        BeanUtils.copyBeanIgnoreNull(source, target);
        return target;
    }

    static class OverviewQueryMapperHolder {
        public Object mapper;
        public Method queryMethod;
        public Class resultType;

        public OverviewQueryMapperHolder(Object mapper, Class resultType) throws NoSuchMethodException {
            this.mapper = mapper;
            this.queryMethod = mapper.getClass().getMethod("getByQuery", PaViewSmallParam.class);
            this.resultType = resultType;
        }

        public Object query(PaViewSmallParam paViewSmallParam) throws RuntimeException {
            try {
                Object invoke = queryMethod.invoke(mapper, paViewSmallParam);
                return invoke == null ? resultType.newInstance() : invoke;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
