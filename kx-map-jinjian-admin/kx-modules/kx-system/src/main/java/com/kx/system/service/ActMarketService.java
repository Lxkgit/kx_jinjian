package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.ActCustLog;
import com.kx.system.domain.ActMarketResultInfo;
import com.kx.system.domain.ActStatisticsManager;
import com.kx.system.domain.vo.ActMarketInfoVo;

import java.util.List;

/**
 * @author  ligen
 */
public interface ActMarketService {

    /**
     * 分页查询
     * @param actMarketInfoVo
     * @return
     */
    List<ActMarketInfoVo>  selectPageActMarketInfo(ActMarketInfoVo actMarketInfoVo);

    /**
     * 新增营销活动
     * @param actMarketInfoVo
     * @return
     */
    AjaxResult addActMarketInfo(ActMarketInfoVo actMarketInfoVo);

    /**
     * 更新营销活动
     */
    AjaxResult updateActMarketInfo(ActMarketInfoVo actMarketInfoVo);

    /**
     * 删除
     * @param id
     * @return
     */
    AjaxResult deleteActMarketInfo(Long id);

    /**
     * 营销活动成功查询
     */
    AjaxResult selectActMarketInfoAndResultById(Long id);


    List<ActMarketInfoVo> selectActMarketInfoVoList(ActMarketInfoVo actMarketInfoVo);

    List<ActCustLog> selectActCustLogList(Long actId);

    List<ActStatisticsManager> selectActStatisticsManagerList(Long actId);

}
