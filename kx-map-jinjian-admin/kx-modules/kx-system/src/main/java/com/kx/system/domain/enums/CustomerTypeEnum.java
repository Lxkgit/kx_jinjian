package com.kx.system.domain.enums;

public enum CustomerTypeEnum {
    performance_customer("绩效客户","1"),
    credit_customer("授信客户","2"),
    filing_customer("建档客户","3"),
    acquiring_customer("收单客户","4"),
    exclude_village_loan_customer("不含乡贷客户","5");


    private  String type;
    private  String code;


    CustomerTypeEnum(String type, String code) {
        this.type = type;
        this.code = code;
    }
    public String getType() {
        return type;
    }
    public String getCode() {
        return code;
    }

}
