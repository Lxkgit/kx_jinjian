//package com.kx.system.service;
//
//import com.kx.system.domain.TaskBaseInfo;
//import com.kx.system.domain.mobile.TaskHomeVo;
//import com.kx.system.domain.mobile.TaskMobileVo;
//import com.kx.system.domain.vo.*;
//
//import java.util.List;
//
///**
// * <p>
// * 任务基本信息表 服务类
// * </p>l
// *
// * @author YuanShiYi
// * @since 2022-04-27
// */
//public interface TaskBaseInfoService {
//
//    /**
//     * @param taskBaseInfo
//     * @return List<TaskBaseInfo>
//     * @Description: 列表查询
//     */
//    List<TaskBaseInfo> list(TaskBaseInfo taskBaseInfo);
//
//    /**
//     * @param id
//     * @return TaskBaseInfo
//     * @Description: 根据id查询
//     */
//    TaskBaseInfo getById(Long id);
//
//    /**
//     * @param taskBaseInfo
//     * @return TaskBaseInfo
//     * @Description: 新增/修改
//     */
//    TaskBaseInfo saveTaskBaseInfo(TaskBaseInfo taskBaseInfo);
//
//    /**
//     * @param ids
//     * @Description: 批量删除
//     */
//    String deleteByIds(String ids);
//
//    /**
//     * @param id
//     * @Description: 删除
//     */
//    void deleteById(Long id);
//
//    /**
//     * 根据状态查询任务集
//     *
//     * @param taskStatusInit
//     * @return
//     */
//    List<TaskBaseInfo> getTasksBystatus(Integer taskStatusInit);
//
//    void updateTaskStatusById(Integer taskStatusRunning, Long id);
//
//    /**
//     * 根据任务名称查询任务
//     *
//     * @param taskName
//     * @return
//     */
//    TaskBaseInfo selectTaskByName(String taskName);
//
//    /**
//     * 根据任务类型查询未开始或者进行中任务数量
//     *
//     * @param asList
//     * @return
//     */
//    Integer selectTaskByStatus(List<Integer> asList);
//
//    /**
//     * 获取基本信息(填充)
//     *
//     * @param taskId
//     * @return
//     */
//    TaskBaseInfo getBaseById(Long taskId);
//
//    /**
//     * 更新任务完成率
//     *
//     * @param taskId
//     */
//    void modifyTaskCompleteProcess(Long taskId);
//
//    /**
//     * 根据用户查询主任务完成率
//     *
//     * @param taskHomeVo
//     * @return
//     */
//    ProgressInfo queryMasterProgressInfo(TaskHomeVo taskHomeVo);
//
//    /**
//     * 根据用户查询子任务完成率
//     *
//     * @param taskHomeVo
//     * @return
//     */
//    ProgressInfo queryChildrenProgressInfo(TaskHomeVo taskHomeVo);
//
//    /**
//     * 根据用户和时间查询子任务规划数
//     *
//     * @param taskHomeVo
//     * @return
//     */
//    Integer queryChildrenPlan(TaskHomeVo taskHomeVo);
//
//    /**
//     * 根据用户和时间查询子任务执行数
//     *
//     * @param taskHomeVo
//     * @return
//     */
//    Integer queryChildrenRecord(TaskHomeVo taskHomeVo);
//
//    List<TaskSelectVo> getTasksNames(TaskVisitVo taskVisitVo);
//
//    List<TaskInfo> queryUnStartedTasks(TaskVisitVo taskVisitVo);
//
//    List<TaskBaseInfoVo> getTaskCustomsByManagers(TaskMobileVo taskMobileVo);
//}
