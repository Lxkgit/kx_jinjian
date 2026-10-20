package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum GuaranteeMethodEnum {
    IS_MORTGAGE(0,"抵押"),
    IS_PLEDGE(1,"质押"),
    IS_ENSURE(2,"保证"),
    IS_CREDIT(3,"信用");

    private Integer code;
    private String name;

    GuaranteeMethodEnum(Integer code, String name) {
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
     * 获取所有担保方式
     * @return
     */
    public static List<EnumVO> listGuaranteeMethod(){
        GuaranteeMethodEnum[] values = GuaranteeMethodEnum.values();
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
