package com.kx.system.domain.dto;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @author lyk
 */
@Data
public class MainBusinessData {

    /**
     * 一般存款余额
     */
    private BigDecimal generalBalance = new BigDecimal(0);
    /**
     * 一般贷款余额
     */
    private BigDecimal generalLoanBalance = new BigDecimal(0);
    /**
     * 对私贷款户数
     */
    private Integer priNum = 0;
    /**
     * 对公贷款户数
     */
    private Integer pubNum = 0;
    /**
     * 不良贷款额
     */
    private BigDecimal badLoan = new BigDecimal(0);
    /**
     * 低收入农户贷款户数
     */
    private Integer lowIncomeFramers = 0;
    /**
     * 低收入农户贷款余额
     */
    private BigDecimal lowIncomeLb = new BigDecimal(0);


}
