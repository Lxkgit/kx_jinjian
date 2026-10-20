package com.kx.system.domain.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 客群分类
 * @TableName customer_group_category
 */
@TableName(value ="customer_group_category")
@Data
public class CustomerGroupCategory implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 上级id
     */
    private Long parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private List<CustomerGroupCategory> childCategoryList;

    private List<CustomGroup> childGroupList;
}