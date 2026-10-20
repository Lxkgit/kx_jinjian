package com.kx.system.service;


import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.dto.KpiIndicatorDTO;
import com.kx.system.domain.kpiManage.KpiIndicator;
import com.kx.system.domain.vo.KpiIndicatorVO;

import java.util.List;

/**
 * 考核指标Service接口
 * 
 * @author xifan_h
 * @date 2022-04-29
 */
public interface IKpiIndicatorService 
{
    /**
     * 查询考核指标
     * 
     * @param id 考核指标主键
     * @return 考核指标
     */
    public KpiIndicator selectKpiIndicatorById(Long id);

    /**
     * 查询考核指标列表
     * 
     * @param kpiIndicator 考核指标
     * @return 考核指标集合
     */
    public List<KpiIndicator> selectKpiIndicatorList(KpiIndicator kpiIndicator);

    /**
     * 新增考核指标
     * 
     * @param kpiIndicator 考核指标
     * @return 结果
     */
    public int insertKpiIndicator(KpiIndicator kpiIndicator);

    /**
     * 修改考核指标
     * 
     * @param kpiIndicator 考核指标
     * @return 结果
     */
    public int updateKpiIndicator(KpiIndicator kpiIndicator);

    /**
     * 批量删除考核指标
     * 
     * @param ids 需要删除的考核指标主键集合
     * @return 结果
     */
    public int deleteKpiIndicatorByIds(String ids);

    /**
     * 删除考核指标信息
     * 
     * @param id 考核指标主键
     * @return 结果
     */
    public int deleteKpiIndicatorById(Long id);

    AjaxResult saveOrUpdateType(KpiIndicatorDTO kpiIndicatorDTO);

    AjaxResult saveOrUpdateIndicator(KpiIndicatorDTO kpiIndicatorDTO);

    AjaxResult enableIndicator(List<Long> indicatorIds, Integer enable);

    AjaxResult deleteIndicator(List<Long> indicatorIds);

    List<KpiIndicatorVO> selectIndicator(String indicatorName, String createUserName, Boolean enable, String calType, Integer pageNum, Integer pageSize);

    AjaxResult selectNetworkByName(String networkName);

}
