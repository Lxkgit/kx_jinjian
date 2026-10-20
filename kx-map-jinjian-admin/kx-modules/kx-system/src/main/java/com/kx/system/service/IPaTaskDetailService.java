//package com.kx.system.service;
//
//import com.kx.common.core.web.domain.AjaxResult;
//import com.kx.system.domain.anchor.AnchorPoint;
//import com.kx.system.domain.task.PaTaskDetail;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.domain.task.TaskTrack;
//import com.kx.system.domain.vo.TaskViewVO;
//
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//public interface IPaTaskDetailService {
//    List<TaskDetailVO> listTaskClient(TaskDetailVO taskDetailVO);
//
//    int deleteByClientIds(List<Long> clientIds);
//
//    List<TaskDetailVO> listDetail(TaskDetailVO taskDetailVO);
//
//    TaskDetailVO getDetailById(Integer id);
//
//    TaskDetailVO editTaskDetail(TaskDetailVO taskDetailVO);
//
//    List<TaskDetailVO> export(TaskDetailVO taskDetailVO);
//
//    List<TaskViewVO> taskView(TaskDetailVO taskDetailVO);
//
//    void deleteByTaskIds(Long[] ids);
//
//    TaskTrack taskTrack();
//
//    void generateTask(List<PaTaskDetail> paTaskDetails, Long id, String feedback, CountDownLatch countDownLatch);
//
//    List<TaskDetailVO> listDetailInView(TaskDetailVO taskDetailVO);
//
//    List<TaskDetailVO> getAnchorDetail(TaskDetailVO taskDetailVO);
//}
