package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import com.kx.common.core.text.Convert;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.dto.KpiIndicatorDTO;
import com.kx.system.domain.enums.kpiManager.CalTypeEnum;
import com.kx.system.domain.enums.kpiManager.KpiBusinessTypeEnum;
import com.kx.system.domain.kpiManage.KpiIndicator;
import com.kx.system.domain.kpiManage.KpiIndicatorNetwork;
import com.kx.system.domain.model.SysNetwork;
import com.kx.system.domain.vo.KpiIndicatorVO;
import com.kx.system.mapper.KpiIndicatorMapper;
import com.kx.system.mapper.KpiIndicatorNetworkMapper;
import com.kx.system.mapper.SysNetworkMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.IKpiIndicatorService;
import com.kx.system.util.CaculateUtil;
import com.kx.system.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.jexl3.JexlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 考核指标Service业务层处理
 *
 * @author xifan_h
 * @date 2022-04-29
 */
@Service
public class KpiIndicatorServiceImpl implements IKpiIndicatorService
{
    @Autowired
    private KpiIndicatorMapper kpiIndicatorMapper;

    @Autowired
    private KpiIndicatorNetworkMapper kpiIndicatorNetworkMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysNetworkMapper sysNetworkMapper;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private Gson gson;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private KpiIndicatorNetworkServiceImpl indicatorNetworkService;

    /**
     * 正则匹配变量 @{字段id}
     */
    private final static Pattern VAR_REG = Pattern.compile("\\@\\{[0-9]+\\}");

    /**
     * 查询考核指标
     *
     * @param id 考核指标主键
     * @return 考核指标
     */
    @Override
    public KpiIndicator selectKpiIndicatorById(Long id)
    {
        return kpiIndicatorMapper.selectKpiIndicatorById(id);
    }

    /**
     * 查询考核指标列表
     *
     * @param kpiIndicator 考核指标
     * @return 考核指标
     */
    @Override
    public List<KpiIndicator> selectKpiIndicatorList(KpiIndicator kpiIndicator)
    {
        return kpiIndicatorMapper.selectKpiIndicatorList(kpiIndicator);
    }

    /**
     * 新增考核指标
     *
     * @param kpiIndicator 考核指标
     * @return 结果
     */
    @Override
    public int insertKpiIndicator(KpiIndicator kpiIndicator)
    {
        kpiIndicator.setCreateTime(DateUtils.getNowDate());
        return kpiIndicatorMapper.insertKpiIndicator(kpiIndicator);
    }

    /**
     * 修改考核指标
     *
     * @param kpiIndicator 考核指标
     * @return 结果
     */
    @Override
    public int updateKpiIndicator(KpiIndicator kpiIndicator)
    {
        kpiIndicator.setUpdateTime(DateUtils.getNowDate());
        return kpiIndicatorMapper.updateKpiIndicator(kpiIndicator);
    }

