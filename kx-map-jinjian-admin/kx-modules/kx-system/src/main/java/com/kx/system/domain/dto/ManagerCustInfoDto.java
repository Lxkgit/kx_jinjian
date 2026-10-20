package com.kx.system.domain.dto;

import lombok.Data;

/**
 * 基于客户经理汇总客户信息
 *
 * @author huhao
 * Date: 2021-12-17
 */
@Data
public class ManagerCustInfoDto {
    private String jobNumber;
    private int clientSum;
    private int increClientThisYear;
    private int increClientThisMonth;
    private int increClientThisDay;
}
