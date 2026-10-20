package com.kx.system.domain.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 客户信息主表
 * @TableName customer_info
 */
@TableName(value ="customer_info")
@Data
public class CustomerInfo implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户类型（1：个人；2：企业；3：商户）
     */
    private Integer customerType;

    /**
     * 是否行内客户（0：否；1：是）
     */
    private Integer bankInterFlag;

    /**
     * 是否公共池（0：否；1：是）
     */
    private Integer commonPoolFlag;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户类别
     */
    private Integer customerCategory;

    /**
     * 证件类型
     */
    private Integer cardType;

    /**
     * 证件号码
     */
    private String cardNum;

    /**
     * 联系方式
     */
    private String contactPhone;

    /**
     * 客户内码
     */
    private String customerInterNum;

    /**
     * 客户地址
     */
    private String customerAddress;

    /**
     * 所属网格id
     */
    private Integer belongGrid;

    /**
     * 责任部门id
     */
    private Integer dutyDeptId;

    /**
     * 网格员
     */
    private Integer gridUserId;

    /**
     * 业务管户人
     */
    private String businessManageUser;

    /**
     * 建档状态（0：未建档；1：已建档）
     */
    private Integer filesStatus;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 商户经营者
     */
    private String merchantManager;

    /**
     * 企业法人
     */
    private String legalPerson;

    /**
     * 企业法人联系方式
     */
    private String legalPersonPhone;

    /**
     * 法人身份证号码
     */
    private String legalPersonCardNum;

    /**
     * 性别
     */
    private Integer customerSex;

    /**
     * 年龄
     */
    private Integer customerAge;

    /**
     * 职业
     */
    private String career;

    /**
     * 是否户主（0：否；1：是）
     */
    private Integer householderFlag;

    /**
     * 婚姻状况
     */
    private Integer marriageFlag;

    /**
     * 是否新市民（0：否；1：是）
     */
    private Integer citizensFlag;

    /**
     * 工作单位
     */
    private String workAddress;

    /**
     * 营业执照
     */
    private String businessLicenseImage;

    /**
     * 经营场所类型
     */
    private Integer businessPlaceType;

    /**
     * 年收入/近一年收入
     */
    private BigDecimal incomeYear;

    /**
     * 注册资本
     */
    private String registerCapital;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String customerCategoryDesc;

    @TableField(exist = false)
    private String filesStatusDesc;

    @TableField(exist = false)
    private String cardTypeDesc;
}
