package com.kx.system.config.enums;

public enum LabelTTypeEnum {
    GRID(0,"网格标签"),
    SYSTEM(1,"系统标签");

    private Integer code;
    private String name;

    LabelTTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
