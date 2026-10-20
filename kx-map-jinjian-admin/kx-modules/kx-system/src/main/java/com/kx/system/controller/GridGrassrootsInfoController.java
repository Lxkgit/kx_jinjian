package com.kx.system.controller;


import com.kx.system.domain.GridGrassrootsInfo2;
import com.kx.system.service.GridGrassrootsInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "基层组织信息管理")
@RequestMapping("/gridGrassrootsInfo")
public class GridGrassrootsInfoController {

    @Autowired
    private GridGrassrootsInfoService gridGrassrootsInfoService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询基层组织信息")
    public GridGrassrootsInfo2 findById(@PathVariable Long id) {
        return gridGrassrootsInfoService.findById(id);
    }

    @GetMapping("/code/{gridCode}")
    @ApiOperation(value = "根据code查询基层组织信息")
    public List<GridGrassrootsInfo2> findByGridCode(@PathVariable String gridCode) {
        return gridGrassrootsInfoService.findByGridCode(gridCode);
    }

    @GetMapping("/all")
    @ApiOperation(value = "查询所有数据")
    public List<GridGrassrootsInfo2> findAll() {
        return gridGrassrootsInfoService.findAll();
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增基层组织信息")
    public int insert(@RequestBody GridGrassrootsInfo2 gridGrassrootsInfo) {
        return gridGrassrootsInfoService.insert(gridGrassrootsInfo);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "根据id修改基层组织信息")
    public int update(@PathVariable Long id, @RequestBody GridGrassrootsInfo2 gridGrassrootsInfo) {
        gridGrassrootsInfo.setId(id);
        return gridGrassrootsInfoService.update(gridGrassrootsInfo);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除基层组织信息")
    public int deleteById(@PathVariable Long id) {
        return gridGrassrootsInfoService.deleteById(id);
    }
}
