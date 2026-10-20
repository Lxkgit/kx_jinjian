package com.kx.system.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaCompanyInfoVO {
    String nm;
    String outlet;
    String outletCode;
    String township;
    String area;
    String managerId;
    String type;
    String areaCode;
    BigDecimal lon;
    BigDecimal lat;
    Integer delFlag;
    String name;
}
