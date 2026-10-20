package com.kx.system.domain.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import com.kx.system.domain.filing.FilingMessageDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author kangxu
 * @date 2024/4/1
 */
@Data
public class CustomGroupDTO extends BaseEntity {
    /**
     * 客群id
     */
    private Long customGroupId;

    /**
     * 客群对应客户的ids
     */
    private List<Long> customerIds;

    /** 客户类型 个人、企业、个体工商*/
    private Integer customType;

    /** 客户类型 0-绩效、1-授信、2-收单*/
    private Integer type;

    /**
     * 客群名称
     */
    private String customGroupName;

    /**
     * 客群分类id
     */
    private Long categoryId;

    /**
     * 客群来源
     */
    private String customGroupSource;

    /**
     * 客群标签
     */
    private List<Long> labelIds;

    /**
     * 说明
     */
    private String groupDesc;

    //授信客户
    /** 客户编号 */
    @Excel(name = "客户编号")
    private String customerNm;

    /** 客户类型 1-个人 2-企业 3-商户 4-其他 */
    @Excel(name = "客户类型 1-个人 2-企业 3-商户 4-其他")
    private String customerType;

    private List<String> customerTypes;

    /** 所属等级 */
    @Excel(name = "所属等级")
    private String customerLevel;

    /** 授信额度 */
    @Excel(name = "授信额度")
    private BigDecimal creditAmount;

    /** 用信额度 */
    @Excel(name = "用信额度")
    private BigDecimal usingCreditAmount;

    /** 五级分类 */
    @Excel(name = "五级分类")
    private String fiveLevel;

    private List<String> fiveLevels;


    private List<String> belongGridCodes;


    /** 网格管理员 */
    @Excel(name = "网格管理员")
    private String gridManager;

    /** 信贷客户经理 */
    @Excel(name = "信贷客户经理")
    private String creditManager;

    /** 信贷客户经理工号 */
    @Excel(name = "信贷客户经理工号")
    private Long creditManagerJobNumber;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String branchBank;

    private String branchBankName;

    private List<String> branchBanks;


    /** 所属分行 */
    @Excel(name = "所属分行")
    private String separateBank;

    /** 担保方式 1-保证 2-信用 3-抵押 4-质押 */
    @Excel(name = "担保方式 1-保证 2-信用 3-抵押 4-质押")
    private String guaranteeMethod;

    private List<Long> guaranteeMethods;


    /** 用途 1-经营 2-消费 */
    @Excel(name = "用途 1-经营 2-消费")
    private Integer usingType;

    /** 逾欠情况 */
    @Excel(name = "逾欠情况")
    private Integer isInArrears;

    /** 乡贷置换他行 */
    @Excel(name = "乡贷置换他行")
    private Integer exchangeMethod;

    /** 乡贷产品标签 0-否 1-是 */
    @Excel(name = "乡贷产品标签 0-否 1-是")
    private String productLabel;

    private List<String> productLabels;

    /** 乡贷线下引流 */
    @Excel(name = "乡贷线下引流")
    private String offlineAttract;

    private List<String> offlineAttracts;

    /** 乡贷信贷提升 */
    @Excel(name = "乡贷信贷提升")
    private String creditPromotion;

    private List<String> creditPromotions;

    /** 乡贷其他标签 */
    @Excel(name = "乡贷其他标签")
    private Integer otherLabel;

    /** 删除标志 */
    @Excel(name = "删除标志")
    private Integer deleteFlag;

    /** 风险类型 1-正常类 2-逾欠类 3-不良类 */
    @Excel(name = "风险类型 1-正常类 2-逾欠类 3-不良类")
    private String riskType;

    /** 乡贷客户类型 1-用信客户 2-签约未用信客户 */
    @Excel(name = "乡贷客户类型 1-用信客户 2-签约未用信客户")
    private String villageLoanType;

    /** 是否乡贷产品 0-否 1-是 */
    @Excel(name = "是否乡贷产品 0-否 1-是")
    private Long isProduct;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 生日 */
    @Excel(name = "生日")
    private String birthday;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 婚姻状况 */
    @Excel(name = "婚姻状况")
    private String maritalStatus;

    /** 职业 */
    @Excel(name = "职业")
    private String job;

    /** 工作单位 */
    @Excel(name = "工作单位")
    private String workUnit;

    /** 法人身份证号码 */
    @Excel(name = "法人身份证号码")
    private String legalPersonIdentityNum;

    /** 法人姓名 */
    @Excel(name = "法人姓名")
    private String legalPersonName;

    /** 法人电话 */
    @Excel(name = "法人电话")
    private String legalPersonPhone;

    /** 经营地址 */
    @Excel(name = "经营地址")
    private String businessAddress;

    /** 客户标签 */
    private List<String> labelNameList;


    //绩效客户
    private List<String> customerLevelList;

    /** 存款余额 */
    @Excel(name = "存款余额")
    private BigDecimal deposit;

    @TableField(exist = false)
    /** 最低存款余额 */
    private BigDecimal minDeposit;

    @TableField(exist = false)
    /** 最高存款余额 */
    private BigDecimal maxDeposit;

    /** 贷款余额 */
    @Excel(name = "贷款余额")
    private BigDecimal loan;

    @TableField(exist = false)
    /** 最低贷款余额 */
    private BigDecimal minLoan;

    @TableField(exist = false)
    /** 最高贷款余额 */
    private BigDecimal maxLoan;

    /** 理财余额 */
    @Excel(name = "理财余额")
    private BigDecimal finance;

    @TableField(exist = false)
    /** 最低理财余额 */
    private BigDecimal minFinance;

    @TableField(exist = false)
    /** 最高理财余额 */
    private BigDecimal maxFinance;

    /** 管理资产余额 */
    @Excel(name = "管理资产余额")
    private BigDecimal property;

    @TableField(exist = false)
    /** 最低管理资产余额 */
    private BigDecimal minProperty;

    @TableField(exist = false)
    /** 最高管理资产余额 */
    private BigDecimal maxProperty;



    //建档客户

    @ApiModelProperty(value = "客户名称")
    private String customerName;


    @ApiModelProperty(value = "联系地址")
    private String customerAddress;


    @ApiModelProperty(value = "手机号码")
    private String customerPhone;


    @ApiModelProperty(value = "证件号(身份证号/统一社会信用代码)")
    private String customerIdentity;


    @ApiModelProperty(value = "客户来源")
    private String customerSource;


    @ApiModelProperty(value = "所属网格编码")
    private String belongGridCode;


    @ApiModelProperty(value = "所属网格名称")
    private String belongGridName;


    @ApiModelProperty(value = "建档人编码")
    private String filingManagerCode;


    @ApiModelProperty(value = "建档人名称")
    private String filingManagerName;


    @ApiModelProperty(value = "建档机构编码")
    private String filingOrgCode;


    @ApiModelProperty(value = "建档机构名称")
    private String filingOrgName;


    @ApiModelProperty(value = "建档进度")
    private String filingProgress;


    @ApiModelProperty(value = "建档来源 1-人工导入 2-批量导入")
    private String filingSource;


    @ApiModelProperty(value = "建档状态 1-已建档 2-完成建档")
    private String filingStatus;



    @ApiModelProperty(value = "建档日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filingDate;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private FilingMessageDetail filingMessageDetail;
}
