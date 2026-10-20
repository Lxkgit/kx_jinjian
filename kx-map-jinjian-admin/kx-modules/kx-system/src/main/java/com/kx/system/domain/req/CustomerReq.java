package com.kx.system.domain.req;

import com.kx.system.domain.common.BasePageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerReq extends BasePageReq {

    @ApiModelProperty("客户类型（0：对私客户；1：对公客户；2：商户客户）")
    private Integer customerType;

    @ApiModelProperty("是否行内客户（0：否；1：是）")
    private Integer bankInterFlag;

    @ApiModelProperty("是否公共池（0：否；1：是）")
    private Integer commonPoolFlag;

    @ApiModelProperty("客户名称/商户名称/企业名称")
    private String customerName;

    @ApiModelProperty("客户内码")
    private String customerInterNum;

    @ApiModelProperty("所属网格id")
    private Long belongGrid;

    @ApiModelProperty("责任机构id")
    private Long dutyDeptId;

    @ApiModelProperty("建档状态（0：未建档；1：已建档）")
    private Integer filesStatus;

    @ApiModelProperty("客户类别")
    private Integer customerCategory;

    @ApiModelProperty("商户经营者")
    private String merchantManager;

    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    @ApiModelProperty("业务管户人")
    private String businessManageUser;

    @ApiModelProperty("企业法人")
    private String legalPerson;

    private Integer pageNum;

    private Integer pageSize;
}
