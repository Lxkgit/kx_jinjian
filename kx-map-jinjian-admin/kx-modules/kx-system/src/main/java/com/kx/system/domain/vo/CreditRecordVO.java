package com.kx.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author kangxu
 * @date 2024/4/8
 */
@Data
public class CreditRecordVO {
    //授信信息
    /** 产品名称 */
    private String productName;

    /** 授信金额 */
    private Long creditAmount;

    /** 客户经理名称 */
    private String manager;

    /** 贷款余额 */
    private Long loanAmount;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    /** 授信时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date creditCreateTime;
}
