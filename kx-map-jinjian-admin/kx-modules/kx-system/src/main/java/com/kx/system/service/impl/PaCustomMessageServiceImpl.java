package com.kx.system.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.kx.common.core.exception.base.BaseException;
import com.kx.common.core.text.Convert;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysRole;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.PaCustomMessage;
import com.kx.system.domain.dto.PaCustomMessageDTO;
import com.kx.system.domain.enums.RoleEnum;
import com.kx.system.domain.model.SysNetwork;
import com.kx.system.domain.vo.PaCustomMessageStatisticsVo;
import com.kx.system.mapper.PaCustomMessageMapper;
import com.kx.system.mapper.SysNetworkMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.IPaCustomMessageService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 消息Service业务层处理
 *
 * @author lc
 * @date 2022-06-01
 */
@Service
@Slf4j
public class PaCustomMessageServiceImpl implements IPaCustomMessageService {
    @Autowired
    private PaCustomMessageMapper paCustomMessageMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysNetworkMapper sysNetworkService;

    @Autowired
    private SysUserMapper sysUserMapper;



    /**
     * 查询消息列表
     *
     * @param paCustomMessageDTO 消息
     * @return 消息
     */
    @Override
    public List<PaCustomMessage> selectPaCustomMessageList(PaCustomMessageDTO paCustomMessageDTO) {

//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        if (loginUser == null) {
//            throw new BaseException("无法获取当前账号！");
//        }
//        SysUser user = loginUser.getSysUser();
//        if (user == null) {
//            throw new BaseException("无法获取当前账号！");
//        }
//        List<SysRole> roles = user.getRoles();
//        List<String> roleKeyList = new ArrayList<>();
//        for (SysRole role : roles) {
//            roleKeyList.add(role.getRoleKey());
//        }
//        if (CollectionUtils.isEmpty(roleKeyList) || roleKeyList.contains(RoleEnum.admin.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZXGLRY.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHXLD.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHJBRY.getRoleKEY())) {
//            //总行管理人员/总行经办人员/总行领导/超级管理员
//            paCustomMessageDTO.setNetworkCode(null);
//        } else if (roleKeyList.contains(RoleEnum.ZXLD.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHXJBRY.getRoleKEY())) {
//            //支行领导/支行经办人员/客户经理
//            String networkCode = user.getNetworkCode();
//            if (networkCode.length() > 6) {
//                networkCode = networkCode.substring(0, 5);
//            }
//            paCustomMessageDTO.setNetworkCode(networkCode);
//        } else if (roleKeyList.contains(RoleEnum.EJZXLD.getRoleKEY())) {
//            paCustomMessageDTO.setNetworkCode(user.getNetworkCode());
//        } else if (roleKeyList.contains(RoleEnum.KHJL.getRoleKEY())) {
//            //客户经理
//            String jobNumber = user.getJobNumber();
//            paCustomMessageDTO.setNotifyObject(jobNumber);
//        }


        List<PaCustomMessage> paCustomMessages = paCustomMessageMapper.selectPaCustomMessageList(paCustomMessageDTO);
        if (paCustomMessages != null) {
            paCustomMessages.stream().forEach(a -> {
                SysNetwork network = sysNetworkService.selectNetWorkByCode(a.getNetworkCode());
                if (network != null) {
                    a.setNetworkName(network.getNetworkName());
                }
                SysUser sysUser = sysUserMapper.selectUserById(a.getId());
                if (sysUser != null) {
                    a.setManagerName(sysUser.getNickName());
                }
                if (StringUtils.isEmpty(a.getMessageDetails())) {

                    List<List<Map>> messageDetails = new ArrayList<>();

                    //标题部分
                    List<Map> titleMap = new ArrayList<>();
                    HashMap<String, String> networkCodeMap = new HashMap<>();
                    networkCodeMap.put("label", "消息标题");
                    String value = a.getMessageTitle();
                    if (a.getMessageTitle().indexOf(":") > 0) {
                        value = a.getMessageTitle().split(":")[1];
                    }
                    networkCodeMap.put("value", value);
                    titleMap.add(networkCodeMap);
                    messageDetails.add(titleMap);
                    a.setMessageDetails(JSONArray.toJSONString(messageDetails));
                }
            });
        }
        return paCustomMessages;
    }

