package com.kx.system.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * @Description 任务主表
 * @Author lxk
 * @CreateTime 2025-07-10
 */

@Data
public class TaskMainInfo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型id
     */
    private Long taskTypeCode;

    /**
     * 任务优先级
     */
    private Integer taskLevel;

    /**
     * 任务频次 1单次任务 2周期任务
     */
    private Integer taskFrequency;

    /**
     * 任务开始时间
     */
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    private Date taskEndTime;

    /**
     * 任务生成周期
     */
    private String taskCron;

    /**
     * 任务当前执行周期
     */
    private Integer taskExecuteSchedule;

    /**
     * 备注
     */
    private String taskRemark;

    /**
     * 任务执行对象类型编码 1人民银行 2银行机构 3网格
     */
    private Integer taskExecuteType;

    /**
     * 任务执行对象编码
     */
    private String taskExecuteCode;

    /**
     * 任务填写内容json
     */
    private String taskJson;

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
