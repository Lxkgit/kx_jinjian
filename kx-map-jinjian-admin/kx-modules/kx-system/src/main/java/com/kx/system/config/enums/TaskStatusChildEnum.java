package com.kx.system.config.enums;

/**
 * 任务状态
 */
public enum TaskStatusChildEnum {
    NOT_FINISHED(0,"未完成"),
    FINISHED(1,"已完成"),
    END(2,"已结束");

    /**
     * 状态代码
     */
    private Integer code;
    /**
     * 状态名称
     */
    private String name;

    TaskStatusChildEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
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
        for (TaskStatusChildEnum value : TaskStatusChildEnum.values()) {
            if(value.getCode().equals(code)){
                return value.getName();
            }
        }
        return null;
    }
}
