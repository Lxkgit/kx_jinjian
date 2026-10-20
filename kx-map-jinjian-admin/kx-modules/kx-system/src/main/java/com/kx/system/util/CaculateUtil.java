package com.kx.system.util;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author xifan_h
 * @date 2022/4/13 15:02
 * @project bg-panorama-vr-center
 * @description 计算工具类
 */
public class CaculateUtil {

    /**
     * 计算字符串表达式
     *
     * @param expression
     * @return
     */
    public static BigDecimal caculateStrExp(String expression) throws JexlException {
        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression jexlExpression = jexlEngine.createExpression(expression);
        Object evaluate = jexlExpression.evaluate(null);
        return new BigDecimal(String.valueOf(evaluate));
    }

    public static Boolean between(BigDecimal num, BigDecimal begin, BigDecimal end) {
        if (num.compareTo(begin) > 0 && num.compareTo(end) < 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 比较num1 ope num2 成立 则返回true, ope为比较运算符(>=/<=/</>/=)
     *
     * @param num1
     * @param ope
     * @param num2
     * @return
     */
    public static Boolean compare(BigDecimal num1, String ope, BigDecimal num2) {
        switch (ope) {
            case ">=":
                return num1.compareTo(num2) >= 0;
            case "<=":
                return num1.compareTo(num2) <= 0;
            case "<":
                return num1.compareTo(num2) < 0;
            case ">":
                return num1.compareTo(num2) > 0;
            case "=":
                return num1.compareTo(num2) == 0;
            default:
                return Boolean.FALSE;
        }
    }

    /**
     * 计算BigDecimal均值 null值不计入
     *
     * @param list  集合
     * @param field 数据字段
     * @return
     */
    public static BigDecimal calAvgVal(List list, String field) {
        BigDecimal nume = BigDecimal.ZERO;
        BigDecimal deno = BigDecimal.ZERO;
        for (Object o : list) {
            BigDecimal num = BeanUtil.getProperty(o, field);
            if (num != null) {
                nume = nume.add(num);
                deno = deno.add(BigDecimal.ONE);
            }
        }
        if (deno.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        return nume.divide(deno, 6, RoundingMode.HALF_UP);
    }

    /**
     * 包含null的减法计算
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal subtractWithNull(BigDecimal num1, BigDecimal num2) {
        if (num1 == null && num2 == null) {
            return null;
        } else if (num1 != null && num2 == null) {
            return num1;
        } else if (num1 == null && num2 != null) {
            return BigDecimal.ZERO.subtract(num2);
        }
        return num1.subtract(num2);
    }
}
