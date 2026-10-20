package com.kx.system.domain.sync.view;

import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class PaViewLoanManagerVo {

    @CsvBindByPosition(position = 2)
    private String loanFtp;

    @CsvBindByPosition(position = 3)
    private String managerCode;

    @CsvBindByPosition(position = 4)
    private String customerType;

    @CsvCustomBindByPosition(position = 5, converter = CsvIntegerConvert.class)
    private Integer grantCreditNum;

    @CsvCustomBindByPosition(position = 6, converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareMonthNum;

    @CsvCustomBindByPosition(position = 7, converter = CsvIntegerConvert.class)
    private Integer grantCreditCompareYearNum;

    @CsvCustomBindByPosition(position = 8, converter = CsvIntegerConvert.class)
    private Integer grantCreditGridInsideNum;

    @CsvBindByPosition(position = 9)
    private String grantCreditInsideNumRatio;

    @CsvCustomBindByPosition(position = 10, converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 11, converter = CsvIntegerConvert.class)
    private Integer grantCreditInsideCompareYearNum;

    @CsvCustomBindByPosition(position = 12, converter = CsvIntegerConvert.class)
    private Integer grantCreditGridOutsideNum;

    @CsvBindByPosition(position = 13)
    private String grantCreditOutsideNumRatio;

    @CsvCustomBindByPosition(position = 14, converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 15, converter = CsvIntegerConvert.class)
    private Integer grantCreditOutsideCompareYearNum;

    @CsvCustomBindByPosition(position = 16, converter = CsvIntegerConvert.class)
    private Integer useCreditNum;

    @CsvCustomBindByPosition(position = 17, converter = CsvIntegerConvert.class)
    private Integer useCreditCompareMonthNum;

    @CsvCustomBindByPosition(position = 18, converter = CsvIntegerConvert.class)
    private Integer useCreditCompareYearNum;

    @CsvCustomBindByPosition(position = 19, converter = CsvIntegerConvert.class)
    private Integer useCreditGridInsideNum;

    @CsvBindByPosition(position = 20)
    private String useCreditInsideNumRatio;

    @CsvCustomBindByPosition(position = 21, converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 22, converter = CsvIntegerConvert.class)
    private Integer useCreditInsideCompareYearNum;

    @CsvCustomBindByPosition(position = 23, converter = CsvIntegerConvert.class)
    private Integer useCreditGridOutsideNum;

    @CsvBindByPosition(position = 24)
    private String useCreditOutsideNumRatio;

    @CsvCustomBindByPosition(position = 25, converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareMonthNum;

    @CsvCustomBindByPosition(position = 26, converter = CsvIntegerConvert.class)
    private Integer useCreditOutsideCompareYearNum;

    @CsvBindByPosition(position = 27)
    private String grantCreditBal;

    @CsvBindByPosition(position = 28)
    private String grantCreditCompareMonthBal;

    @CsvBindByPosition(position = 29)
    private String grantCreditCompareYearBal;

    @CsvBindByPosition(position = 30)
    private String grantCreditGridInsideBal;

    @CsvBindByPosition(position = 31)
    private String grantCreditInsideBalRatio;

    @CsvBindByPosition(position = 32)
    private String grantCreditInsideCompareMonthBal;

    @CsvBindByPosition(position = 33)
    private String grantCreditInsideCompareYearBal;

    @CsvBindByPosition(position = 34)
    private String grantCreditGridOutsideBal;

    @CsvBindByPosition(position = 35)
    private String grantCreditOutsideBalRatio;

    @CsvBindByPosition(position = 36)
    private String grantCreditOutsideCompareMonthBal;

    @CsvBindByPosition(position = 37)
    private String grantCreditOutsideCompareYearBal;

    @CsvBindByPosition(position = 38)
    private String useCreditBal;

    @CsvBindByPosition(position = 39)
    private String useCreditCompareMonthBal;

    @CsvBindByPosition(position = 40)
    private String useCreditCompareYearBal;

    @CsvBindByPosition(position = 41)
    private String useCreditGridInsideBal;

    @CsvBindByPosition(position = 42)
    private String useCreditInsideBalRatio;

    @CsvBindByPosition(position = 43)
    private String useCreditInsideCompareMonthBal;

    @CsvBindByPosition(position = 44)
    private String useCreditInsideCompareYearBal;

    @CsvBindByPosition(position = 45)
    private String useCreditGridOutsideBal;

    @CsvBindByPosition(position = 46)
    private String useCreditOutsideBalRatio;

    @CsvBindByPosition(position = 47)
    private String useCreditOutsideCompareMonthBal;

    @CsvBindByPosition(position = 48)
    private String useCreditOutsideCompareYearBal;

    @CsvBindByPosition(position = 49)
    private String totalBadBal;

    @CsvBindByPosition(position = 50)
    private String totalBadCompareMonthBal;

    @CsvBindByPosition(position = 51)
    private String totalBadCompareYearBal;

    @CsvBindByPosition(position = 52)
    private String totalBadRatio;

    @CsvBindByPosition(position = 53)
    private String totalBadGridInsideRatio;

    @CsvBindByPosition(position = 54)
    private String totalBadGridOutsideRatio;

    @CsvBindByPosition(position = 55)
    private String totalOverdueBal;

    @CsvBindByPosition(position = 56)
    private String totalOverdueCompareMonthBal;

    @CsvBindByPosition(position = 57)
    private String totalOverdueCompareYearBal;

    @CsvBindByPosition(position = 58)
    private String totalOverdueRatio;

    @CsvBindByPosition(position = 59)
    private String totalOverdueGridInsideRatio;

    @CsvBindByPosition(position = 60)
    private String totalOverdueGridOutsideRatio;

    @CsvBindByPosition(position = 61)
    private String totalInterestRatio;

    @CsvBindByPosition(position = 62)
    private String totalInterestGridInsideRatio;

    @CsvBindByPosition(position = 63)
    private String totalInterestGridOutsideRatio;

    @CsvCustomBindByPosition(position = 64, converter = CsvIntegerConvert.class)
    private Integer creditNum;

    @CsvBindByPosition(position = 65)
    private String creditBal;

    @CsvBindByPosition(position = 66)
    private String creditBadBal;

    @CsvBindByPosition(position = 67)
    private String creditBadRatio;

    @CsvBindByPosition(position = 68)
    private String creditOverdueBal;

    @CsvBindByPosition(position = 69)
    private String creditOverdueRatio;

    @CsvCustomBindByPosition(position = 70, converter = CsvIntegerConvert.class)
    private Integer ensureNum;

    @CsvBindByPosition(position = 71)
    private String ensureBal;

    @CsvBindByPosition(position = 72)
    private String ensureBadBal;

    @CsvBindByPosition(position = 73)
    private String ensureBadRatio;

    @CsvBindByPosition(position = 74)
    private String ensureOverdueBal;

    @CsvBindByPosition(position = 75)
    private String ensureOverdueRatio;

    @CsvCustomBindByPosition(position = 76, converter = CsvIntegerConvert.class)
    private Integer mortgageNum;

    @CsvBindByPosition(position = 77)
    private String mortgageBal;

    @CsvBindByPosition(position = 78)
    private String mortgageBadBal;

    @CsvBindByPosition(position = 79)
    private String mortgageBadRatio;

    @CsvBindByPosition(position = 80)
    private String mortgageOverdueBal;

    @CsvBindByPosition(position = 81)
    private String mortgageOverdueRatio;

    @CsvCustomBindByPosition(position = 82, converter = CsvIntegerConvert.class)
    private Integer pledgeNum;

    @CsvBindByPosition(position = 83)
    private String pledgeBal;

    @CsvBindByPosition(position = 84)
    private String pledgeBadBal;

    @CsvBindByPosition(position = 85)
    private String pledgeBadRatio;

    @CsvBindByPosition(position = 86)
    private String pledgeOverdueBal;

    @CsvBindByPosition(position = 87)
    private String pledgeOverdueRatio;

    @CsvCustomBindByPosition(position = 88, converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @CsvCustomBindByPosition(position = 89, converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
