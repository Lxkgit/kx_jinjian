//package com.kx.system.config.enums;
//
//import com.kx.common.core.utils.StringUtils;
//import com.kx.system.domain.task.TaskMethodVo;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 任务方式
// */
//public enum TaskMethodEnum {
//    PHONE(1,"电话沟通"),
//    ONSITE(2,"实地沟通"),
//    WECHAT(3,"微信沟通"),
//    INBANK(4,"行内沟通");
//
//    /**唯一码*/
//    private Integer code;
//    /**中文名*/
//    private String name;
//
//    TaskMethodEnum(Integer code, String name) {
//        this.code = code;
//        this.name = name;
//    }
//
//    public static List<TaskMethodVo> listTaskMethodVo() {
//        List<TaskMethodVo> list = new ArrayList<>(TaskMethodEnum.values().length);
//        for (TaskMethodEnum typeEnum : TaskMethodEnum.values()) {
//            list.add(new TaskMethodVo(typeEnum.getCode(),typeEnum.getName()));
//        }
//        return list;
//    }
//
//    public static TaskMethodEnum  getTaskMethodEnumByName(String name) {
//        if (StringUtils.isNotEmpty(name)) {
//            for (TaskMethodEnum typeEnum : TaskMethodEnum.values()) {
//                if (typeEnum.getName().equals(name)) {
//                    return typeEnum;
//                }
//            }
//        }
//        return null;
//    }
//    public static TaskMethodEnum  getTaskMethodEnumByCode(Integer code) {
//        if (null != code) {
//            for (TaskMethodEnum typeEnum : TaskMethodEnum.values()) {
//                if (typeEnum.getCode().equals(code)) {
//                    return typeEnum;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static String  getTaskMethodNameByCode(Integer code) {
//        if (null != code) {
//            for (TaskMethodEnum typeEnum : TaskMethodEnum.values()) {
//                if (typeEnum.getCode().equals(code)) {
//                    return typeEnum.getName();
//                }
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
