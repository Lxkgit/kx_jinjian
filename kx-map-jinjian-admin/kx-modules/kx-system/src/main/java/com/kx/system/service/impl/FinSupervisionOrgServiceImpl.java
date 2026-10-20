package com.kx.system.service.impl;

import com.kx.system.domain.FinSupervisionOrg;
import com.kx.system.mapper.FinSupervisionOrgMapper;
import com.kx.system.service.IFinSupervisionOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FinSupervisionOrgServiceImpl implements IFinSupervisionOrgService {

    @Resource
    private FinSupervisionOrgMapper finSupervisionOrgMapper;

    @Override
    public List<FinSupervisionOrg> selectByAreaCode(String areaCode, boolean includeChildren, Integer level) {
        if (includeChildren) {
            return finSupervisionOrgMapper.selectByAreaCodePrefix(resolvePrefix(areaCode), level);
        }
        return finSupervisionOrgMapper.selectByAreaCode(areaCode, level);
    }

    /**
     * 根据行政区划编码计算 LIKE 前缀
     * <ul>
     *   <li>省级（6位，后4位全0，如 330000）→ 取前2位（33）</li>
     *   <li>市级（6位，后2位为00，如 330100）→ 取前4位（3301）</li>
     *   <li>区县/街道/村（其余情况）→ 取完整编码</li>
     * </ul>
     */
    private String resolvePrefix(String areaCode) {
        if (areaCode == null || areaCode.length() < 6) {
            return areaCode;
        }
        if (areaCode.length() == 6) {
            if (areaCode.endsWith("0000")) {
                return areaCode.substring(0, 2);
            }
            if (areaCode.endsWith("00")) {
                return areaCode.substring(0, 4);
            }
        }
        return areaCode;
    }
}