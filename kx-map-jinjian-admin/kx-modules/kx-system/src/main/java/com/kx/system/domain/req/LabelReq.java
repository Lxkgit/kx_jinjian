package com.kx.system.domain.req;

import com.kx.system.domain.common.BasePageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LabelReq extends BasePageReq {

    @ApiModelProperty("标签名称")
    private String labelName;

    @ApiModelProperty("标签分组id")
    private Long labelGroupId;

    private Integer pageNum;

    private Integer pageSize;
}
