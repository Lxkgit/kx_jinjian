package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerLabelDTO implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("标签id")
    private Integer labelId;

    @ApiModelProperty("标签种类（0：基础标签；1：网络标签）")
    private Integer labelType;

    @ApiModelProperty("标签名称")
    private String labelName;


}
