package com.kx.system.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户标签
 * @TableName customer_label
 */
@TableName(value ="customer_label")
@Data
public class CustomerLabel implements Serializable {
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
     * 标签id
     */
    private Long labelId;

    /**
     * 标签种类（0：网格标签；1：系统标签）
     */
    private Integer labelType;

    /**
     * 客户类型
     */
    private Integer type;

    /**
     * 客户编码
     */
    private String customerNm;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}