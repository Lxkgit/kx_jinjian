package com.kx.system.domain.station;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 金融服务站实体类
 * @Author lxk
 * @CreateTime 2025-07-09
 */

@Data
public class StationInfo {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 网格编码
     */
    private String girdCode;

    /**
     * sys_dept表parent_id字段
     */
    private Long parentId;

    /**
     * 父级部门名称
     */
    private String parentDeptName;

    /**
     * 父部门编号
     */
    private String parentOrgNum;

    /**
     * sys_dept表org_num字段（网点编码）
     */
    private String orgNum;

    /**
     * 服务站名称
     */
    private String stationName;

    /**
     * 具体地址
     */
    private String stationAddr;

    /**
     * 站长姓名
     */
    private String stationCharge;

    /**
     * 联系电话
     */
    private String stationPhone;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 小面额功能机具数
     */
    private Integer stationMachinesNum;

    /**
     * 人工现金柜台总数
     */
    private Integer stationCounterNum;

    /**
     * 存款取一体机CRS数量
     */
    private Integer stationCrsNum;

    /**
     * 硬币兑换机数量
     */
    private Integer coinExchangeMachinesNum;

    /**
     * 网点零钱包兑换数量
     */
    private Integer changeWalletsNum;

    /**
     * 网点零钱包兑换金额
     */
    private BigDecimal changeWalletsAmount;

    /**
     * 硬币兑换金额
     */
    private BigDecimal coinExchangeAmount;

    /**
     * 硬币兑换枚数
     */
    private Integer coinExchangeCount;

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
