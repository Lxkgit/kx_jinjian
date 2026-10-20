package com.kx.system.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author huhao
 * Date: 2021-12-21
 */
@Data
public class MangerIncrTrendsDto {
    private String jobNumber;
    private BigDecimal month1Value;
    private BigDecimal month2Value;
    private BigDecimal month3Value;
    private BigDecimal month4Value;
    private BigDecimal month5Value;
    private BigDecimal month6Value;
}
