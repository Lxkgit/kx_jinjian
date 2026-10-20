//package com.kx.system.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.kx.system.domain.TaskBaseInfo;
//import com.kx.system.domain.vo.UserInfoVo;
//
//import java.util.List;
//
//public interface TaskInfoService {
//    /**
//     * 新增任务
//     *
//     * @param taskBaseInfo 任务基本信息对象
//     * @return
//     */
//    JSONObject addTask(TaskBaseInfo taskBaseInfo);
//
//    /***
//     *  强制结束任务
//     * @param taskId
//     * @return
//     */
//    String forceFinishTask(Long taskId);
//
//    /**
//     * 任务催办
//     *
//     * @param taskId 传入任务id/任务下派记录id
//     * @param type   任务类型 0-主任务 1-子任务
//     * @return
//     */
//    String callDealWithTask(String taskId, Integer type);
//
//    /**
//     * 任务重办
//     *
//     * @param detailId
//     * @return
//     */
//    String repeatTask(String detailId);
//
//    /**
//     * 获取当前用户所在网点及下级的所有networkcdoe集
//     *
//     * @return
//     */
//    List<String> getUserNetworkCodesList();
//
//    /**
//     * 变更客户名单
//     *
//     * @param taskBaseInfo
//     * @return
//     */
//    JSONObject modifyTaskCustom(TaskBaseInfo taskBaseInfo);
//
//    /**
//     * 更新任务基本信息
//     *
//     * @param taskBaseInfo
//     * @return
//     */
//    JSONObject modifyTask(TaskBaseInfo taskBaseInfo);
//
//    List<String> getQueryNetworkCodesList(Long networkId);
//
//    String getUserNickName();
//
//    /**
//     *  获取登录客户信息
//     * @return
//     */
//    UserInfoVo getUserInfo();
//
//    /**
//     *  根据机构查询
//     * @param networkId
//     * @return
//     */
//    List<String> getQueryListByNetworkIds(String networkId);
//
//    /**
//     *  判断是否是总行
//     * @return
//     */
//    Boolean isHeadOffice();
//
//    /**
//     *  获取总行对应的code集
//     * @return
//     */
//    List<String> getHeadOfficeNetworkLCodeList();
//
//}
