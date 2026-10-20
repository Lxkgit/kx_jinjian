package com.kx.system.mapper;

import com.kx.system.domain.kpiManage.KpiIndicatorNetwork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 指标和机构关联Mapper接口
 * 
 * @author xifan_h
 * @date 2022-04-12
 */
@Mapper
public interface KpiIndicatorNetworkMapper 
{
    /**
     * 查询指标和机构关联
     * 
     * @param id 指标和机构关联主键
     * @return 指标和机构关联
     */
    public KpiIndicatorNetwork selectKpiIndicatorNetworkById(Long id);

    /**
     * 查询指标和机构关联列表
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 指标和机构关联集合
     */
    public List<KpiIndicatorNetwork> selectKpiIndicatorNetworkList(KpiIndicatorNetwork kpiIndicatorNetwork);

    /**
     * 新增指标和机构关联
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 结果
     */
    public int insertKpiIndicatorNetwork(KpiIndicatorNetwork kpiIndicatorNetwork);

    /**
     * 修改指标和机构关联
     * 
     * @param kpiIndicatorNetwork 指标和机构关联
     * @return 结果
     */
    public int updateKpiIndicatorNetwork(KpiIndicatorNetwork kpiIndicatorNetwork);

    /**
     * 删除指标和机构关联
     * 
     * @param id 指标和机构关联主键
     * @return 结果
     */
    public int deleteKpiIndicatorNetworkById(Long id);

    /**
     * 批量删除指标和机构关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteKpiIndicatorNetworkByIds(@Param("ids") List<Long> ids);

    int insertBatch(@Param("kpiIndicatorNetworkCollection") Collection<KpiIndicatorNetwork> kpiIndicatorNetworkCollection);

    int deleteByIndicatorId(@Param("indicatorId") Long indicatorId);

    int deleteByIndicatorIds(@Param("indicatorIds") List<Long> indicatorIds);

}
