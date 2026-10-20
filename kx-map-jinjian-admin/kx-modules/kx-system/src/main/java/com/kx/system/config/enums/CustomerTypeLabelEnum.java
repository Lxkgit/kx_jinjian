package com.kx.system.config.enums;

/**
 * 客户类型标签映射
 */
public enum CustomerTypeLabelEnum {
    PERSON("1",21313628L,21313651L),
    COMPANY("2",21313629L,21313652L),
    BUSINESS("3",21313630L,21313653L);

    private String name;

    //绩效类型标签
    private Long perId;

//    授信类型标签
    private Long creId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public Long getCreId() {
        return creId;
    }

    public void setCreId(Long creId) {
        this.creId = creId;
    }

    CustomerTypeLabelEnum(String name, Long perId, Long creId) {
        this.name = name;
        this.perId = perId;
        this.creId = creId;
    }
}
