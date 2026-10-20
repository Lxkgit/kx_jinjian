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
 * 客户持有产品详情
 * @TableName customer_product_detail
 */
@TableName(value ="customer_product_detail")
@Data
public class CustomerProductDetail implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 持有产品人id
     */
    private Long holderId;

    /**
     * 产品种类（0：负债类；1：资产类；2：产品类）
     */
    private Integer productType;

    /**
     * 持有产品id
     */
    private Long productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 金额
     */
    private BigDecimal handleAmount;

    /**
     * 办理时间
     */
    private Date handleTime;

    /**
     * 到期时间
     */
    private Date endTime;

    /**
     * 负责人id
     */
    private Long manager;

    /**
     * 起息日
     */
    private Date interestDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}