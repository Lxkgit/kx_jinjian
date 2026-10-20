package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户基础信息
 */
@Data
public class PaBusinessInfo extends BaseEntity {
    /**
     * 个人内码
     */
    private String nm;

    /**
     * 企业内码
     */
    private String businessNm;

    /**
     * 商户号
     */
    private String businessNo;

    /**
     * 商户类别
     */
    private String type;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 商户法人
     */
    private String legalPerson;
    /**
     * 营业执照号
     */
    private String businessLicenseNo;

    /**
     * 商户法人身份证
     */
    private String identify;
    /**
     * 经营地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系人电话
     */
    private String contactTelephone;

    /**
     * 所在网点
     */
    private String outlet;

    /**
     * 所在网点编号
     */
    private String outletCode;
    /**
     * 所属村/社区
     */
    private String area;
    /**
     * 所属村/社区代码
     */
    private String areaCode;
    /**
     * 所属客户经理
     */
    private String managerId;
    /**
     * 绑定账号列表
     */
    private String accountList;
    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 跑批时间
     */
    private Date etlDt;

    /**
     * 删除标志0-代表存在 1-代表删除
     */
    private Integer delFlag;

    /**
     * 营业执照
     */
    private String businessLicenseUrl;

    private static final long serialVersionUID = 1L;
}
