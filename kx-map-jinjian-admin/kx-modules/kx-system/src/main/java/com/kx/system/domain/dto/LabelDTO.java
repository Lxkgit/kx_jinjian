package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
public class LabelDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("标签名称")
    private String labelName;

    @ApiModelProperty("标签组id")
    private Long labelGroupId;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("拼接标签组名称")
    private String labelGroupName;

    @ApiModelProperty("是否多选")
    private Integer isMultiple;
}
