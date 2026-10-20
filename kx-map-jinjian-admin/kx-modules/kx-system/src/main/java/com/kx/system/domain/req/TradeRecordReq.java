package com.kx.system.domain.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TradeRecordReq implements Serializable {

    @ApiModelProperty("客户id")
    private Long customerId;

    @ApiModelProperty("交易记录查询开始时间")
    private Date startDate;

    @ApiModelProperty("交易记录查询结束时间")
    private Date endDate;

    @ApiModelProperty("交易卡号")
    private Long tradeCardNum;
}
