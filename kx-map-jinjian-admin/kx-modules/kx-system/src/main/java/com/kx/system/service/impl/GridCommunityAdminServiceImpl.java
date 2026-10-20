package com.kx.system.service.impl;

import com.kx.system.domain.GridCommunityAdmin;
import com.kx.system.mapper.GridCommunityAdminMapper;
import com.kx.system.service.GridCommunityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GridCommunityAdminServiceImpl implements GridCommunityAdminService {

    @Autowired
    private GridCommunityAdminMapper adminMapper;

    @Override
    public int insertAdmin(GridCommunityAdmin admin) {
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.insert(admin);
    }

    @Override
    public int updateAdmin(GridCommunityAdmin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public int deleteAdmin( Long id) {
        return adminMapper.delete(id);
    }

    @Override
    public List<GridCommunityAdmin> selectAdminByGridCode(String gridCode) {
        return adminMapper.selectByGridCode(gridCode);
    }

    @Override
    public List<GridCommunityAdmin> selectAllAdmins() {
        return adminMapper.selectAll();
    }
}