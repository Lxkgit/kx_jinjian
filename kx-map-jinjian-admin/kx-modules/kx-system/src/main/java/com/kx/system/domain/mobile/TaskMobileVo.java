package com.kx.system.domain.mobile;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 任务移动端查询参数
 */
@Data
public class TaskMobileVo implements Serializable {

    private static final long serialVersionUID = 100L;
    /**
     * 登录用户客户经理号(查询)
     */
    private String userManagerCode;
    /**
     * 登陆用户机构及下辖
     */
    private List<String> userList;
    /**
     * 任务类型
     */
    private List<Long> typeList;
    /**
     * 主任务状态
     */
    private List<Integer> statusList;
    /**
     * 子任务状态
     */
    private List<Integer> childStatusList;
    /**
     * 优先级
     */
    private List<Integer> priorityList;
    /**
     * 任务名称
     */
    private String taskMasterName;

    /**视图参数*/
    /**
     * 地址code
     */
    private String addrCode;
    /**
     * 地址码辖内客户经理集
     */
    private List<String> managers;
    /**
     * 地址码辖内客户内码集
     */
    private List<String> objectCodes;
    /**
     * 责任机构(查询)
     */
    private String networkId;
    /**
     * 责任机构(查询)
     */
    private List<String> queryList;

    /**
     *  任务明细列表id集查询
     */
    private List<Long> ids;
}
