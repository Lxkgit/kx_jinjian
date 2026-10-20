package com.kx.system.service;

import com.kx.system.domain.riskclue.RiskClueFeedbackRecord;
import com.kx.system.domain.riskclue.RiskClueReport;
import com.kx.system.domain.riskclue.RiskClueReviewRequest;
import com.kx.system.domain.riskclue.RiskClueTransferRecord;

import java.util.List;

public interface IRiskClueReportService {
    List<RiskClueReport> selectRiskClueReportList(RiskClueReport query);

    RiskClueReport selectRiskClueReportById(Long id);

    RiskClueReport addRiskClueReport(RiskClueReport report);

    RiskClueReport saveRiskClueDraft(RiskClueReport report);

    int updateRiskClueReport(RiskClueReport report);

    int submitDraftReport(RiskClueReport report);

    int resubmitRiskClueReport(RiskClueReport report);

    int reviewRiskClueReport(RiskClueReviewRequest request);

    int transferRiskClueReport(RiskClueTransferRecord record);

    int feedbackRiskClueReport(RiskClueFeedbackRecord record);

    int deleteRiskClueReport(Long id);
}
