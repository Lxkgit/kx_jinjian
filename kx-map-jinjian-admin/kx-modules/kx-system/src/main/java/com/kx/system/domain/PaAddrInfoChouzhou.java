package com.kx.system.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 稠州网格统计数据
 *
 * @TableName pa_addr_info_chouzhou
 */
@Data
public class PaAddrInfoChouzhou implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 行政区划编码
     */
    private String code;

    /**
     * 行政区名称
     */
    private String name;

    /**
     * 父级行政区划编码
     */
    private String parentCode;

    /**
     * 是否被维护，1-已维护，0-未维护
     */
    private String isMaintained;

    /**
     * 所属网点编码
     */
    private String belongNetworkCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 信用村标志名
     */
    private String creditLevel;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 维度
     */
    private BigDecimal lat;

    /**
     * 别名/简称
     */
    private String anotherName;

    /**
     * 边界点集
     */
    private String boundaryPoints;

    /**
     * 中心点坐标
     */
    private String centerPoint;

    /**
     * 边界数据
     */
    private String coordinates;

    /**
     * 行政编码层级
     */
    private Integer addrLevel;

    /**
     * 边界类型 0行政 1产业园区 2小区 3商圈
     */
    private String type;

    /**
     *
     */
    private String etlDt;

    /**
     * 网格人数
     */
    private Integer gridPeopleNum;

    /**
     * 经济总量（万元）
     */
    private String gdp;

    /**
     * 小区数量
     */
    private Integer villageNum;

    /**
     * 小区人口
     */
    private Integer villagePeopleNum;

    /**
     * 工业园数量
     */
    private Integer industrialParkNum;

    /**
     * 企业数量
     */
    private Integer companyNum;

    /**
     * 金融机构数量
     */
    private Integer financialInstitutionNum;

    /**
     * 商圈数量
     */
    private Integer businessAreaNum;

    /**
     * SubList
     */
    private List<PaAddrInfoChouzhou> subList;
}