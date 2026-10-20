package com.kx.system.domain.dto;

import com.kx.system.domain.customer.CustomerInfo;
import com.kx.system.domain.customer.CustomerLabel;
import com.kx.system.domain.customer.CustomerProductDetail;
import com.kx.system.domain.customer.CustomerTrade;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerInfoDetailDTO implements Serializable {

    @ApiModelProperty("客户基本信息")
    private CustomerInfo customerInfo;

    @ApiModelProperty("客户产品持有情况")
    private List<CustomerProductDetail> holdDetail;

    @ApiModelProperty("客户标签")
    private List<CustomerLabelDTO> labels;

    @ApiModelProperty("产品到期一览表")
    private List<CustomerProductDetail> endProductList;

    @ApiModelProperty("交易记录查询")
    private List<CustomerTradeDTO> customerTradeList;

    @ApiModelProperty("客户数据")
    private CustomerDataDTO customerDataDTO;
}
