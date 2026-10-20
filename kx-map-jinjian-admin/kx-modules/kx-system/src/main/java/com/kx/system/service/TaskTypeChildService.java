//package com.kx.system.service;
//
//import com.kx.system.domain.task.TaskTypeChild;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型字段表 服务类
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//public interface TaskTypeChildService {
//
//    /**
//     * @param taskTypeChild
//     * @return List<TaskTypeChild>
//     * @Description: 列表查询
//     */
//    List<TaskTypeChild> list(TaskTypeChild taskTypeChild);
//
//    /**
//     * @param id
//     * @return TaskTypeChild
//     * @Description: 根据id查询
//     */
//    TaskTypeChild getById(Long id);
//
//    /**
//     * @param taskTypeChild
//     * @return TaskTypeChild
//     * @Description: 新增/修改
//     */
//    TaskTypeChild save(TaskTypeChild taskTypeChild);
//
//    /**
//     * 根据parentId删除
//     *
//     * @param parentId
//     */
//    void deleteByParentId(Long parentId);
//
//    /**
//     * 根据ids删除
//     *
//     * @param ids
//     * @return
//     */
//    String deleteByIds(String ids);
//
//    /**
//     * 根据id删除
//     *
//     * @param id
//     */
//    void deleteById(Long id);
//}
