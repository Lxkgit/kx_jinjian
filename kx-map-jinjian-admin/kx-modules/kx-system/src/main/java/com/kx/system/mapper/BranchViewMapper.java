package com.kx.system.mapper;

import com.kx.system.domain.BranchEvents;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.domain.dto.GridManageInfoDTO;
import com.kx.system.domain.grid.GridMessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangxu
 */
public interface BranchViewMapper {

    PerformanceCustomerInfo selectPerformanceInfo(String customerName);

    BranchInfoDTO selectGridInfo(String customerName);

    GridMessageInfo selectBranchBifferentiate(@Param("anchorName") String anchorName);

    BranchEvents selectBranchEvent(String customerName);

    List<BranchEvents> selectBranchEventAll(String customerName);

    Integer insertBranchEvent(BranchEvents branchEvents);

    boolean selectBranch(String anchorName);

    void deleteBranchEvent(Long id);

    BranchEvents selectBranchEventById(Long id);

    Integer updateBranchEvent(BranchEvents branchEvents);

    String getCategoryName(String currentCode);

    String getParentCode(String currentCode);



    List<CashServicePoints> selectCashServiceInfo(List<String> gridCodes);

    List<VillageFinancialServicePoints> selectFinancialServiceInfo(List<String> gridCodes);

    List<GridManageInfoDTO> selectGridManageInfo(@Param("gridCodes") List<String> gridCodes);
}
