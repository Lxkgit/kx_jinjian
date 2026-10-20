package com.kx.system.domain.dto;

import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zxy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VillageFinancialServicePointsQuery extends BaseEntity {

    @ApiModelProperty(value = "服务点名称")
    private String servePointName;

    @ApiModelProperty(value = "所属网格编码集合")
    private List<String> belongGridCodeList;
}
