package com.kx.system.service;

import com.kx.system.domain.buildingsweep.BuildingSweepReport;
import com.kx.system.domain.buildingsweep.BuildingSweepReviewRequest;
import com.kx.system.domain.riskclue.RiskClueReport;

import java.util.List;

public interface IBuildingSweepReportService {

    List<BuildingSweepReport> selectBuildingSweepReportList(BuildingSweepReport query);

    BuildingSweepReport selectBuildingSweepReportById(Long id);

    int insertBuildingSweepReport(BuildingSweepReport report);

    int updateBuildingSweepReport(BuildingSweepReport report);

    int reviewBuildingSweepReport(BuildingSweepReviewRequest request);

    List<RiskClueReport> selectRelatedClueOptions(String keyword);

    int resubmitBuildingSweepReport(BuildingSweepReport report);

    int deleteBuildingSweepReport(Long id);
}
