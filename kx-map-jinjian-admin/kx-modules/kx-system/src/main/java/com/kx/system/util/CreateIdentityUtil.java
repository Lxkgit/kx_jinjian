package com.kx.system.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author ligen
 * 身份证生成工具
 */
public class CreateIdentityUtil {

    // 生成随机身份证号码的方法
    public static String generateIdCardNumber() {
        // 生成地区码
        String areaCode = generateAreaCode();

        // 生成出生年月日
        String birthDate = generateBirthDate();

        // 生成顺序码
        String sequenceCode = generateSequenceCode();

        // 根据地区码、出生年月日和顺序码生成完整身份证号码
        String idCardNumber = areaCode + birthDate + sequenceCode;

        // 生成校验码
        String checkCode = generateCheckCode(idCardNumber);

        // 将校验码拼接到身份证号码末尾
        idCardNumber += checkCode;

        return idCardNumber;
    }

    // 生成地区码的方法
    public static String generateAreaCode() {
        return "440300"; // 深圳市地区码
    }
    // 生成顺序码的方法
    public static String generateSequenceCode() {
        Random random = new Random();
        int sequence = random.nextInt(999) + 1; // 生成1-999之间的随机数
        return String.format("%03d", sequence); // 将随机数格式化为3位数
    }

    // 生成校验码的方法
    public static String generateCheckCode(String idCardNumber) {
        return "X"; // 固定校验码
    }
    // 生成出生年月日的方法
    public static String generateBirthDate() {
        int year = 1900;
        int randomAge = RandomUserUtil.getRandomAge(20, 90);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.add(Calendar.YEAR, randomAge);
        calendar.add(Calendar.DAY_OF_YEAR,randomAge);

        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        return sdf.format(time); // 出生日期为1990年1月1日
    }
}
