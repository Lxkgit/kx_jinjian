package com.kx.system.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * @Description 任务类型
 * @Author lxk
 * @CreateTime 2025-07-10
 */

@Data
public class TaskType {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 任务类型名称
     */
    private String typeName;

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
