package com.kx.system.config.enums;

/**
 * @author xiatian
 */

public enum IsMaintainedEnum {
    IS_MAINTAINED(1,"已维护"),
    NOT_MAINTAINED(0,"未维护");

    private Integer key;
    private String name;

    IsMaintainedEnum(Integer key, String name) {
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
