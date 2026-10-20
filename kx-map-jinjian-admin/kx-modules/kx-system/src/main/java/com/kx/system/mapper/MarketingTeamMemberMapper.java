package com.kx.system.mapper;

import com.kx.system.domain.MarketingTeamMember;
import com.kx.system.domain.dto.MarketingTeamMemberDto;
import com.kx.system.domain.vo.MarketingTeamMemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarketingTeamMemberMapper {

    //营销团队成员分页列表
    List<MarketingTeamMemberDto> selectMarketingTeamMemberPageList(MarketingTeamMemberVo marketingTeamMemberVo);

}
