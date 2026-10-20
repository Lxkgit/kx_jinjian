//package com.kx.system.config.enums;
//
//import com.kx.system.domain.task.PriorityVo;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  任务优先级,值越小,级别越高
// */
//public enum TaskLevelEnum {
//    FIRST_LEVEL(1,"一级"),
//    SECOND_LEVEL(2,"二级"),
//    THIRD_LEVEL(3,"三级"),
//    FORTH_LEVEL(4,"四级");
//
//    /**唯一码*/
//    private Integer code;
//    /**中文名*/
//    private String name;
//
//    TaskLevelEnum(Integer code, String name) {
//        this.code = code;
//        this.name = name;
//    }
//
//    public static List<PriorityVo> listPriorityVo() {
//        List<PriorityVo> list = new ArrayList<>(TaskLevelEnum.values().length);
//        for (TaskLevelEnum typeEnum : TaskLevelEnum.values()) {
//            list.add(new PriorityVo(typeEnum.getCode(), typeEnum.getName()));
//        }
//        return list;
//    }
//
//    public static String getNameByCode(Integer code) {
//        for (TaskLevelEnum typeEnum : TaskLevelEnum.values()) {
//            if (typeEnum.getCode().equals(code)){
//                return typeEnum.getName();
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
//    }}
