//package com.kx.system.service.impl;
//
//import com.kx.common.core.utils.StringUtils;
//import com.kx.system.config.exception.BusinessValidationException;
//import com.kx.system.domain.task.TaskTypeChild;
//import com.kx.system.mapper.TaskTypeChildMapper;
//import com.kx.system.service.TaskTypeChildService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型字段表 服务实现类
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Slf4j
//@Service
//public class TaskTypeChildServiceImpl implements TaskTypeChildService {
//
//    @Autowired
//    private TaskTypeChildMapper taskTypeChildMapper;
//
//    @Override
//    public List<TaskTypeChild> list(TaskTypeChild taskTypeChild) {
//        List<TaskTypeChild> list = taskTypeChildMapper.list(taskTypeChild);
//        return list;
//    }
//
//    @Override
//    public TaskTypeChild getById(Long id) {
//        return taskTypeChildMapper.getById(id);
//    }
//
//    @Override
//    public TaskTypeChild save(TaskTypeChild taskTypeChild) {
//        TaskTypeChild exsitTaskTypeChild = null;
//        if (taskTypeChild.getId() != null) {
//            exsitTaskTypeChild = taskTypeChildMapper.getById(taskTypeChild.getId());
//        }
//        if (exsitTaskTypeChild == null) {
//            taskTypeChildMapper.insertTaskTypeChild(taskTypeChild);
//        } else {
//            BeanUtils.copyProperties(taskTypeChild, exsitTaskTypeChild);
//            taskTypeChildMapper.updateTaskTypeChild(taskTypeChild);
//        }
//        return taskTypeChild;
//    }
//
//    @Override
//    public void deleteByParentId(Long parentId) {
//        taskTypeChildMapper.deleteByParentId(parentId);
//    }
//
//    @Override
//    public String deleteByIds(String ids) {
//        if (StringUtils.isEmpty(ids)) {
//            throw new BusinessValidationException("参数不能为空");
//        }
//        String[] idsArr = ids.split(",");
//        if (idsArr == null || idsArr.length == 0) {
//            throw new BusinessValidationException("参数不能为空");
//        }
//        for (int i = 0; i < idsArr.length; i++) {
//            deleteById(Long.valueOf(idsArr[i]));
//        }
//        return "ok";
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        taskTypeChildMapper.deleteById(id);
//    }
//}
