package com.kx.system.mapper;

import java.util.List;
import com.kx.system.domain.PaSchedule;

/**
 * 日程Mapper接口
 * 
 * @author kangxu
 * @date 2024-01-25
 */
public interface PaScheduleMapper 
{
    /**
     * 查询日程
     * 
     * @param id 日程主键
     * @return 日程
     */
    public PaSchedule selectPaScheduleById(Long id);

    /**
     * 查询日程列表
     * 
     * @param paSchedule 日程
     * @return 日程集合
     */
    public List<PaSchedule> selectPaScheduleList(PaSchedule paSchedule);

    /**
     * 新增日程
     * 
     * @param paSchedule 日程
     * @return 结果
     */
    public int insertPaSchedule(PaSchedule paSchedule);

    /**
     * 修改日程
     * 
     * @param paSchedule 日程
     * @return 结果
     */
    public int updatePaSchedule(PaSchedule paSchedule);

    /**
     * 删除日程
     * 
     * @param id 日程主键
     * @return 结果
     */
    public int deletePaScheduleById(Long id);

    /**
     * 批量删除日程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaScheduleByIds(Long[] ids);
}
