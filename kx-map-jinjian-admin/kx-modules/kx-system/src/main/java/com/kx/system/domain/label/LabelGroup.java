package com.kx.system.domain.label;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签分组表
 * @TableName label_group
 */
@TableName(value ="label_group")
@Data
public class LabelGroup implements Serializable {
    /**
     * 标签组id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签组名称
     */
    private String labelGroupName;

    /**
     * 上级分组id
     */
    private Long parentId;

    /**
     * 删除标志（0：正常；1：删除）
     */
    private Integer delFlag;

    private Integer isMultiple;

    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}