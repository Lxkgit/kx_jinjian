package com.kx.system.config.enums;

public enum CreditCustomerLabelEnum {
//    五级分类
    NORMAL("0",21313635L),
    ATTENTION("1",21313636L),
    SECONDARY("2",21313637L),
    DOUBT("3",21313638L),
    LOST("4",21313639L),

//    担保方式
    DY("0",21313631L),
    ZY("1",21313632L),
    BZ("2",21313633L),
    XY("3",21313634L),

//    用途
    JY("0",21313721L),
    XF("1",21313722L),

//    逾欠
    YQ("1",21313640L),
    NOT_YQ("0",21313723L);

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

    CreditCustomerLabelEnum(String name, Long id) {
        this.name = name;
        this.id = id;
    }
}
