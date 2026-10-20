package com.kx.system.domain.riskclue;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RiskClueReviewRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "线索ID不能为空")
    private Long id;
    @NotNull(message = "复核结果不能为空")
    private Integer reviewResult;
    @NotBlank(message = "复核意见不能为空")
    private String reviewOpinion;
}
