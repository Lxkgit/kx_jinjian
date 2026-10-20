package com.kx.system.domain.enums;

import lombok.Getter;

@Getter
public enum LogType {
    GRID_MODIFY("0", "网格变更"),
    GRID_USER_MODIFY("1", "网格员变更"),
    ANCHOR_MODIFY("2", "锚点变更");

    private final String code;
    private final String desc;

    LogType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
