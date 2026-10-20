//package com.kx.system.service.impl;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.stream.Collectors;
//
//import javax.annotation.Resource;
//
//import com.kx.system.api.domain.SysUser;
//import org.springframework.aop.framework.AopContext;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.github.pagehelper.PageHelper;
//import com.kx.common.core.utils.StringUtils;
//import com.kx.common.datascope.annotation.DataScope;
//import com.kx.system.api.domain.SysDept;
//import com.kx.system.config.enums.TaskChildStatusEnum;
//import com.kx.system.config.enums.TaskLevelEnum;
//import com.kx.system.config.enums.TaskMethodEnum;
//import com.kx.system.config.enums.TaskStatusEnum;
//import com.kx.system.config.exception.BusinessValidationException;
//import com.kx.system.domain.PaCustomMessage;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.group.CustomGroupCustomer;
//import com.kx.system.domain.task.*;
//import com.kx.system.domain.vo.TaskBaseInfoVo;
//import com.kx.system.domain.vo.TaskStatusVo;
//import com.kx.system.mapper.*;
//import com.kx.system.service.IPaTaskDetailService;
//import com.kx.system.service.IPaTaskService;
//import com.kx.system.task.TaskStatusJob;
//import com.kx.system.util.UserUtil;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 任务Service业务层处理
// *
// * @author kangxu
// * @date 2024-01-23
// */
//@Service
//@Slf4j
//public class PaTaskServiceImpl implements IPaTaskService {
//    @Resource
//    private PaTaskMapper paTaskMapper;
//
//    @Resource
//    private PaTaskDetailMapper paTaskDetailMapper;
//
//    @Resource
//    private CustomGroupCustomerMapper customGroupCustomerMapper;
//
//    @Resource
//    private SysDeptMapper sysDeptMapper;
//
//    @Resource
//    private PaCustomMessageMapper paCustomMessageMapper;
//
//    @Resource
//    private TaskStatusJob taskStatusJob;
//
//    @Resource
//    private UserUtil userUtil;
//
//    @Resource
//    private IPaTaskDetailService paTaskDetailService;
//
//    @Resource
//    private CustomGroupMapper customGroupMapper;
//    /**
//     * 查询任务
//     *
//     * @param id 任务主键
//     * @return 任务
//     */
//    @Override
//    public PaTask selectPaTaskById(Long id) {
//        return paTaskMapper.selectPaTaskById(id);
//    }
//
//    /**
//     * 查询任务列表
//     *
//     * @param paTask 任务
//     * @return 任务
//     */
//    @Override
//    @DataScope(deptAlias = "u",userAlias = "u")
//    public List<TaskBaseInfoVo> selectPaTaskList(PaTask paTask) {
//        if (StringUtils.isNotNull(paTask.getPageNum()) && StringUtils.isNotNull(paTask.getPageSize())) {
//            PageHelper.startPage(paTask.getPageNum(), paTask.getPageSize());
//        }
//        List<TaskBaseInfoVo> tasks = paTaskMapper.selectPaTaskList(paTask);
//        for (TaskBaseInfoVo task : tasks) {
//            task.setCustomGroupIds(task.getCustomGroupId().split(","));
//            task.setTaskLevelName(TaskLevelEnum.getNameByCode(task.getTaskLevel()));
//        }
//        return tasks;
//    }
//
//    /**
//     * 新增任务
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    @Override
//    public int insertPaTask(PaTask paTask) {
//        // 任务当前状态判断
//        if (paTask.getStartTime().after(new Date())) {
//            paTask.setTaskStatus(TaskStatusEnum.NOT_STARTED.getCode());
//        }
//        if (paTask.getStartTime().before(new Date()) && paTask.getEndTime().after(new Date())) {
//            paTask.setTaskStatus(TaskStatusEnum.RUNNING.getCode());
//        }
//        if (paTask.getEndTime().before(new Date())) {
//            throw new BusinessValidationException("任务结束日期不能早于当天日期!");
//        }
//        String customGroupId = paTask.getCustomGroupId();
//        String customGroupName = null;
//        if (StringUtils.isNotEmpty(customGroupId)) {
//           List<String> customGroupNameList = customGroupMapper.selectCustomGroupNameByIds(Arrays.asList(customGroupId.split(",")).stream().map(Long::parseLong).collect(Collectors.toList()));
//            customGroupName = String.join(",", customGroupNameList);
//        }
////        String[] customGroups = customGroupId.split(",");
//
//        // 根据客群查询该客群下所有客户的信息
////        List<SysDept> customDeptInfo = customGroupCustomerMapper
////            .selectCustomerByCustomGroups(Arrays.asList(customGroups), paTask.getTaskObjectId());
////        if (customDeptInfo == null || customDeptInfo.size() == 0) {
////            throw new BusinessValidationException("客群下未查询到客户信息");
////        }
//        // 任务相关的部门id
////        String deptId =
////            customDeptInfo.stream().map(SysDept::getDeptId).map(String::valueOf).collect(Collectors.joining(","));
//
//        // 任务相关的部门名称
////        String deptName = customDeptInfo.stream().map(SysDept::getDeptName).collect(Collectors.joining(","));
//
//        SysUser loginUser = userUtil.getLoginUser();
//        // 创建人
//        String createBy = loginUser.getUserName();
//        SysDept sysDept = sysDeptMapper.selectDeptById(loginUser.getDeptId());
//        paTask.setDeptId(String.valueOf(sysDept.getDeptId()));
//        paTask.setDeptName(sysDept.getDeptName());
//        paTask.setCustomGroupId(customGroupId);
//        paTask.setCustomGroupName(customGroupName);
//        paTask.setCreateBy(createBy);
//        int count = 0;
//        if (paTask.getId() == null) {
//            count = paTaskMapper.insertPaTask(paTask);
//        } else {
//            count = paTaskMapper.updatePaTask(paTask);
//        }
//        // 如果新增的主任务开始时间小于当前时间的,那么直接为其分配子任务,否则等待定时任务去触发
//        if (paTask.getTaskStatus().equals(TaskStatusEnum.RUNNING.getCode())) {
//            taskStatusJob.insertChildTask(paTask);
//        }
//
//        return count;
//    }
//
//    /**
//     * 修改任务
//     *
//     * @param paTask 任务
//     * @return 结果
//     */
//    @Override
//    public int updatePaTask(PaTask paTask) {
//        if (paTask.getCustomGroupIdList() != null && !paTask.getCustomGroupIdList().isEmpty()) {
//            paTask.setCustomGroupId(
//                paTask.getCustomGroupIdList().stream().map(String::valueOf).collect(Collectors.joining(",")));
//            // 该任务下新的客群ids
//            List<Long> newCustomGroupIdList = paTask.getCustomGroupIdList();
//            if (paTaskMapper.selectPaTaskById(paTask.getId()) != null) {
//                String oldCustomGroupIds = paTaskMapper.selectPaTaskById(paTask.getId()).getCustomGroupId();
//                // 该任务下旧的客群ids
//                List<Long> oldCustomGroupIdList = Arrays.asList(oldCustomGroupIds.split(",")).stream()
//                    .map(Long::parseLong).collect(Collectors.toList());
//                // 新增的客群id
//                List<Long> addGroupIds = new ArrayList<>(newCustomGroupIdList);
//                addGroupIds.removeAll(oldCustomGroupIdList);
//                // 如果有新的客群id，则增量插入任务详情
//                List<PaTaskDetail> paTaskDetails = new ArrayList<>();
//                // 客群下客户id
//                List<Long> customIds = new ArrayList<>();
//                if (addGroupIds != null && addGroupIds.size() > 0) {
//                    for (Long addGroupId : addGroupIds) {
//                        List<Long> customs = customGroupCustomerMapper
//                            .selectCustomer(CustomGroupCustomer.builder().customGroupId(addGroupId).build());
//                        customIds.addAll(customs);
//                    }
//                    customIds.stream().map(customId -> {
//                        PaTaskDetail paTaskDetail = new PaTaskDetail();
//                        paTaskDetail.setTaskId(paTask.getId());
//                        paTaskDetail.setClientId(customId);
//                        paTaskDetail.setStatus(TaskStatusEnum.NOT_STARTED.getCode());
//                        return paTaskDetail;
//                    }).forEach(paTaskDetails::add);
//                    if (!paTaskDetails.isEmpty()) {
//                        paTaskDetailMapper.insertBatch(paTaskDetails);
//                    }
//                }
//                // 删除的客群id
//                List<Long> removedGroupIds = new ArrayList<>(oldCustomGroupIdList);
//                removedGroupIds.removeAll(newCustomGroupIdList);
//                // 如果有缺少的客群id，则删除对应的任务详情
//                if (removedGroupIds != null && removedGroupIds.size() > 0) {
//                    TaskDetailVO taskDetailVO = new TaskDetailVO();
//                    taskDetailVO.setTaskId(paTask.getId());
//                    taskDetailVO.setCustomGroupIds(removedGroupIds);
//                    paTaskDetailMapper.deleteByGroupIdAndTaskId(taskDetailVO);
//                }
//            }
//        }
//        return paTaskMapper.updatePaTask(paTask);
//    }
//
//    /**
//     * 批量删除任务
//     *
//     * @param ids 需要删除的任务主键
//     * @return 结果
//     */
//    @Override
//    @Transactional
//    public int deletePaTaskByIds(Long[] ids) {
//        // 进行逻辑删除
//        paTaskDetailService.deleteByTaskIds(ids);
//        return paTaskMapper.deletePaTaskByIds(ids);
//    }
//
//    /**
//     * 删除任务信息
//     *
//     * @param id 任务主键
//     * @return 结果
//     */
//    @Override
//    public int deletePaTaskById(Long id) {
//        return paTaskMapper.deletePaTaskById(id);
//    }
//
//    /**
//     * 主任务状态下拉
//     *
//     * @return
//     */
//    @Override
//    public List<TaskStatusVo> listTaskStatus() {
//        return TaskStatusEnum.listTaskStatusVo();
//    }
//
//    /**
//     * 子任务状态下拉
//     *
//     * @return
//     */
//    @Override
//    public List listChildTaskStatus() {
//        return TaskChildStatusEnum.listChildTaskStatusVo();
//    }
//
//    @Override
//    public List<TaskMethodVo> listTaskMethod() {
//        return TaskMethodEnum.listTaskMethodVo();
//    }
//
//    @Override
//    public void taskUrge(Long[] ids) {
//        if (ids == null) {
//            throw new BusinessValidationException("任务不能为空");
//        }
//        for (Long id : ids) {
//            PaTask paTask = paTaskMapper.selectPaTaskById(id);
//            if (paTask.getTaskStatus().equals(TaskStatusEnum.NOT_STARTED.getCode())) {
//                throw new BusinessValidationException("任务未开始！");
//            }
//            if (paTask.getTaskStatus().equals(TaskStatusEnum.END.getCode())) {
//                throw new BusinessValidationException("任务已结束！");
//            }
//            if ("100.00%".equals(paTask.getCompleteProcess())) {
//                throw new BusinessValidationException("任务已完成！");
//            }
//            String deptIdStr = paTask.getDeptId();
//            List<Long> deptIds = JSON.parseObject(deptIdStr, new TypeReference<List<Long>>() {});
//            List<PaCustomMessage> messages = new ArrayList<>();
//
//            for (Long deptId : deptIds) {
//                PaCustomMessage paCustomMessage = new PaCustomMessage();
//                String leader = sysDeptMapper.selectDeptById(deptId).getLeader();
//                paCustomMessage.setMessageTitle("你的任务" + paTask.getTaskName() + "即将结束，请及时处理！");
//                paCustomMessage.setManagerName(leader);
//                paCustomMessage.setNotifyTime(new Date());
//                paCustomMessage.setMessageState(String.valueOf(0));
//                paCustomMessage.setMessageStar(String.valueOf(0));
//                paCustomMessage.setNetworkCode(String.valueOf(deptId));
//                paCustomMessage.setMessageType(String.valueOf(14));
//                paCustomMessage.setMessageReadState(String.valueOf(0));
//                paCustomMessage.setCreateTime(new Date());
//                paCustomMessage.setUpdateTime(new Date());
//                paCustomMessage.setDelFlag(0L);
//                messages.add(paCustomMessage);
//            }
//            paCustomMessageMapper.batchInsertPaCustomMessage(messages);
//        }
//
//    }
//
//    @Override
//    public TaskRateStatistics listTaskRank(PaTask paTask) {
//        PaTaskServiceImpl self = (PaTaskServiceImpl)AopContext.currentProxy();
//        List<Future<?>> futures = new ArrayList<>();
//
//        TaskRateStatistics taskRateStatistics = new TaskRateStatistics();
//        // 主任务和子任务完成率和数量
//        futures.add(self.handleRateAndCount(taskRateStatistics, paTask));
//        // 子任务支行排行
//        futures.add(self.handleChildTaskOrgRank(taskRateStatistics, paTask));
//        // 子任务分行排行
//        futures.add(self.handleChildTaskSeparateRank(taskRateStatistics, paTask));
//        // 子任务客户经理排名
//        futures.add(self.handleChildTaskManagerRank(taskRateStatistics, paTask));
//        // 主任务支行排行
//        futures.add(self.handleMasterTaskOrgRank(taskRateStatistics, paTask));
//        // 主任务分行排行
//        futures.add(self.handleMasterTaskSeparateRank(taskRateStatistics, paTask));
//        // 主任务客户经理排行
//        futures.add(self.handleMasterTaskManagerRank(taskRateStatistics, paTask));
//
//        // 等待查询完毕
//        try {
//            for (Future<?> future : futures) {
//                future.get();
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//
//        return taskRateStatistics;
//    }
//
//    // 主任务和子任务完成率和数量
//    @Async
//    protected Future<?> handleRateAndCount(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRankData> rateAndCount = paTaskMapper.getRateAndCount(paTask);
//        //数量为0特殊处理
//        if(rateAndCount.size() == 1 && rateAndCount.get(0).getTaskCount() == 0L){
//            TaskRankData taskRankData = new TaskRankData("0%",0L);
//            rateAndCount.set(0,taskRankData);
//            rateAndCount.add(taskRankData);
//        }
//        taskRateStatistics.setRateAndCount(rateAndCount);
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 子任务支行排行
//    @Async
//    protected Future<?> handleChildTaskOrgRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> childTaskOrgRank = paTaskDetailMapper.selectChildTaskOrgRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveChildTaskOrgRank(getTopAndFillRank(childTaskOrgRank));
//        // 后5
//        taskRateStatistics.setLastFiveChildTaskOrgRank(getLastAndFillRank(childTaskOrgRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 子任务分行排行
//    @Async
//    protected Future<?> handleChildTaskSeparateRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> childTaskSeparateRank = paTaskDetailMapper.selectChildTaskSeparateRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveChildTaskSeparateRank(getTopAndFillRank(childTaskSeparateRank));
//        // 后5
//        taskRateStatistics.setLastFiveChildTaskSeparateRank(getLastAndFillRank(childTaskSeparateRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 子任务客户经理排名
//    @Async
//    protected Future<?> handleChildTaskManagerRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> childTaskManagerRank = paTaskDetailMapper.selectChildTaskManagerRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveChildTaskManagerRank(getTopAndFillRank(childTaskManagerRank));
//        // 后5
//        taskRateStatistics.setLastFiveChildTaskManagerRank(getLastAndFillRank(childTaskManagerRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 主任务支行排行
//    @Async
//    protected Future<?> handleMasterTaskOrgRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> masterTaskOrgRank = paTaskDetailMapper.selectMasterTaskOrgRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveMasterTaskOrgRank(getTopAndFillRank(masterTaskOrgRank));
//        // 后5
//        taskRateStatistics.setLastFiveMasterTaskOrgRank(getLastAndFillRank(masterTaskOrgRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 主任务分行排行
//    @Async
//    protected Future<?> handleMasterTaskSeparateRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> masterTaskSeparateRank = paTaskDetailMapper.selectMasterTaskSeparateRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveMasterTaskSeparateRank(getTopAndFillRank(masterTaskSeparateRank));
//        // 后5
//        taskRateStatistics.setLastFiveMasterTaskSeparateRank(getLastAndFillRank(masterTaskSeparateRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    // 主任务客户经理排行
//    @Async
//    protected Future<?> handleMasterTaskManagerRank(TaskRateStatistics taskRateStatistics, PaTask paTask) {
//        List<TaskRateRank> masterTaskManagerRank = paTaskDetailMapper.selectMasterTaskManagerRank(paTask);
//        // 前5
//        taskRateStatistics.setTopFiveMasterTaskManagerRank(getTopAndFillRank(masterTaskManagerRank));
//        // 后5
//        taskRateStatistics.setLastFiveMasterTaskManagerRank(getLastAndFillRank(masterTaskManagerRank));
//        return CompletableFuture.completedFuture(null);
//    }
//
//    @Override
//    public List<TaskBaseInfoVo> listPaTask(PaTask paTask) {
//        return paTaskMapper.selectPaTaskList(paTask);
//    }
//
//    @Override
////    @DataScope(deptAlias = "pt")
//    public TaskRateStatistics listTaskProgress(PaTask paTask) {
//        // 当前用户所属部门
//        // Long deptId = userUtil.getLoginUser().getDeptId();
//        // 所属部门的下级部门
//        // List<SysDept> depts = sysDeptMapper.selectChildrenDeptById(deptId);
//        // List<String> deptIds =
//        // depts.stream().map(dept -> String.valueOf(dept.getDeptId())).collect(Collectors.toList());
//        // paTask.setDeptIds(deptIds);
//        paTask.setTaskStatus(TaskStatusEnum.RUNNING.getCode());
//        // 进行中任务
//        List<TaskBaseInfoVo> runningTask = paTaskMapper.selectProgress(paTask);
//        paTask.setTaskStatus(TaskStatusEnum.NOT_STARTED.getCode());
//        // 待开始任务
//        List<TaskBaseInfoVo> notStartedTask = paTaskMapper.selectProgress(paTask);
//        paTask.setTaskStatus(TaskStatusEnum.END.getCode());
//        // 已结束任务
//        List<TaskBaseInfoVo> endTask = paTaskMapper.selectProgress(paTask);
//
//        TaskRateStatistics taskRateStatistics = new TaskRateStatistics();
//        taskRateStatistics.setRunningTask(runningTask);
//        taskRateStatistics.setNotStartedTask(notStartedTask);
//        taskRateStatistics.setEndTask(endTask);
//        return taskRateStatistics;
//    }
//
//    @Override
//    public TaskTrack getOwnCount(Long id) {
//        String jobNo = userUtil.getLoginUser().getUserName();
//        long finishNum = paTaskDetailMapper.getOwnFinishedCount(id,jobNo);
//        long unFinishNum =paTaskDetailMapper.getOwnUnfinish(id,jobNo);
//        TaskTrack taskTrack = new TaskTrack();
//        taskTrack.setUnfinishCount((int) unFinishNum);
//        taskTrack.setFinishCount((int) finishNum);
//        long total = finishNum + unFinishNum;
//        String finishRate = "0%";
//        if(total > 0){
//            double rate = (double) finishNum / total * 100;
//            finishRate = String.format("%.2f%%", rate);
//            taskTrack.setFinishRate(finishRate);
//        }
//        return taskTrack;
//    }
//
//    @Override
//    public List<TaskBaseInfoVo> listMobile(PaTask paTask) {
//        if (StringUtils.isNotNull(paTask.getPageNum()) && StringUtils.isNotNull(paTask.getPageSize())) {
//            PageHelper.startPage(paTask.getPageNum(), paTask.getPageSize());
//        }
//        String managerNum = userUtil.getLoginUser().getManagerNum();
//        paTask.setManagerJobNumber(managerNum);
//        List<TaskBaseInfoVo> tasks = paTaskMapper.selectPaTaskList(paTask);
//        for (TaskBaseInfoVo task : tasks) {
//            task.setCustomGroupIds(task.getCustomGroupId().split(","));
//            task.setTaskLevelName(TaskLevelEnum.getNameByCode(task.getTaskLevel()));
//            //当前主任务下子任务数量
//            int ownChildTaskCount = paTaskDetailMapper.getOwnChildTaskCount(managerNum, task.getId());
//            task.setTaskDetailNum(ownChildTaskCount);
//            //当前主任务下完成子任务数量
//            int ownFinishedCount = paTaskDetailMapper.getOwnFinishedCount(task.getId(), managerNum);
//            task.setFinishNum(ownFinishedCount);
//            //填充未完成子任务数量
//            task.setUnFinishNum(ownChildTaskCount - ownFinishedCount);
//            String finishRate = "0%";
//            if(ownChildTaskCount > 0){
//                double rate = (double) ownFinishedCount / ownChildTaskCount * 100;
//                finishRate = String.format("%.2f%%", rate);
//            }
//            task.setCompleteProcess(finishRate);
//        }
//        return tasks;
//    }
//
//    /**
//     * 获取列表前5并填充序号
//     *
//     * @param list
//     * @return
//     */
//    private List<TaskRateRank> getTopAndFillRank(List<TaskRateRank> list) {
//        List<TaskRateRank> topFiveChildTaskOrgRank = list.stream().limit(5).collect(Collectors.toList());
//        int rank = 1;
//        for (TaskRateRank taskRateRank : topFiveChildTaskOrgRank) {
//            taskRateRank.setRank(rank);
//            rank++;
//        }
//        return topFiveChildTaskOrgRank;
//    }
//
//    /**
//     * 获取列表后5并填充序号
//     *
//     * @param list
//     */
//    private List<TaskRateRank> getLastAndFillRank(List<TaskRateRank> list) {
//        int size = list.size();
//        List<TaskRateRank> lastFiveChildTaskOrgRank =
//            list.stream().skip(Math.max(0, size - 5)).collect(Collectors.toList());
//        for (int i = lastFiveChildTaskOrgRank.size() - 1; i >= 0; i--) {
//            lastFiveChildTaskOrgRank.get(i).setRank(size);
//            size--;
//        }
//        return lastFiveChildTaskOrgRank;
//    }
//
//}
