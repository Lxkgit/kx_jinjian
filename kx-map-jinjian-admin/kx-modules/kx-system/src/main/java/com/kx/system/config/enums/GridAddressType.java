package com.kx.system.config.enums;

public enum GridAddressType {
    MANAGER(1,"经营地址"),
    WORK(2,"工作地址"),
    FAMILY(3,"家庭地址"),
    RESIDENCE(4,"户籍地址");

    public static String getNameByCode(Integer code) {
        for (GridAddressType typeEnum : GridAddressType.values()) {
            if (typeEnum.getCode().equals(code)){
                return typeEnum.getName();
            }
        }
        return null;
    }

    private  Integer code;
    private  String name;

    GridAddressType(Integer code, String name) {
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
