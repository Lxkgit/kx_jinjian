package com.kx.system.config.enums;

import com.kx.system.domain.vo.TaskStatusVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gary
 * @Date: Created in 11:20 2022/1/11
 */
public enum TaskChildStatusEnum {
    UN_FINISH(0,"未完成"),
    FINISH(1,"已完成"),
    MANUAL_END(2,"手动结束");

    private  Integer code;
    private  String name;

    TaskChildStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<TaskStatusVo> listChildTaskStatusVo() {
        List<TaskStatusVo> list = new ArrayList<>(TaskChildStatusEnum.values().length);
        for (TaskChildStatusEnum statusEnum : TaskChildStatusEnum.values()) {
            list.add(new TaskStatusVo(statusEnum.getCode(), statusEnum.getName()));
        }
        return list;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return name;
    }

    public static Integer getCodeByName(String name) {
        for (TaskChildStatusEnum value : TaskChildStatusEnum.values()) {
            if(value.getName().equals(name)){
                return value.getCode();
            }
        }
        return null;
    }
}


