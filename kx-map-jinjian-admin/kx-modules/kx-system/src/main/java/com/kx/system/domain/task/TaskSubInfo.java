package com.kx.system.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * @Description 子任务表
 * @Author lxk
 * @CreateTime 2025-07-10
 */

@Data
public class TaskSubInfo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 任务类型 1:我的任务 2:已转派任务
     */
    private Byte taskType;

    /**
     * 0: 任务创建人直接指派 x: 被指派任务id
     */
    private Long taskParentId;

    /**
     * 主任务id
     */
    private Long taskId;

    /**
     * 任务周期（记录任务当前周期）
     */
    private Integer taskSchedule;

    /**
     * 任务执行对象编码
     */
    private String taskOrgCode;

    /**
     * 任务状态 0未完成 1已完成
     */
    private Byte taskStatus;

    /**
     * 任务上报信息完整度
     */
    private String taskFillProgress;

    /**
     * 任务填写数据json
     */
    private String taskDetailJson;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人账号
     */
    private Long createBy;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人账号
     */
    private Long updateBy;
}
