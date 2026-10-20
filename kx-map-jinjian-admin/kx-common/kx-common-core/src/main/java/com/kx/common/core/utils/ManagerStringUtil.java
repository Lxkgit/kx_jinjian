package com.kx.common.core.utils;

public class ManagerStringUtil {

    //客户经理工号和名称拼接
    public static String managerNumSplitName(String managerNum,String managerName){
        return managerName+"("+managerNum+")";
    }

}
