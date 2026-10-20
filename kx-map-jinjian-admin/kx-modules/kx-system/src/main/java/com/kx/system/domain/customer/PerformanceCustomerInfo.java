package com.kx.system.domain.customer;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 绩效客户信息对象 performance_customer_info
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Data

public class PerformanceCustomerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id
     */
    @ExcelProperty(index = 0)
    private Long id;

    /**
     * 客群id
     */
    @TableField(exist = false)
    private Long customGroupId;

    private List<Long> ids;

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
    @ExcelProperty(value = "网点名称", index = 1)
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

    private List<String> customerLevelList;

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
    /** 所属机构id */
    private Long deptId;

    /** 所属机构 */
    @Excel(name = "所属机构")
    private String branchBank;

    private String orgNum;

    private String branchBankName;

    /** 所属分行id */
    private String separateBankId;

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

    /** 地址类型 */
    private String addrType;

    /** 客户标签 */
    private List<String> labelNameList;

    /** 移动端查询参数 */
    private String query;

    private String point;

    private String newLegalPerson;

    //客户类型(1-个人 2-企业 3-商户 4-其他)，多选筛选
    private List<String> customerTypes;

    //Es查询 权限控制
    private JSONObject queryEsRole;

    private Date updateTime;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 维度
     */
    private BigDecimal lat;

    /**
     * 人行-网格长
     */
    private String gridMainManagerName;

    /**
     * 网点责任人
     */
    private String dutyName;

    private String userId;

    /**
     * 网点责任人联系电话
     */
    private String dutyPhone;

    /**
     * 银行机构名称
     */
    private String bankAgencyName;

    /**
     * 网点服务类型
     */
    private String ServiceType;

    /**
     * 网点服务类型集合
     */
    private List<String> serviceTypeList;

    /**
     * 所属网格编号集合
     */
    private List<String> belongGridCodeList;

    /**
     * 保存的客群名称
     */
    private String customGroupName;

    /**
     * 保存的客群描述
     */
    private String groupDesc;

    /**
     * 网点客群id
     */
    private Long groupId;


    /**
     *网点类型
     */
    private String dotType;

    /**
     *网点类型集合
     */
    private List<String> dotTypeList;

    /**
     *网点介绍
     */
    private String dotIntroduce;

    /**
     * 银行分类
     */
    private String deptClassify;

    /**
     * 银行分类
     */
    private List<String> deptClassifyList;


    //所属网格类型 0 行政村网格 1 社区网格
    private String ruralGridType;

    private Integer pageNum;
    private Integer pageSize;
}
