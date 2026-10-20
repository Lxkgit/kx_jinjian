package com.kx.system.mapper;

import com.kx.system.domain.buildingsweep.BuildingSweepReport;
import com.kx.system.domain.buildingsweep.BuildingSweepReviewRequest;
import com.kx.system.domain.riskclue.RiskClueReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuildingSweepReportMapper {

    List<BuildingSweepReport> selectBuildingSweepReportList(BuildingSweepReport query);

    BuildingSweepReport selectBuildingSweepReportById(Long id);

    int insertBuildingSweepReport(BuildingSweepReport report);

    int updateReportNo(@Param("id") Long id, @Param("reportNo") String reportNo);

    int updateBuildingSweepReport(BuildingSweepReport report);

    int reviewBuildingSweepReport(BuildingSweepReviewRequest request);

    List<RiskClueReport> selectRelatedClueOptions(@Param("keyword") String keyword);

    int countAvailableRelatedClue(@Param("clueNo") String clueNo);

    int resubmitBuildingSweepReport(BuildingSweepReport report);

    int deleteBuildingSweepReport(@Param("id") Long id, @Param("updateBy") String updateBy);
}
