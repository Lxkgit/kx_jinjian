package com.kx.common.core.domain.branch.cash;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

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
     * sys_dept表org_num字段（服务点编码）
     */
    private String orgNum;

    /**
     * 现金服务点名称
     */
    private String serviceName;

    /**
     * 服务点类别
     */
    private Integer serviceType;

    /**
     * 现金服务点地址
     */
    private String serviceAddr;

    /**
     * 选点位置
     */
    private String servicePoint;

    /**
     * 现金服务点责任人
     */
    private String serviceChargePerson;

    /**
     * 联系电话
     */
    private String serviceChargePhone;

    /**
     * 建设模式（字典值）
     */
    private Integer serviceBuildType;

    /**
     * 推进情况（字典值）
     */
    private Integer serviceBuildProgress;

    /**
     * 计划启用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceEnableTime;

    /**
     * 服务模式
     */
    private String serviceModel;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 本期零钞兑换业务笔数-总笔数
     */
    private Integer changeCountAllCurrent;

    /**
     * 本期零钞兑换业务笔数-零钱包兑换个数
     */
    private Integer changeCountWalletCurrent;

    /**
     * 本期零钞兑换业务金额-总金额
     */
    private BigDecimal changeAmountAllCurrent;

    /**
     * 本期零钞兑换业务金额-零钱包兑换金额
     */
    private BigDecimal changeAmountWalletCurrent;

    /**
     * 零钞兑换业务累计笔数-总笔数
     */
    private Integer changeCountAllTotal;

    /**
     * 零钞兑换业务累计笔数-零钱包兑换个数
     */
    private Integer changeCountWalletTotal;

    /**
     * 零钞兑换业务累计金额-总金额
     */
    private BigDecimal changeAmountAllTotal;

    /**
     * 零钞兑换业务累计金额-零钱包兑换金额
     */
    private BigDecimal changeAmountWalletTotal;

    /**
     * 本期开展残币收兑累计金额
     */
    private BigDecimal brokenCoinAmountCurrent;

    /**
     * 开展残币收兑累计金额
     */
    private BigDecimal brokenCoinAmountTotal;

    /**
     * 是否布放小面额现金自助兑换设备（1是 0否）
     */
    private Integer cashExchangeDeviceFlag;

    /**
     * 好的经验/存在问题
     */
    private String serviceRemark;

    /**
     * 数据状态（0已删除 1正常）
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
