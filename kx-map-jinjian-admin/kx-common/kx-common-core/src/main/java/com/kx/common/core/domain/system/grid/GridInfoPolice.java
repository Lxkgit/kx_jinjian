package com.kx.common.core.domain.system.grid;

import lombok.Data;

import java.util.Date;

/**
 * @Description
 * @Author lxk
 * @CreateTime 2025-07-29
 */

@Data
public class GridInfoPolice {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String gridCode;

    /** 派出所名称 */
    private String policeStationName;

    /** 派出所地址 */
    private String policeStationAddr;

    /** 反假联系人姓名 */
    private String policeName;

    /** 联系电话 */
    private String policePhone;

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
