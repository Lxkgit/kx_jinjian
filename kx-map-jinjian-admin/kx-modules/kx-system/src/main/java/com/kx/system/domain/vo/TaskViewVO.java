package com.kx.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskViewVO {
    //客户id
    private Long clientId;
    //客户类型
    private String taskObjectId;
    private BigDecimal lon;
    private BigDecimal lat;
}
