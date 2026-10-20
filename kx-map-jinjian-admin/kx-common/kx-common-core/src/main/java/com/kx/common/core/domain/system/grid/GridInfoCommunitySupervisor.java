package com.kx.common.core.domain.system.grid;

import lombok.Data;

import java.util.Date;

/**
 * @Description 基层组织信息表
 * @Author lxk
 * @CreateTime 2025-07-28
 */

@Data
public class GridInfoCommunitySupervisor {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String gridCode;

    /** 监督员名称 */
    private String supervisorName;

    /** 联系电话 */
    private String supervisorPhone;

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
