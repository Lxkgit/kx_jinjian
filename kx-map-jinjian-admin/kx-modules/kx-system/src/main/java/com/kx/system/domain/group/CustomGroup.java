package com.kx.system.domain.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 客群
 * @TableName custom_group
 */
@TableName(value ="custom_group")
@Data
public class CustomGroup implements Serializable {
    /**
     * 客群id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客群类型（0：对私客户；1：对公客户；2：商户客户）
     */
    private Integer customType;

    /**
     * 批量删除删除ids
     */
    private List<Long> ids;

    /**
     * 客群分类id
     */
    private Long categoryId;

    /**
     * 客群编号
     */
    private String customGroupCode;

    /**
     * 客群名称
     */
    private String customGroupName;

    /**
     * 客户成员id集合
     */
    private String customMember;

    /**
     * 客户数量
     */
    private Integer customNum;

    /**
     * 客群来源
     */
    private String customGroupSource;

    /**
     * 说明
     */
    private String groupDesc;

    /**
     * 客群标签
     */
    private String labelIds;

    /**
     * 是否自动更新0：否；1：是
     */
    private Integer updateFlag;

    /**
     * 更新周期
     */
    private Integer updateCycle;

    /**
     * 状态（0：待生成；1：已生成）
     */
    private Integer groupStatus;

    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建人工号
     */
    private String createNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0：正常；1：删除）
     */
    private Integer delFlag;

    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}