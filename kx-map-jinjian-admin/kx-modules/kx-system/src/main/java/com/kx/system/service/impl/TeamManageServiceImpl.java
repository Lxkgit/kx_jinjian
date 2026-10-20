package com.kx.system.service.impl;

import com.kx.system.domain.dto.MarketingTeamDto;
import com.kx.system.domain.dto.MarketingTeamMemberDto;
import com.kx.system.domain.vo.MarketingTeamMemberVo;
import com.kx.system.domain.vo.MarketingTeamVo;
import com.kx.system.mapper.MarketingTeamMapper;
import com.kx.system.mapper.MarketingTeamMemberMapper;
import com.kx.system.service.TeamManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * auth ligen
 *
 * 营销团队管理业务层
 */
@Service
public class TeamManageServiceImpl implements TeamManageService {

    @Resource
    private MarketingTeamMemberMapper marketingTeamMemberMapper;
    @Resource
    private MarketingTeamMapper marketingTeamMapper;

    @Override
    public List<MarketingTeamDto> selectTeamPage(MarketingTeamVo marketingTeamVo) {
        return marketingTeamMapper.selectMarketingTeamPageList(marketingTeamVo);
    }

    @Override
    public List<MarketingTeamMemberDto> selectTeamMemberPage(MarketingTeamMemberVo marketingTeamMemberVo) {
        return marketingTeamMemberMapper.selectMarketingTeamMemberPageList(marketingTeamMemberVo);
    }
}
