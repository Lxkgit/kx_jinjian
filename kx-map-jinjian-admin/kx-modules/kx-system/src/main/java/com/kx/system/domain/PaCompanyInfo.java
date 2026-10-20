package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 企业基本信息
    */
@Data
public class PaCompanyInfo extends BaseEntity {
    /**
    * 企业名称
    */
    private String name;

    /**
    * 企业内码
    */
    private String nm;

    /**
     * 所属网点code
     */
    private String outletCode;

    /**
     * 所属网点名称
     */
    private String outlet;

    /**
     * 所属客户经理工号
     */
    private String managerId;

    /**
     * 所属客户经理姓名
     */
    private String managerName;

    /**
     * 所属客户经理姓名
     */
    private int labelNum;

    private String clientType;

    /**
    * 统一社会信用代码
    */
    private String creditCode;

    /**
    * 经营地址
    */
    private String address;

    /**
     * 乡镇
     */
    private String township;

    /**
     * 村/社区
     */
    private String area;

    /**
    * 联系电话
    */
    private String telephone;

    /**
    * 企业法人姓名
    */
    private String legalPerson;

    /**
    * 注册资金
    */
    private BigDecimal capital;

    /**
    * 经营范围
    */
    private String scope;

    /**
    * 所属行业
    */
    private String industry;

    /**
    * 近两年未按时结息次数
    */
    private Integer badInterestSettlementTwoYear;

    /**
    * 近一年累计交易笔数借方
    */
    private Integer borrowerTransCntYear;

    /**
    * 近一年累计交易笔数贷方
    */
    private Integer loanTransCntYear;

    /**
    * 本年存款总数
    */
    private BigDecimal sumDepositYear;

    /**
    * 本年贷款总数
    */
    private BigDecimal sumLoanYear;

    /**
    * 总资产
    */
    private BigDecimal asset;

    /**
    * 应收账款
    */
    private BigDecimal receivable;

    /**
    * 房产类资产
    */
    private BigDecimal houseAsset;

    /**
    * 设备类资产
    */
    private BigDecimal equipmentAsset;

    /**
    * 我行存款
    */
    private BigDecimal deposit;

    /**
    * 总负债
    */
    private BigDecimal totalLiability;

    /**
    * 我行贷款
    */
    private String loan;

    /**
    * 对外担保
    */
    private String externalGuarantee;

    /**
    * 水电费代扣
    */
    private String sdfdk;

    /**
    * 企业网银
    */
    private String corporateInternetBanking;

    /**
    * 跑批时间
    */
    private Date etlDt;

    private String poolClientType;

    private String belongArea;

    private BigDecimal lon;
    private BigDecimal lat;
    /**
     * 实际控制人
     */
    private String actualController;
    /**
     * 企业规模
     */
    private String scale;
    /**
     * 企业类型
     */
    private String type;
    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 可视区域编码
     */
    private String areaCode;



    private static final long serialVersionUID = 1L;
}
