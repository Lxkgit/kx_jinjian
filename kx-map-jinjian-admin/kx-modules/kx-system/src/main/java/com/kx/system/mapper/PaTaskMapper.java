//package com.kx.system.mapper;
//
//import com.kx.common.datascope.annotation.DataScope;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.TaskOverviewEntity;
//import com.kx.system.domain.task.TaskRankData;
//import com.kx.system.domain.vo.TaskBaseInfoVo;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
///**
// * 任务Mapper接口
// *
// * @author kangxu
// * @date 2024-01-23
// */
//@Mapper
//public interface PaTaskMapper
//{
//    /**
//     * 查询任务
//     *
//     * @param id 任务主键
//     * @return 任务
//     */
//    public PaTask selectPaTaskById(Long id);
//
//    /**
//     * 查询任务列表
//     *
//     * @param paTask 任务
//     * @return 任务集合
//     */
//    public List<TaskBaseInfoVo> selectPaTaskList(PaTask paTask);
//
//    /**
//     * 新增任务
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    public int insertPaTask(PaTask paTask);
//
//    /**
//     * 修改任务
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    public int updatePaTask(PaTask paTask);
//
//    /**
//     * 删除任务
//     *
//     * @param id 任务主键
//     * @return 结果
//     */
//    public int deletePaTaskById(Long id);
//
//    /**
//     * 批量删除任务
//     *
//     * @param ids 需要删除的数据主键集合
//     * @return 结果
//     */
//    public int deletePaTaskByIds(Long[] ids);
//
//    /**
//     * 任务工作足迹概览
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    public TaskOverviewEntity workTrackOverview(PaTask paTask);
//
//    /**
//     * 任务工作足迹列表
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    public List<TaskOverviewEntity> workTrackList(PaTask paTask);
//
//    Integer selectCountByTaskTypeId(@Param("idList") List<Long> idList);
//
//    void updateTaskStatus(Date now);
//
//    List<TaskRankData> getRateAndCount(PaTask paTask);
//
//    void updateTaskStatusToRunning(Date now);
//
//    List<PaTask> getPaTaskList(Date now);
//
//    int selectUsedCount(Long id);
//
//    List<Long> selectUnfinishTask(Date now);
//
//    @DataScope(deptAlias = "u",userAlias = "u")
//    public List<TaskBaseInfoVo> selectProgress(PaTask paTask);
//}
