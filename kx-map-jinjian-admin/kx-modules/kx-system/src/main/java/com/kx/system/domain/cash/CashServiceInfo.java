package com.kx.system.domain.cash;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description CashServiceInfo
 * @Author lxk
 * @CreateTime 2025-07-08
 */

@Data
public class CashServiceInfo {

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
     * 服务点编码
     */
    private String orgNum;

    /**
     * 现金服务点名称
     */
    private String serviceName;

    /**
     * 服务点类别（字典值，逗号分割）
     */
    private String serviceType;

    /**
     * 现金服务点地址
     */
    private String serviceAddr;

    /**
     * 选点位置
     */
    private String servicePoint;

    /**
     * 选点位置经度
     */
    private BigDecimal servicePointLon;

    /**
     * 选点位置维度
     */
    private BigDecimal servicePointLat;

    /**
     * 现金服务点责任人
     */
    private String serviceChargePerson;

    /**
     * 联系电话
     */
    private String serviceChargePhone;

    /**
     * 建设模式（字典值，逗号分割）
     */
    private Integer serviceBuildType;

    /**
     * 推进情况（字典值，逗号分割）
     */
    private Integer serviceBuildProgress;

    /**
     * 计划启用时间
     */
    private Date serviceEnableTime;

    /**
     * 服务模式（字典值，逗号分割）
     */
    private Integer serviceModel;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 维度
     */
    private BigDecimal lat;

    /**
     * 本期零钞兑换业务累计笔数
     */
    private Integer changeExchangeCountCurrent;

    /**
     * 本期零钞兑换业务累计金额
     */
    private BigDecimal changeExchangeAmountCurrent;

    /**
     * 零钞兑换业务累计笔数
     */
    private Integer changeExchangeCountTotal;

    /**
     * 零钞兑换业务累计金额
     */
    private BigDecimal changeExchangeAmountTotal;

    /**
     * 本期网点零钱包兑换数量
     */
    private Integer changeWalletsNumCurrent;

    /**
     * 本期网点零钱包兑换金额
     */
    private BigDecimal changeWalletsAmountCurrent;

    /**
     * 网点零钱包兑换数量
     */
    private Integer changeWalletsNumTotal;

    /**
     * 网点零钱包兑换金额
     */
    private BigDecimal changeWalletsAmountTotal;

    /**
     * 本期开展残币收兑累计金额
     */
    private BigDecimal brokenCoinAmountCurrent;

    /**
     * 开展残币收兑累计金额
     */
    private BigDecimal brokenCoinAmountTotal;

    /**
     * 是否布放小面额现金自助兑换设备 (1是 0否)
     */
    private Integer cashExchangeDeviceFlag;

    /**
     * (好的经验/存在问题)字段
     */
    private String serviceRemark;

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
