package com.kx.system.domain.req;

import com.kx.system.domain.common.BasePageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomGroupPageReq extends BasePageReq implements Serializable {

    @ApiModelProperty("客群分类id")
    private Long categoryId;

    @ApiModelProperty("客户名称")
    private String customGroupName;

    @ApiModelProperty("客群分类父id")
    private Long parentId;

    @ApiModelProperty(hidden = true)
    private String userName;

    @ApiModelProperty(hidden = true)
    private String managerNum;

}
