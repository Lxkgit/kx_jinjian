package com.kx.system.config.enums;

import com.kx.system.domain.vo.TaskObjectVo;
import com.kx.system.domain.vo.TaskStatusVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务状态
 */
public enum TaskStatusEnum {
    NOT_STARTED(0,"待开始"),
    RUNNING(1,"考核中"),
    END(2,"已结束");

    /**
     * 状态代码
     */
    private Integer code;
    /**
     * 状态名称
     */
    private String name;

    TaskStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<TaskStatusVo> listTaskStatusVo() {
        List<TaskStatusVo> list = new ArrayList<>(TaskStatusEnum.values().length);
        for (TaskStatusEnum statusEnum : TaskStatusEnum.values()) {
            list.add(new TaskStatusVo(statusEnum.getCode(), statusEnum.getName()));
        }
        return list;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code获取状态名称
     * @param code
     * @return
     */
    public static String getNameByCode(Integer code){
        for (TaskStatusEnum value : TaskStatusEnum.values()) {
            if(value.getCode().equals(code)){
                return value.getName();
            }
        }
        return null;
    }
}
