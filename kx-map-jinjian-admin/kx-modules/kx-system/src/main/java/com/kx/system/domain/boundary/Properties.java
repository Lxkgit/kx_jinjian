package com.kx.system.domain.boundary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Properties implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "网格编码")
    private String code;

    @ApiModelProperty(name = "网格名称")
    private String name;

    @ApiModelProperty(name = "中心点位")
    private Object center;

    @ApiModelProperty(name = "网格层级")
    private Integer level;

    @ApiModelProperty(name = "父网格编码")
    private String parentCode;

    @ApiModelProperty(name = "当前网格用信人数")
    private Integer gridCreditNum;

    @ApiModelProperty(name = "下辖网格用信最高人数")
    private Integer bestNum;

    @ApiModelProperty(value = "是否现金服务重点网格  0-否 1-是")
    private String isCash;

    private String type;
}
