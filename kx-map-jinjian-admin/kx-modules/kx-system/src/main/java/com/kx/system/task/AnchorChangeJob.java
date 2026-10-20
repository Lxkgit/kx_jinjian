package com.kx.system.task;

import com.alibaba.fastjson.JSON;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.PaChangeLog;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.mapper.PaChangeLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

//@Component
public class AnchorChangeJob extends BaseJob {
    @Resource
    private PaChangeLogMapper paChangeLogMapper;

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private CustomInfoSyncJob customInfoSyncJob;

//    @Value("${data-runtime.AnchorChangeJob:-}")
    private String cron;

    /**
     * 定时更新网格归属信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    protected void execute(Date date) {
        //需要重新落格的区域
        HashSet<String> codes = new HashSet<>();
        //查找昨日的网格变更记录
        List<PaChangeLog> paChangeLogs = paChangeLogMapper.selectYesterdayLog(date);
        for (PaChangeLog paChangeLog : paChangeLogs) {
            //网格操作
            if ("0".equals(paChangeLog.getLogType())) {
                //新增操作，获取新增区域的父编码
                if ("0".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigUpdate())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigUpdate(), PaAddrInfoType.class);
                    codes.add(paAddrInfoType.getParentCode());
                }
                //修改
                if ("1".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigUpdate())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigUpdate(), PaAddrInfoType.class);
                    if (StringUtils.isNotEmpty(paAddrInfoType.getName())) {
                        codes.add(paAddrInfoType.getParentCode());
                    }
                }
                //删除操作，获取操作前的区域编码
                if ("2".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    codes.add(paAddrInfoType.getCode());
                }
                //拆分网格，获取被切分的区域编码
                if ("20".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    codes.add(paAddrInfoType.getCode());
                }
                //切分网格，获取被拆分的区域编码
                if ("21".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    codes.add(paAddrInfoType.getCode());
                }
                //合并网格，获取被合并的两个网格编码
                if ("22".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    List<PaAddrInfoType> paAddrInfoTypes = JSON.parseArray(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
                        codes.add(paAddrInfoType.getCode());
                    }
                }
                //删除子网格，获取所有被删除的子网格编码
                if ("23".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    List<PaAddrInfoType> paAddrInfoTypes = JSON.parseArray(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
                        codes.add(paAddrInfoType.getCode());
                    }
                }
                //调整边界，获取被调整的网格编码
                if ("24".equals(paChangeLog.getOperateType()) && StringUtils.isNotEmpty(paChangeLog.getConfigBefore())) {
                    PaAddrInfoType paAddrInfoType = JSON.parseObject(paChangeLog.getConfigBefore(), PaAddrInfoType.class);
                    if (paAddrInfoType != null) {
                        codes.add(paAddrInfoType.getCode());
                    }
                }
            }
        }
        List<String> codeList = new ArrayList<>(codes);
        //待跑批的锚点
        List<AnchorPoint> anchorPoints = anchorPointMapper.selectAnchorPointByAreaCodes(codeList);
        customInfoSyncJob.initAnchor(anchorPoints);
    }

    @Override
    public String getJobDesc() {
        return "更新网格归属信息";
    }

    @Override
    public String getCron() {
        return cron;
    }
}
