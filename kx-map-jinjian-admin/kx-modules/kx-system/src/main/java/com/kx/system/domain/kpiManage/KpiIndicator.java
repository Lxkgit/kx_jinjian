package com.kx.system.domain.kpiManage;

import com.kx.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 考核指标对象 kpi_indicator
 * 
 * @author xifan_h
 * @date 2022-04-29
 */
@Data
public class KpiIndicator
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 父id */
    @Excel(name = "父id")
    private Long parentId;

    /** 指标名称 */
    @Excel(name = "指标名称")
    private String name;

    /** 是否启用 1-启用 0-关闭 */
    @Excel(name = "是否启用 1-启用 0-关闭")
    private Integer enable;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String type;

    /** 计算方式 SYS-系统计算 ART-人工填写 */
    @Excel(name = "计算方式 SYS-系统计算 ART-人工填写")
    private String calType;

    /** 计算表达式, 变量使用${field_id}匹配 */
    @Excel(name = "计算表达式, 变量使用${field_id}匹配")
    private String calExpression;

    /** 计算表达式前端格式存储 */
    @Excel(name = "计算表达式前端格式存储")
    private String calExpressionFront;

    /** 创建人用户id */
    @Excel(name = "创建人用户id")
    private Long createUserId;

    /** 删除标记 1-删除 */
    private Integer delFlag;

    private String remark;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("name", getName())
            .append("enable", getEnable())
            .append("type", getType())
            .append("remark", getRemark())
            .append("calType", getCalType())
            .append("calExpression", getCalExpression())
            .append("calExpressionFront", getCalExpressionFront())
            .append("createUserId", getCreateUserId())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
