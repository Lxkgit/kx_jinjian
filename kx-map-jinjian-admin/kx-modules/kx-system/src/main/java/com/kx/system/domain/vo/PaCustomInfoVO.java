package com.kx.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jy
 * @Date: Created in 16:44 2021/12/2
 */
@Data
public class PaCustomInfoVO {

    /**
     * 客户与锚点关联ID
     */
    @ApiModelProperty(name = "客户与锚点关联ID")
    private Long relationId;

    /**
     * 客户锚点主键ID
     */
    @ApiModelProperty(name = "客户锚点主键ID")
    private Long clientAnchorId;

    /**
     * 客户名称
     */
    @ApiModelProperty(name = "客户名称")
    private String name;

    /**
     * 客户内码
     */
    @ApiModelProperty(name = "客户内码")
    private String nm;

    /**
     * 身份证号
     */
    @ApiModelProperty(name = "身份证号")
    private String identify;

    /**
     * 所属区域
     */
    @ApiModelProperty(name = "所属区域")
    private String belongArea;

    /**
     * 地址
     */
    @ApiModelProperty(name = "地址")
    private String addressArea;

    /**
     * 工作单位
     */
    @ApiModelProperty(name = "所属支行")
    private String work;

    /**
     * 经营商户
     */
    @ApiModelProperty(name = "锚点id")
    private String  ownBusinessName;
}
