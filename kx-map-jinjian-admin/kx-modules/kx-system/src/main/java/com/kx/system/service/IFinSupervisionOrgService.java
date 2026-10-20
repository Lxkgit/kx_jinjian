package com.kx.system.service;

import com.kx.system.domain.FinSupervisionOrg;

import java.util.List;

/**
 * 金融监管机构服务接口
 */
public interface IFinSupervisionOrgService {

    /**
     * 按行政区划代码查询金融监管机构
     *
     * @param areaCode        行政区划代码（省330000 / 市330100 / 区330102 / 街道330102001 / 村330102001001）
     * @param includeChildren true=本级及下辖，false=仅本级
     * @param level           层级过滤：1-省级 2-市级 3-区级，null 不过滤
     */
    List<FinSupervisionOrg> selectByAreaCode(String areaCode, boolean includeChildren, Integer level);
}