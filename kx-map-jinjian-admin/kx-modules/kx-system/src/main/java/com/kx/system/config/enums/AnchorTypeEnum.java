package com.kx.system.config.enums;

public enum AnchorTypeEnum {
    PERSON(1,"个人锚点"),
    COMPANY(2,"企业锚点"),
    BUSINESS(3,"商户锚点"),
    OTHER(4,"其他锚点");

    private  Integer code;
    private  String name;

    AnchorTypeEnum(Integer code, String name) {
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

    public static String getNameByCode(Integer code) {
        for (AnchorTypeEnum typeEnum : AnchorTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)){
                return typeEnum.getName();
            }
        }
        return null;
    }
}
