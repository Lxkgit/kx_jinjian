package com.kx.system.domain;

import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 开卡对象 opening_card_record
 *
 * @author kangxu
 * @date 2024-04-08
 */
@Data
public class OpeningCardRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户id */
    @Excel(name = "客户id")
    private String customNm;

    /** 客户类型 */
    @Excel(name = "客户类型")
    private String type;

    /** 卡号 */
    @Excel(name = "卡号")
    private String cardNum;

    /** 卡类型 */
    @Excel(name = "卡类型")
    private String cardType;

    /** 客户经理号 */
    @Excel(name = "客户经理号")
    private String managerNum;

    /** 客户经理名称 */
    @Excel(name = "客户经理名称")
    private String manager;

    private String identity;

    private Date createTime;

    private Date etlDate;
}
