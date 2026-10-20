package com.kx.system.service.impl;

import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.ServletUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingCustomerTransfer;
import com.kx.system.mapper.FilingCustomerInfoMapper;
import com.kx.system.mapper.FilingCustomerTransferMapper;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.mapper.SysUserMapper;
import com.kx.system.service.FilingCustomerTransferService;
import com.kx.system.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kx.common.datascope.domain.enums.DataScopeEnums.DATA_SCOPE_DEPT;

/**
 * <p>
 * 建档客户移交审批表 服务实现类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-09
 */
@Slf4j
@Service
public class FilingCustomerTransferServiceImpl implements FilingCustomerTransferService {

    @Resource
    private FilingCustomerTransferMapper filingCustomerTransferMapper;
    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<FilingCustomerTransfer> list(FilingCustomerTransfer filingCustomerTransfer) {
        return filingCustomerTransferMapper.list(filingCustomerTransfer);
    }

    @Override
    public FilingCustomerTransfer getById(Long id) {
        return filingCustomerTransferMapper.getById(id);
    }

    @Override
    public FilingCustomerTransfer saveFilingCustomerTransfer(FilingCustomerTransfer filingCustomerTransfer) {
        FilingCustomerTransfer existFilingCustomerTransfer = null;
        if (filingCustomerTransfer.getId() != null) {
            existFilingCustomerTransfer = filingCustomerTransferMapper.getById(filingCustomerTransfer.getId());
        }
        if (existFilingCustomerTransfer == null) {
            filingCustomerTransfer.setCreateTime(DateUtils.getNowDate());
            filingCustomerTransfer.setUpdateTime(DateUtils.getNowDate());
            filingCustomerTransferMapper.insertFilingCustomerTransfer(filingCustomerTransfer);
        } else {
            BeanUtils.copyBeanIgnoreNull(filingCustomerTransfer, existFilingCustomerTransfer);
            existFilingCustomerTransfer.setUpdateTime(DateUtils.getNowDate());
            filingCustomerTransferMapper.updateFilingCustomerTransfer(existFilingCustomerTransfer);
        }
        return filingCustomerTransfer;
    }

