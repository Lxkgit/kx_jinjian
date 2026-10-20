package com.kx.system.service.impl;

import com.kx.common.datascope.domain.enums.DataScopeEnums;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.PaViewDeptMemberVo;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.service.IPaViewDeptMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.kx.common.datascope.utils.DataScopeUtil.getHighestRoleLevel;

@Service
@Slf4j
public class IPaViewDeptMemberServiceImpl implements IPaViewDeptMemberService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private IPaAddrInfoTypeService paAddrInfoTypeService;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Override
    public PaViewDeptMemberVo listPaViewDeptMembers(Long deptId) {
        List<PaViewDeptMemberVo> vos = new ArrayList<>();
        PaViewDeptMemberVo parent = null;
        if (deptId == null) {
            SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
            log.info("SysUser:{}", sysUser);
            DataScopeEnums dataScope = getHighestRoleLevel(sysUser);
            deptId = dataScope.getHighestLevelDeptId(sysUser.getDept());
            if (deptId == null) {
                //只查看本人数据
                parent = convert(sysUser);
                vos.add(parent);
            }
        }

        if (deptId != null) {
            SysDept sysDept = sysDeptMapper.selectDeptById(deptId);
            List<SysDept> childrenDepts = sysDeptMapper.listDirectChildrenDept(deptId);
            List<SysUser> users = sysUserMapper.listDeptUsers(deptId);

            parent = convert(sysDept);
            vos.add(parent);
            List<PaViewDeptMemberVo> childVo = childrenDepts.stream().map(this::convert).collect(Collectors.toList());
            parent.getChildren().addAll(childVo);
            vos.addAll(childVo);
            List<PaViewDeptMemberVo> userVos = users.stream().map(this::convert).collect(Collectors.toList());
            parent.getChildren().addAll(userVos);
            vos.addAll(userVos);
        }

        // 查询管辖的网络，并填充到addrTypes字段中
        searchDeptMemberAddrInfoType(vos);

        // 移除没有管辖机构的客户经理
        Iterator<PaViewDeptMemberVo> it = parent.getChildren().iterator();
        while (it.hasNext()) {
            PaViewDeptMemberVo next = it.next();
            if (next.getType() == 0 && CollectionUtils.isEmpty(next.getAddrTypes())) {
                it.remove();
            }
        }
        return parent;
    }

    private void searchDeptMemberAddrInfoType(List<PaViewDeptMemberVo> paViewDeptMemberVoList) {
        // 获取所有网格数据
        Long dictCode = paAddrInfoTypeService.getDefaultAddrInfoTypeId();
        if (dictCode == null) {
            // 没有网格数据
            return;
        }
        List<PaAddrInfoType> paAddrInfoTypeList = paAddrInfoTypeMapper.selectDeptIdAndUserIdByTypeCode(dictCode);

        // 机构/个人 管辖的网格ID集合
        Map<String, List<PaAddrInfoType>> managerIdMap = new HashMap<>();
        paAddrInfoTypeList.forEach(type -> {
            if (type.getDeptId() != null) {
                putToMap(managerIdMap, "" + type.getDeptId(), type);
            }
            if (type.getUserId() != null) {
                putToMap(managerIdMap, getTempUserId(type.getUserId()), type);
            }
        });
        // 填充管辖网格
        paViewDeptMemberVoList.forEach(vo -> {
            List<PaAddrInfoType> paAddrInfoTypes = managerIdMap.get(vo.getId());
            if (!CollectionUtils.isEmpty(paAddrInfoTypes)) {
                paAddrInfoTypeService.filterChildPaAddrInfoType(paAddrInfoTypes);
                vo.setAddrTypes(paAddrInfoTypes.stream().map(PaAddrInfoType::getCode).collect(Collectors.toSet()));
            }
        });
    }

    private PaViewDeptMemberVo convert(SysUser sysUser) {
        PaViewDeptMemberVo paViewDeptMemberVo = new PaViewDeptMemberVo();
        Long userId = sysUser.getUserId();
        String tempUserId = getTempUserId(userId);
        log.info("userId:{}", userId);
        log.info("tempUserId:{}", tempUserId);

        paViewDeptMemberVo.setId(tempUserId);
        paViewDeptMemberVo.setName(sysUser.getNickName());
        paViewDeptMemberVo.setCode(sysUser.getManagerNum());
        paViewDeptMemberVo.setParentId("" + sysUser.getDeptId());
        paViewDeptMemberVo.setType(0);
        return paViewDeptMemberVo;
    }

    private PaViewDeptMemberVo convert(SysDept sysDept) {
        PaViewDeptMemberVo paViewDeptMemberVo = new PaViewDeptMemberVo();
        paViewDeptMemberVo.setId("" + sysDept.getDeptId());
        paViewDeptMemberVo.setName(sysDept.getDeptName());
        paViewDeptMemberVo.setCode(sysDept.getOrgNum());
        paViewDeptMemberVo.setParentId("" + sysDept.getParentId());
        paViewDeptMemberVo.setType(1);
        return paViewDeptMemberVo;
    }

    private String getTempUserId(Long userId) {
        return "u" + userId;
    }

    private void putToMap(Map<String, List<PaAddrInfoType>> managerIdMap, String id, PaAddrInfoType infoType) {
        if (!managerIdMap.containsKey(id)) {
            managerIdMap.put(id, new ArrayList<>());
        }
        managerIdMap.get(id).add(infoType);
    }
}
