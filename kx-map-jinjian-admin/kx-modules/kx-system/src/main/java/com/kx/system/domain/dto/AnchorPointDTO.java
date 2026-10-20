package com.kx.system.domain.dto;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 锚点对象 anchor_point
 *
 * @author kangxu
 * @date 2024-04-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnchorPointDTO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    @Excel(name = "客户id")
    private Long customerId;

    @Excel(name = "客户类型 1-绩效客户 2-授信客户 3-建档客户")
    private String type;

    @Excel(name = "经度")
    private BigDecimal lon;

    @Excel(name = "纬度")
    private BigDecimal lat;

    @Excel(name = "归属网格")
    private String belongGrid;

    /**
     * 归属网格体系ID
     */
    private Integer belongGridType;

    private String belongGridName;

    private String belongOrgCode;

    private String belongOrgName;

    private String customerPhone;

    @Excel(name = "关联楼宇锚点id")
    private Long buildingId;

    @Excel(name = "地址类型")
    private Integer addressType;

    @Excel(name = "网格地址")
    private String customerAddress;

    /**
     * 省市区code集合字符串
     */
    private String addrDetail;

    private Integer addrLevel;

    /**
     * 地理信息数组
     */
    private List<String> addrInfo;

    @Excel(name = "锚点类型")
    private String anchorType;

    /** 锚点名称 */
    @Excel(name = "锚点名称")
    private String anchorName;

    /** 地址说明 */
    private String addressDesc;

    /**
     * 客户编码
     */
    private String customerNm;

    private String customerIdentity;

    private String gridManager;

    private String creditManager;

    private String creditManagerJobNumber;

    private List<Long> deptIds;

    private String branchBank;

    private String separateBank;

    private Integer anchorTypeCode;

    private List<String> belongGrids;

    private String identity;

    private String areaCode;

    private String belongManagerCode;

    private String belongManagerName;

    //Es查询 权限控制
    private JSONObject queryEsRole;

    private String ancestorCodes;
}
