package com.kx.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 任务列表信息返回结果集
 */
@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆用户机构及下辖
     */
    private List<String> networkLCodeList;
    /**
     * 登录用户名姓名(查询)
     */
    private String nickName;
    /**
     * 登录用户名id(查询)
     */
    private Long userId;
    /**
     * 登录用户名(查询)
     */
    private String userName;
    /**
     * 登录用户客户经理号(查询)
     */
    private String managerCode;
    /**
     * 登录用户网点
     */
    private String networkCode;
    /**
     * 登录用户网点名称
     */
    private String networkName;
    /**
     *  用户工号
     */
    private String jobNumber;
}
