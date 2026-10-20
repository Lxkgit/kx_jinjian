package com.kx.system.mapper;

import com.kx.system.domain.sync.SysTeamMemberVo;
import com.kx.system.domain.sync.SysTeamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysTeamMapper {

    void batchInsertSysTeams(@Param("sysTeamVos") List<SysTeamVo> sysTeamVos);

    void batchInsertSysTeamMembers(@Param("sysTeamMemberVos") List<SysTeamMemberVo> sysTeamMemberVos);

    void trancateSysTeams();

    void trancateSysTeamMembers();
}
