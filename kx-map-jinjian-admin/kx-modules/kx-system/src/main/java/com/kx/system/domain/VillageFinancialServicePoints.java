package com.kx.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 乡村金融服务点信息对象 village_financial_service_points
 *
 * @author zxy
 * @date 2024-11-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageFinancialServicePoints {
    private static final long serialVersionUID = 1L;

    /** 唯一标识，主键自增 */
    private Long id;

    /** 服务点名称 */
    @Excel(name = "服务点名称")
    private String servePointName;

    /** 服务点地址 */
    @Excel(name = "服务点地址")
    private String servePointAddr;

    /** 现金服务点责任人 */
    @Excel(name = "现金服务点责任人")
    private String dutyName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String dutyPhone;

    /** 所属网格编码 */
    @Excel(name = "所属网格编码")
    private String gridCode;

    /** 所属网格名称 */
    @Excel(name = "所属网格名称")
    private String gridName;

    /** 记录创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 记录最后一次修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录最后一次修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal lon;

    /** 维度 */
    @Excel(name = "维度")
    private BigDecimal lat;

}
