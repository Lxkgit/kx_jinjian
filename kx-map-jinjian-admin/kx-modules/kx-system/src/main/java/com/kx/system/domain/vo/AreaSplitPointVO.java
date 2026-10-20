package com.kx.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 行政区划垂直分割点位VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaSplitPointVO {

    @ApiModelProperty("行政区划编码")
    private String code;

    @ApiModelProperty("点位类型：1-左区域中心点，2-右区域中心点")
    private Integer pointType;

    @ApiModelProperty("经度")
    private Double lon;

    @ApiModelProperty("纬度")
    private Double lat;
}