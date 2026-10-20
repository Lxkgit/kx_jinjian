package com.kx.system.domain.enums.filing;

/**
 * 建档状态
 */
public enum FilingStatuEnum {
    TEMP("0"),
    BASE("1"),
    VALID("2");

    private String code;

    FilingStatuEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static FilingStatuEnum getByCode(String code) {
        for (FilingStatuEnum value : FilingStatuEnum.values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        return null;
    }
}
