//package com.kx.system.task;
//
//import com.alibaba.fastjson.JSON;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.task.PaTaskDetail;
//import com.kx.system.domain.task.TaskDetailContent;
//import com.kx.system.mapper.CustomGroupCustomerMapper;
//import com.kx.system.mapper.PaTaskMapper;
//import com.kx.system.mapper.TaskTypeChildMapper;
//import com.kx.system.service.IPaTaskDetailService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.CountDownLatch;
//import java.util.stream.Collectors;
//
///**
// * 任务模块定时任务
// *
// * @author zhangwenxuan
// */
//@Component
//@Slf4j
//public class TaskStatusJob extends BaseJob {
//    @Resource
//    private PaTaskMapper paTaskMapper;
//
//    @Resource
//    private CustomGroupCustomerMapper customGroupCustomerMapper;
//
//    @Resource
//    private TaskTypeChildMapper taskTypeChildMapper;
//
//    @Resource
//    private IPaTaskDetailService paTaskDetailService;
//
//    //0 */30 * * * *
////    @Value("${data-runtime.TaskStatusJob:-}")
//    private String cron;
//
//    /**
//     * 每分钟检测将超时未完成的主任务以及其子任务任务状态更新为已结束
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    protected void execute(Date date) {
//        //当前时间
//        Date now = new Date();
//        //超时未完成的主任务id
//        List<Long> taskIds = paTaskMapper.selectUnfinishTask(now);
//        //查询StartTime 小于当前时间 并且该主任务状态为 待开始 的所有任务的Id
//        List<PaTask> paTaskList = paTaskMapper.getPaTaskList(now);
//        CompletableFuture.runAsync(() -> {
//            //将未开始的任务设置为考核中
//            paTaskMapper.updateTaskStatusToRunning(now);
//        });
//
//        CompletableFuture.runAsync(() -> {
//            //将超时未完成的任务状态修改为已结束
//            paTaskMapper.updateTaskStatus(now);
//        });
//
////        CompletableFuture.runAsync(() -> {
////            if(taskIds != null && taskIds.size() > 0){
////                //将上面主任务中的子任务状态修改为已结束
////                paTaskDetailMapper.updateChildTaskStatus(taskIds);
////            }
////        });
//
//        CompletableFuture.runAsync(() -> {
//            if (paTaskList != null && paTaskList.size() > 0) {
//                //为该主任务下发子任务
//                for (PaTask paTask : paTaskList) {
//                    insertChildTask(paTask);
//                }
//            }
//        });
//    }
//
//    /**
//     * 任务下发
//     *
//     * @param paTask
//     */
//    @Async
//    public void insertChildTask(PaTask paTask) {
//        log.info("【" + paTask.getTaskName() + "】任务开始下发");
//        String[] groupIds = paTask.getCustomGroupId().split(",");
//        List<Long> customGroupIds = Arrays.asList(groupIds).stream().map(Long::valueOf).collect(Collectors.toList());
//        //获取所有客群下的客户信息
//        List<PaTaskDetail> paTaskDetailList = customGroupCustomerMapper.selectCustomIdByGroupIds(customGroupIds, paTask.getTaskObjectId());
//        //去重处理
//        HashSet<PaTaskDetail> detailSet = new HashSet<>(paTaskDetailList);
//        //重新得到去重后的list
//        List<PaTaskDetail> paTaskDetails = new ArrayList<>(detailSet);
//        Long taskType = paTask.getTaskType();
//        //查询该任务下的反馈字段
//        List<TaskDetailContent> fieldList = taskTypeChildMapper.selectByTaskTypeParentId(taskType);
//
//        Long id = paTask.getId();
//        //反馈内容的json
//        String feedback = JSON.toJSONString(fieldList);
//        //设置分批次list大小
//        int size = 20;
//        List<List<PaTaskDetail>> groupList = splitGroup(paTaskDetails, size);
//        CountDownLatch countDownLatch = new CountDownLatch(groupList.size());
//
//        //分批次插入子任务
//        for (List<PaTaskDetail> taskDetails : groupList) {
//            paTaskDetailService.generateTask(taskDetails, id, feedback, countDownLatch);
//        }
//
//        try {
//            countDownLatch.await();
//        } catch (Exception e) {
//            log.error("线程池异常:" + e.getMessage());
//        }
//
//        log.info("【" + paTask.getTaskName() + "】任务指派成功");
//    }
//
//    /**
//     * 将list分隔为指定大小的list集合
//     *
//     * @param paTaskDetails
//     * @param size
//     */
//    private List<List<PaTaskDetail>> splitGroup(List<PaTaskDetail> paTaskDetails, int size) {
//        List<List<PaTaskDetail>> groupList = new ArrayList<>();
//        for (int i = 0; i < paTaskDetails.size(); i += size) {
//            int endIndex = Math.min(i + size, paTaskDetails.size());
//            groupList.add(paTaskDetails.subList(i, endIndex));
//        }
//        return groupList;
//    }
//
//    @Override
//    public String getJobDesc() {
//        return "任务模块定时任务";
//    }
//
//    @Override
//    public String getCron() {
//        return cron;
//    }
//}
