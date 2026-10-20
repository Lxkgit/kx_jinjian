package com.kx.system.domain.riskclue;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RiskClueReviewRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long clueId;
    private Integer reviewRound;
    /** 2复核通过、3退回补正。 */
    private Integer reviewResult;
    private String reviewOpinion;
    private Long reviewerUserId;
    private String reviewerUserName;
    private String reviewerNickName;
    private Long reviewerDeptId;
    private String reviewerDeptName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
