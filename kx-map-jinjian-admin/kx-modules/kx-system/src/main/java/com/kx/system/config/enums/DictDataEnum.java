package com.kx.system.config.enums;

/**
 * @author frr
 */

public enum DictDataEnum {
    IS_TRUE(1,"是"),
    IS_FALSE(0,"否");

    private Integer key;
    private String name;

    DictDataEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
