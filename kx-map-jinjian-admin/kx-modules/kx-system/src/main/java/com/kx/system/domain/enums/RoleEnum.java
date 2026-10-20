package com.kx.system.domain.enums;



import com.kx.system.api.domain.SysRole;

import java.util.Arrays;
import java.util.List;


public enum RoleEnum {
    admin(1L,"admin","超级管理员"),
    ZXLD(26L,"ZXLD","支行领导"),
    KHJL(7L,"manager","客户经理"),
    ZHXJBRY(28L,"ZHXJBRY","支行经办人员"),
    EJZXLD(29L,"FLC/EJZXLD","分理处/二级支行领导"),
    ZHXLD(30L,"ZHXLD","总行领导"),
    ZHJBRY(31L,"ZHJBRY","总行经办人员"),
    ZXGLRY(32L,"ZXGLRY","总行管理员"),
    TDZ(6L,"teamPresident","团队长");

    private  Long roleId;
    private String roleKey;
    private  String name;

    RoleEnum(Long roleId, String roleKey, String name) {
        this.roleId = roleId;
        this.roleKey = roleKey;
        this.name = name;
    }

    public Long getRoleId() {
        return this.roleId;
    }
    public String getRoleKEY() {
        return roleKey;
    }
    public String getName() {
        return name;
    }

    /**
     * 管理角色
     */
    private static final List<RoleEnum> adminRoles = Arrays.asList(new RoleEnum[]{ZXLD,EJZXLD,ZHXLD,admin});

    /**
     * 判断是否包含管理角色
     * @param roles
     * @return
     */
    public static Boolean isAdminRole(List<SysRole> roles){
        try{
            for (SysRole role : roles) {
                if(adminRoles.contains(valueOf(role.getRoleKey()))){
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }catch (Exception e){
            return Boolean.FALSE;
        }
    }


}
