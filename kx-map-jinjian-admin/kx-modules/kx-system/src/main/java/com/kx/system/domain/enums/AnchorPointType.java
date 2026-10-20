package com.kx.system.domain.enums;

public enum AnchorPointType {
    PERFORMANCE("1", "绩效客户"),
    CREDIT("2", "授信客户"),
    FILING("3", "建档客户"),
    UNKNOW("", "未知");

    private String code;
    private String name;

    AnchorPointType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AnchorPointType getByCode(String code) {
        for (AnchorPointType value : AnchorPointType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return UNKNOW;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
