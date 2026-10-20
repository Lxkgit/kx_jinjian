package com.kx.system.domain.enums.filing;


import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.vo.FillingVillageTypeVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 表模板性质类型
 */
public enum ModelTypeEnum {

    family("1", "个人客户"),
    company("2", "企业客户"),
    business("3", "商户客户"),
    other("4", "其他客户");

    /**
     * 唯一码
     */
    private String code;
    /**
     * 中文名
     */
    private String name;

    ModelTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ModelTypeEnum getModelTypeEnumByCode(String code) {
        if (StringUtils.isNotEmpty(code)) {
            for (ModelTypeEnum typeEnum : ModelTypeEnum.values()) {
                if (typeEnum.getCode().equals(code)) {
                    return typeEnum;
                }
            }
        }
        return null;
    }

    public static List<FillingVillageTypeVo> listModelTypeVo() {
        List<FillingVillageTypeVo> list = new ArrayList<>(VillageTypeEnum.values().length);
        for (ModelTypeEnum typeEnum : ModelTypeEnum.values()) {
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
