package com.kx.system.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 客户旅程统一返回字段
 */
@Data
public class CustomerJourneyVO {
    //客户旅程时间
    private Date travelTime;

    private String firstField;

    private String secondField;

    private Integer type;
}
