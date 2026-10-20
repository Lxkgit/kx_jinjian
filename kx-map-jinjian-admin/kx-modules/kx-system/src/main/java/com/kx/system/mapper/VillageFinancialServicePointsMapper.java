package com.kx.system.mapper;


import com.kx.system.domain.CashServicePointExport;
import com.kx.system.domain.VillageExport;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.dto.VillageFinancialServicePointsQuery;

import java.util.List;

/**
 * 乡村金融服务点信息Mapper接口
 * 
 * @author zxy
 * @date 2024-11-08
 */
public interface VillageFinancialServicePointsMapper 
{

    List<VillageFinancialServicePoints> selectServiceMapper(VillageFinancialServicePointsQuery query);

    List<AnchorPoint> selectAll();

    void updateVillageFinancialServiceGridCode(VillageFinancialServicePoints villageFinancialServicePoints);

    List<VillageFinancialServicePoints> getAnchorInfo(String gridCode);


    List<VillageExport> exportBatch(VillageFinancialServicePointsQuery village);
}
