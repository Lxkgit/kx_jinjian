package com.kx.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授信客户信息对象 credit_customer_info
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Data
public class CreditCustomerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    private List<Long> ids;

    /**
     * 客群id
     */
    @TableField(exist = false)
    private Long customGroupId;

    /** 客群 IdList */
    private List<Long> groupIdList;

    @ApiModelProperty("所属网格编号集合")
    @TableField(exist = false)
    private List<String> belongGridCodeArr;

    /** 客户编号 */
    @Excel(name = "客户编号")
    private String customerNm;

    /** 客户名称/商户名称 */
    @Excel(name = "客户名称/商户名称")
    private String customerName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String customerPhone;

    /** 客户类型 1-个人 2-企业 3-商户 4-其他 */
    @Excel(name = "客户类型 1-个人 2-企业 3-商户 4-其他")
    private String customerType;

    private List<String> customerTypes;


    /** 网格地址 */
    @Excel(name = "网格地址")
    private String customerAddress;

    /** 证件号(身份证号/统一社会信用代码) */
    @Excel(name = "证件号(身份证号/统一社会信用代码)")
    private String customerIdentity;

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

    /** 所属网格编号 */
    @Excel(name = "所属网格编号")
    private String belongGridCode;

    private List<String> belongGridCodes;

    /** 所属网格名称 */
    @Excel(name = "所属网格名称")
    private String belongGridName;

    /** 网格管理员 */
    @Excel(name = "网格管理员")
    private String gridManager;
    /** 网格管理员名称 */
    private String gridManagerName;
    /** 信贷客户经理 */
    @Excel(name = "信贷客户经理")
    private String creditManager;

    /** 信贷客户经理工号 */
    @Excel(name = "信贷客户经理工号")
    private String creditManagerJobNumber;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String branchBank;

    private String orgNum;

    private String branchBankName;

    private List<String> branchBanks;

    /** 所属分行id */
    private String separateBankId;

    /** 所属分行 */
    @Excel(name = "所属分行")
    private String separateBank;

    /** 担保方式 1-保证 2-信用 3-抵押 4-质押 */
    @Excel(name = "担保方式 1-保证 2-信用 3-抵押 4-质押")
    private String guaranteeMethod;

    private List<String> guaranteeMethods;


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

    /** 地址类型 */
    private String addrType;

    /** 客户标签 */
    private List<String> labelNameList;

    private Long deptId;

    private String creditManagerBranchBank;

    private String creditManagerSeparateBank;

    /** 移动端查询参数 */
    private String query;

    private String newLegalPerson;

    private Date updateTime;


    //Es查询 权限控制
    private JSONObject queryEsRole;

    private BigDecimal lon;
    private BigDecimal lat;
}
