package com.kx.common.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author kangxu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 部门表的别名
     */
    String deptAlias() default "";

    /**
     * 部门字段的别名
     */
    String deptIdAlias() default "dept_id";

    /**
     * 用户表的别名
     */
    String userAlias() default "";

    /**
     * 用户Id字段的别名
     */
    String userIdAlias() default "user_id";

    /**
     * 权限字符（用于多个角色匹配符合要求的权限）默认根据权限注解@RequiresPermissions获取，多个权限用逗号分隔开来
     */
    String permission() default "";
}
