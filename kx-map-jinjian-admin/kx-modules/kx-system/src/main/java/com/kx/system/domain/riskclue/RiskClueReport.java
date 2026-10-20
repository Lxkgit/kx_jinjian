package com.kx.system.domain.riskclue;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RiskClueReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String clueNo;
    private Integer clueSource;
    private String clueTitle;
    private Integer clueType;
    private String riskSubject;
    private String unifiedSocialCreditCode;
    private String clueDescription;
    private String subjectAddress;
    private String contactName;
    private String contactPhone;
    private String locationAddress;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String coordinateSystem;
    private Integer riskLevel;
    private String reportOrgCode;
    private String reportOrgName;
    private String reporterUserName;
    private String reporterNickName;
    private String areaCode;
    private String areaFullName;
    private String streetCode;
    private String streetName;
    private String sourceBizType;
    private String sourceBizId;
    /** 0草稿、1待复核、2复核通过、3退回补正。 */
    private Integer reviewStatus;
    private Integer reviewRound;
    /** 0待移送、1已移送。 */
    private Integer transferStatus;
    /** 0待反馈、1已反馈。 */
    private Integer feedbackStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date firstReportTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;
    private String delFlag;

    private List<RiskClueFile> files = new ArrayList<>();
    private List<RiskClueReviewRecord> reviewRecords = new ArrayList<>();
    private RiskClueTransferRecord transferRecord;
    private List<RiskClueFeedbackRecord> feedbackRecords = new ArrayList<>();

    /** 查询条件及状态并发校验字段。 */
    private String keyword;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTimeStart;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTimeEnd;
    private Integer originalReviewStatus;
}
