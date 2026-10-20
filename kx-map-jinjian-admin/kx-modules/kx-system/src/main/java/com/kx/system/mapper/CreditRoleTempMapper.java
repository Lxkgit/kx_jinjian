package com.kx.system.mapper;

import com.kx.system.domain.CreditRoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CreditRoleTempMapper {
    List<CreditRoleVO> list();
}
