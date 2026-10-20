package com.kx.system.service;

import com.kx.system.domain.BranchEvents;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.domain.dto.GridManageInfoDTO;

import java.util.HashMap;
import java.util.List;

/**
 * 针对大屏网格视图的数据库操作Service
 * @author kangxu

 */
public interface BranchViewService {
    String branchBifferentiate(String anchorName);

    BranchInfoDTO selectBranchInfo(String customerName);

    List<String> selectBranchService(String customerName);

    BranchEvents queryBranchEvent(String customerName);

    List<BranchEvents> queryBranchEventAll(String customerName);

    Integer insertBranchEvent(BranchEvents branchEvents);

    String deleteBranchEvent(Long id);

    Integer updateBranchEvent(BranchEvents branchEvents);

    HashMap<String, String> getCategoryPath(String code);

    List<GridManageInfoDTO> getGridManageInfo(String dutyNetworkName);

    List <CashServicePoints> getCashServiceInfo(List<String> gridCodes);

    List<VillageFinancialServicePoints> getFinancialServiceInfo(List<String> gridCodes);
}
