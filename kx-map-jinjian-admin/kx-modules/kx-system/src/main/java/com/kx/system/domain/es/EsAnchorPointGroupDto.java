package com.kx.system.domain.es;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class EsAnchorPointGroupDto {

    private Long id;
    private Long customerId;
    private BigDecimal lon;
    private BigDecimal lat;

    private String customType;
    private long userId;
    private long deptId;
    private String ancestorsDeptIds;
    private Long customGroupId;
    private String ancestorCodes;
    private String belongManagerCode;
}
