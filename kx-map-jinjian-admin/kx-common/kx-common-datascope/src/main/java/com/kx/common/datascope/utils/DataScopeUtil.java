package com.kx.common.datascope.utils;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.domain.enums.DataScopeEnums;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysRole;
import com.kx.system.api.domain.SysUser;
import org.springframework.util.CollectionUtils;

import static com.kx.common.datascope.domain.enums.DataScopeEnums.DATA_SCOPE_SELF;

public class DataScopeUtil {

    /**
     * 判断是否是一级支行
     * 如果机构号前五位和父级机构号前五位相同，则认为是一级支行
     *
     * @param dept
     * @return
     */
    public static boolean isTheHeadBranchLevel(SysDept dept) {
        if (StringUtils.isEmpty(dept.getOrgNum())
                || StringUtils.isEmpty(dept.getParentOrgNum())
                || dept.getOrgNum().length() < 5
                || dept.getParentOrgNum().length() < 5) {
            return false;
        }
        return dept.getOrgNum().substring(0, 5).equalsIgnoreCase(dept.getParentOrgNum().substring(0, 5));
    }

    /**
     * 获取父级机构id
     * getParentDeptIdByAncestors("100,200,300",1) -> 100
     * getParentDeptIdByAncestors("100,200,300",2) -> 200
     * getParentDeptIdByAncestors("100,200,300",3) -> 300
     */
    public static Long getParentDeptIdByAncestors(String ancestors, int i) {
        String[] split = StringUtils.split(ancestors, ",");
        return split.length >= i ? Long.valueOf(split[i - 1]) : 0;
    }

    public static DataScopeEnums getHighestRoleLevel(SysUser user) {
        if (CollectionUtils.isEmpty(user.getRoles())) {
            return DATA_SCOPE_SELF;
        }
        //获取范围最大的权限
        return user.getRoles().stream()
                .map(SysRole::getDataScope)
                .min(String::compareTo)
                .map(DataScopeEnums::getByCode)
                .orElse(DATA_SCOPE_SELF);
    }

    /**
     * 获取用户有权限访问的最高级别的机构ID
     *
     * @param user
     * @return
     */
    public static Long getHighestLevelDeptId(SysUser user) {
        return getHighestRoleLevel(user).getHighestLevelDeptId(user.getDept());
    }
}
