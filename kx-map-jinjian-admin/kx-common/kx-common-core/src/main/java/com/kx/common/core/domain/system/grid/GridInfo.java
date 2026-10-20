package com.kx.common.core.domain.system.grid;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 网格信息
 * @Author lxk
 * @CreateTime 2025-07-17
 */

@Data
public class GridInfo {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 是否重点网格 1是 0否
     */
    private Integer keyGridFlag;

    /**
     * 银行网格长
     */
    private String gridBankCharge;

    /**
     * 银行网格长联系电话
     */
    private String gridBankChargePhone;

    /**
     * 银行网格员
     */
    private String gridBankMember;

    /**
     * 银行网格员联系电话
     */
    private String gridBankMemberPhone;

    /**
     * 网格责任网点
     */
    private String gridBranchCharge;

    /**
     * 网格内摸排商户数
     */
    private Integer merchantNum;

    /**
     * 网格内现金收付摸排次数
     */
    private Integer cashNum;

    /**
     * 网格内宣传次数
     */
    private Integer publicityNum;

    /**
     * 宣传受众人数（万人）
     */
    private Integer publicityPersonNum;

    /**
     * 网格内大篷车、小钱箱等现金服务次数
     */
    private Integer cashServiceNum;

    /**
     * 发现拒收现金线索数量
     */
    private Integer refuseCashNum;

    /**
     * 网格内小面额兑出数量
     */
    private Integer cashExchangeNum;

    /**
     * 网格内小面额兑出金额
     */
    private BigDecimal cashExchangeAmount;

    /**
     * 残损币收兑数量
     */
    private Integer brokenCoinCount;

    /**
     * 残损币收兑金额
     */
    private BigDecimal brokenCoinAmount;

    /**
     * 文章删除标识 0已删除 1 正常
     */
    private Integer delFlag;

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
