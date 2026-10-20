package com.kx.system.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 金融监管机构信息表
 */
@Data
public class FinSupervisionOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;

    /**
     * 类型代码
     */
    @ApiModelProperty("类型代码")
    private String typeCode;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal lat;

    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty("区/县")
    private String district;

    /**
     * 行政区划代码（冗余）
     */
    @ApiModelProperty("行政区划代码")
    private String areaCode;

    /**
     * 层级：1-省级 2-市级 3-区级
     */
    @ApiModelProperty("层级：1-省级 2-市级 3-区级")
    private Integer level;

    /**
     * 上级机构ID
     */
    @ApiModelProperty("上级机构ID")
    private Long parentId;

    /**
     * 上级机构名称（查询时关联返回）
     */
    @ApiModelProperty("上级机构名称")
    private String parentName;
}