package com.kx.system.domain.sync.view;

import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class PaViewDepositModuleManagerVo {

    @CsvBindByPosition(position = 2)
    private String managerCode;

    @CsvBindByPosition(position = 3)
    private String customerType;

    @CsvBindByPosition(position = 4)
    private String saveFtp;

    @CsvBindByPosition(position = 5)
    private String saveBalance;

    @CsvBindByPosition(position = 6)
    private String saveBalanceLastMonth;

    @CsvBindByPosition(position = 7)
    private String saveBalanceBeginYear;

    @CsvBindByPosition(position = 8)
    private String saveDayAvg;

    @CsvBindByPosition(position = 9)
    private String saveDayAvgLastMonth;

    @CsvBindByPosition(position = 10)
    private String saveDayAvgBeginYear;

    @CsvBindByPosition(position = 11)
    private String depositSaveBalance;

    @CsvBindByPosition(position = 12)
    private String depositSaveBalanceLastMonth;

    @CsvBindByPosition(position = 13)
    private String depositSaveBalanceBeginYear;

    @CsvBindByPosition(position = 14)
    private String depositSaveDayAvg;

    @CsvBindByPosition(position = 15)
    private String depositSaveDayAvgLastMonth;

    @CsvBindByPosition(position = 16)
    private String depositSaveDayAvgBeginYear;

    @CsvCustomBindByPosition(position = 17, converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @CsvCustomBindByPosition(position = 18, converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
