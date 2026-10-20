package com.kx.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 小区类型
 */
@Data
public class FillingVillageTypeVo implements Serializable {
    /**
     * code
     */
    private String code;
    /**
     * 中文名
     */
    private String name;

    public FillingVillageTypeVo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
