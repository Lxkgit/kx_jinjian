package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 乡镇村对象 addr_info
 *
 * @author gary
 * @date 2021-11-25
 */
@Data
public class PaAddrInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;
    /**别名/简称*/
    private String anotherName;

    private String creditLevel;

    /**
     * 是否含有叶子节点
     */
    private Boolean isLeaf;


    /** 父级编码 */
    private String parentCode;

    /** 是否被维护 0-否 1-是 */
    private Integer isMaintained;
    private Boolean isManage = false;



    /** 所属网点id */
    private Long belongNetworkId;

    private String belongNetworkCode;
    /**边界点集*/
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
     * 网格层级
     */
    private Integer addrLevel;


    /** 经度 */
    private BigDecimal lon;

    /** 纬度 */
    private BigDecimal lat;

    /**
     * 边界类型 0行政 1产业园区 2小区
     */
    private String type;

    private List<PaAddrInfo> childList;
    private String ancestorCodes;
}
