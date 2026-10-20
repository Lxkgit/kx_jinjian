package com.kx.system.service;


import com.alibaba.fastjson2.JSONObject;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.dto.VillageFinancialServicePointsQuery;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 乡村金融服务点信息Service接口
 *
 * @author zxy
 * @date 2024-11-08
 */
public interface IVillageFinancialServicePointsService {

    List<VillageFinancialServicePoints> selectVillageFinancialServicePointsList(VillageFinancialServicePointsQuery servicePointsDTO);

    void villageFinancialAnchCode();

    List<VillageFinancialServicePoints> getAnchorInfo(String gridCode);

    void exportBatch(HttpServletResponse response, VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException;

    void exportBatchTest(VillageFinancialServicePointsQuery village) throws UnsupportedEncodingException;
}
