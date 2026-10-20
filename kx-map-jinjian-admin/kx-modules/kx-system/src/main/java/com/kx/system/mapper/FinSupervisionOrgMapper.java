package com.kx.system.mapper;

import com.kx.system.domain.FinSupervisionOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinSupervisionOrgMapper {

    /** 精确匹配（本级），level=null 不过滤层级 */
    List<FinSupervisionOrg> selectByAreaCode(@Param("areaCode") String areaCode, @Param("level") Integer level);

    /** 前缀匹配（本级及下辖），prefix 已由 Service 层计算好，level=null 不过滤层级 */
    List<FinSupervisionOrg> selectByAreaCodePrefix(@Param("prefix") String prefix, @Param("level") Integer level);

    FinSupervisionOrg selectByPrimaryKey(Long id);

    int insert(FinSupervisionOrg record);

    int updateByPrimaryKeySelective(FinSupervisionOrg record);

    int deleteByPrimaryKey(Long id);
}