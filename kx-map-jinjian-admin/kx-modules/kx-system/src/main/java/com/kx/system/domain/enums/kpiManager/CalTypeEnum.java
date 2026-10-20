package com.kx.system.domain.enums.kpiManager;

/**
 * @author xifan_h
 * @date 2022/4/12 16:43
 * @project bg-panorama-vr-center
 * @description 计算方式
 */
public enum CalTypeEnum {
    SYS("1", "系统计算"),
    ART("2", "人工填写")
    ;

    private String code;
    private String desc;

    CalTypeEnum(String code, String name) {
        this.code = code;
        this.desc = name;
    }

    public static CalTypeEnum getByName(String baseCalType) {
        for (CalTypeEnum value : CalTypeEnum.values()) {
            if(value.desc.equals(baseCalType)){
                return value;
            }
        }
        throw new IllegalArgumentException("未找到枚举");
    }

    public static String getDescByName(String name) {
        for (CalTypeEnum value : CalTypeEnum.values()) {
            if(value.desc.equals(name)){
                return value.getDesc();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
