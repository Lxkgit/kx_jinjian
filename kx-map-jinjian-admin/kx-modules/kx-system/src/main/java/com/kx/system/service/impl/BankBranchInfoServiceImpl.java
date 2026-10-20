package com.kx.system.service.impl;

import com.kx.system.domain.BankBranchInfo;
import com.kx.system.mapper.BankBranchInfoMapper;
import com.kx.system.service.IBankBranchInfoService;
import com.kx.system.util.EsSearchUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BankBranchInfoServiceImpl implements IBankBranchInfoService {

    @Resource
    private BankBranchInfoMapper bankBranchInfoMapper;

    @Override
    public List<BankBranchInfo> selectByAreaCode(String areaCode, boolean includeChildren, Integer bankType) {
        if (includeChildren) {
            return bankBranchInfoMapper.selectByAreaCodePrefix(EsSearchUtil.resolvePrefix(areaCode),EsSearchUtil.getLevel(areaCode)+1, bankType);
        }
        return bankBranchInfoMapper.selectByAreaCode(areaCode, bankType);
    }
}