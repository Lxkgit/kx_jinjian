package com.kx.system.domain.riskclue;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class RiskClueTransferRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "线索ID不能为空")
    private Long clueId;
    @NotBlank(message = "移送依据不能为空")
    private String transferBasis;
    private Long receiveOrgId;
    private String receiveOrgCode;
    @NotBlank(message = "移送接收单位不能为空")
    private String receiveOrgName;
    @NotNull(message = "移送方式不能为空")
    private Integer transferMethod;
    @NotBlank(message = "移送公文信息不能为空")
    private String documentInfo;
    @NotNull(message = "移送时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transferTime;
    private Long operatorUserId;
    private String operatorUserName;
    private String operatorNickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
