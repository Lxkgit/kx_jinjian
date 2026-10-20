package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FiveLevelEnum {
    IS_NORMAL(0,"正常"),
    IS_FOCUS(1,"关注"),
    IS_SECONDARY(2,"次级"),
    IS_DUBIOUS(3,"可疑"),
    IS_LOSS(4,"损失");

    private Integer code;
    private String name;

    FiveLevelEnum(Integer code, String name) {
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

    /**
     * 获取所有用途
     * @return
     */
    public static List<EnumVO> listFiveLevel(){
        FiveLevelEnum[] values = FiveLevelEnum.values();
        List<EnumVO> enumVOS = Arrays.stream(values).map(value -> {
                    EnumVO enumVO = new EnumVO();
                    enumVO.setName(value.getName());
                    enumVO.setCode(value.getCode());
                    return enumVO;
                })
                .collect(Collectors.toList());
        return enumVOS;
    }
}
