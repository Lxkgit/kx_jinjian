package com.kx.system.domain.sync.view;

import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class PaViewGridOverviewOrgGridVo {

    @CsvBindByPosition(position = 2)
    private String orgCode;

    @CsvBindByPosition(position = 3)
    private String gridCode;

    @CsvBindByPosition(position = 4)
    private String ftp;

    @CsvBindByPosition(position = 5)
    private String customerType;

    @CsvCustomBindByPosition(position = 6, converter = CsvIntegerConvert.class)
    private Integer levelFourGridNum;

    @CsvCustomBindByPosition(position = 7, converter = CsvIntegerConvert.class)
    private Integer levelFiveGridNum;

    @CsvCustomBindByPosition(position = 8, converter = CsvIntegerConvert.class)
    private Integer customerTotalNum;

    @CsvCustomBindByPosition(position = 9, converter = CsvIntegerConvert.class)
    private Integer performanceCustomerNum;

    @CsvCustomBindByPosition(position = 10, converter = CsvIntegerConvert.class)
    private Integer performanceInsideCustomerNum;

    @CsvBindByPosition(position = 11)
    private String performanceInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 12, converter = CsvIntegerConvert.class)
    private Integer performanceOutsideCustomerNum;

    @CsvBindByPosition(position = 13)
    private String performanceOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 14, converter = CsvIntegerConvert.class)
    private Integer creditCustomerNum;

    @CsvCustomBindByPosition(position = 15, converter = CsvIntegerConvert.class)
    private Integer creditInsideCustomerNum;

    @CsvBindByPosition(position = 16)
    private String creditInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 17, converter = CsvIntegerConvert.class)
    private Integer creditOutsideCustomerNum;

    @CsvBindByPosition(position = 18)
    private String creditOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 19, converter = CsvIntegerConvert.class)
    private Integer acquireCustomerNum;

    @CsvCustomBindByPosition(position = 20, converter = CsvIntegerConvert.class)
    private Integer acquireInsideCustomerNum;

    @CsvBindByPosition(position = 21)
    private String acquireInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 22, converter = CsvIntegerConvert.class)
    private Integer acquireOutsideCustomerNum;

    @CsvBindByPosition(position = 23)
    private String acquireOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 24, converter = CsvIntegerConvert.class)
    private Integer valueCustomerNum;

    @CsvCustomBindByPosition(position = 25, converter = CsvIntegerConvert.class)
    private Integer valueInsideCustomerNum;

    @CsvBindByPosition(position = 26)
    private String valueInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 27, converter = CsvIntegerConvert.class)
    private Integer valueOutsideCustomerNum;

    @CsvBindByPosition(position = 28)
    private String valueOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 29, converter = CsvIntegerConvert.class)
    private Integer recordCustomerNum;

    @CsvCustomBindByPosition(position = 30, converter = CsvIntegerConvert.class)
    private Integer recordInsideCustomerNum;

    @CsvBindByPosition(position = 31)
    private String recordInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 32, converter = CsvIntegerConvert.class)
    private Integer recordOutsideCustomerNum;

    @CsvBindByPosition(position = 33)
    private String recordOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 34, converter = CsvIntegerConvert.class)
    private Integer gridPeopleNum;

    @CsvBindByPosition(position = 35)
    private String gdp;

    @CsvCustomBindByPosition(position = 36, converter = CsvIntegerConvert.class)
    private Integer villageNum;

    @CsvCustomBindByPosition(position = 37, converter = CsvIntegerConvert.class)
    private Integer villagePeopleNum;

    @CsvCustomBindByPosition(position = 38, converter = CsvIntegerConvert.class)
    private Integer industrialParkNum;

    @CsvCustomBindByPosition(position = 39, converter = CsvIntegerConvert.class)
    private Integer companyNum;

    @CsvCustomBindByPosition(position = 40, converter = CsvIntegerConvert.class)
    private Integer financialInstitutionNum;

    @CsvCustomBindByPosition(position = 41, converter = CsvIntegerConvert.class)
    private Integer businessAreaNum;

    @CsvCustomBindByPosition(position = 42, converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @CsvCustomBindByPosition(position = 43, converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
