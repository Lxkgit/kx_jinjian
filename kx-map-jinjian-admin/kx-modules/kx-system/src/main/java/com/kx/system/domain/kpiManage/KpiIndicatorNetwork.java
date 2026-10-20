package com.kx.system.domain.kpiManage;

import com.kx.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 指标和机构关联对象 kpi_indicator_network
 * 
 * @author xifan_h
 * @date 2022-04-12
 */
@Data
public class KpiIndicatorNetwork
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 指标id */
    @Excel(name = "指标id")
    private Long indicatorId;

    /** 机构id */
    @Excel(name = "机构id")
    private Long networkId;

    private Date createTime;

    private Date updateTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("indicatorId", getIndicatorId())
            .append("networkId", getNetworkId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
