package com.kx.common.datascope.aspect;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.BaseEntity;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.kx.common.datascope.utils.DataScopeUtil.getHighestRoleLevel;

/**
 * 数据过滤处理
 *
 * @author kangxu
 */
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope dataScope) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotNull(loginUser.getSysUser())) {
            SysUser user = loginUser.getSysUser();
            String dataScopeSql = getHighestRoleLevel(user).dataScopeSql(user, dataScope);
            if (StringUtils.isNotBlank(dataScopeSql)) {
                Object params = joinPoint.getArgs()[0];
                if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) params;
                    baseEntity.getParams().put(DATA_SCOPE, StringUtils.format(" AND ({})", dataScopeSql));
                }
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DATA_SCOPE, "");
        }
    }
}
