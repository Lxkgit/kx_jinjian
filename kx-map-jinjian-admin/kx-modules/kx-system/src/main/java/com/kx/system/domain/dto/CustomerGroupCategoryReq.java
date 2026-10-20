package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerGroupCategoryReq implements Serializable {

    @ApiModelProperty("分类id，编辑传")
    private Long id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("上级id")
    private Long parentId;
}
