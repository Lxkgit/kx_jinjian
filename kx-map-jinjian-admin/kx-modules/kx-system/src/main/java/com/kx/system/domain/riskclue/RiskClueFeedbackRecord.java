package com.kx.system.domain.riskclue;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class RiskClueFeedbackRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "线索ID不能为空")
    private Long clueId;
    private String feedbackOrgName;
    private String feedbackPerson;
    private String feedbackPhone;
    @NotBlank(message = "反馈内容不能为空")
    private String feedbackContent;
    @NotNull(message = "反馈时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
