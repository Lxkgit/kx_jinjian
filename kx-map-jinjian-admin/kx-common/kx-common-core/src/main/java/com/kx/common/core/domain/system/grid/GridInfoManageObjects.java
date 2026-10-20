package com.kx.common.core.domain.system.grid;

import lombok.Data;

import java.util.Date;

/**
 * @Description 重点兼管对象表
 * @Author lxk
 * @CreateTime 2025-07-28
 */

@Data
public class GridInfoManageObjects {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String gridCode;

    /** 重点管理对象名称 */
    private String managerName;

    /** 重点管理对象类型 */
    private Integer managerType;

    /** 重点管理对象地址 */
    private String managerAddr;

    /** 创建时间 */
    private Date createTime;

    /** 创建人名称 */
    private String createName;

    /** 创建人账号 */
    private Long createBy;

    /** 最近修改时间 */
    private Date updateTime;

    /** 修改人名称 */
    private String updateName;

    /** 修改人账号 */
    private Long updateBy;
}
