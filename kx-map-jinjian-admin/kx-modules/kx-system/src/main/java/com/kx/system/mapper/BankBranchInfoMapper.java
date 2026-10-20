package com.kx.system.mapper;

import com.kx.system.domain.BankBranchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankBranchInfoMapper {

    /** 精确匹配（本级） */
    List<BankBranchInfo> selectByAreaCode(@Param("areaCode") String areaCode, @Param("bankType") Integer bankType);

    /** 前缀匹配（本级及下辖），prefix 已由 Service 层计算好 */
    List<BankBranchInfo> selectByAreaCodePrefix(@Param("prefix") String prefix,@Param("level") Integer level, @Param("bankType") Integer bankType);

    BankBranchInfo selectByPrimaryKey(Long id);

    int insert(BankBranchInfo record);

    int updateByPrimaryKeySelective(BankBranchInfo record);

    int deleteByPrimaryKey(Long id);
}