package com.kx.system.service;

import com.kx.system.domain.GridCommunityAdmin;

import java.util.List;

public interface GridCommunityAdminService {
    int insertAdmin(GridCommunityAdmin admin);
    int updateAdmin(GridCommunityAdmin admin);
    int deleteAdmin(Long id);
    List<GridCommunityAdmin> selectAdminByGridCode(String gridCode);
    List<GridCommunityAdmin>selectAllAdmins();
}