    @Override
    public String deleteByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new BusinessValidationException("参数不能为空");
        }
        String[] idsArr = ids.split(",");
        if (idsArr == null || idsArr.length == 0) {
            throw new BusinessValidationException("参数不能为空");
        }
        for (int i = 0; i < idsArr.length; i++) {
            deleteById(Long.valueOf(idsArr[i]));
        }
        return "ok";
    }

    public void deleteById(Long id) {
        filingCustomerTransferMapper.deleteById(id);
    }


    @Override
    public String moveBatch(List<FilingCustomerTransfer> list) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("移交列表为空");
        }
        List<Long> ids = list.stream().map(FilingCustomerTransfer::getFilingId).collect(Collectors.toList());
        List<FilingCustomerTransfer> transfers = filingCustomerTransferMapper.getByFilingIds(ids);
        if (transfers != null && transfers.size() > 0) {
            throw new RuntimeException("客户当中存在已提交审批的客户");
        }
        //获取当前登录人工号
        SysUser sysUser = tokenService.getLoginUser(ServletUtils.getRequest()).getSysUser();

        //移交对象所属机构
        String moveInOrgCode = list.get(0).getMoveInOrgCode();
        SysDept moveInDept = sysDeptMapper.selectDeptByCode(moveInOrgCode);

        //查询移交客户所属的机构
        List<Long> filingIds = list.stream().map(FilingCustomerTransfer::getFilingId).collect(Collectors.toList());
        List<FilingCustomerInfo> filingCustomerInfos = filingCustomerInfoMapper.listByIds(filingIds);
        Map<Long, String> filingIdOrgCodeMap = filingCustomerInfos.stream().collect(Collectors.toMap(FilingCustomerInfo::getId, FilingCustomerInfo::getFilingOrgCode));
        Map<Long , String> belongGridNameMap = filingCustomerInfos.stream().collect(Collectors.toMap(FilingCustomerInfo::getId, FilingCustomerInfo::getBelongGridName));

        //查询所有的机构
        List<String> orgCodeList = filingIdOrgCodeMap.values().stream().collect(Collectors.toList());
        List<SysDept> sysDepts = sysDeptMapper.listDeptByCode(orgCodeList);
        Map<String, SysDept> deptAncestoerMap = sysDepts.stream().collect(Collectors.toMap(SysDept::getOrgNum, Function.identity()));

        for (FilingCustomerTransfer t : list) {
            t.setMoveStatus("0");
            t.setMoveDate(DateUtils.getNowDate());
            t.setCreateTime(DateUtils.getNowDate());
            t.setUpdateTime(DateUtils.getNowDate());
            //移交人
            t.setMoveOutManagerCode(sysUser.getManagerNum());
            t.setMoveOutManagerName(sysUser.getNickName());
            t.setMoveOutOrgCode(sysUser.getDept().getOrgNum());
            t.setMoveOutOrgName(sysUser.getDept().getDeptName());
            //审批人
            t.setMoveJudgeDeptId(getJudgeOrgCode(deptAncestoerMap.get(filingIdOrgCodeMap.get(t.getFilingId())), moveInDept));
            //所属网格
            t.setBelongGridName(belongGridNameMap.get(t.getFilingId()));
        }
        filingCustomerTransferMapper.insertBatch(list);
        return "ok";
    }

    /**
     * 获取审批机构
     *
     * @param filingDept
     * @param moveInDept
     * @return
     */
    private Long getJudgeOrgCode(SysDept filingDept, SysDept moveInDept) {
        if (null == filingDept || null == moveInDept) return null;
        if (filingDept.getOrgNum().equalsIgnoreCase(moveInDept.getOrgNum())) {
            //如果接收人和建档客户在同一个机构，则要判断这个机构是否是一级支行
            //如果是一级支行，则需要一级支行支行长审批，一级支行支行长对应的权限需要往上提一级
            return DATA_SCOPE_DEPT.getHighestLevelDeptId(filingDept);
        }
        String filingOrgAncestor = filingDept.getAncestors();
        String moveOutOrgAncestor = moveInDept.getAncestors();
        return Long.valueOf(getCommonAncestor(filingOrgAncestor.split(","), moveOutOrgAncestor.split(",")));
    }

    private Long getCommonAncestor(String[]... ancestors) {
        if (Arrays.isNullOrEmpty(ancestors) || Arrays.isNullOrEmpty(ancestors[0])) {
            return 0L;
        }
        int i = 0;
        String dept_id = null;
        while (true) {
            if (ancestors[0].length - 1 < i) {
                return dept_id == null ? 0L : Long.valueOf(dept_id);
            }
            String temp_dept_id = ancestors[0][i];
            for (int j = 1; j < ancestors.length; j++) {
                if (!temp_dept_id.equalsIgnoreCase(ancestors[j][i])) {
                    return dept_id == null ? 0L : Long.valueOf(dept_id);
                }
            }
            dept_id = temp_dept_id;
            i++;
        }
    }

    @Override
    @Transactional
    public String judgeBatch(List<FilingCustomerTransfer> list) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("审批列表为空");
        }
        SysUser sysUser = tokenService.getLoginUser().getSysUser();
        for (FilingCustomerTransfer o : list) {
            FilingCustomerTransfer transfer = filingCustomerTransferMapper.getById(o.getId());
            transfer.setMoveStatus(o.getMoveStatus());
            transfer.setMoveJudgeName(sysUser.getUserName());
            transfer.setMoveJudgeDeptName(sysUser.getDept().getDeptName());
            transfer.setMoveJudgeCode(sysUser.getManagerNum());
            if ("1".equals(o.getMoveStatus())) {
                FilingCustomerInfo customerInfo = new FilingCustomerInfo();
                customerInfo.setId(transfer.getFilingId());
                customerInfo.setFilingManagerName(transfer.getMoveInManagerName());
                customerInfo.setFilingManagerCode(transfer.getMoveInManagerCode());
                customerInfo.setFilingOrgCode(transfer.getMoveInOrgCode());
                customerInfo.setFilingOrgName(transfer.getMoveInOrgName());
                filingCustomerInfoMapper.updateFilingCustomerInfo(customerInfo);
            }
            filingCustomerTransferMapper.updateFilingCustomerTransfer(transfer);
        }
        //审批完成后批量更新建档表归属机构以及归属客户经理
        return "ok";
    }

}
