package com.kx.system.domain.filing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 建档客户移交审批表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-09
 */
@Data
@ApiModel(value = "建档客户移交审批表对象", description = "建档客户移交审批表")
public class FilingCustomerTransfer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "移交id", required = true)
    private Long id;


    @ApiModelProperty(value = "建档客户id")
    private Long filingId;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户类型 1-个人 2-企业 3-商户 4-其他")
    private String customerType;


    @ApiModelProperty(value = "客户证件号(身份证号/统一社会信用代码)")
    private String customerIdentity;


    @ApiModelProperty(value = "客户联系电话")
    private String customerPhone;


    @ApiModelProperty(value = "所属网格名称")
    private String belongGridName;


    @ApiModelProperty(value = "移交人名称")
    private String moveOutManagerName;


    @ApiModelProperty(value = "移交人工号")
    private String moveOutManagerCode;


    @ApiModelProperty(value = "移交人机构名称")
    private String moveOutOrgName;


    @ApiModelProperty(value = "移交人机构编码")
    private String moveOutOrgCode;


    @ApiModelProperty(value = "接收人名称")
    private String moveInManagerName;


    @ApiModelProperty(value = "接收人工号")
    private String moveInManagerCode;


    @ApiModelProperty(value = "接收人机构名称")
    private String moveInOrgName;


    @ApiModelProperty(value = "接收人机构编码")
    private String moveInOrgCode;


    @ApiModelProperty(value = "审批人名称")
    private String moveJudgeName;


    @ApiModelProperty(value = "审批人工号")
    private String moveJudgeCode;

    @ApiModelProperty(value = "审批机构")
    private Long moveJudgeDeptId;

    @ApiModelProperty(value = "审批机构名称")
    private String moveJudgeDeptName;

    @ApiModelProperty(value = "移交说明")
    private String moveManual;


    @ApiModelProperty(value = "审批意见")
    private String judgeIndicate;


    @ApiModelProperty(value = "移交状态 0-未审核 1-同意 2-驳回")
    private String moveStatus;


    @ApiModelProperty(value = "申请日期")
    private Date moveDate;


    @ApiModelProperty(value = "完成日期")
    private Date finishDate;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 类型
     */
    @TableField(exist = false)
    private Integer pageType;

    @ApiModelProperty(value = "近两年贷款通过率")
    private BigDecimal twoYearLoanPassRate;

    @ApiModelProperty(value = "上月正常收息率")
    private BigDecimal lastMonthNormalInteRate;

}
