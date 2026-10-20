package com.kx.system.domain.enums.filing;

import lombok.Data;

/**
 * 地址类型枚举
 */

public enum AddressTypeEnum {

    address_one("0", "户籍地址"),
    address_two("1", "常住地址"),
    address_three("2", "现居地址"),
    address_four("3", "邮寄地址"),
    address_five("4", "通讯地址"),
    address_six("5", "单位地址"),
    address_seven("6", "证件地址"),
    address_eight("7", "收货地址"),
    address_night("8", "个人其他地址"),
    address_ten("9", "注册地址"),
    address_one_one("10", "邮寄地址"),
    address_one_two("11", "经营地址"),
    address_one_three("12", "办公地址"),
    address_one_four("13", "机构地址"),
    address_one_five("14", "英文地址"),
    address_one_six("15", "家庭地址");


    /**
     * 唯一码
     */
    private String type;
    /**
     * 中文名
     */
    private String name;

    AddressTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public static AddressTypeEnum getTypeByName(String name) {
        for (AddressTypeEnum value : AddressTypeEnum.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

}
