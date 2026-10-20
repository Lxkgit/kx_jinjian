//package com.kx.system.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.kx.common.core.utils.StringUtils;
//import com.kx.system.config.enums.TaskMethodEnum;
//import com.kx.system.config.enums.TaskStatusChildEnum;
//import com.kx.system.config.exception.BusinessValidationException;
//import com.kx.system.domain.PaTask;
//import com.kx.system.domain.customer.CustomerLabel;
//import com.kx.system.domain.dto.LabelGroupDTO;
//import com.kx.system.domain.label.LabelGroup;
//import com.kx.system.domain.label.LabelInfo;
//import com.kx.system.domain.task.FillFormEnum;
//import com.kx.system.domain.task.TaskDetailContent;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.mapper.*;
//import com.kx.system.service.ITaskDetailContentService;
//import com.kx.system.service.LabelInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TaskDetailContentServiceImpl implements ITaskDetailContentService {
//    @Autowired
//    private TaskDetailContentMapper taskDetailContentMapper;
//
//    @Autowired
//    private PaTaskDetailMapper paTaskDetailMapper;
//
//    @Autowired
//    private PaTaskMapper paTaskMapper;
//
//    @Autowired
//    private LabelInfoMapper labelInfoMapper;
//
//    @Autowired
//    private CustomerLabelMapper customerLabelMapper;
//
//    @Override
//    public List<TaskDetailContent> list(TaskDetailContent taskDetailContent) {
//        String feedback = paTaskDetailMapper.getDetailContentById(taskDetailContent.getTaskDetailId());
//        List<TaskDetailContent> taskDetailContents = JSONObject.parseArray(feedback, TaskDetailContent.class);
////        for (TaskDetailContent content : taskDetailContents) {
////            //内容为标签时特殊处理
////            if(content.getFillingForm().equals("9") && content.getDetailContent() != null && content.getDetailContent() != ""){
////                String detailContent = content.getDetailContent();
////                List<LabelInfo> labelInfos = new ArrayList<>();
////                if(detailContent != null && detailContent != ""){
////                    String[] labelArray = detailContent.split(",");
////                    List<Long> labelIds = Arrays.asList(labelArray).stream().map(Long::valueOf).collect(Collectors.toList());
////                    for (Long labelId : labelIds) {
////                        LabelInfo label = labelInfoMapper.selectLabelInfoById(labelId);
////                        labelInfos.add(label);
////                    }
////                }
////                content.setDetailContent(JSON.toJSONString(labelInfos));
////            }
////            //内容为图片时
////            if(content.getFillingForm().equals("8") && content.getDetailContent() != null && content.getDetailContent() != ""){
////                String detailContent = content.getDetailContent();
////                content.setDetailContent(JSON.toJSONString(detailContent.split(",")));
////            }
////        }
//        return taskDetailContents;
//    }
//
//    @Override
//    @Transactional
//    public TaskDetailVO edit(TaskDetailVO taskDetailVO) {
//        PaTask task = paTaskMapper.selectPaTaskById(taskDetailVO.getTaskId());
//        if(task.getTaskStatus() != null && task.getTaskStatus() == 2){
//            throw new BusinessValidationException("任务已结束！");
//        }
//        if(taskDetailVO.getContents().size() == 0){
//            taskDetailVO.setIntegrity("100.00%");
//            taskDetailVO.setChildTaskStatus(TaskStatusChildEnum.FINISHED.getCode());
//        }
//        if(taskDetailVO.getContents() != null && taskDetailVO.getContents().size() > 0){
//            //子任务下字段内容非空的数量
//            int notBlankNum = 0;
//            for (TaskDetailContent content : taskDetailVO.getContents()) {
//                if(StringUtils.isNotEmpty(content.getDetailContent())){
//                    notBlankNum ++;
//                }
//                if("9".equals(content.getFillingForm())){
//                    List<Long> ids = JSON.parseArray(content.getDetailContent(), Long.class);
//                    if(!CollectionUtils.isEmpty(ids)){
//                        List<CustomerLabel> customerLabels = new ArrayList<>();
//                        for (Long id : ids) {
//                            CustomerLabel customerLabel = new CustomerLabel();
//                            customerLabel.setLabelId(id);
//                            customerLabel.setCustomerId(taskDetailVO.getClientId());
//                            customerLabel.setCustomerNm(taskDetailVO.getCustomerNm());
//                            customerLabel.setLabelType(0);
//                            customerLabel.setType(task.getTaskObjectId());
//                            customerLabels.add(customerLabel);
//                        }
//                        customerLabelMapper.deleteByCustomerIdAndCustomerType(taskDetailVO.getClientId(),String.valueOf(task.getTaskObjectId()));
//                        customerLabelMapper.insertBatch(customerLabels);
//                    }
//                }
//            }
//            //子任务下字段有不为空的内容，则将子任务状态置为已完成
//            if(notBlankNum > 0){
//                taskDetailVO.setChildTaskStatus(TaskStatusChildEnum.FINISHED.getCode());
//            }
//            //信息完整度
//            double rate = (double) notBlankNum / taskDetailVO.getContents().size() * 100;
//            //格式化为百分数
//            taskDetailVO.setIntegrity(String.format("%.2f%%", rate));
//            taskDetailVO.setFeedback(JSON.toJSONString(taskDetailVO.getContents()));
//
//
//        }
//
//        paTaskDetailMapper.editTaskDetail(taskDetailVO);
//        //子任务完成数
//        int finishedCount = paTaskDetailMapper.getFinishedCount(taskDetailVO.getTaskId());
//        //子任务总数
//        int totalCount = paTaskDetailMapper.getTotalCount(taskDetailVO.getTaskId());
//        //任务完成率
//        double finishRate = (double) finishedCount / totalCount * 100;
//        //格式化为百分数
//        PaTask paTask = new PaTask();
//        paTask.setId(taskDetailVO.getTaskId());
//        paTask.setCompleteProcess(String.format("%.2f%%", finishRate));
//        paTaskMapper.updatePaTask(paTask);
//        return taskDetailVO;
//    }
//}