    /**
     * 批量删除考核指标
     *
     * @param ids 需要删除的考核指标主键
     * @return 结果
     */
    @Override
    public int deleteKpiIndicatorByIds(String ids)
    {
        return kpiIndicatorMapper.deleteKpiIndicatorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除考核指标信息
     *
     * @param id 考核指标主键
     * @return 结果
     */
    @Override
    public int deleteKpiIndicatorById(Long id)
    {
        return kpiIndicatorMapper.deleteKpiIndicatorById(id);
    }

    /**
     * 新增或更新指标类型
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional("groupTransactionManager")
    public AjaxResult saveOrUpdateType(KpiIndicatorDTO dto) {
        if (CollectionUtils.isEmpty(dto.getNetworkIdArray())) {
            return AjaxResult.error("请选择适用部门");
        }
        Long userId = userUtil.getLoginUser().getUserId();
        dto.setCreateUserId(userId);
        if (userMapper.selectUserById(dto.getCreateUserId()) == null) {
            return AjaxResult.error("创建人不存在");
        }
        KpiBusinessTypeEnum businessType = KpiBusinessTypeEnum.getByName(dto.getType());
        if(businessType == null){
            return AjaxResult.error("未知的业务类型");
        }
        dto.setType(businessType.getCode());
        KpiIndicator indicator = new KpiIndicator();
        BeanUtil.copyProperties(dto, indicator);
        //查询指标类型是否重复
        Long count = selectRepeatIndicatorTypeByUserId(indicator.getName(), dto.getId(), userId);
        if(count.longValue() > 0L){
            return AjaxResult.error("您创建的指标类型中该名称已存在！");
        }

        if (Objects.isNull(dto.getId())) {
            //新增逻辑
            insertKpiIndicator(indicator);
        } else {
            //更新逻辑
            updateKpiIndicator(indicator);
            //删除指标和机构关联关系
            kpiIndicatorNetworkMapper.deleteByIndicatorId(indicator.getId());
        }
        //获取选中的机构id
        List<Long> tempIds = new ArrayList<>();
        dto.getNetworkIdArray().forEach(arr -> tempIds.add(arr.get(arr.size() - 1)));
        List<Long> networkIds = tempIds.stream().distinct().collect(Collectors.toList());
        //新增指标和机构关联关系
        List<KpiIndicatorNetwork> inList = networkIds.stream().map(nid -> {
            KpiIndicatorNetwork in = new KpiIndicatorNetwork();
            in.setNetworkId(nid);
            in.setIndicatorId(indicator.getId());
            return in;
        }).collect(Collectors.toList());
        kpiIndicatorNetworkMapper.insertBatch(inList);
        return AjaxResult.success();
    }

    /**
     * 查询重复指标类型数量
     * @param name
     * @param excpetId
     * @return
     */
    private Long selectRepeatIndicatorTypeByUserId(String name, Long excpetId, Long createUserId) {
        //类型无parentId
        return kpiIndicatorMapper.selectRepeatIndicator(name, null, excpetId, createUserId);
    }

    /**
     * 新增或更新指标
     *
     * @param dto
     * @return
     */
    @Override
    public AjaxResult saveOrUpdateIndicator(KpiIndicatorDTO dto) {
        try {
            //校验枚举是否存在
            CalTypeEnum.valueOf(dto.getCalType());
        } catch (IllegalArgumentException e) {
            return AjaxResult.error("计算类型不存在");
        }
        if(StringUtils.isNotBlank(dto.getCalExpression())){
            if (!validateExp(dto.getCalExpression())) {
                return AjaxResult.error("计算表达式有误, 请检查表达式");
            }
        }
        if(dto.getCreateUserId() == null){
            SysUser loginUser = userUtil.getLoginUser();
            dto.setCreateUserId(loginUser.getUserId());
        }
        if (userMapper.selectUserById(dto.getCreateUserId()) == null) {
            return AjaxResult.error("创建人不存在");
        }
        //查询指标名称是否重复
        Long count = kpiIndicatorMapper.selectRepeatIndicator(dto.getName(), dto.getParentId(), dto.getId(), null);
        if(count.longValue() > 0L){
            return AjaxResult.error("同指标类型下存在相同名称的指标, 无法创建!");
        }
        KpiIndicator indicator = new KpiIndicator();
        BeanUtil.copyProperties(dto, indicator);
        if (Objects.isNull(dto.getId())) {
            //新增逻辑
            insertKpiIndicator(indicator);
        } else {
            if(validateInUse(dto.getId())){
                return AjaxResult.error("当前指标被考核任务使用中, 无法修改!");
            }
            //更新逻辑
            updateKpiIndicator(indicator);
        }

        return AjaxResult.success();
    }

    private Boolean validateInUse(Long id) {
        return kpiIndicatorMapper.countInUseById(id) != 0;
    }

    private Boolean validateInUse(List<Long> ids) {
        return kpiIndicatorMapper.countInUseByIds(ids) != 0;
    }

    /**
     * 开启/关闭指标
     *
     * @param indicatorIds
     * @param enable
     * @return
     */
    @Override
    public AjaxResult enableIndicator(List<Long> indicatorIds, Integer enable) {
        kpiIndicatorMapper.updateEnableByIds(indicatorIds, enable);
        return AjaxResult.success();
    }

    /**
     * 删除指标 (非物理删除)
     *
     * @param indicatorIds
     * @return
     */
    @Override
    @Transactional("groupTransactionManager")
    public AjaxResult deleteIndicator(List<Long> indicatorIds) {
        if(validateInUse(indicatorIds)){
            return AjaxResult.error("删除的指标中包含正在使用的指标, 无法删除!");
        }
        //非物理删除 设置del_flag字段为1
        kpiIndicatorMapper.updateDelFlagByIds(indicatorIds);
        //删除关联关系
        kpiIndicatorNetworkMapper.deleteByIndicatorIds(indicatorIds);
        return AjaxResult.success();
    }

    @Override
    public List<KpiIndicatorVO> selectIndicator(String indicatorName, String createUserName, Boolean enable,
                                                String calType, Integer pageNum, Integer pageSize) {
        Long pNetworkId = 180L;
        SysUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest()).getSysUser();
        Long networkId = loginUser.getDeptId();
        SysNetwork network = sysNetworkMapper.selectNetworkById(networkId);
        if(!Arrays.asList(network.getAncestors().split(",")).contains(pNetworkId.toString())) {
            pNetworkId = networkId;
        }
        List<Long> networkIds;
        if(loginUser.isAdmin()){
            networkIds = null;
        }else{
            //获取子机构的所有id
            networkIds = indicatorNetworkService.listAllSonIds(Collections.singletonList(networkId));
            networkIds.add(networkId);
        }

        //查询父指标
        if(pageNum != null && pageSize != null){
            PageHelper.startPage(pageNum, pageSize);
        }
        List<KpiIndicatorVO> voList =
                kpiIndicatorMapper.selectTypeByNameAndUserName(indicatorName, createUserName, pNetworkId.toString(), networkIds, loginUser.getUserId());
        if(!CollectionUtils.isEmpty(voList)){
            List<Long> parentIdList = voList.stream().map(vo -> vo.getId()).collect(Collectors.toList());
            //依据父指标查询子指标
            List<KpiIndicatorVO> indicatorList =
                    kpiIndicatorMapper.selectIndicatorByNameAndUserNameAndParentIds(indicatorName, createUserName, parentIdList, enable, calType);
            //分组
            Map<Long, List<KpiIndicatorVO>> groupByParentId = indicatorList.stream().collect(Collectors.groupingBy(KpiIndicatorVO::getParentId));
            Type arrayType = new TypeToken<List<List<Long>>>() {
            }.getType();
            for (KpiIndicatorVO vo : voList) {
                if(vo.getNetworkIdArrayStr() != null){
                    vo.setNetworkIdArray(gson.fromJson(vo.getNetworkIdArrayStr(), arrayType));
                }
                vo.setSonIndicatorList(groupByParentId.get(vo.getId()));
                if(!StringUtils.isEmpty(vo.getSonIndicatorList())){
                    vo.getSonIndicatorList().forEach(v -> v.setCalExpressionFront(generateExpFront(v.getCalExpressionFrontStr())));
                }
                if(StringUtils.isNotBlank(vo.getType())){
                    vo.setType(KpiBusinessTypeEnum.valueOf(vo.getType()).getName());
                }
            }
        }
        if(enable != null && enable){
            //如果只查开启的 空分组不展示
            Iterator<KpiIndicatorVO> voIterator = voList.iterator();
            while (voIterator.hasNext()){
                if(CollectionUtils.isEmpty(voIterator.next().getSonIndicatorList())){
                    voIterator.remove();
                }
            }
        }
        return voList;
    }

