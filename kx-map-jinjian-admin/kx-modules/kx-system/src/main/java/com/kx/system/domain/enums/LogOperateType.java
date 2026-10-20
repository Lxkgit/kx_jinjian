package com.kx.system.domain.enums;

import lombok.Getter;

@Getter
public enum LogOperateType {
    ADD("0", "新增"),
    EDIT("1", "修改"),
    DELETE("2", "删除"),
    GRID_CUT("20", "网格切分"),
    GRID_SPLIT("21", "网格拆分"),
    GRID_MERGE("22", "子网合并"),
    GRID_CLEAN_CHILD("23", "删除子网络"),
    GRID_COORDINATES_MODIFY("24", "边界调整"),
    GRID_DEPT_EDIT("25", "辖区分配")
    ;

    private final String code;
    private final String name;

    LogOperateType(String code, String desc) {
        this.code = code;
        this.name = desc;
    }

}
