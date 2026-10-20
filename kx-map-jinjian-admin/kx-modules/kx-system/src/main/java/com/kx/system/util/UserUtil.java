package com.kx.system.util;

import com.kx.common.core.utils.ServletUtils;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xifan_h
 * @date 2022/4/24 18:20
 * @project bg-panorama-vr-center
 * @description
 */
@Component
public class UserUtil {

    @Autowired
    private TokenService tokenService;

    /**
     * 获取当前登录用户
     * @return
     */
    public SysUser getLoginUser(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return loginUser.getSysUser();
    }
}
