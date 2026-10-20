package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CustomerUpdateInfoReq implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("性别")
    private Integer customerSex;

    @ApiModelProperty("婚姻状况")
    private Integer marriageFlag;

    @ApiModelProperty("职业")
    private String career;

    @ApiModelProperty("身份证号")
    private String cardNum;

    @ApiModelProperty("联系方式")
    private String contactPhone;

    @ApiModelProperty("客户地址/商户地址/企业地址")
    private String customerAddress;

    @ApiModelProperty("工作单位")
    private String workAddress;

    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    @ApiModelProperty("商户经营者")
    private String merchantManager;

    @ApiModelProperty("经营场所类型")
    private Integer businessPlaceType;

    @ApiModelProperty("年收入/近一年收入/近一年累计营业收入")
    private BigDecimal incomeYear;

    @ApiModelProperty("法人代表")
    private String legalPerson;

    @ApiModelProperty("企业法人联系方式")
    private String legalPersonPhone;

    @ApiModelProperty("法人身份证号码")
    private String legalPersonCardNum;

    @ApiModelProperty("注册资本")
    private String registerCapital;

    private Integer filesStatus;
}
