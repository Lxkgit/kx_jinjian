package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AndOrEnum {
    OR(0,"或"),
    AND(1,"且");

    private Integer code;
    private String name;

    AndOrEnum(Integer code, String name) {
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
     * 获取所有客户类型
     * @return
     */
    public static List<EnumVO> listCustomType(){
        AndOrEnum[] values = AndOrEnum.values();
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
