//package com.kx.system.service.impl;
//
//import com.github.pagehelper.PageHelper;
//import com.kx.common.core.utils.StringUtils;
//import com.kx.common.datascope.annotation.DataScope;
//import com.kx.system.config.enums.TaskChildStatusEnum;
//import com.kx.system.config.enums.TaskLevelEnum;
//import com.kx.system.config.enums.TaskStatusChildEnum;
//import com.kx.system.domain.anchor.AnchorPoint;
//import com.kx.system.domain.task.PaTaskDetail;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.domain.task.TaskTrack;
//import com.kx.system.domain.vo.TaskViewVO;
//import com.kx.system.mapper.AnchorPointMapper;
//import com.kx.system.mapper.PaTaskDetailMapper;
//import com.kx.system.mapper.PaTaskMapper;
//import com.kx.system.mapper.TaskDetailContentMapper;
//import com.kx.system.service.IAnchorPointService;
//import com.kx.system.service.IPaTaskDetailService;
//import com.kx.system.util.UserUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.ObjectUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CountDownLatch;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class PaTaskDetailServiceImpl implements IPaTaskDetailService {
//    @Autowired
//    private PaTaskDetailMapper paTaskDetailMapper;
//
//    @Autowired
//    private TaskDetailContentMapper taskDetailContentMapper;
//
//    @Autowired
//    private PaTaskMapper paTaskMapper;
//
//    @Autowired
//    private AnchorPointMapper anchorPointMapper;
//
//    @Autowired
//    private UserUtil userUtil;
//    @Autowired
//    private IAnchorPointService anchorPointService;
//    @Override
//    public List<TaskDetailVO> listTaskClient(TaskDetailVO taskDetailVO) {
//        return paTaskDetailMapper.listTaskClient(taskDetailVO);
//    }
//
//    @Override
//    public int deleteByClientIds(List<Long> clientIds) {
//        return paTaskDetailMapper.deleteByClientIds(clientIds);
//    }
//
//
//    @Override
//    public List<TaskDetailVO> listDetail(TaskDetailVO taskDetailVO) {
//        List<TaskDetailVO> list = new ArrayList<>();
//        //主任务id为空时，查询我的任务
//        if(taskDetailVO.getTaskId() == null&&ObjectUtils.isNotEmpty(userUtil.getLoginUser())){
//            String managerNum = userUtil.getLoginUser().getManagerNum();
//            taskDetailVO.setManagerJobNumber(managerNum);
//            list = paTaskDetailMapper.listMyTask(taskDetailVO);
//        }else {
//            list = paTaskDetailMapper.listDetail(taskDetailVO);
//        }
//
//        //获取任务对象锚点的经纬度
//        List<String> customerNmList = list.stream().map(TaskDetailVO::getCustomerNm).collect(Collectors.toList());
//        String taskObjectId = list.size()>0 ? list.get(0).getTaskObjectId().toString():null;
//        Map<String, AnchorPoint> anchorPointMap = anchorPointService.getAnchorPointByCustomerNm(customerNmList, taskObjectId);
//        BigDecimal defaultVal = new BigDecimal(0);
//        for (TaskDetailVO detailVO : list) {
//            detailVO.setTaskLevelName(TaskLevelEnum.getNameByCode(detailVO.getTaskLevel()));
//            AnchorPoint p = anchorPointMap.get(detailVO.getCustomerNm());
//            detailVO.setLon(null!=p ?p.getLon():defaultVal);
//            detailVO.setLat(null!=p ?p.getLat():defaultVal);
//        }
//        return list;
//    }
//
//    @Override
//    public TaskDetailVO getDetailById(Integer id) {
//        return paTaskDetailMapper.getDetailById(id);
//    }
//
//    @Transactional
//    @Override
//    public TaskDetailVO editTaskDetail(TaskDetailVO taskDetailVO) {
//        return taskDetailVO;
//    }
//
//    @Override
//    public List<TaskDetailVO> export(TaskDetailVO taskDetailVO) {
//        return paTaskDetailMapper.listDetail(taskDetailVO);
//    }
//
//    @Override
//    @DataScope(deptAlias = "su", userAlias = "su")
//    public List<TaskViewVO> taskView(TaskDetailVO taskDetailVO) {
//        List<TaskViewVO> clients = paTaskDetailMapper.taskView(taskDetailVO);
//        return clients;
//    }
//
//    @Async
//    @Override
//    public void deleteByTaskIds(Long[] ids) {
//        paTaskDetailMapper.deleteByPaTaskIds(ids);
//    }
//
//    @Override
//    public TaskTrack taskTrack() {
//        TaskTrack taskTrack = new TaskTrack();
//        if(ObjectUtils.isEmpty(userUtil.getLoginUser())){
//            return  taskTrack;
//        }
//        String jobNo = userUtil.getLoginUser().getManagerNum();
//        CompletableFuture<Integer> childTaskCountFuture = CompletableFuture.supplyAsync(() -> paTaskDetailMapper.getChildTaskCount(jobNo, null));
//        CompletableFuture<Integer> unfinishCountFuture = CompletableFuture.supplyAsync(() -> paTaskDetailMapper.getChildTaskCount(jobNo, TaskChildStatusEnum.UN_FINISH.getCode()));
//        CompletableFuture<Integer> finishCountFuture = CompletableFuture.supplyAsync(() -> paTaskDetailMapper.getChildTaskCount(jobNo, TaskChildStatusEnum.FINISH.getCode()));
//
//        CompletableFuture<Void> allFuture = CompletableFuture.allOf(childTaskCountFuture, unfinishCountFuture, finishCountFuture);
//
//        allFuture.thenRun(() -> {
//            try {
//                //子任务数
//                Integer childTaskCount = childTaskCountFuture.get();
//                //未完成数
//                Integer unfinishCount = unfinishCountFuture.get();
//                //已完成数
//                Integer finishCount = childTaskCount - unfinishCount;
//
//                taskTrack.setChildTaskCount(childTaskCount);
//                taskTrack.setUnfinishCount(unfinishCount);
//                taskTrack.setFinishCount(finishCount);
//                if(childTaskCount != 0){
//                    taskTrack.setFinishRate(String.format("%.2f%%",(double)finishCount/childTaskCount*100));
//                }else {
//                    taskTrack.setFinishRate("0%");
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).join();
//        return taskTrack;
//    }
//
//    @Override
//    @Async("asyncServiceExecutor")
//    public void generateTask(List<PaTaskDetail> paTaskDetails, Long id, String feedback, CountDownLatch countDownLatch) {
//        try{
//            paTaskDetails.stream().forEach(paTaskDetail -> {
//                paTaskDetail.setTaskId(id);
//                paTaskDetail.setStatus(TaskStatusChildEnum.NOT_FINISHED.getCode());
//                paTaskDetail.setFeedback(feedback);
//            });
//            //批量插入任务下客户
//            paTaskDetailMapper.insertBatch(paTaskDetails);
//        }finally {
//            countDownLatch.countDown();
//        }
//    }
//
//    @Override
//    public List<TaskDetailVO> listDetailInView(TaskDetailVO taskDetailVO) {
//        List<TaskDetailVO> list = paTaskDetailMapper.listDetail(taskDetailVO);
//        for (TaskDetailVO detailVO : list) {
//            detailVO.setTaskLevelName(TaskLevelEnum.getNameByCode(detailVO.getTaskLevel()));
//        }
//        return list;
//    }
//
//    @Override
//    @DataScope(deptAlias = "su", userAlias = "su")
//    public List<TaskDetailVO> getAnchorDetail(TaskDetailVO taskDetailVO) {
//        List<TaskDetailVO> details = paTaskDetailMapper.getAnchorDetail(taskDetailVO);
//        if (null == details) details = new ArrayList<>();
//        List<String> customerNmList = details.stream().map(TaskDetailVO::getCustomerNm).collect(Collectors.toList());
//        String taskObjectId = details.size()>0? details.get(0).getTaskObjectId().toString():null;
//        Map<String, AnchorPoint> anchorPointMap = anchorPointService.getAnchorPointByCustomerNm(customerNmList, taskObjectId);
//        BigDecimal bigDecimal = new BigDecimal(0);
//        for (TaskDetailVO detail : details) {
//            detail.setTaskLevelName(TaskLevelEnum.getNameByCode(detail.getTaskLevel()));
//            AnchorPoint p = anchorPointMap.get(detail.getCustomerNm());
//            detail.setLon(null!=p ? p.getLon(): bigDecimal);
//            detail.setLat(null!=p ? p.getLat(): bigDecimal);
//        }
//        return details;
//    }
//}
