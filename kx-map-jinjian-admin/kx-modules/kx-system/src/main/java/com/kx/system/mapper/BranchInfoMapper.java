package com.kx.system.mapper;


import com.kx.system.domain.branch.BranchInfo;

import java.util.List;

/**
 * @author kangxu
 */
public interface BranchInfoMapper {

    List<BranchInfo> selectBranchList(BranchInfo branchInfo);

    BranchInfo selectBranchById(Long branchId);

    void insertBranch(BranchInfo branchInfo);

    void insertBranchList(List<BranchInfo> branchInfoList);

    void updateBranchByOrgNum(BranchInfo branchInfo);


}
