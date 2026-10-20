package com.kx.common.datascope.domain.enums;

import com.kx.common.core.utils.StringUtils;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;

import static com.kx.common.datascope.utils.DataScopeUtil.getParentDeptIdByAncestors;
import static com.kx.common.datascope.utils.DataScopeUtil.isTheHeadBranchLevel;

public enum DataScopeEnums {

    /**
     * 超管 查询全部数据权限
     */
    DATA_SCOPE_ADMIN(0) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            return "";
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            return getParentDeptIdByAncestors(dept.getAncestors(), 1);
        }
    },

    /**
     * 总行管理员，查看总行及以下数据
     */
    DATA_SCOPE_HEAD(10) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            Long deptId = getHighestLevelDeptId(user.getDept());
            return StringUtils.format(
                    "{}.{} IN ( SELECT dept_id FROM sys_dept WHERE ancestors like concat('%,', {}, ',%'))",
                    dataScope.deptAlias(), dataScope.deptIdAlias(), deptId);
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            return getParentDeptIdByAncestors(dept.getAncestors(), 1);
        }
    },

    /**
     * 分行管理员，查看分行及以下数据
     */
    DATA_SCOPE_BRANCH(20) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            Long deptId = getHighestLevelDeptId(user.getDept());
            return StringUtils.format(
                    "{}.{} IN ( SELECT dept_id FROM sys_dept WHERE ancestors like concat('%,', {}, ',%') )",
                    dataScope.deptAlias(), dataScope.deptIdAlias(), deptId);
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            return getParentDeptIdByAncestors(dept.getAncestors(), 2);
        }
    },

    /**
     * 支行管理员，查看本部门数据
     */
    DATA_SCOPE_DEPT(30) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            // 如果是一级支行，则查询所有一级和二级支行数据
            if (isTheHeadBranchLevel(user.getDept())) {
                Long deptId = getParentDeptIdByAncestors(user.getDept().getAncestors(), 4);
                return StringUtils.format(
                        "{}.{} IN ( SELECT dept_id FROM sys_dept WHERE ancestors like concat('%,', {}, ',%') )",
                        dataScope.deptAlias(), dataScope.deptIdAlias(), deptId);
            } else {
                //只查询本机构数据
                return StringUtils.format("{}.{} = {} ", dataScope.deptAlias(), dataScope.deptIdAlias(), user.getDeptId());
            }
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            // 如果是一级支行，则查询所有一级和二级支行数据
            if (isTheHeadBranchLevel(dept)) {
                return getParentDeptIdByAncestors(dept.getAncestors(), 4);
            } else {
                //只查询本机构数据
                return dept.getDeptId();
            }
        }
    },

    /**
     * 团队长，查看团队下的数据
     */
    DATA_SCOPE_TEAM(40) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            if (StringUtils.isNotBlank(dataScope.userAlias())) {
                return StringUtils.format("{}.{} IN (" +
                        "SELECT u.user_id FROM F_OCRM_F_CM_TEAM_CUST_MANAGER f\n" +
                        "LEFT JOIN sys_user u ON f.user_id = u.manager_num\n" +
                        "WHERE mkt_team_id IN\n" +
                        "(select mkt_team_id from F_OCRM_F_CM_TEAM_CUST_MANAGER f inner join sys_user u on f.user_id = u.manager_num\n" +
                        "where is_leader = '1' and u.user_id = {})" +
                        ")", dataScope.userAlias(), dataScope.userIdAlias(), user.getUserId());
            } else {
                // 没有userAlias别名不查询任何数据
                return StringUtils.format("{}.{} = 0 ", dataScope.deptAlias(), dataScope.deptIdAlias());
            }
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            return null;
        }
    },

    /**
     * 仅本人数据权限
     */
    DATA_SCOPE_SELF(50) {
        @Override
        public String dataScopeSql(SysUser user, DataScope dataScope) {
            //查看本人数据
            if (StringUtils.isNotBlank(dataScope.userAlias())) {
                return StringUtils.format("{}.{} = {} ", dataScope.userAlias(), dataScope.userIdAlias(), user.getUserId());
            } else {
                // 数据权限为仅本人且没有userAlias别名不查询任何数据
                return StringUtils.format("{}.{} = 0 ", dataScope.deptAlias(), dataScope.deptIdAlias());
            }
        }

        @Override
        public Long getHighestLevelDeptId(SysDept dept) {
            return null;
        }
    };

    private Integer code;

    DataScopeEnums(Integer code) {
        this.code = code;
    }

    /**
     * 获取权限sql
     *
     * @param user
     * @return
     */
    public abstract String dataScopeSql(SysUser user, DataScope dataScope);

    /**
     * 获取用户有权限访问的最高级别的机构ID
     *
     * @param dept
     * @return
     */
    public abstract Long getHighestLevelDeptId(SysDept dept);

    public static DataScopeEnums getByCode(String code) {
        for (DataScopeEnums value : DataScopeEnums.values()) {
            if (value.code.toString().equalsIgnoreCase(code)) {
                return value;
            }
        }
        return DATA_SCOPE_SELF;
    }
}
