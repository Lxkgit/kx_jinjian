package com.kx.system.config.enums;

import com.kx.system.domain.vo.TaskObjectVo;

import java.util.ArrayList;
import java.util.List;

/**
 *  任务对象枚举
 */
public enum TaskObjectEnum {
    CUSTOM(1,"个人客户"),
    COMPANY(2,"商户客户"),
    BUSINESS(3,"公司客户");

    /**唯一码*/
    private Integer code;
    /**中文名*/
    private String name;

    TaskObjectEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<TaskObjectVo> listTaskObjectVo() {
        List<TaskObjectVo> list = new ArrayList<>(TaskObjectEnum.values().length);
        for (TaskObjectEnum typeEnum : TaskObjectEnum.values()) {
            list.add(new TaskObjectVo(typeEnum.getCode(), typeEnum.getName()));
        }
        return list;
    }

    public static TaskObjectEnum getTaskObjectEnumByCode(Integer code) {
        if (code != null) {
            for (TaskObjectEnum typeEnum : TaskObjectEnum.values()) {
                if (typeEnum.getCode().equals(code)){
                    return typeEnum;
                }
            }
        }
        return null;
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
    }}
