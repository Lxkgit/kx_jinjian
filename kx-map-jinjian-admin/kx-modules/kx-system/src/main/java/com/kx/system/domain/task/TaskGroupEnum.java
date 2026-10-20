//package com.kx.system.domain.task;
//
//import com.kx.common.core.utils.StringUtils;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  任务群组枚举
// */
//public enum TaskGroupEnum {
//    visit("10001","走访","visit","taskHandleVisitService");
//
//    /**唯一码*/
//    private String code;
//    /**中文名*/
//    private String chineseName;
//    /**英文名*/
//    private String englishName;
//    /**对应接口名*/
//    private String service;
//
//    TaskGroupEnum(String code, String chineseName, String englishName, String service) {
//        this.code = code;
//        this.chineseName = chineseName;
//        this.englishName = englishName;
//        this.service = service;
//    }
//
//    public static List<TaskGroupVo> listTaskGroupVo() {
//        List<TaskGroupVo> list = new ArrayList<>(TaskGroupEnum.values().length);
//        for (TaskGroupEnum typeEnum : TaskGroupEnum.values()) {
//            list.add(new TaskGroupVo(typeEnum.getCode(), typeEnum.getChineseName(),typeEnum.getEnglishName()));
//        }
//        return list;
//    }
//
//    public static TaskGroupEnum getTaskGroupEnumByCode(String code) {
//        if (StringUtils.isNotEmpty(code)) {
//            for (TaskGroupEnum typeEnum : TaskGroupEnum.values()) {
//                if (typeEnum.getCode().equals(code)){
//                    return typeEnum;
//                }
//            }
//        }
//        return null;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getChineseName() {
//        return chineseName;
//    }
//
//    public void setChineseName(String chineseName) {
//        this.chineseName = chineseName;
//    }
//
//    public String getEnglishName() {
//        return englishName;
//    }
//
//    public void setEnglishName(String englishName) {
//        this.englishName = englishName;
//    }
//
//    public String getService() {
//        return service;
//    }
//
//    public void setService(String service) {
//        this.service = service;
//    }}
