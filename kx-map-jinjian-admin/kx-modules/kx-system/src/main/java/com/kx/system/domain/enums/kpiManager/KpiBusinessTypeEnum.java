package com.kx.system.domain.enums.kpiManager;

/**
 * @author xifan_h
 * @date 2022/4/12 16:43
 * @project bg-panorama-vr-center
 * @description 业务类型
 */
public enum KpiBusinessTypeEnum {
    RETAIL("RETAIL", "零售"),
    COMPANY("COMPANY", "公司"),
    BUSINESS("BUSINESS", "商户")
    ;

    private String code;
    private String name;

    KpiBusinessTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static KpiBusinessTypeEnum getByName(String name) {
        for (KpiBusinessTypeEnum value : KpiBusinessTypeEnum.values()) {
            if(value.name.equals(name)){
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
