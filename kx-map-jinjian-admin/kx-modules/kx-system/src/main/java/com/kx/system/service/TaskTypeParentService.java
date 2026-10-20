//package com.kx.system.service;
//
//import com.kx.system.domain.task.*;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型主表 服务类
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//public interface TaskTypeParentService {
//
//    /**
//     * @param taskTypeParent
//     * @return List<TaskTypeParent>
//     * @Description: 列表查询
//     */
//    List<TaskTypeParent> list(TaskTypeParent taskTypeParent);
//
//    /**
//     * @param taskTypeParent
//     * @return List<TaskTypeParent>
//     * @Description: 列表查询
//     */
//    List<TaskTypeParent> selectOwnAll(TaskTypeParent taskTypeParent);
//
//    /**
//     * @param id
//     * @return TaskTypeParent
//     * @Description: 根据id查询
//     */
//    TaskTypeParent getById(Long id);
//
//    /**
//     * @param taskTypeParent
//     * @return TaskTypeParent
//     * @Description: 新增/修改
//     */
//    TaskTypeParent save(TaskTypeParent taskTypeParent);
//
//    /**
//     * 批量删除
//     *
//     * @param ids
//     * @return
//     */
//    String deleteByIds(String ids);
//
//    /**
//     * 任务对象列表
//     *
//     * @return
//     */
//    List listTaskObject();
//
//    /**
//     * 任务群组列表
//     *
//     * @return
//     */
//    List<TaskGroupVo> listTaskGroup();
//
//    /**
//     * 填写形式
//     *
//     * @return
//     */
//    List<FillFormVo> listFillForm();
//
//    /**
//     * 任务优先级
//     *
//     * @return
//     */
//    List<PriorityVo> listPriority();
//
//    /**
//     * 任务周期
//     *
//     * @return
//     */
//    List<TaskPeriodVo> listPeriod();
//
//    TaskTypeParent edit(TaskTypeParent taskTypeParent);
//
//    List<TaskTypeParent> listTaskType(String name);
//}