    @Override
    public List<PaCustomMessageStatisticsVo> listMessageStatistics(PaCustomMessageDTO paCustomMessageDTO) {
        List<PaCustomMessageStatisticsVo> paCustomMessageStatisticsVos = new ArrayList<>();
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        if (loginUser == null) {
//            throw new BaseException("无法获取当前账号！");
//        }
//        SysUser user = loginUser.getSysUser();
//        if (user == null) {
//            throw new BaseException("无法获取当前账号！");
//        }
//        List<SysRole> roles = user.getRoles();
//        List<String> roleKeyList = new ArrayList<>();
//        for (SysRole role : roles) {
//            roleKeyList.add(role.getRoleKey());
//        }
//
//        roleKeyList.add("admin");
//        if (CollectionUtils.isEmpty(roleKeyList) || roleKeyList.contains(RoleEnum.admin.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZXGLRY.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHXLD.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHJBRY.getRoleKEY())) {
//            //总行管理人员/总行经办人员/总行领导/超级管理员
//        } else if (roleKeyList.contains(RoleEnum.ZXLD.getRoleKEY()) || roleKeyList.contains(RoleEnum.ZHXJBRY.getRoleKEY())) {
//            //支行领导/支行经办人员/客户经理
//            String networkCode = user.getNetworkCode();
//            if (networkCode.length() > 6) {
//                networkCode = networkCode.substring(0, 5);
//            }
//            paCustomMessageDTO.setNetworkCode(networkCode);
//        } else if (roleKeyList.contains(RoleEnum.EJZXLD.getRoleKEY())) {
//            paCustomMessageDTO.setNetworkCode(user.getNetworkCode());
//        } else if (roleKeyList.contains(RoleEnum.KHJL.getRoleKEY())) {
//            //客户经理
//            String jobNumber = user.getJobNumber();
//            paCustomMessageDTO.setNotifyObject(jobNumber);
//        }

        //查询结果
        List<PaCustomMessage> list = paCustomMessageMapper.selectPaCustomMessageList(paCustomMessageDTO);
        if (list != null && list.size() > 0) {

            //获取查询出来的消息类型
            List<String> messageTypes = list.stream().map(PaCustomMessage::getMessageType).distinct().collect(Collectors.toList());

            if (messageTypes != null && messageTypes.size() > 0) {
                // 0：代表统计全部
                messageTypes.add(0, "0");
                //遍历消息类型，分别生成统计数据
                messageTypes.stream().forEach(a -> {

                    DecimalFormat df = new DecimalFormat("0%");
                    //涉及客户数量
                    int clientQuantity = list.stream().filter(b -> a.equals("0") || b.getMessageType().equals(a)).collect(Collectors.toList()).size();


                    //未服务的客户数量
                    int notClientQuantity = list.stream()
                            .filter(b -> b.getMessageType() != null && (a.equals("0") || b.getMessageType().equals(a)))
                            .filter(b -> b.getMessageState()!= null && b.getMessageState().equals("1"))
                            .collect(Collectors.toList()).size();

                    //已经服务的客户数量
                    int alreadyClientQuantity = list.stream()
                            .filter(b -> b.getMessageType() != null && (a.equals("0") || b.getMessageType().equals(a)))
                            .filter(b -> b.getMessageState()!= null && b.getMessageState().equals("2"))
                            .collect(Collectors.toList())
                            .size();


                    //完成率
                    double completionRate = new BigDecimal((float) alreadyClientQuantity / clientQuantity).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    PaCustomMessageStatisticsVo paCustomMessageStatisticsVo = new PaCustomMessageStatisticsVo();
                    paCustomMessageStatisticsVo.setMessageType(a);
                    paCustomMessageStatisticsVo.setClientQuantity(clientQuantity);
                    paCustomMessageStatisticsVo.setAlreadyClientQuantity(alreadyClientQuantity);
                    paCustomMessageStatisticsVo.setNotClientQuantity(notClientQuantity);
                    paCustomMessageStatisticsVo.setCompletionRate(df.format(completionRate));
                    paCustomMessageStatisticsVos.add(paCustomMessageStatisticsVo);

                });

            }

        }

        return paCustomMessageStatisticsVos;
    }



    /**
     * 新增消息
     *
     * @param paCustomMessage 消息
     * @return 结果
     */
    @Override
    public int insertPaCustomMessage(PaCustomMessage paCustomMessage) {
        paCustomMessage.setCreateTime(DateUtils.getNowDate());
        paCustomMessage.setNotifyTime(new Date());
        return paCustomMessageMapper.insertPaCustomMessage(paCustomMessage);
    }

    /**
     * 批量设置星级
     */
    @Override
    public int batchMessageStar(String[] idList, String messageStar) {
        return paCustomMessageMapper.updateMessageStar(idList,messageStar);
    }

    /**
     * 批量设置消息状态
     */
    @Override
    public int batchMessageState(String managerId,String[] idList, String messageState) {
        return paCustomMessageMapper.updateMessageState(managerId,idList,messageState);
    }



}
