//package com.kx.system.service;
//
//import java.util.List;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.task.TaskMethodVo;
//import com.kx.system.domain.task.TaskRankData;
//import com.kx.system.domain.task.TaskRateStatistics;
//import com.kx.system.domain.task.TaskTrack;
//import com.kx.system.domain.vo.TaskBaseInfoVo;
//
///**
// * 任务Service接口
// *
// * @author kangxu
// * @date 2024-01-23
// */
//public interface IPaTaskService
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
//     * 批量删除任务
//     *
//     * @param ids 需要删除的任务主键集合
//     * @return 结果
//     */
//    public int deletePaTaskByIds(Long[] ids);
//
//    /**
//     * 删除任务信息
//     *
//     * @param id 任务主键
//     * @return 结果
//     */
//    public int deletePaTaskById(Long id);
//
//    /**
//     * 主任务下拉
//     * @return
//     */
//    List listTaskStatus();
//
//    /**
//     * 子任务下拉
//     * @return
//     */
//    List listChildTaskStatus();
//
//    List<TaskMethodVo> listTaskMethod();
//
//    void taskUrge(Long[] ids);
//
//    TaskRateStatistics listTaskRank(PaTask paTask);
//
//    List<TaskBaseInfoVo> listPaTask(PaTask paTask);
//
//    TaskRateStatistics listTaskProgress(PaTask paTask);
//
//    TaskTrack getOwnCount(Long id);
//
//    List<TaskBaseInfoVo> listMobile(PaTask paTask);
//}
