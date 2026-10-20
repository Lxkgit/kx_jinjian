package com.kx.system.task;

import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.CreditRoleVO;
import com.kx.system.domain.SysUserRole;
import com.kx.system.mapper.CreditRoleTempMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.mapper.SysUserRoleMapper;
import com.kx.system.util.ListSplitUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRoleSyncJob extends BaseJob {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserRoleMapper userRoleMapper;
    @Resource
    private CreditRoleTempMapper creditRoleTempMapper;

//    @Value("${data-runtime.UserRoleSyncJob:-}")
    private String cron;

    private Set<Long> EMPTY_SET = new HashSet<>();

    private static final Map<String, Long> roleMap = new HashMap<>();

    static {
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (1, '超级管理员');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (2, '总行行长');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (3, '总行网格管理');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (4, '分行行长');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (5, '分行网格管理员');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (6, '支行行长');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (7, '支行网格管理员');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (8, '团队长');
        //INSERT INTO RMMSUAT2.sys_role (role_id, role_name) VALUES (9, '客户经理');

        //A001
        //AA001
        //AAA001
        roleMap.put("A001", 4L);
        roleMap.put("AA001", 6L);
        roleMap.put("AAA001", 6L);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        List<CreditRoleVO> roleVoS = creditRoleTempMapper.list();
        if (CollectionUtils.isEmpty(roleVoS)) {
            return;
        }
        Map<Long, Set<Long>> existRoles = new HashMap<>();
        List<SysUserRole> dblist = userRoleMapper.listAll();
        for (SysUserRole r : dblist) {
            if (!existRoles.containsKey(r.getUserId())) {
                existRoles.put(r.getUserId(), new HashSet<>());
            }
            existRoles.get(r.getUserId()).add(r.getRoleId());
        }

        List<SysUser> sysUsers = sysUserMapper.selectUserList(new SysUser());
        List<SysUserRole> roleList = new ArrayList<>();
        Map<String, String> userRoles = roleVoS.stream().collect(Collectors.toMap(CreditRoleVO::getUserId, CreditRoleVO::getRoleId));

        for (SysUser sysUser : sysUsers) {
            //默认都是客户经理
            if (!existRoles.getOrDefault(sysUser.getUserId(), EMPTY_SET).contains(9L)) {
                roleList.add(new SysUserRole(sysUser.getUserId(), 9L));
            }
            if (userRoles.containsKey(sysUser.getManagerNum())) {
                String roleName = userRoles.get(sysUser.getManagerNum());
                if (roleMap.containsKey(roleName)) {
                    Long roleId = roleMap.get(roleName);
                    if (!existRoles.getOrDefault(sysUser.getUserId(), EMPTY_SET).contains(roleId)) {
                        roleList.add(new SysUserRole(sysUser.getUserId(), roleId));
                    }
                }
            }
        }
        //添加超管
        if (!existRoles.getOrDefault(1L, EMPTY_SET).contains(1L)) {
            SysUserRole admin = new SysUserRole();
            admin.setRoleId(1L);
            admin.setUserId(1L);
            roleList.add(admin);
        }
        //批量插入
        ListSplitUtils.split(roleList, 100, temp -> userRoleMapper.batchUserRole(temp));
    }

    @Override
    public String getJobDesc() {
        return "用户角色同步";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
