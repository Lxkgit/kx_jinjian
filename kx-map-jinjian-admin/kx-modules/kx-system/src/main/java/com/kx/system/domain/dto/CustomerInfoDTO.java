package com.kx.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerInfoDTO implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    /**
     * 客户类型（0：对私客户；1：对公客户；2：商户客户）
     */
    @ApiModelProperty("客户类型（0：对私客户；1：对公客户；2：商户客户）")
    private Integer customerType;

    /**
     * 是否行内客户（0：否；1：是）
     */
    @ApiModelProperty("是否行内客户（0：否；1：是）")
    private Integer bankInterFlag;

    /**
     * 客户名称
     */
    @ApiModelProperty("客户名称")
    private String customerName;

    /**
     * 客户类别
     */
    @ApiModelProperty("客户类别")
    private Integer customerCategory;

    @ApiModelProperty("客户类别")
    private String customerCategoryDesc;

    /**
     * 证件类型
     */
    @ApiModelProperty("证件类型")
    private Integer cardType;

    @ApiModelProperty("证件类型")
    private String cardTypeDesc;

    /**
     * 证件号码
     */
    @ApiModelProperty("证件号码")
    private String cardNum;

    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String contactPhone;

    /**
     * 客户内码
     */
    @ApiModelProperty("客户内码")
    private String customerInterNum;

    /**
     * 客户地址
     */
    @ApiModelProperty("客户地址")
    private String customerAddress;

    /**
     * 所属网格id
     */
    @ApiModelProperty("所属网格id")
    private Integer belongGrid;

    @ApiModelProperty("所属网格")
    private String belongGridDesc;

    @ApiModelProperty("责任部门id")
    private Integer dutyDeptId;

    @ApiModelProperty("责任部门")
    private String dutyDeptDesc;

    @ApiModelProperty("网格员")
    private Integer gridUserId;

    @ApiModelProperty("网格员")
    private String gridUserDesc;

    @ApiModelProperty("业务管户人")
    private String businessManageUser;

    @ApiModelProperty("业务管户人")
    private String businessManageUserDesc;

    /**
     * 建档状态（0：未建档；1：已建档）
     */
    @ApiModelProperty("建档状态（0：未建档；1：已建档）")
    private Integer filesStatus;

    @ApiModelProperty("建档状态")
    private String filesStatusDesc;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    /**
     * 商户经营者
     */
    @ApiModelProperty("商户经营者")
    private String merchantManager;

    /**
     * 企业法人
     */
    @ApiModelProperty("企业法人")
    private String legalPerson;

    /**
     * 企业法人联系方式
     */
    @ApiModelProperty("企业法人联系方式")
    private String legalPersonPhone;

    /**
     * 法人身份证号码
     */
    @ApiModelProperty("法人身份证号码")
    private String legalPersonCardNum;


    @ApiModelProperty("性别")
    private Integer customerSex;

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer customerAge;

    /**
     * 职业
     */
    @ApiModelProperty("职业")
    private String career;

    /**
     * 是否户主（0：否；1：是）
     */
    @ApiModelProperty("是否户主（0：否；1：是）")
    private Integer householderFlag;

    /**
     * 婚姻状况
     */
    @ApiModelProperty("婚姻状况")
    private Integer marriageFlag;

    /**
     * 是否新市民（0：否；1：是）
     */
    @ApiModelProperty("是否新市民（0：否；1：是）")
    private Integer citizensFlag;

    /**
     * 工作单位
     */
    @ApiModelProperty("工作单位")
    private String workAddress;

    /**
     * 营业执照
     */
    @ApiModelProperty("营业执照")
    private String businessLicenseImage;

}
