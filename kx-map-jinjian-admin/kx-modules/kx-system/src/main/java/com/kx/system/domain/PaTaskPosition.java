package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.system.util.convert.CsvJavaUtilDateConvert;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 务时空轨迹记录
 */
@Data
public class PaTaskPosition {

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户经理工号
     */
    @CsvBindByPosition(position = 0)
    private String managerNum;

    /**
     * 经度
     */
    @CsvBindByPosition(position = 1)
    private BigDecimal lon;

    /**
     * 维度
     */
    @CsvBindByPosition(position = 2)
    private BigDecimal lat;

    /**
     * 时间
     */
    @CsvCustomBindByPosition(position = 3, converter = CsvJavaUtilDateConvert.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
