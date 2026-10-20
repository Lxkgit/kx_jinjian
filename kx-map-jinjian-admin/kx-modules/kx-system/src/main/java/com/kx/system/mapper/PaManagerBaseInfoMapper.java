package com.kx.system.mapper;

import com.kx.system.domain.PaManagerBaseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaManagerBaseInfoMapper {

    PaManagerBaseInfo selectBaseInfoByManagerCode(String managerCode);

    List<PaManagerBaseInfo> selectBaseInfoAll();

    List<PaManagerBaseInfo> selectBaseInfoByManagerCodes(@Param("list") List<String> codes);

    List<PaManagerBaseInfo> selectBaseInfoByManagerCodesAndName(@Param("list") List<String> codes, @Param("name") String name);
}
