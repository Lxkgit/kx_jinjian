package com.kx.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务视图参数
 */
@Data
public class TaskVisitVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录用户名id(查询)
     */
    private Long userId;
    /**
     * 登录用户客户经理号
     */
    private String userManagerCode;
    /**
     * 登陆用户机构及下辖
     */
    private List<String> userList;
    /**
     * 机构
     */
    private String networkCode;
    /**
     * 责任机构(查询)
     */
    private List<String> queryList;
    /**
     * 客户经理号
     */
    private String managerId;
    /**
     *  任务id查询
     */
    private String taskId;
    /**
     *  任务名称
     */
    private String taskName;
    /**
     *  数据跑批日期
     */
    private String statisticsDate;
    /**
     *  数据统计开始时间
     */
    private Date startDate;
    /**
     *  数据统计结束时间
     */
    private Date endDate;
    /**
     *  主任务状态
     */
    private Integer status;
}
