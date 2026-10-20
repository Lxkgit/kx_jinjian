package com.kx.system.domain.customer;

import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收单客户信息对象 acquiring_customer_info
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Data
public class AcquiringCustomerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 所属网格编码 */
    private String belongGridCode;

    /** 所属机构编码 */
    private String belongOrgCode;

    @Excel(name = "客户编号")
    private String customNm;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customName;

    /** 客户简称 */
    @Excel(name = "客户简称")
    private String customShortName;

    /** 营业执照号码 */
    @Excel(name = "营业执照号码")
    private String businessLicenseNum;

    /** 所属网格 */
    private String belongGridName;

    /** 所属支行 */
    @Excel(name = "所属支行")
    private Long branchBank;

    private String branchBankName;

    /** 法人姓名 */
    @Excel(name = "法人姓名")
    private String legalPersonName;

    /** 法人身份证号码 */
    @Excel(name = "法人身份证号码")
    private String legalPersonIdentityNum;

    /** 结算账户账号 */
    @Excel(name = "结算账户账号")
    private String settlementAccountNum;

    /** 客户类型 */
    @Excel(name = "客户类型")
    private Integer customType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCustomNm(String customNm)
    {
        this.customNm = customNm;
    }

    public String getCustomNm()
    {
        return customNm;
    }
    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    public String getCustomName()
    {
        return customName;
    }
    public void setCustomShortName(String customShortName)
    {
        this.customShortName = customShortName;
    }

    public String getCustomShortName()
    {
        return customShortName;
    }
    public void setBusinessLicenseNum(String businessLicenseNum)
    {
        this.businessLicenseNum = businessLicenseNum;
    }

    public String getBusinessLicenseNum()
    {
        return businessLicenseNum;
    }
    public void setLegalPersonName(String legalPersonName)
    {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonName()
    {
        return legalPersonName;
    }
    public void setLegalPersonIdentityNum(String legalPersonIdentityNum)
    {
        this.legalPersonIdentityNum = legalPersonIdentityNum;
    }

    public String getLegalPersonIdentityNum()
    {
        return legalPersonIdentityNum;
    }
    public void setSettlementAccountNum(String settlementAccountNum)
    {
        this.settlementAccountNum = settlementAccountNum;
    }

    public String getSettlementAccountNum()
    {
        return settlementAccountNum;
    }
    public void setCustomType(Integer customType)
    {
        this.customType = customType;
    }

    public Integer getCustomType()
    {
        return customType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customNm", getCustomNm())
                .append("customName", getCustomName())
                .append("customShortName", getCustomShortName())
                .append("businessLicenseNum", getBusinessLicenseNum())
                .append("legalPersonName", getLegalPersonName())
                .append("legalPersonIdentityNum", getLegalPersonIdentityNum())
                .append("settlementAccountNum", getSettlementAccountNum())
                .append("customType", getCustomType())
                .toString();
    }
}
