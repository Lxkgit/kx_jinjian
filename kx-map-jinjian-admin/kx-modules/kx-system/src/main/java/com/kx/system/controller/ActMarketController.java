package com.kx.system.controller;

import com.kx.common.core.web.controller.BaseController;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.ActCustLog;
import com.kx.system.domain.ActStatisticsManager;
import com.kx.system.domain.vo.ActMarketInfoVo;
import com.kx.system.service.ActMarketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/marketing/activities")
public class ActMarketController extends BaseController {

    @Resource
    private ActMarketService actMarketService;

    @GetMapping("/page/list")
    public TableDataInfo pageList(ActMarketInfoVo actMarketInfoVo){
        startPage();
        List<ActMarketInfoVo> actMarketInfoVos = actMarketService.selectPageActMarketInfo(actMarketInfoVo);
        return getDataTable(actMarketInfoVos);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody ActMarketInfoVo actMarketInfoVo){
        return actMarketService.addActMarketInfo(actMarketInfoVo);
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody ActMarketInfoVo actMarketInfoVo){
        return actMarketService.updateActMarketInfo(actMarketInfoVo);
    }

    @GetMapping("/gain/{id}")
    public AjaxResult gain(@PathVariable("id") Long id){
        return actMarketService.selectActMarketInfoAndResultById(id);
    }


    @GetMapping("/bus/statistics/list")
    public TableDataInfo busList(ActMarketInfoVo actMarketInfoVo){
        startPage();
        List<ActMarketInfoVo> actMarketInfoVos = actMarketService.selectActMarketInfoVoList(actMarketInfoVo);
        return getDataTable(actMarketInfoVos);
    }

    @GetMapping("/cust/list")
    public TableDataInfo custList(ActMarketInfoVo actMarketInfoVo){
        startPage();
        List<ActCustLog> actCustLogs = actMarketService.selectActCustLogList(actMarketInfoVo.getId());
        return getDataTable(actCustLogs);
    }
    @GetMapping("/manager/statistics/list")
    public TableDataInfo managerStatisticsList(ActMarketInfoVo actMarketInfoVo){
        startPage();
        List<ActStatisticsManager> actStatisticsManagers = actMarketService.selectActStatisticsManagerList(actMarketInfoVo.getId());
        return getDataTable(actStatisticsManagers);
    }

}
