package com.kx.system.controller;

import com.kx.system.domain.GridCommunitySupervisors2;
import com.kx.system.service.GridCommunitySupervisorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "社会网格员信息管理")
@RequestMapping("/grid/community/supervisor")
public class GridCommunitySupervisorsController {

    // todo 社会网格员

    @Autowired
    private GridCommunitySupervisorsService supervisorsService;

    @PostMapping("/insert")
    @ApiOperation(value = "新增社区监督员")
    public int insertSupervisor(@RequestBody GridCommunitySupervisors2 supervisor) {
        return supervisorsService.insertSupervisor(supervisor);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改社区监督员")
    public int updateSupervisor(@RequestBody GridCommunitySupervisors2 supervisor) {
        return supervisorsService.updateSupervisor(supervisor);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除社区监督员")
    public int deleteSupervisor(@PathVariable Long id) {
        return supervisorsService.deleteSupervisor(id);
    }

    @GetMapping("/select/{gridCode}")
    @ApiOperation(value = "根据code查询社区监督员")
    public List<GridCommunitySupervisors2> selectSupervisorByGridCode(@PathVariable String gridCode) {
        return supervisorsService.selectSupervisorByGridCode(gridCode);
    }

    @GetMapping("/selectAll")
    @ApiOperation(value = "查询所有社区监督员")
    public List<GridCommunitySupervisors2> selectAllSupervisors() {
        return supervisorsService.selectAllSupervisors();
    }
}