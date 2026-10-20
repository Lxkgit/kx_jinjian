package com.kx.system.config.enums;

/**
 * 客户评级宽表与标签id映射
 */
public enum CustomerLevelLabelEnum {
    ZERO("0",21313622L),
    ONE("1",21313623L),
    TWO("2",21313624L),
    THREE("3",21313625L),
    FOUR("4",21313626L),
    FIVE("5",21313627L);

    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    CustomerLevelLabelEnum(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}
