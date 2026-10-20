package com.kx.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.domain.param.CreditHeatMapParam;
import com.kx.system.mapper.CreditCustomerInfoMapper;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.service.CustomGroupService;
import com.kx.system.service.IAnchorPointService;
import com.kx.system.service.ICreditCustomerInfoService;
import com.kx.system.service.ISysUserService;
import com.kx.system.service.es.EsCreditCustomerInfoService;
import com.kx.system.util.UserUtil;
import com.kx.system.util.ValidationUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 授信客户信息Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Service
public class CreditCustomerInfoServiceImpl implements ICreditCustomerInfoService {
    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private CustomGroupService customGroupService;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;


    @Resource
    private ISysUserService sysUserService;
    @Resource
    private EsCreditCustomerInfoService esCreditCustomerInfoService;

    @Resource
    private IAnchorPointService anchorPointService;


    /**
     * 查询授信客户信息
     *
     * @param customNm 授信客户信息主键
     * @return 授信客户信息
     */
    @Override
    public CreditCustomerInfo selectCreditCustomerInfoByCustomNm(String customNm) {
        return creditCustomerInfoMapper.selectCreditCustomerInfoByCustomNm(customNm);
    }

    /**
     * 查询授信客户信息列表
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 授信客户信息
     */
    @Override
    @DataScope(deptAlias = "cci", userAlias = "su")
    public List<CreditCustomerInfo> selectCreditCustomerInfoList(CreditCustomerInfo creditCustomerInfo) {
        //客群的IdList
        List<Long> groupIdList = creditCustomerInfo.getGroupIdList();
        if (groupIdList != null) {
            //根据客群 IdList 取出其下面的客户 IdList
            List<Long> customerIdList = customGroupService.getCustomerIdList(groupIdList);
            creditCustomerInfo.setIds(customerIdList);
        }
        startPage(creditCustomerInfo);
        List<CreditCustomerInfo> creditCustomerInfos = creditCustomerInfoMapper.selectCreditCustomerInfoList(creditCustomerInfo);
        creditCustomerInfos.forEach(a -> {

            if (StringUtils.isNotEmpty(a.getGridManager()) && StringUtils.isNotEmpty(a.getGridManagerName())) {
                String name = ManagerStringUtil.managerNumSplitName(a.getGridManager(), a.getGridManagerName());
                a.setGridManager(name);
            }

        });
        return creditCustomerInfos;
    }

    @Override
    public TableDataInfo esCreditCustomerInfoList(CreditCustomerInfo creditCustomerInfo, LoginUser loginUser) {
        TableDataInfo rspData = new TableDataInfo();

        JSONObject esUserInfo = sysUserService.getESUserInfo(loginUser);
        creditCustomerInfo.setQueryEsRole(esUserInfo);
        String indexName = "es_credit_customer_info";
        if (creditCustomerInfo.getGroupIdList() != null && creditCustomerInfo.getGroupIdList().size()>0){
            indexName = "es_credit_customer_group_info";
        }

        JSONObject jsonObject = esCreditCustomerInfoService.selectCredCustomerInfoList(creditCustomerInfo, indexName, creditCustomerInfo.getPageSize(), creditCustomerInfo.getPageNum());
        List<CreditCustomerInfo> customerInfoList = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), CreditCustomerInfo.class);

        //获取客户锚点数据
        List<String> customerNmList = customerInfoList.stream().map(CreditCustomerInfo::getCustomerNm).collect(Collectors.toList());
        Map<String, AnchorPoint> anchorPointMap = anchorPointService.getAnchorPointByCustomerNm(customerNmList, CustomTypeNewEnum.CREDIT.getCode().toString());
        BigDecimal decimal = new BigDecimal(0);

        Integer total = jsonObject.getInteger("total");
        customerInfoList.forEach(a->{
            if (StringUtils.isNotEmpty(a.getGridManagerName()) && StringUtils.isNotEmpty(a.getGridManager())){
                String gridManagerName = ManagerStringUtil.managerNumSplitName(a.getGridManager(), a.getGridManagerName());
                //原工号字段 改为  姓名（工号） 显示
                a.setGridManager(gridManagerName);
            }
            AnchorPoint p = anchorPointMap.get(a.getCustomerNm());
            a.setLon(null!=p? p.getLon(): decimal);
            a.setLat(null!=p? p.getLat(): decimal);
        });
        rspData.setMsg("查询成功");
        rspData.setTotal(total);
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(customerInfoList);
        return rspData;
    }

    /**
     * 新增授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    @Override
    public int insertCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo) {
        return creditCustomerInfoMapper.insertCreditCustomerInfo(creditCustomerInfo);
    }

    /**
     * 修改授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    @Override
    public int updateCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo) {
        return creditCustomerInfoMapper.updateCreditCustomerInfo(creditCustomerInfo);
    }

    @Override
//    @Async("asyncServiceExecutor")
    public void insertBatch(List<CreditCustomerInfo> creditCustomerInfos, CountDownLatch countDownLatch) {
        try {
            creditCustomerInfoMapper.insertBatch(creditCustomerInfos);
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public Map<String, Integer> heatMap(CreditHeatMapParam param) {
        List<String> gridCodes = param.getGridCodes();
        if (CollectionUtils.isEmpty(gridCodes)) {
            return new HashMap<>();
        }
        //查询本级及子网格下的所有授信客户数
        if (gridCodes.size() == 1) {
            PaAddrInfoType addrInfoType = paAddrInfoTypeMapper.getByGridCode(gridCodes.get(0));
            ValidationUtil.notNull(addrInfoType, "网格不存在");
            List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.listAllChildGrid(addrInfoType.getCode(), addrInfoType.getTypeCode());
            if (!CollectionUtils.isEmpty(paAddrInfoTypes)) {
                gridCodes.addAll(paAddrInfoTypes.stream().map(PaAddrInfoType::getCode).collect(Collectors.toList()));
            }

        }
        if (gridCodes.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Integer> result = new HashMap<>();
        for (String gridCode : gridCodes) {
            param.setGridCode(gridCode);
            Integer count = ((CreditCustomerInfoServiceImpl) AopContext.currentProxy()).countGridCreditCustomerNumber(param);
            result.put(gridCode, count == null ? 0 : count);
        }
        return result;
    }




    @Resource
    private UserUtil userUtil;
    /**
     * 查询网格内授信客户信息
     *
     * @param param
     * @param param
     */
    public Integer countGridCreditCustomerNumber(CreditHeatMapParam param) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        JSONObject esUserInfo = sysUserService.getESUserInfo(loginUser);
        CreditCustomerInfo creditCustomerInfo = new CreditCustomerInfo();
        creditCustomerInfo.setBelongGridCodeArr(param.getGridCodes());
        creditCustomerInfo.setBelongGridCode(param.getGridCode());
        creditCustomerInfo.setQueryEsRole(esUserInfo);
        JSONObject jsonObject = esCreditCustomerInfoService.selectCredCustomerInfoList(creditCustomerInfo, "es_credit_customer_info", 1, 10);
        Integer total = jsonObject.getInteger("total");


//        return creditCustomerInfoMapper.countGridCreditCustomerNumber(param);
        return total;

    }
}
