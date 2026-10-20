package com.kx.system.domain.mobile;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录用户相关查询参数
 */
@Data
public class TaskHomeVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录用户名姓名(查询)
     */
    private String nickName;
    /**
     * 登录用户名(查询)
     */
    private String userName;
    /**
     * 登录用户客户经理号(查询)
     */
    private String userManagerCode;
    /**
     * 登陆用户机构及下辖
     */
    private List<String> userList;

    /**
     * 任务规划时间
     */
    private Date chooseDate;

    /**
     * 任务走访开始时间
     */
    private Date beginDate;
    /**
     * 任务走访结束时间
     */
    private Date endDate;


    /**任务视图*/
    /**
     * 责任机构(查询)
     */
    private String networkId;
    /**
     * 责任机构
     */
    private String networkCode;
    /**
     * 责任机构(查询)
     */
    private List<String> queryList;

    /**
     * 任务id查询
     */
    private String taskId;

    /**
     * 数据跑批日期
     */
    private String statisticsDate;
}
