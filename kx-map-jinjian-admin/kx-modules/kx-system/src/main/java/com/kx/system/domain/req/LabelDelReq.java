package com.kx.system.domain.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LabelDelReq implements Serializable {

    @ApiModelProperty("删除ids")
    private List<Long> ids;
}
