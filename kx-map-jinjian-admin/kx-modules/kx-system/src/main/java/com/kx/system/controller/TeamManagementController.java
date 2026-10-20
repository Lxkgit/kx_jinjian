package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.dto.MarketingTeamDto;
import com.kx.system.domain.dto.MarketingTeamMemberDto;
import com.kx.system.domain.vo.MarketingTeamMemberVo;
import com.kx.system.domain.vo.MarketingTeamVo;
import com.kx.system.service.TeamManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"营销团队管理"})
@RestController
@RequestMapping("/team/manage")
public class TeamManagementController extends BaseController {


    @Resource
    private TeamManageService teamManageService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "团队管理分页")
    public TableDataInfo teamList(MarketingTeamVo marketingTeamVo) {
        startPage();
        List<MarketingTeamDto> marketingTeamDtos = teamManageService.selectTeamPage(marketingTeamVo);
        return getDataTable(marketingTeamDtos);
    }

    @GetMapping(value = "/member/list")
    @ApiOperation(value = "团队成员列表")
    public TableDataInfo teamMemberList(MarketingTeamMemberVo marketingTeamMemberVo) {
        startPage();
        List<MarketingTeamMemberDto> marketingTeamMemberDtos = teamManageService.selectTeamMemberPage(marketingTeamMemberVo);
        return getDataTable(marketingTeamMemberDtos);
    }



}
