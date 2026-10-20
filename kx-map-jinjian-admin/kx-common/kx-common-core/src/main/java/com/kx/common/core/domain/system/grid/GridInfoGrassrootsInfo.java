package com.kx.common.core.domain.system.grid;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-29
 */

@Data
public class GridInfoGrassrootsInfo {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String gridCode;

    /** 基层组织名称 */
    private String organizationName;

    /** 基层组织类型 */
    private Integer organizationType;

    /** 联系人姓名 */
    private String contactsName;

    /** 联系人电话 */
    private String contactsPhone;

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
