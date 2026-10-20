//package com.kx.system.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.kx.common.core.utils.ServletUtils;
//import com.kx.common.core.utils.SpringUtils;
//import com.kx.common.core.utils.StringUtils;
//import com.kx.common.security.service.TokenService;
//import com.kx.system.api.model.LoginUser;
//import com.kx.system.config.exception.BusinessValidationException;
//import com.kx.system.domain.TaskBaseInfo;
//import com.kx.system.domain.model.SysNetwork;
//import com.kx.system.domain.vo.UserInfoVo;
//import com.kx.system.mapper.SysNetworkMapper;
//import com.kx.system.service.TaskInfoService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service("taskInfoService")
//public class TaskInfoServiceImpl implements TaskInfoService {
//    @Resource
//    private SysNetworkMapper sysNetworkMapper;
//
//    @Override
//    public JSONObject addTask(TaskBaseInfo taskBaseInfo) {
//        return null;
//    }
//
//    @Override
//    public String forceFinishTask(Long taskId) {
//        return null;
//    }
//
//    @Override
//    public String callDealWithTask(String taskId, Integer type) {
//        return null;
//    }
//
//    @Override
//    public String repeatTask(String detailId) {
//        return null;
//    }
//
//    @Override
//    public List<String> getUserNetworkCodesList() {
//        return null;
//    }
//
//    @Override
//    public JSONObject modifyTaskCustom(TaskBaseInfo taskBaseInfo) {
//        return null;
//    }
//
//    @Override
//    public JSONObject modifyTask(TaskBaseInfo taskBaseInfo) {
//        return null;
//    }
//
//    @Override
//    public List<String> getQueryNetworkCodesList(Long networkId) {
//        return null;
//    }
//
//    @Override
//    public String getUserNickName() {
//        return null;
//    }
//
//    @Override
//    public UserInfoVo getUserInfo() {
//        System.out.println("進來了");
//        return null;
//    }
//
//    @Override
//    public List<String> getQueryListByNetworkIds(String networkId) {
//        return null;
//    }
//
//    @Override
//    public Boolean isHeadOffice() {
//        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
//        if (loginUser == null) {
//            throw new BusinessValidationException("用户未登录!");
//        }
//        SysNetwork sysNetwork = sysNetworkMapper.selectNetworkById(loginUser.getSysUser().getDeptId());
//        String[] ancestors = sysNetwork.getAncestors().split(",");
//        boolean isHeadOffice = false;
//        for (String ancestor : ancestors) {
//            SysNetwork network = sysNetworkMapper.selectNetworkById(Long.parseLong(ancestor));
//            if (network != null && "总行".equals(network.getNetworkName())) {
//                isHeadOffice = true;
//                break;
//            }
//        }
//        return isHeadOffice;
//    }
//
//    @Override
//    public List<String> getHeadOfficeNetworkLCodeList() {
//        LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
//        if (loginUser == null) {
//            throw new BusinessValidationException("用户未登录!");
//        }
//        SysNetwork sysNetwork = sysNetworkMapper.selectNetworkById(loginUser.getUser().getDeptId());
//        String[] ancestors = sysNetwork.getAncestors().split(",");
//        Long networkId = null;
//        for (String ancestor : ancestors) {
//            SysNetwork network = sysNetworkMapper.selectNetworkById(Long.parseLong(ancestor));
//            if (network != null && network.getType() == 1) {
//                networkId = network.getNetworkId();
//                break;
//            }
//        }
//        List<SysNetwork> networks=selectSelfAndChildrenNetworkById(networkId);
//        List<String> networkCodes = networks.stream().map(SysNetwork::getNetworkCode).collect(Collectors.toList());
//        return networkCodes;
//    }
//
//    /**
//     *  查询本级及下辖
//     * @param networkId
//     * @return
//     */
//    private List<SysNetwork> selectSelfAndChildrenNetworkById(Long networkId) {
//        List<SysNetwork> result=new ArrayList<>();
//        List<SysNetwork>  networks= sysNetworkMapper.selectAllSysNetwork();
//        if (StringUtils.isNotEmpty(networks)){
//            result=networks.stream().filter(a->networkId.longValue()==a.getNetworkId().longValue()|| a.getAncestors().contains(networkId+"")).collect(Collectors.toList());
//        }
//        return result;
//    }
//}
