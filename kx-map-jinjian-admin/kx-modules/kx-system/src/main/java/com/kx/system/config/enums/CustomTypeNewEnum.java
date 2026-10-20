package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CustomTypeNewEnum {
    PERFORMANCE(1,"绩效客户"),
    CREDIT(2,"授信客户"),
    FILING(3,"建档客户");

    private Integer code;
    private String name;

    CustomTypeNewEnum(Integer code, String name) {
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
        CustomTypeNewEnum[] values = CustomTypeNewEnum.values();
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
