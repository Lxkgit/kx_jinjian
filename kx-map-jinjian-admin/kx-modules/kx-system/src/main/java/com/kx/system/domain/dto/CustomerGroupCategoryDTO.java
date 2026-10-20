package com.kx.system.domain.dto;

import com.kx.system.domain.group.CustomerGroupCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerGroupCategoryDTO implements Serializable {

    private Long id;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("下级节点")
    private List<CustomerGroupCategory> child;
}
