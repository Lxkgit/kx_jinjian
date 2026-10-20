package com.kx.system.config.enums;

import com.kx.system.domain.common.EnumVO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CreditPromotionEnum {
    EBTITY(0,"有经营实体"),
    EAGER_RATE(1,"资金渴求度"),
    B_CARD(2,"B卡分优质客户");

    private Integer code;
    private String name;

    CreditPromotionEnum(Integer code, String name) {
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
    public static List<EnumVO> listCreditPromotion(){
        CreditPromotionEnum[] values = CreditPromotionEnum.values();
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
