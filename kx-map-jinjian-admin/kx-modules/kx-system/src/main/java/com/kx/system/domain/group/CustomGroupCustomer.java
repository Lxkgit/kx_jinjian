package com.kx.system.domain.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 客群与客户对应关系表
 * @TableName custom_group_customer
 */
@TableName(value ="custom_group_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomGroupCustomer extends BaseEntity {
    /**
     * 客群客户关联表id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客群id
     */
    private Long customGroupId;

    /**
     * 客群对应客户id
     */
    private Long customerId;

    /**
     * 客群对应客户的ids
     */
    @TableField(exist = false)
    private List<Long> customerIds;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}