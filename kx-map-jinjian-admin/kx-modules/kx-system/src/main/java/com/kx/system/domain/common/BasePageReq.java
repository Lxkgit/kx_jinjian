package com.kx.system.domain.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BasePageReq implements Serializable {

    @ApiModelProperty("页码")
    private Integer pageNum;

    @ApiModelProperty("页面大小")
    private Integer pageSize;
}
