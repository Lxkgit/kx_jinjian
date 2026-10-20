package com.kx.system.mapper;

import com.kx.system.domain.kpiManage.KpiIndicator;
import com.kx.system.domain.vo.KpiIndicatorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 考核指标Mapper接口
 * 
 * @author xifan_h
 * @date 2022-04-29
 */
@Mapper
public interface KpiIndicatorMapper 
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
     * 删除考核指标
     * 
     * @param id 考核指标主键
     * @return 结果
     */
    public int deleteKpiIndicatorById(Long id);

    /**
     * 批量删除考核指标
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKpiIndicatorByIds(String[] ids);


    int updateEnableByIds(@Param("indicatorIds") List<Long> indicatorIds, @Param("enable") Integer enable);

    int updateDelFlagByIds(@Param("indicatorIds") List<Long> indicatorIds);

    /**
     * 查找指标类型
     * @param indicatorName
     * @param createUserName
     * @return
     */
    List<KpiIndicatorVO> selectTypeByNameAndUserName(@Param("indicatorName") String indicatorName,
                                                     @Param("createUserName") String createUserName,
                                                     @Param("pNetworkId") String pNetworkId,
                                                     @Param("networkIds") List<Long> networkIds,
                                                     @Param("currentUserId") Long currentUserId);

    List<KpiIndicatorVO> selectIndicatorByNameAndUserNameAndParentIds(@Param("indicatorName") String indicatorName,
                                                                      @Param("createUserName") String createUserName,
                                                                      @Param("parentIdList") List<Long> parentIdList,
                                                                      @Param("enable") Boolean enable,
                                                                      @Param("calType") String calType);

    @Select("select parent_id from kpi_indicator where id = #{id}")
    Long getParentIdById(@Param("id") Long indicatorId);

    Long selectRepeatIndicator(@Param("name") String name,
                               @Param("parentId") Long parentId,
                               @Param("exceptId") Long exceptId,
                               @Param("createUserId") Long createUserId);

    List<KpiIndicator> selectListByTaskId(Long taskId);

    int countByEnableAndIdList(@Param("enable") Boolean enable, @Param("idList") List<Long> idList);

    /**
     * 统计该指标id被使用的数量
     * @param id
     * @return
     */
    int countInUseById(@Param("id") Long id);

    int countInUseByIds(@Param("ids") List<Long> ids);
}
