package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerDataDTO implements Serializable {

    @ApiModelProperty("总GDP")
    private BigDecimal gdp;

    @ApiModelProperty("负债GDP")
    private BigDecimal liabilitiesGDP;

    @ApiModelProperty("资产GDP")
    private BigDecimal propertyGDP;

    @ApiModelProperty("中收GDP")
    private BigDecimal midIncomeGDP;

    @ApiModelProperty("客户数据")
    private List<OtherData> dataList;

    @Data
    private static class OtherData implements Serializable{

        @ApiModelProperty("类型")
        private Integer type;

        @ApiModelProperty("金额")
        private BigDecimal amount;

        @ApiModelProperty("较上月，正的上升，负的下降")
        private BigDecimal compareMonth;

        @ApiModelProperty("较年初，正的上升，负的下降")
        private BigDecimal compareYear;
    }
}
