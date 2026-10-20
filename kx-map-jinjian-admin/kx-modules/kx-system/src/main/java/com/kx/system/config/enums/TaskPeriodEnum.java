//package com.kx.system.config.enums;
//
//import com.kx.system.domain.task.TaskPeriodVo;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 任务周期
// */
//public enum TaskPeriodEnum {
//
//    DAY(1, "每天", 1),
//    WEEK(2, "每周", 7),
//    TWO_WEEK(3, "每两周", 14),
//    MONTH(4, "每月", 30),
//    SEASON(5, "每季度", 90),
//    YEAR(6, "每年", 365),
//    CUSTOMIZE(7, "自定义", 0),
//    ;
//    /**
//     * 唯一码
//     */
//    private Integer code;
//    /**
//     * 中文名
//     */
//    private String name;
//    /**
//     * 天数
//     */
//    private Integer days;
//
//    TaskPeriodEnum(Integer code, String name, Integer days) {
//        this.code = code;
//        this.name = name;
//        this.days = days;
//    }
//
//    public static List<TaskPeriodVo> listTaskPeriodVo() {
//        List<TaskPeriodVo> list = new ArrayList<>(TaskPeriodEnum.values().length);
//        for (TaskPeriodEnum typeEnum : TaskPeriodEnum.values()) {
//            list.add(new TaskPeriodVo(typeEnum.getCode(), typeEnum.getName(), typeEnum.getDays()));
//        }
//        return list;
//    }
//
//
//    public static Integer getDaysByCode(Integer code) {
//        for (TaskPeriodEnum value : TaskPeriodEnum.values()) {
//            if (value.getCode()==code) {
//                return value.getDays();
//            }
//        }
//        return null;
//    }
//
//    public static String getNameByCode(Integer code) {
//        for (TaskPeriodEnum value : TaskPeriodEnum.values()) {
//            if (value.getCode()==code) {
//                return value.getName();
//            }
//        }
//        return null;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getDays() {
//        return days;
//    }
//
//    public void setDays(Integer days) {
//        this.days = days;
//    }}
