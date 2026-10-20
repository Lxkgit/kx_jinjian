package com.kx.system.mapper;

import com.kx.system.domain.riskclue.RiskClueFeedbackRecord;
import com.kx.system.domain.riskclue.RiskClueFile;
import com.kx.system.domain.riskclue.RiskClueReport;
import com.kx.system.domain.riskclue.RiskClueReviewRecord;
import com.kx.system.domain.riskclue.RiskClueTransferRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RiskClueReportMapper {
    List<RiskClueReport> selectRiskClueReportList(RiskClueReport query);

    RiskClueReport selectRiskClueReportById(Long id);

    int insertRiskClueReport(RiskClueReport report);

    int updateClueNo(@Param("id") Long id, @Param("clueNo") String clueNo);

    int updateRiskClueContent(RiskClueReport report);

    int submitRiskClueReport(RiskClueReport report);

    int updateReviewStatus(@Param("id") Long id, @Param("reviewStatus") Integer reviewStatus,
                           @Param("updateBy") String updateBy);

    int updateTransferStatus(@Param("id") Long id, @Param("updateBy") String updateBy);

    int updateFeedbackStatus(@Param("id") Long id, @Param("updateBy") String updateBy);

    int deleteRiskClueReport(@Param("id") Long id, @Param("updateBy") String updateBy);

    List<RiskClueFile> selectFilesByClueId(Long clueId);

    int deleteFilesByClueId(Long clueId);

    int batchInsertFiles(@Param("files") List<RiskClueFile> files);

    List<RiskClueReviewRecord> selectReviewRecordsByClueId(Long clueId);

    int insertReviewRecord(RiskClueReviewRecord record);

    RiskClueTransferRecord selectTransferRecordByClueId(Long clueId);

    int insertTransferRecord(RiskClueTransferRecord record);

    List<RiskClueFeedbackRecord> selectFeedbackRecordsByClueId(Long clueId);

    int insertFeedbackRecord(RiskClueFeedbackRecord record);
}
