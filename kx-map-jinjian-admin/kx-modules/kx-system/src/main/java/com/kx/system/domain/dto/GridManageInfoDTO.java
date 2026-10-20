package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网点列表：管理网格信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GridManageInfoDTO {

    @ApiModelProperty("网格编码")
    private String gridCode;

    @ApiModelProperty("所属县")
    private String belongCounty;

    @ApiModelProperty("所属镇街")
    private String  belongTown;

    @ApiModelProperty("网格名称")
    private String  belongGrid;

    @ApiModelProperty("网点地址")
    private String  addr;

}
