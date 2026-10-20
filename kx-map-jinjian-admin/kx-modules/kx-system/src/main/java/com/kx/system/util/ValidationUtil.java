package com.kx.system.util;

import com.kx.system.config.exception.BusinessValidationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 参数校验工具类
 */
public class ValidationUtil {
    /**
     * 所有参数均不能为空
     *
     * @param o
     */
    public static void notNullAll(Object... o) {
        if (null == o) {
            throw new BusinessValidationException("参数不能为空！");
        }
        for (Object object : o) {
            if (null == object) {
                throw new BusinessValidationException("参数不能为空！");
            }
            if (object instanceof String && "".equals(object)) {
                throw new BusinessValidationException("参数" + object.getClass().getSimpleName() + "不能为空！");
            }
        }
    }

    /**
     * 判断value是否是非空
     */
    public static void notNull(Object value) {
        if (Objects.isNull(value)) {
            throw new BusinessValidationException("参数不能为空");
        }
    }

    /**
     * 判断value是否是非空
     */
    public static void notNull(Object value, String errorMessage) {
        if (Objects.isNull(value)) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断集合是否是非空
     */
    public static void notEmpty(Collection<?> coll, String errorMessage) {
        if (coll==null || coll.isEmpty()) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断一个字符串是否为空串
     */
    public static void notEmpty(String str, String errorMessage) {
        if (str==null || "".equals(str.trim())) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断一个对象数组是否为空
     */
    public static void notEmpty(Object[] objects, String errorMessage) {
        if (objects==null || objects.length == 0) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断一个Map是否为空
     */
    public static void notEmpty(Map<?, ?> map, String errorMessage) {
        if (map==null || map.isEmpty()) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断字符串是否为空白
     */
    public static void notBlank(String value, String errorMessage) {
        if (StringUtils.isBlank(value)) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断字符串是否为空白
     */
    public static void notBlank(String value) {
        if (StringUtils.isBlank(value)) {
            throw new BusinessValidationException("参数不能为空！");
        }
    }


    /**
     * 判断value是否与期望值相等
     *
     * @param except 期待值
     * @param value
     */
    public static void equals(int except, Integer value, String errorMessage) {
        if (value == null) {
            throw new BusinessValidationException(errorMessage);
        }

        if (except != value.intValue()) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    public static void equals(long except, Long value, String errorMessage) {
        if (value == null) {
            throw new BusinessValidationException(errorMessage);
        }

        if (except != value.longValue()) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    /**
     * 判断value是否与期望值不相等
     *
     * @param except
     * @param value
     */
    public static void notEquals(int except, Integer value, String errorMessage) {
        if (value == null) {
            throw new BusinessValidationException(errorMessage);
        }

        if (except == value.intValue()) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    public static void isTrue(Boolean value, String errorMessage) {
        if (value == null) {
            throw new BusinessValidationException(errorMessage);
        }

        if (BooleanUtils.isNotTrue(value)) {
            throw new BusinessValidationException(errorMessage);
        }
    }

    public static void isFalse(Boolean value, String errorMessage) {
        if (value == null) {
            throw new BusinessValidationException(errorMessage);
        }

        if (BooleanUtils.isNotFalse(value)) {
            throw new BusinessValidationException(errorMessage);
        }
    }
}
