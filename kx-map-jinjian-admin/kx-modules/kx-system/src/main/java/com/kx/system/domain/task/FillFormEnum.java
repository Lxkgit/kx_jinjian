//package com.kx.system.domain.task;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  填写形式
// */
//public enum FillFormEnum {
//    WORD("1","文字"),
//    TEXT("2","文本"),
//    OPTION("3","是否选项"),
//    PIC("4","图片"),
//    LABEL("5","标签选择"),
//    SIGNIN("6","打卡签到");
//
//    /**唯一码*/
//    private String code;
//    /**中文名*/
//    private String name;
//
//    FillFormEnum(String code, String name) {
//        this.code = code;
//        this.name = name;
//    }
//
//    public static List<FillFormVo> listFillFormVo() {
//        List<FillFormVo> list = new ArrayList<>(FillFormEnum.values().length);
//        for (FillFormEnum typeEnum : FillFormEnum.values()) {
//            list.add(new FillFormVo(typeEnum.getCode(), typeEnum.getName()));
//        }
//        return list;
//    }
//
//    public static FillFormEnum getFillFormEnumByCode(String code) {
//        if (code != null) {
//            for (FillFormEnum fillFormEnum : FillFormEnum.values()) {
//                if (fillFormEnum.getCode().equals(code)){
//                    return fillFormEnum;
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
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }}
