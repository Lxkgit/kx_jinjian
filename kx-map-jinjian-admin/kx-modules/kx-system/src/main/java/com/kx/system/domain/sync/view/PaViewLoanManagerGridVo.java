package com.kx.system.domain.sync.view;

import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class PaViewLoanManagerGridVo {

    @CsvBindByPosition(position = 2)
    private String loanFtp;

    @CsvBindByPosition(position = 3)
    private String managerCode;

    @CsvBindByPosition(position = 4)
    private String gridCode;

    @CsvBindByPosition(position = 5)
    private String customerType;

    @CsvCustomBindByPosition(position = 6, converter = CsvIntegerConvert.class)
    private Integer grantCreditNum;

    @CsvCustomBindByPosition(position = 7, converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareMonthNum;

    @CsvCustomBindByPosition(position = 8, converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareYearNum;

    @CsvCustomBindByPosition(position = 9, converter = CsvIntegerConvert.class)
    private Integer grantCreditGridInsideNum;

    @CsvBindByPosition(position = 10)
    private String grantCreditInsideNumRatio;

    @CsvCustomBindByPosition(position = 11, converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 12, converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareYearNum;

    @CsvCustomBindByPosition(position = 13, converter = CsvIntegerConvert.class)
    private Integer grantCreditGridOutsideNum;

    @CsvBindByPosition(position = 14)
    private String grantCreditOutsideNumRatio;

    @CsvCustomBindByPosition(position = 15, converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 16, converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareYearNum;

    @CsvCustomBindByPosition(position = 17, converter = CsvIntegerConvert.class)
    private Integer useCreditNum;

    @CsvCustomBindByPosition(position = 18, converter = CsvIntegerConvert.class)
    private Integer useCreditCompareMonthNum;

    @CsvCustomBindByPosition(position = 19, converter = CsvIntegerConvert.class)
    private Integer useCreditCompareYearNum;

    @CsvCustomBindByPosition(position = 20, converter = CsvIntegerConvert.class)
    private Integer useCreditGridInsideNum;

    @CsvBindByPosition(position = 21)
    private String useCreditInsideNumRatio;

    @CsvCustomBindByPosition(position = 22, converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 23, converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareYearNum;

    @CsvCustomBindByPosition(position = 24, converter = CsvIntegerConvert.class)
    private Integer useCreditGridOutsideNum;

    @CsvBindByPosition(position = 25)
    private String useCreditOutsideNumRatio;

    @CsvCustomBindByPosition(position = 26, converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 27, converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareYearNum;

    @CsvBindByPosition(position = 28)
    private String grantCreditBal;

    @CsvBindByPosition(position = 29)
    private String grantCreditCompareMonthBal;

    @CsvBindByPosition(position = 30)
    private String grantCreditCompareYearBal;

    @CsvBindByPosition(position = 31)
    private String grantCreditGridInsideBal;

    @CsvBindByPosition(position = 32)
    private String grantCreditInsideBalRatio;

    @CsvBindByPosition(position = 33)
    private String grantCreditInsideCompareMonthBal;

    @CsvBindByPosition(position = 34)
    private String grantCreditInsideCompareYearBal;

    @CsvBindByPosition(position = 35)
    private String grantCreditGridOutsideBal;

    @CsvBindByPosition(position = 36)
    private String grantCreditOutsideBalRatio;

    @CsvBindByPosition(position = 37)
    private String grantCreditOutsideCompareMonthBal;

    @CsvBindByPosition(position = 38)
    private String grantCreditOutsideCompareYearBal;

    @CsvBindByPosition(position = 39)
    private String useCreditBal;

    @CsvBindByPosition(position = 40)
    private String useCreditCompareMonthBal;

    @CsvBindByPosition(position = 41)
    private String useCreditCompareYearBal;

    @CsvBindByPosition(position = 42)
    private String useCreditGridInsideBal;

    @CsvBindByPosition(position = 43)
    private String useCreditInsideBalRatio;

    @CsvBindByPosition(position = 44)
    private String useCreditInsideCompareMonthBal;

    @CsvBindByPosition(position = 45)
    private String useCreditInsideCompareYearBal;

    @CsvBindByPosition(position = 46)
    private String useCreditGridOutsideBal;

    @CsvBindByPosition(position = 47)
    private String useCreditOutsideBalRatio;

    @CsvBindByPosition(position = 48)
    private String useCreditOutsideCompareMonthBal;

    @CsvBindByPosition(position = 49)
    private String useCreditOutsideCompareYearBal;

    @CsvBindByPosition(position = 50)
    private String totalBadBal;

    @CsvBindByPosition(position = 51)
    private String totalBadCompareMonthBal;

    @CsvBindByPosition(position = 52)
    private String totalBadCompareYearBal;

    @CsvBindByPosition(position = 53)
    private String totalBadRatio;

    @CsvBindByPosition(position = 54)
    private String totalBadGridInsideRatio;

    @CsvBindByPosition(position = 55)
    private String totalBadGridOutsideRatio;

    @CsvBindByPosition(position = 56)
    private String totalOverdueBal;

    @CsvBindByPosition(position = 57)
    private String totalOverdueCompareMonthBal;

    @CsvBindByPosition(position = 58)
    private String totalOverdueCompareYearBal;

    @CsvBindByPosition(position = 59)
    private String totalOverdueRatio;

    @CsvBindByPosition(position = 60)
    private String totalOverdueGridInsideRatio;

    @CsvBindByPosition(position = 61)
    private String totalOverdueGridOutsideRatio;

    @CsvBindByPosition(position = 62)
    private String totalInterestRatio;

    @CsvBindByPosition(position = 63)
    private String totalInterestGridInsideRatio;

    @CsvBindByPosition(position = 64)
    private String totalInterestGridOutsideRatio;

    @CsvCustomBindByPosition(position = 65, converter = CsvIntegerConvert.class)
    private Integer creditNum;

    @CsvBindByPosition(position = 66)
    private String creditBal;

    @CsvBindByPosition(position = 67)
    private String creditBadBal;

    @CsvBindByPosition(position = 68)
    private String creditBadRatio;

    @CsvBindByPosition(position = 69)
    private String creditOverdueBal;

    @CsvBindByPosition(position = 70)
    private String creditOverdueRatio;

    @CsvCustomBindByPosition(position = 71, converter = CsvIntegerConvert.class)
    private Integer ensureNum;

    @CsvBindByPosition(position = 72)
    private String ensureBal;

    @CsvBindByPosition(position = 73)
    private String ensureBadBal;

    @CsvBindByPosition(position = 74)
    private String ensureBadRatio;

    @CsvBindByPosition(position = 75)
    private String ensureOverdueBal;

    @CsvBindByPosition(position = 76)
    private String ensureOverdueRatio;

    @CsvCustomBindByPosition(position = 77, converter = CsvIntegerConvert.class)
    private Integer mortgageNum;

    @CsvBindByPosition(position = 78)
    private String mortgageBal;

    @CsvBindByPosition(position = 79)
    private String mortgageBadBal;

    @CsvBindByPosition(position = 80)
    private String mortgageBadRatio;

    @CsvBindByPosition(position = 81)
    private String mortgageOverdueBal;

    @CsvBindByPosition(position = 82)
    private String mortgageOverdueRatio;

    @CsvCustomBindByPosition(position = 83, converter = CsvIntegerConvert.class)
    private Integer pledgeNum;

    @CsvBindByPosition(position = 84)
    private String pledgeBal;

    @CsvBindByPosition(position = 85)
    private String pledgeBadBal;

    @CsvBindByPosition(position = 86)
    private String pledgeBadRatio;

    @CsvBindByPosition(position = 87)
    private String pledgeOverdueBal;

    @CsvBindByPosition(position = 88)
    private String pledgeOverdueRatio;

    @CsvCustomBindByPosition(position = 89, converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @CsvCustomBindByPosition(position = 90, converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
