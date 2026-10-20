package com.kx.system.domain.dto;

import com.kx.system.domain.customer.CustomerTrade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerTradeDTO implements Serializable {

    @ApiModelProperty("交易卡号")
    private Long tradeCardNum;

    @ApiModelProperty("交易记录")
    private List<CustomerTrade> tradeList;
}
