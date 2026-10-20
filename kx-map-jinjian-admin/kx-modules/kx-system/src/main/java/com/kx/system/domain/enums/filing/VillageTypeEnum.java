package com.kx.system.domain.enums.filing;


import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.vo.FillingVillageTypeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 小区类型
 */
public enum VillageTypeEnum {

    _1("1", "共享社·幸福里"),
    _2("2", "村"),
    _3("3", "社区"),
    _4("4", "小区"),
    _5("5", "园区");

    /**
     * 唯一码
     */
    private String code;
    /**
     * 中文名
     */
    private String name;

    VillageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static VillageTypeEnum getVillageTypeEnumByCode(String code) {
        if (StringUtils.isNotEmpty(code)) {
            for (VillageTypeEnum typeEnum : VillageTypeEnum.values()) {
                if (typeEnum.getCode().equals(code)) {
                    return typeEnum;
                }
            }
        }
        return null;
    }

    public static List<FillingVillageTypeVo> listFillingVillageTypeVo() {
        List<FillingVillageTypeVo> list = new ArrayList<>(VillageTypeEnum.values().length);
        for (VillageTypeEnum typeEnum : VillageTypeEnum.values()) {
            list.add(new FillingVillageTypeVo(typeEnum.getCode(), typeEnum.getName()));
        }
        return list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
