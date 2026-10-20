package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OfflineAttractEnum {
    MORTGAGE(0,"有抵押贷款"),
    UNDER_AVERAGE(1,"振兴贷低于他行平均授信"),
    ABOVE_AVERAGE(2,"振兴贷高于他行在用余额");

    private Integer code;
    private String name;

    OfflineAttractEnum(Integer code, String name) {
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
    public static List<EnumVO> listOfflineAttract(){
        OfflineAttractEnum[] values = OfflineAttractEnum.values();
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
