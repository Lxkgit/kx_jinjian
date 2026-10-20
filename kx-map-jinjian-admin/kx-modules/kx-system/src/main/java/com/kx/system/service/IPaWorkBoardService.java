package com.kx.system.service;

import com.kx.system.domain.*;

import java.util.List;

/**
 * 工作看板Service接口
 *
 * @author kangxu
 * @date 2024-01-29
 */
public interface IPaWorkBoardService
{
    /**
     * 业务导航列表
     * @return
     */
    public List<SysMenu> businessNavigation();

    /**
     * 数据概览
     * @return
     */
    public DataOverviewEntity dataOverview();

    /**
     * 本周日程
     * @return
     */
    public List<PaSchedule> weekSchedule(PaSchedule paSchedule);

    /**
     * 待办事项
     * @return
     */
    public List<PaBacklog> toDoList();

    /**
     * 工作足迹概览
     * @return
     */
    public TaskOverviewEntity workTrackOverview();

    /**
     * 工作足迹列表
     * @return
     */
    public List<TaskOverviewEntity> workTrackList(PaTask paTask);

}
