package com.kx.system.domain;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 浙江省银行网点信息表
 */
@Data
public class BankBranchInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 机构代码
     */
    @ApiModelProperty("机构代码")
    private String orgCode;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String orgName;

    /**
     * 银行类型（枚举值，见 BankTypeEnum；1=国家开发银行，4=中国工商银行，10=招商银行，35=农村商业银行 等）
     */
    @ApiModelProperty("银行类型（枚举code）")
    private Integer bankType;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String orgAddress;

    /**
     * 区域描述
     */
    @ApiModelProperty("区域描述")
    private String area;

    /**
     * 行政区划代码
     */
    @ApiModelProperty("行政区划代码")
    private String areaCode;

    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty("区/县")
    private String district;

    /**
     * 邮政编码
     */
    @ApiModelProperty("邮政编码")
    private String districtCode;

    /**
     * 发证日期
     */
    @ApiModelProperty("发证日期")
    private Date issueDate;

    /**
     * 批准日期
     */
    @ApiModelProperty("批准日期")
    private Date approvalDate;

    /**
     * 颁证机构
     */
    @ApiModelProperty("颁证机构")
    private String issueAuthority;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Long serial;

    /**
     * 业务范围
     */
    @ApiModelProperty("业务范围")
    private String scope;

    /**
     * 退出日期
     */
    @ApiModelProperty("退出日期")
    private Date exitDate;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private BigDecimal lat;
}