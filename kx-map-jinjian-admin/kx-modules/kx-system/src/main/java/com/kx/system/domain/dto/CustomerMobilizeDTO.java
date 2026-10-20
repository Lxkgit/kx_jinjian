package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerMobilizeDTO implements Serializable {

    @ApiModelProperty("调动客户id集合")
    private List<Long> customerIds;

    @ApiModelProperty("所属网格id：网格调动")
    private Integer belongGrid;

    @ApiModelProperty("责任机构id（调回公共池）")
    private Integer dutyDeptId;
}
