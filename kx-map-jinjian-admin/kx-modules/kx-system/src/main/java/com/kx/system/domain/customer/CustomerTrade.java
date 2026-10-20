package com.kx.system.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 商户交易金额表
 * @TableName customer_trade
 */
@TableName(value ="customer_trade")
@Data
public class CustomerTrade implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 交易年份
     */
    private String tradeYear;

    /**
     * 交易月份
     */
    private String tradeMonth;

    /**
     * 交易日
     */
    private String tradeDay;

    /**
     * 交易卡号
     */
    private Long tradeCardNum;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}