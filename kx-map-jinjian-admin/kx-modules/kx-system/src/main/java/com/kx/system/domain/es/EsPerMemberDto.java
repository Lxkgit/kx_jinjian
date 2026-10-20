package com.kx.system.domain.es;

import com.kx.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EsPerMemberDto {


    /** 主键id
     */
    private Long id;

    /** 客户编号 */
    @Excel(name = "客户编号")
    private String customerNm;

    /** 客户名称/商户名称 */
    @Excel(name = "客户名称/商户名称")
    private String customerName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String customerPhone;

    /** 证件号(身份证号/统一社会信用代码) */
    @Excel(name = "证件号(身份证号/统一社会信用代码)")
    private String customerIdentity;
    /** 客户类型 1-个人 2-企业 3-商户 4-其他 */
    @Excel(name = "客户类型 1-个人 2-企业 3-商户 4-其他")
    private Integer customerType;

    /** 客户等级 */
    @Excel(name = "客户等级")
    private String customerLevel;
    /** 网格地址 */
    @Excel(name = "网格地址")
    private String customerAddress;


    /** 工作单位 */
    @Excel(name = "工作单位")
    private String workUnit;

    /** 职业 */
    @Excel(name = "职业")
    private String job;

    /** 存款余额 */
    @Excel(name = "存款余额")
    private BigDecimal deposit;

    /** 贷款余额 */
    @Excel(name = "贷款余额")
    private BigDecimal loan;
    /** 理财余额 */
    @Excel(name = "理财余额")
    private BigDecimal finance;
    /** 管理资产余额 */
    @Excel(name = "管理资产余额")
    private BigDecimal property;
    /** 所属网格编号 */
    @Excel(name = "所属网格编号")
    private String belongGridCode;

    /** 所属网格名称 */
    @Excel(name = "所属网格名称")
    private String belongGridName;

    /** 网格管理员  工号*/
    @Excel(name = "网格管理员")
    private String gridManager;
    /** 网格管理员  姓名*/
    private String gridManagerName;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String branchBank;
    /** 所属分行 */
    @Excel(name = "所属分行")
    private String separateBank;

    /** 管户人 */
    @Excel(name = "管户人")
    private String creditManager;

    /** 管户人工号 */
    @Excel(name = "管户人工号")
    private String creditManagerJobNumber;

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

    private String point;
    private Long userDeptId;
    private Long userId;
    private String ancestorsId;
    private Long groupId;
    private String ancestorCodes;


}
