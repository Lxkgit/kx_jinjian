package com.kx.system.controller;

import com.kx.system.domain.GridCommunityAdmin;
import com.kx.system.service.GridCommunityAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "社区网格员信息管理")
@RequestMapping("/grid/community/admin")
public class GridCommunityAdminController {

    @Autowired
    private GridCommunityAdminService adminService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增社区网格员")
    public int insertAdmin(@RequestBody GridCommunityAdmin admin) {
        return adminService.insertAdmin(admin);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改社区网格员")
    public int updateAdmin(@RequestBody GridCommunityAdmin admin) {
        return adminService.updateAdmin(admin);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除社区网格员")
    public int deleteAdmin(@PathVariable Long id) {
        return adminService.deleteAdmin(id);
    }

    @GetMapping("/select/{gridCode}")
    @ApiOperation(value = "根据code查询社区网格员")
    public List<GridCommunityAdmin> selectAdminByGridCode(@PathVariable String gridCode) {
        return adminService.selectAdminByGridCode(gridCode);
    }

    @GetMapping("/selectAll")
    @ApiOperation(value = "查询所有社区网格员")
    public List<GridCommunityAdmin> selectAllAdmins() {
        return adminService.selectAllAdmins();
    }
}