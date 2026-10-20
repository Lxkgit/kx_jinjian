package com.kx.system.domain.anchor;

import java.math.BigDecimal;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 楼宇锚点对象 building_anchor_point
 *
 * @author kangxu
 * @date 2024-01-23
 */
@Data
public class BuildingAnchorPoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 楼宇锚点id */
    private Long id;

    /** 楼宇类型 */
    @Excel(name = "楼宇类型")
    private Integer buildingType;

    /** 楼宇类型 */
    @Excel(name = "楼宇类型名称")
    private String buildingTypeName;

    /** 楼宇名称 */
    @Excel(name = "楼宇名称")
    private String buildingName;

    /** 省 */
    @Excel(name = "省")
    private String buildingProvince;

    /** 省 */
    @Excel(name = "省名称")
    private String buildingProvinceName;

    /** 市 */
    @Excel(name = "市")
    private String buildingCity;

    /** 市 */
    @Excel(name = "市名称")
    private String buildingCityName;

    /** 区 */
    @Excel(name = "区")
    private String buildingArea;

    /** 区 */
    @Excel(name = "区名称")
    private String buildingAreaName;

    /** 街道 */
    @Excel(name = "街道")
    private String buildingStreet;

    /** 街道 */
    @Excel(name = "街道名称")
    private String buildingStreetName;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String buildingAddress;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal lon;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal lat;

    /** 层数 */
    @Excel(name = "层数")
    private Integer floorNum;

    /** 单元数 */
    @Excel(name = "单元数")
    private Integer unitNum;

    /** 户数 */
    @Excel(name = "户数")
    private Integer householdNum;

    /** 归属网格 */
    @Excel(name = "归属网格")
    private Long belongGrid;

    /** 归属网格名称 */
    @Excel(name = "归属网格名称")
    private String belongGridName;

    /** 省市区级联编码 */
    @Excel(name = "省市区级联编码")
    private String networkCode;

    private Integer pageNum;
    private Integer pageSize;
}
