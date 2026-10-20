//package com.kx.system.service.impl;
//
//import com.kx.common.core.utils.ServletUtils;
//import com.kx.common.core.utils.SpringUtils;
//import com.kx.common.core.utils.StringUtils;
//import com.kx.common.security.service.TokenService;
//import com.kx.system.api.model.LoginUser;
//import com.kx.system.config.enums.TaskLevelEnum;
//import com.kx.system.config.enums.TaskObjectEnum;
//import com.kx.system.config.enums.TaskPeriodEnum;
//import com.kx.system.config.exception.BusinessValidationException;
//import com.kx.system.domain.task.*;
//import com.kx.system.domain.vo.TaskObjectVo;
//import com.kx.system.mapper.PaTaskMapper;
//import com.kx.system.mapper.TaskTypeChildMapper;
//import com.kx.system.mapper.TaskTypeParentMapper;
//import com.kx.system.service.TaskTypeChildService;
//import com.kx.system.service.TaskTypeParentService;
//import com.kx.system.util.UserUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * <p>
// * 任务类型主表 服务实现类
// * </p>
// *
// * @author YuanShiYi
// * @since 2022-04-24
// */
//@Service
//public class TaskTypeParentServiceImpl implements TaskTypeParentService {
//
//    @Autowired
//    private TaskTypeParentMapper taskTypeParentMapper;
//
//    @Autowired
//    private TaskTypeChildService taskTypeChildService;
//
//    @Autowired
//    private TaskTypeChildMapper taskTypeChildMapper;
//
//    @Autowired
//    private PaTaskMapper paTaskMapper;
//
//    @Autowired
//    private UserUtil userUtil;
//
//    @Override
//    public List<TaskTypeParent> selectOwnAll(TaskTypeParent taskTypeParent) {
//        TaskDetailVO taskDetailVO = new TaskDetailVO();
//        String managerNum = userUtil.getLoginUser().getManagerNum();
//        taskDetailVO.setManagerJobNumber(managerNum);
//        List<TaskTypeParent> list = taskTypeParentMapper.listOwn(taskDetailVO);
//        return list;
//    }
//
//    @Override
//    public List<TaskTypeParent> list(TaskTypeParent taskTypeParent) {
//        if (StringUtils.isNotEmpty(taskTypeParent.getTaskObjectIds())) {
//            String[] arr = taskTypeParent.getTaskObjectIds().split(",");
//            taskTypeParent.setTaskObjectArr(Arrays.asList(arr));
//        }
//        //当前所属机构号
//        String orgNum = userUtil.getLoginUser().getDept().getOrgNum();
//        taskTypeParent.setCreateOrg(orgNum);
//        List<TaskTypeParent> list = taskTypeParentMapper.list(taskTypeParent);
//        return list;
//    }
//
//    /**
//     * 根据类型id查询子任务表单
//     * @param parent
//     */
//    private void fillArr(TaskTypeParent parent) {
//        TaskTypeChild taskTypeChild = new TaskTypeChild();
//        taskTypeChild.setParentId(parent.getId());
//        List<TaskTypeChild> list = taskTypeChildService.list(taskTypeChild);
//        if (list != null && list.size() > 0) {
//            parent.setList(list);
//        }
//    }
//
//    @Override
//    public TaskTypeParent getById(Long id) {
//        TaskTypeParent parent = taskTypeParentMapper.getById(id);
//        if (parent != null) {
//            fillArr(parent);
//        }
//        return parent;
//    }
//
//    @Override
//    @Transactional
//    public TaskTypeParent save(TaskTypeParent taskTypeParent) {
//        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
//        if (loginUser != null) {
//            taskTypeParent.setCreateBy(loginUser.getUsername());
//            taskTypeParent.setUpdateBy(loginUser.getUsername());
//            taskTypeParent.setCreateId(loginUser.getUserid());
//            taskTypeParent.setCreateOrg(loginUser.getSysUser().getDept().getOrgNum());
//        }
//        /**1.校验任务类型名称是否存在*/
//        if (taskTypeParent.getId() == null) {
//            int count = taskTypeParentMapper.getByName(taskTypeParent.getTaskTypeName());
//            if (count > 0) {
//                throw new BusinessValidationException("任务类型名称已存在");
//            }
//        }
//        /**2.校验任务类型字段不重复*/
//        if (taskTypeParent.getList() != null && taskTypeParent.getList().size() > 0) {
//            Set set = new HashSet<>();
//            for (TaskTypeChild taskTypeChild : taskTypeParent.getList()) {
//                set.add(taskTypeChild.getFieldName());
//            }
//            if (set.size() < taskTypeParent.getList().size()) {
//                throw new BusinessValidationException("字段名称不能重复");
//            }
//        }
//        /**检查任务对象*/
//        if (taskTypeParent.getTaskObjectId() == null) {
//            throw new BusinessValidationException("任务对象不能为空!");
//        }
//        /**3.任务类型主表入库*/
//        TaskTypeParent exsitTaskTypeParent = null;
//        if (taskTypeParent.getId() != null) {
//            exsitTaskTypeParent = taskTypeParentMapper.getById(taskTypeParent.getId());
//        }
//        if (exsitTaskTypeParent == null) {
//            taskTypeParent.setCreateDate(new Date());
//            taskTypeParent.setUpdateDate(new Date());
//            taskTypeParentMapper.insertTaskTypeParent(taskTypeParent);
//        }
//        /**4.任务类字段表入库*/
//        List<TaskTypeChild> list = taskTypeParent.getList();
//        if (list != null && list.size() > 0) {
//            list.stream().forEach(taskTypeChild -> taskTypeChild.setParentId(taskTypeParent.getId()));
//            taskTypeChildMapper.insertBatch(list);
//        }
//        return taskTypeParent;
//    }
//
//    @Override
//    @Transactional
//    public String deleteByIds(String ids) {
//        if (StringUtils.isEmpty(ids)) {
//            throw new BusinessValidationException("参数不能为空");
//        }
//        String[] idsArr = ids.split(",");
//        if (idsArr == null || idsArr.length == 0) {
//            throw new BusinessValidationException("参数不能为空");
//        }
//        List<Long> idList = Arrays.stream(idsArr).map(Long::parseLong).collect(Collectors.toList());
//        Integer total = paTaskMapper.selectCountByTaskTypeId(idList);
//        if (total > 0) {
//            throw new BusinessValidationException("有类型存在于任务中，不可删除");
//        }
//        taskTypeParentMapper.deleteByIds(idList);
//        taskTypeChildMapper.deleteByParentIds(idList);
//        return "删除成功";
//    }
//
//    @Override
//    public List<TaskObjectVo> listTaskObject() {
//        return TaskObjectEnum.listTaskObjectVo();
//    }
//
//    @Override
//    public List<TaskGroupVo> listTaskGroup() {
//        return TaskGroupEnum.listTaskGroupVo();
//    }
//
//    @Override
//    public List<FillFormVo> listFillForm() {
//        return FillFormEnum.listFillFormVo();
//    }
//
//    @Override
//    public List<PriorityVo> listPriority() {
//        return TaskLevelEnum.listPriorityVo();
//    }
//
//    @Override
//    public List<TaskPeriodVo> listPeriod() {
//        return TaskPeriodEnum.listTaskPeriodVo();
//    }
//
//    @Override
//    @Transactional
//    public TaskTypeParent edit(TaskTypeParent taskTypeParent) {
//        //修改前校验该任务类型是否被正在进行中的任务使用
//        if(taskTypeParent.getId() != null && paTaskMapper.selectUsedCount(taskTypeParent.getId()) > 0){
//            throw new BusinessValidationException("该任务类型已被进行中任务使用");
//        }
//        //查询除本类型外同名的任务类型数量
//        int sameCount = taskTypeParentMapper.selectSameNameCount(taskTypeParent);
//        if(sameCount > 0){
//            throw new BusinessValidationException("任务类型名称重复！");
//        }
//        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
//        taskTypeParent.setUpdateDate(new Date());
//        taskTypeParent.setUpdateBy(loginUser.getUsername());
//        /**检查任务对象*/
//        if (taskTypeParent.getTaskObjectId() == null) {
//            throw new BusinessValidationException("任务对象不能为空!");
//        }
//        taskTypeParentMapper.updateTaskTypeParent(taskTypeParent);
//        List<TaskTypeChild> list = taskTypeParent.getList();
//        if(list != null && list.size() > 0){
//            //批量插入子任务
//            list.stream().forEach(taskTypeChild -> taskTypeChild.setParentId(taskTypeParent.getId()));
//        }
//        for (TaskTypeChild taskTypeChild :list) {
//            if (null == taskTypeChild.getId()) {
//                taskTypeChildMapper.insertTaskTypeChild(taskTypeChild);
//            } else {
//                taskTypeChildMapper.updateTaskTypeChild(taskTypeChild);
//            }
//        }
//        return taskTypeParent;
//    }
//
//    @Override
//    public List<TaskTypeParent> listTaskType(String name) {
//        TaskTypeParent taskTypeParent = new TaskTypeParent();
//        taskTypeParent.setTaskTypeName(name);
//        List<TaskTypeParent> types = taskTypeParentMapper.listTaskType(taskTypeParent);
//        return types;
//    }
//}
