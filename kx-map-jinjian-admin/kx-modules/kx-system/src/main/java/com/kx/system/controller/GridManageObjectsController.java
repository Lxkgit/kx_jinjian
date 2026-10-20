package com.kx.system.controller;


import com.kx.system.domain.GridManageObjects2;
import com.kx.system.service.GridManageObjectsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "重点处理对象管理")
@RequestMapping("/gridManageObjects")
public class GridManageObjectsController {

    @Autowired
    private GridManageObjectsService gridManageObjectsService;

    @PostMapping("/insert")
    @ApiOperation(value = "增加重点处理对象管理")
    public void addGridManageObjects(@RequestBody GridManageObjects2 gridManageObjects) {
        gridManageObjectsService.addGridManageObjects(gridManageObjects);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除重点处理对象管理")
    public void deleteGridManageObjectsById(@PathVariable long id) {
        gridManageObjectsService.deleteGridManageObjectsById(id);
    }

    @GetMapping("/get/{gridCode}")
    @ApiOperation(value = "获取重点处理对象管理")
    public List<GridManageObjects2> getGridManageObjectsByGridCode(@PathVariable String gridCode) {
        return gridManageObjectsService.getGridManageObjectsByGridCode(gridCode);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改重点处理对象管理")
    public void updateGridManageObjectsByGridCode(@RequestBody GridManageObjects2 gridManageObjects) {
        gridManageObjectsService.updateGridManageObjectsByGridCode(gridManageObjects);
    }

    @GetMapping("/all")
    @ApiOperation(value = "增加重点处理对象管理")
    public List<GridManageObjects2> allGridManageObjects() {
        return gridManageObjectsService.allGridManageObjects();
    }
}
