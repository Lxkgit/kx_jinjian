package com.kx.system.config.enums;

/**
 * 建档地址类型码值对应关系
 */
public enum FilingAddressTypeEnum {
    ONE("0","301"),
    TWO("1","302"),
    THREE("2","304"),
    FOUR("3","306"),
    FIVE("4","307"),
    SIX("5","399"),
    SEVEN("6","401"),
    EIGHT("7","403"),
    NINE("8","404"),
    TEN("9","499");

    private String code;
    private String name;

    FilingAddressTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(String code) {
        for (FilingAddressTypeEnum value : FilingAddressTypeEnum.values()) {
            if(code.equals(value.getCode())){
                return value.getName();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
