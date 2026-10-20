//package com.kx.system.mapper;
//
//import com.kx.common.datascope.annotation.DataScope;
//import com.kx.system.domain.task.TaskDetailVO;
//import com.kx.system.domain.task.TaskTypeParent;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
///**
// * <p>
// * 任务类型主表 Mapper 接口
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Mapper
//public interface TaskTypeParentMapper {
//
//  List<TaskTypeParent> list(TaskTypeParent taskTypeParent);
//
//  List<TaskTypeParent> listOwn(TaskDetailVO taskDetailVO);
//
//  TaskTypeParent getById(@Param("id") Long id);
//
//  Long insertTaskTypeParent(TaskTypeParent taskTypeParent);
//
//  Long updateTaskTypeParent(TaskTypeParent taskTypeParent);
//
//  void deleteById(@Param("id") Long id);
//
//  int getByName(@Param("taskTypeName") String taskTypeName);
//
//  void deleteByIds(@Param("idList") List<Long> idList);
//
//    List<TaskTypeParent> selectAll();
//
//    int selectSameNameCount(TaskTypeParent taskTypeParent);
//
//  @DataScope(deptAlias = "u", userAlias = "u")
//  List<TaskTypeParent> listTaskType(TaskTypeParent taskTypeParent);
//}
