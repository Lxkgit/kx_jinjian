package com.kx.system.domain.dto;


import com.kx.system.domain.label.LabelGroup;
import com.kx.system.domain.label.LabelInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LabelGroupDTO {

    @ApiModelProperty("标签组id")
    private Long id;

    @ApiModelProperty("标签组名称")
    private String labelGroupName;

    @ApiModelProperty("上级分组id")
    private Long parentId;

    @ApiModelProperty("下级节点")
    private List<LabelGroup> child;

    @ApiModelProperty("子标签列表")
    private List<LabelInfo> childLabels;

    @ApiModelProperty("是否多选，1-多选，0-单选")
    private Integer isMultiple;

    @ApiModelProperty("标签类型，1-走访，0-网格")
    private Integer type;
}
