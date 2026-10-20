package com.kx.system.task;

import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.domain.sync.SyncUserVo;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.util.FtpUtil;
import com.kx.system.util.ListSplitUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Component
public class UserSyncJob extends BaseJob {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
//    @Value("${data-runtime.UserSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        List<SysDept> sysDepts = sysDeptMapper.selectDeptList(new SysDept());
        Map<String, SysDept> codeDeptMap = sysDepts.stream().collect(Collectors.toMap(SysDept::getOrgNum, Function.identity()));

        List<SyncUserVo> sysUserVos = FtpUtil.parseFtpCsvFileToBean(FtpSyncFileEnum.USER_FILE_PATH, date);
        List<SysUser> users = sysUserVos.stream().map(sysUserVo -> {
                    SysUser temp = new SysUser();
                    temp.setManagerNum(sysUserVo.getEmpId());
                    temp.setUserName(sysUserVo.getEmpId());
                    temp.setNickName(sysUserVo.getEmpName());
                    //默认启用
                    temp.setStatus("0");
                    if (codeDeptMap.containsKey(sysUserVo.getUnit5Code())) {
                        temp.setDeptId(codeDeptMap.get(sysUserVo.getUnit5Code()).getDeptId());
                    }
                    return temp;
                }
        ).sorted(Comparator.comparing(SysUser::getManagerNum)).collect(Collectors.toList());
        handleSysUsers(users);
    }

    private void handleSysUsers(List<SysUser> users) {
        //区分更新和新增
        List<SysUser> existUsers = sysUserMapper.selectUserList(new SysUser());
        Map<String, SysUser> userCodeMap = existUsers.stream()
                .filter(t -> t.getManagerNum() != null)
                .collect(Collectors.toMap(SysUser::getManagerNum, Function.identity()));

        List<SysUser> updateUsers = new ArrayList<>();
        List<SysUser> insertUsers = new ArrayList<>();

        for (SysUser user : users) {
            if (userCodeMap.containsKey(user.getManagerNum())) {
                SysUser existUser = new SysUser();
                existUser.setUserId(userCodeMap.get(user.getManagerNum()).getUserId());
                existUser.setUserName(user.getUserName());
                existUser.setNickName(user.getNickName());
                existUser.setDeptId(user.getDeptId());
                existUser.setStatus("0");
                updateUsers.add(existUser);
            } else {
                insertUsers.add(user);
            }
        }

        //新增用户
        ListSplitUtils.split(insertUsers, 50, temp -> sysUserMapper.batchInsert(temp));
        //更新已有用户
        for (SysUser updateUser : updateUsers) {
            sysUserMapper.updateUser(updateUser);
        }
    }

    @Override
    public String getJobDesc() {
        return "客户经理数据同步";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