    /**
     * 生成前端表达式结构
     * @param expFrontStr
     * @return
     */
    private List<Map> generateExpFront(String expFrontStr) {
        if(StringUtils.isBlank(expFrontStr)){
            return null;
        }
        Type type = new TypeToken<List<Map>>() {
        }.getType();
        return gson.fromJson(expFrontStr, type);
    }

    /**
     * 只查询总行下的组织架构
     * @param networkName
     * @return
     */
    @Override
    public AjaxResult selectNetworkByName(String networkName) {
        Long headNetworkId = 180L;
        Long networkId = tokenService.getLoginUser(ServletUtils.getRequest()).getSysUser().getDeptId();
        SysNetwork network = sysNetworkMapper.selectNetworkById(networkId);
        //总行人员可以查看总行架构
        if(Arrays.asList(network.getAncestors().split(",")).contains(headNetworkId.toString())){
            network = sysNetworkMapper.selectNetworkById(headNetworkId);
        }
        List<SysNetwork> networkList = new ArrayList<>();
        networkList.add(network);
        //子级
        networkList.addAll(sysNetworkMapper.selectChildrenNetworkById(network.getNetworkId()));
        List<SysNetwork> networkTrees = buildNetworkTree(networkList);
        return AjaxResult.success(networkTrees);
    }


    /**
     * 表达式校验
     *
     * @param calExpression
     * @return
     */
    private static Boolean validateExp(String calExpression) {
        //将表达式占位符全部替换为1
        Matcher matcher = VAR_REG.matcher(calExpression);
        while (matcher.find()) {
            String group = matcher.group();
            calExpression = calExpression.replace(group, "1");
        }
        //通过计算校验
        try {
            CaculateUtil.caculateStrExp(calExpression);
        } catch (JexlException e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param networks 网点列表
     * @return 树结构列表
     */
    public List<SysNetwork> buildNetworkTree(List<SysNetwork> networks) {
        List<SysNetwork> returnList = new ArrayList<SysNetwork>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysNetwork network : networks) {
            tempList.add(network.getNetworkId());
        }
        for (Iterator<SysNetwork> iterator = networks.iterator(); iterator.hasNext(); ) {
            SysNetwork network = (SysNetwork) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(network.getParentId())) {
                recursionFn(networks, network);
                returnList.add(network);
            }
        }
        if (returnList.isEmpty()) {
            returnList = networks;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysNetwork> list, SysNetwork t) {
        // 得到子节点列表
        List<SysNetwork> childList = getChildList(list, t);
        if(CollectionUtils.isNotEmpty(childList)){
            t.setChildren(childList);
        }
        for (SysNetwork tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysNetwork> it = childList.iterator();
                while (it.hasNext()) {
                    SysNetwork n = (SysNetwork) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysNetwork> getChildList(List<SysNetwork> list, SysNetwork t) {
        List<SysNetwork> tlist = new ArrayList<SysNetwork>();
        Iterator<SysNetwork> it = list.iterator();
        while (it.hasNext()) {
            SysNetwork n = (SysNetwork) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getNetworkId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysNetwork> list, SysNetwork t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
