package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.system.domain.kpiManage.KpiIndicatorNetwork;
import com.kx.system.mapper.KpiIndicatorNetworkMapper;
import com.kx.system.mapper.SysNetworkMapper;
import com.kx.system.service.IKpiIndicatorNetworkService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 指标和机构关联Service业务层处理
 * 
 * @author xifan_h
 * @date 2022-04-12
 */
@Service
public class KpiIndicatorNetworkServiceImpl implements IKpiIndicatorNetworkService
{
    @Autowired
    private KpiIndicatorNetworkMapper kpiIndicatorNetworkMapper;

    @Autowired
    private SysNetworkMapper networkMapper;

    /**
     * 查询指标和机构关联
     * 
     * @param id 指标和机构关联主键
     * @return 指标和机构关联
     */
    @Override
    public KpiIndicatorNetwork selectKpiIndicatorNetworkById(Long id)
    {
        return kpiIndicatorNetworkMapper.selectKpiIndicatorNetworkById(id);
    }

    /**
     * 查询指标和机构关联列表
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 指标和机构关联
     */
    @Override
    public List<KpiIndicatorNetwork> selectKpiIndicatorNetworkList(KpiIndicatorNetwork kpiIndicatorNetwork)
    {
        return kpiIndicatorNetworkMapper.selectKpiIndicatorNetworkList(kpiIndicatorNetwork);
    }

    /**
     * 新增指标和机构关联
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 结果
     */
    @Override
    public int insertKpiIndicatorNetwork(KpiIndicatorNetwork kpiIndicatorNetwork)
    {
        kpiIndicatorNetwork.setCreateTime(DateUtils.getNowDate());
        return kpiIndicatorNetworkMapper.insertKpiIndicatorNetwork(kpiIndicatorNetwork);
    }

    /**
     * 修改指标和机构关联
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 结果
     */
    @Override
    public int updateKpiIndicatorNetwork(KpiIndicatorNetwork kpiIndicatorNetwork)
    {
        kpiIndicatorNetwork.setUpdateTime(DateUtils.getNowDate());
        return kpiIndicatorNetworkMapper.updateKpiIndicatorNetwork(kpiIndicatorNetwork);
    }

    /**
     * 批量删除指标和机构关联
     * 
     * @param ids 需要删除的指标和机构关联主键
     * @return 结果
     */
    @Override
    public int deleteKpiIndicatorNetworkByIds(List<Long> ids)
    {
        return kpiIndicatorNetworkMapper.deleteKpiIndicatorNetworkByIds(ids);
    }

    /**
     * 删除指标和机构关联信息
     * 
     * @param id 指标和机构关联主键
     * @return 结果
     */
    @Override
    public int deleteKpiIndicatorNetworkById(Long id)
    {
        return kpiIndicatorNetworkMapper.deleteKpiIndicatorNetworkById(id);
    }

    /**
     * 获取所有子机构id
     * @param networkIds
     * @return
     */
    public List<Long> listAllSonIds(List<Long> networkIds) {
        List<Long> idList = new ArrayList<>();
        List<Long> sonIds = networkMapper.listSonIds(networkIds);
        if(CollectionUtils.isEmpty(sonIds)){
            return idList;
        }
        idList.addAll(sonIds);
        idList.addAll(listAllSonIds(sonIds));
        return idList;
    }

}
