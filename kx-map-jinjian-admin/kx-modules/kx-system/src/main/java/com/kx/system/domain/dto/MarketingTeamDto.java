package com.kx.system.domain.dto;

import com.kx.system.domain.MarketingTeam;
import lombok.Data;

/**
 * auth ligen
 */
@Data
public class MarketingTeamDto extends MarketingTeam {

    /**
     * 团队成员数量
     */
    private int teamNum;
}
