//package com.kx.system.mapper;
//
//import com.kx.system.domain.task.TaskDetailContent;
//import com.kx.system.domain.task.TaskTypeChild;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型字段表 Mapper 接口
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Mapper
//public interface TaskTypeChildMapper {
//
//  List<TaskTypeChild> list(TaskTypeChild taskTypeChild);
//
//  TaskTypeChild getById(@Param("id") Long id);
//
//  Long insertTaskTypeChild(TaskTypeChild taskTypeChild);
//
//  Long updateTaskTypeChild(TaskTypeChild taskTypeChild);
//
//  void deleteByParentId(@Param("parentId") Long parentId);
//
//  void deleteById(@Param("id") Long id);
//
//  void deleteByParentIds(@Param("idList") List<Long> idList);
//
//  void insertBatch(@Param("list") List<TaskTypeChild> list);
//
//  List<TaskDetailContent> selectByTaskTypeParentId(Long taskType);
//}
