package com.kx.system.service.impl;

import com.kx.common.core.utils.bean.BeanUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.api.domain.SysUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.ActCustLog;
import com.kx.system.domain.ActMarketInfo;
import com.kx.system.domain.ActStatisticsManager;
import com.kx.system.domain.vo.ActMarketInfoVo;
import com.kx.system.mapper.ActCustLogMapper;
import com.kx.system.mapper.ActMarketInfoMapper;
import com.kx.system.mapper.ActMarketResultInfoMapper;
import com.kx.system.mapper.ActStatisticsManagerMapper;
import com.kx.system.service.ActMarketService;
import com.kx.system.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author  ligen
 */
@Service
@Slf4j
public class ActMarketServiceImpl implements ActMarketService {

    @Resource
    private ActMarketInfoMapper actMarketInfoMapper;
    @Resource
    private UserUtil userUtil;
    @Resource
    private ActCustLogMapper custLogMapper;
    @Autowired
    private ActStatisticsManagerMapper actStatisticsManagerMapper;


    @Override
    public List<ActMarketInfoVo> selectPageActMarketInfo(ActMarketInfoVo actMarketInfoVo) {

        if (actMarketInfoVo.getActTimeList() != null && actMarketInfoVo.getActTimeList().size() > 0) {
            actMarketInfoVo.setActTimeStart(new Date(actMarketInfoVo.getActTimeList().get(0)));
            actMarketInfoVo.setActTimeEnd(new Date(actMarketInfoVo.getActTimeList().get(1)));
        }
        List<ActMarketInfoVo> actMarketInfos = actMarketInfoMapper.selectPage(actMarketInfoVo);

        actMarketInfos.forEach(a ->{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<String> actTimeList = new ArrayList<>();

            actTimeList.add(sdf.format(a.getActTimeStart()));
            actTimeList.add(sdf.format(a.getActTimeEnd()));

            a.setActTimeList(actTimeList);
        });

        return actMarketInfos;
    }

    @Override
    public AjaxResult addActMarketInfo(ActMarketInfoVo actMarketInfoVo) {
        ActMarketInfo actMarketInfo = new ActMarketInfo();
        BeanUtils.copyProperties(actMarketInfoVo,actMarketInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (actMarketInfoVo.getActTimeList() == null || actMarketInfoVo.getActTimeList().size() <= 1) {
            throw new BusinessValidationException("活动时间不允许为空！");
        }
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(actMarketInfoVo.getActTimeList().get(0));
            endDate = sdf.parse(actMarketInfoVo.getActTimeList().get(1));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        actMarketInfo.setActTimeStart(startDate);
        actMarketInfo.setActTimeEnd(endDate);
        //活动状态判断
        if (startDate.after(new Date())){
            actMarketInfo.setActStatus(1);
        }else if (!startDate.after(new Date())
                && endDate.after(new Date()) ){
            actMarketInfo.setActStatus(2);
        } else if (endDate.after(new Date()) ) {
            actMarketInfo.setActStatus(3);
        }


        actMarketInfo.setCreateTime(new Date());
        int i = actMarketInfoMapper.insertSelective(actMarketInfo);
        return (i>0 ? AjaxResult.success() : AjaxResult.error());
    }



    @Override
    public AjaxResult updateActMarketInfo(ActMarketInfoVo actMarketInfoVo) {

        //获取当前登录人信息
        SysUser loginUser = userUtil.getLoginUser();
        ActMarketInfo actMarketInfo = new ActMarketInfo();
        BeanUtils.copyProperties(actMarketInfoVo,actMarketInfo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (actMarketInfoVo.getActTimeList() == null || actMarketInfoVo.getActTimeList().size() <= 1) {
            throw new BusinessValidationException("活动时间不允许为空！");
        }
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(actMarketInfoVo.getActTimeList().get(0));
            endDate = sdf.parse(actMarketInfoVo.getActTimeList().get(1));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        actMarketInfo.setActTimeStart(startDate);
        actMarketInfo.setActTimeEnd(endDate);

        //活动状态判断
        if (startDate.after(new Date())){
            actMarketInfo.setActStatus(1);
        }else if (!startDate.after(new Date())
                && endDate.after(new Date()) ){
            actMarketInfo.setActStatus(2);
        } else if (endDate.after(new Date()) ) {
            actMarketInfo.setActStatus(3);
        }

        actMarketInfo.setUpdateTime(new Date());
        actMarketInfo.setUpdateBy(loginUser.getNickName());
        actMarketInfo.setUpdateDeptName(loginUser.getDept().getDeptName());
        actMarketInfo.setUpdateManagerNum(loginUser.getManagerNum());
        actMarketInfo.setUpdateOrgNum(loginUser.getDept().getOrgNum());

        int i = actMarketInfoMapper.updateByPrimaryKeySelective(actMarketInfo);
        return (i>0 ? AjaxResult.success() : AjaxResult.error());
    }

    @Override
    public AjaxResult deleteActMarketInfo(Long id) {
        int i = actMarketInfoMapper.deleteByPrimaryKey(id);
        return (i>0 ? AjaxResult.success() : AjaxResult.error());
    }

    @Override
    public AjaxResult selectActMarketInfoAndResultById(Long id) {
        ActMarketInfoVo actMarketInfoVo = actMarketInfoMapper.selectActMarketInfoAndResultById(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> actTimeList = new ArrayList<>();
        actTimeList.add(sdf.format(actMarketInfoVo.getActTimeStart()));
        actTimeList.add(sdf.format(actMarketInfoVo.getActTimeEnd()));
        actMarketInfoVo.setActTimeList(actTimeList);
        return AjaxResult.success(actMarketInfoVo);
    }

    @Override
    public List<ActMarketInfoVo> selectActMarketInfoVoList(ActMarketInfoVo actMarketInfoVo) {
        return actMarketInfoMapper.selectActMarketInfoAndResultList(actMarketInfoVo);
    }

    @Override
    public List<ActCustLog> selectActCustLogList(Long actId) {
        return custLogMapper.selectByActId(actId);
    }

    @Override
    public List<ActStatisticsManager> selectActStatisticsManagerList(Long actId) {
        return actStatisticsManagerMapper.selectByActId(actId);
    }
}
