package com.kx.system.service;

import com.kx.system.domain.dto.MarketingTeamDto;
import com.kx.system.domain.dto.MarketingTeamMemberDto;
import com.kx.system.domain.vo.MarketingTeamMemberVo;
import com.kx.system.domain.vo.MarketingTeamVo;

import java.util.List;

/**
 * auth ligen
 * 营销团队管理
 */

public interface TeamManageService {

    /**
     * 营销团队分页查询
     * @param marketingTeamVo
     * @return
     */
    List<MarketingTeamDto> selectTeamPage(MarketingTeamVo marketingTeamVo);

    /**
     * 营销团队成员分页查询
     * @return
     */
    List<MarketingTeamMemberDto> selectTeamMemberPage(MarketingTeamMemberVo marketingTeamMemberVo);
}
