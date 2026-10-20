package com.kx.system.mapper;

import com.kx.system.domain.MarketingTeam;
import com.kx.system.domain.dto.MarketingTeamDto;
import com.kx.system.domain.vo.MarketingTeamVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * auth ligen
 * 营销团队管理mapper
 */
@Mapper
public interface MarketingTeamMapper {
    //营销团队分页列表
    List<MarketingTeamDto> selectMarketingTeamPageList(MarketingTeamVo marketingTeam);


}
