//package com.kx.system.domain.task;
//
//import com.kx.system.config.exception.BusinessValidationException;
//import lombok.extern.slf4j.Slf4j;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *  数据转换工具类
// */
//@Slf4j
//public class DataSwitchUtil {
//    public static List<Integer> StingArrSwitchList(String[] arr) {
//        if (arr == null || arr.length == 0) {
//            throw new BusinessValidationException("数组不能为空!");
//        }
//        List<Integer> list = new ArrayList<>(arr.length);
//        for (int i = 0; i < arr.length; i++) {
//            Integer one = Integer.parseInt(arr[i]);
//            list.add(one);
//        }
//        return list;
//    }
//}
