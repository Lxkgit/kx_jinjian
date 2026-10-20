package com.kx.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 授信对象 credit_record
 *
 * @author kangxu
 * @date 2024-04-12
 */
@Data
public class CreditRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customId;

    /** 客户类型 */
    @Excel(name = "客户类型")
    private String type;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNum;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireDate;

    /** 授信金额 */
    @Excel(name = "授信金额")
    private BigDecimal creditAmount;

    /** 贷款余额 */
    @Excel(name = "贷款余额")
    private BigDecimal loanAmount;

    /** 客户经理工号 */
    @Excel(name = "客户经理工号")
    private String managerNum;

    /** 客户经理名称 */
    @Excel(name = "客户经理名称")
    private String manager;

    /** 身份证 */
    @Excel(name = "身份证")
    private String identity;

    private String customerNm;

    private Date etlDate;
}
