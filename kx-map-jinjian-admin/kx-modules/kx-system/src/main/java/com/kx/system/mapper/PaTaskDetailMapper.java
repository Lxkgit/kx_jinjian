//package com.kx.system.mapper;
//
//import java.util.List;
//
//import com.kx.system.domain.vo.TaskViewVO;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import com.kx.common.datascope.annotation.DataScope;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.task.PaTaskDetail;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.domain.task.TaskRateRank;
//
//
//public interface PaTaskDetailMapper {
//    void insertBatch(@Param("paTaskDetails") List<PaTaskDetail> paTaskDetails);
//
//    List<TaskDetailVO> listTaskClient(TaskDetailVO taskDetailVO);
//
//    int deleteByClientIds(@Param("clientIds") List<Long> clientIds);
//
//    @DataScope(deptAlias = "ptd", userAlias = "u")
//    List<TaskDetailVO> listDetail(TaskDetailVO taskDetailVO);
//
//    TaskDetailVO getDetailById(Integer id);
//
//    void editTaskDetail(TaskDetailVO taskDetailVO);
//
//    int getFinishedCount(Long taskId);
//
//    int getTotalCount(Long taskId);
//
//    void updateChildTaskStatus(@Param("taskIds") List<Long> taskIds);
//
//    List<TaskRateRank> selectChildTaskOrgRank(PaTask paTask);
//
//    List<TaskRateRank> selectChildTaskManagerRank(PaTask paTask);
//
//    void deleteByGroupIdAndTaskId(TaskDetailVO taskDetailVO);
//
//    List<TaskRateRank> selectChildTaskSeparateRank(PaTask paTask);
//
//    List<TaskRateRank> selectMasterTaskOrgRank(PaTask paTask);
//
//    List<TaskRateRank> selectMasterTaskSeparateRank(PaTask paTask);
//
//    List<TaskRateRank> selectMasterTaskManagerRank(PaTask paTask);
//
//    /**
//     * 查询主任务下是否存在未完成的子任务
//     *
//     * @param ids 主任务的id列表
//     * @return true:存在 false:不存在
//     */
//    Boolean selectIsExistChildTask(Long[] ids);
//
//    /**
//     *
//     * @param id
//     * @return
//     */
//    Integer selectChildNumByPaTaskId(Long id);
//
//    void deleteByPaTaskIds(Long[] ids);
//
//    List<TaskViewVO> taskView(TaskDetailVO taskDetailVO);
//
//    String getDetailContentById(Long taskDetailId);
//
//    Integer getChildTaskCount(@Param("userId") String userId, @Param("status") Integer status);
//
//    List<TaskDetailVO> listMyTask(TaskDetailVO taskDetailVO);
//
//    int getOwnChildTaskCount(@Param("userName") String userName, @Param("id") Long id);
//
//    int getOwnFinishedCount(@Param("id") Long id,@Param("jobNo") String jobNo);
//
//    long getOwnUnfinish(@Param("id")Long id, @Param("jobNo")String jobNo);
//
//    List<TaskDetailVO> getAnchorDetail(TaskDetailVO taskDetailVO);
//}
