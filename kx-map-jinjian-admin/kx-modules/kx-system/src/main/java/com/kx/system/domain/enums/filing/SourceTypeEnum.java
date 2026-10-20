package com.kx.system.domain.enums.filing;

public enum SourceTypeEnum {
    customer("1", "客户介绍"),
    other("2", "第三方介绍"),
    mobai("3", "陌拜");


    /**
     * 唯一码
     */
    private String type;
    /**
     * 中文名
     */
    private String name;

    SourceTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
}
