package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerLabelReq implements Serializable {

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("新增标签id")
    private List<Long> addLabelIds;

    @ApiModelProperty("删除标签id")
    private List<Long> delLabelIds;
}
