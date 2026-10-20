package com.kx.system.domain.sync;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SysManagerTransferReferIndex {
    //客户经理工号
    @CsvBindByPosition(position = 0)
    private String managerNum;
    //近两年贷款通过率
    @CsvBindByPosition(position = 1)
    private BigDecimal twoYearLoanPassRate;
    //上月正常收息率
    @CsvBindByPosition(position = 2)
    private BigDecimal lastMonthNormalInteRate;
}