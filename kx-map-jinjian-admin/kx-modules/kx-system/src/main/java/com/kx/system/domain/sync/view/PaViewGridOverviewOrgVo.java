package com.kx.system.domain.sync.view;

import com.kx.system.util.convert.CsvIntegerConvert;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.util.Date;

@Data
public class PaViewGridOverviewOrgVo {

    @CsvBindByPosition(position = 2)
    private String orgCode;

    @CsvBindByPosition(position = 3)
    private String ftp;

    @CsvBindByPosition(position = 4)
    private String customerType;

    @CsvCustomBindByPosition(position = 5, converter = CsvIntegerConvert.class)
    private Integer levelFourGridNum;

    @CsvCustomBindByPosition(position = 6, converter = CsvIntegerConvert.class)
    private Integer levelFiveGridNum;

    @CsvCustomBindByPosition(position = 7, converter = CsvIntegerConvert.class)
    private Integer customerTotalNum;

    @CsvCustomBindByPosition(position = 8, converter = CsvIntegerConvert.class)
    private Integer performanceCustomerNum;

    @CsvCustomBindByPosition(position = 9, converter = CsvIntegerConvert.class)
    private Integer performanceInsideCustomerNum;

    @CsvBindByPosition(position = 10)
    private String performanceInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 11, converter = CsvIntegerConvert.class)
    private Integer performanceOutsideCustomerNum;

    @CsvBindByPosition(position = 12)
    private String performanceOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 13, converter = CsvIntegerConvert.class)
    private Integer creditCustomerNum;

    @CsvCustomBindByPosition(position = 14, converter = CsvIntegerConvert.class)
    private Integer creditInsideCustomerNum;

    @CsvBindByPosition(position = 15)
    private String creditInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 16, converter = CsvIntegerConvert.class)
    private Integer creditOutsideCustomerNum;

    @CsvBindByPosition(position = 17)
    private String creditOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 18, converter = CsvIntegerConvert.class)
    private Integer acquireCustomerNum;

    @CsvCustomBindByPosition(position = 19, converter = CsvIntegerConvert.class)
    private Integer acquireInsideCustomerNum;

    @CsvBindByPosition(position = 20)
    private String acquireInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 21, converter = CsvIntegerConvert.class)
    private Integer acquireOutsideCustomerNum;

    @CsvBindByPosition(position = 22)
    private String acquireOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 23, converter = CsvIntegerConvert.class)
    private Integer valueCustomerNum;

    @CsvCustomBindByPosition(position = 24, converter = CsvIntegerConvert.class)
    private Integer valueInsideCustomerNum;

    @CsvBindByPosition(position = 25)
    private String valueInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 26, converter = CsvIntegerConvert.class)
    private Integer valueOutsideCustomerNum;

    @CsvBindByPosition(position = 27)
    private String valueOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 28, converter = CsvIntegerConvert.class)
    private Integer recordCustomerNum;

    @CsvCustomBindByPosition(position = 29, converter = CsvIntegerConvert.class)
    private Integer recordInsideCustomerNum;

    @CsvBindByPosition(position = 30)
    private String recordInsideCustomerRatio;

    @CsvCustomBindByPosition(position = 31, converter = CsvIntegerConvert.class)
    private Integer recordOutsideCustomerNum;

    @CsvBindByPosition(position = 32)
    private String recordOutsideCustomerRatio;

    @CsvCustomBindByPosition(position = 33, converter = CsvIntegerConvert.class)
    private Integer gridPeopleNum;

    @CsvBindByPosition(position = 34)
    private String gdp;

    @CsvCustomBindByPosition(position = 35, converter = CsvIntegerConvert.class)
    private Integer villageNum;

    @CsvCustomBindByPosition(position = 36, converter = CsvIntegerConvert.class)
    private Integer villagePeopleNum;

    @CsvCustomBindByPosition(position = 37, converter = CsvIntegerConvert.class)
    private Integer industrialParkNum;

    @CsvCustomBindByPosition(position = 38, converter = CsvIntegerConvert.class)
    private Integer companyNum;

    @CsvCustomBindByPosition(position = 39, converter = CsvIntegerConvert.class)
    private Integer financialInstitutionNum;

    @CsvCustomBindByPosition(position = 40, converter = CsvIntegerConvert.class)
    private Integer businessAreaNum;

    @CsvCustomBindByPosition(position = 41, converter = CsvJavaUtilDateConvert.class)
    private Date createTime;

    @CsvCustomBindByPosition(position = 42, converter = CsvJavaUtilDateConvert.class)
    private Date updateTime;
}
