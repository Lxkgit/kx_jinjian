package com.kx.system.task;

import com.kx.common.core.utils.StringUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import com.kx.system.domain.sync.SyncDetpVo;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.util.FtpUtil;
import com.kx.system.util.ListSplitUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DeptSyncJob extends BaseJob {

    @Resource
    private SysDeptMapper sysDeptMapper;

//    @Value("${data-runtime.DeptSyncJob:-}")
    private String cron;

    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        List<SyncDetpVo> syncDetpVos = FtpUtil.parseFtpCsvFileToBean(FtpSyncFileEnum.ORG_FILE_PATH, date);
        List<SysDept> sysDepts = syncDetpVos.stream().map(sysDept -> {
            SysDept temp = new SysDept();
            temp.setOrgNum(sysDept.getOrgId());
            temp.setDeptName(sysDept.getOrgName());
            temp.setParentOrgNum(sysDept.getUpOrgId());
            return temp;
        }).sorted(Comparator.comparing(SysDept::getOrgNum)).collect(Collectors.toList());
        handleSysDepts(sysDepts);
    }

    private void handleSysDepts(List<SysDept> depts) {
        List<SysDept> existDepts = sysDeptMapper.selectAll();
        //判断新增和更新
        Map<String, SysDept> existDeptMap = existDepts.stream().collect(Collectors.toMap(SysDept::getOrgNum, Function.identity()));
        List<SysDept> update = depts.stream().filter(t -> existDeptMap.containsKey(t.getOrgNum())).collect(Collectors.toList());
        for (SysDept u : update) {
            u.setDeptId(existDeptMap.get(u.getOrgNum()).getDeptId());
            sysDeptMapper.updateDept(u);
        }
        List<SysDept> insert = new ArrayList<>(depts);
        insert.removeAll(update);
        //Batchinsert
        ListSplitUtils.split(insert, 50, temp -> sysDeptMapper.batchInsert(temp));

        //设置parentID和ancestors
        Map<String, SysDept> orgNumMap = depts.stream().collect(Collectors.toMap(SysDept::getOrgNum, Function.identity()));
        depts.forEach(dept -> {
            if (orgNumMap.containsKey(dept.getParentOrgNum())) {
                dept.setParentId(orgNumMap.get(dept.getParentOrgNum()).getDeptId());
                dept.setParentName(orgNumMap.get(dept.getParentOrgNum()).getDeptName());
            }
        });
        //设置ancestors
        depts.forEach(dept -> {
            LinkedList<Long> ancestors = new LinkedList<>();
            ancestors.addFirst(dept.getDeptId());
            String parentOrgNum = dept.getParentOrgNum();
            while (orgNumMap.containsKey(parentOrgNum)) {
                SysDept parentDept = orgNumMap.get(parentOrgNum);
                ancestors.addFirst(parentDept.getDeptId());
                parentOrgNum = parentDept.getParentOrgNum();
            }
            dept.setAncestors("," + StringUtils.join(ancestors, ",") + ",");
        });
        //update
        for (SysDept dept : depts) {
            sysDeptMapper.updateDept(dept);
        }
    }

    @Override
    public String getJobDesc() {
        return "同步机构信息";
    }

    @Override
    public String getCron() {
        return cron;
    }

}
