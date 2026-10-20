package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CustomTypeEnum {
    PERSONAL(1,"个人客户"),
    COMPANY(2,"企业客户"),
    BUSINESS(3,"个体工商户客户");

    private Integer code;
    private String name;

    CustomTypeEnum(Integer code, String name) {
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
        CustomTypeEnum[] values = CustomTypeEnum.values();
        List<EnumVO> enumVOS = Arrays.stream(values).map(value -> {
                    EnumVO enumVO = new EnumVO();
                    enumVO.setName(value.getName());
                    enumVO.setCode(value.getCode());
                    return enumVO;
                })
                .collect(Collectors.toList());
        return enumVOS;
    }

    /**
     * 根据编码获取名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code){
        for (CustomTypeEnum value : CustomTypeEnum.values()) {
            if(code.equals(value.getCode())){
                return value.getName();
            }
        }
        return null;
    }

}
