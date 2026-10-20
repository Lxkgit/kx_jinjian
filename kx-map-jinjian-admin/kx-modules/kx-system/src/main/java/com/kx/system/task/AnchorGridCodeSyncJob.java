package com.kx.system.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.mapper.*;
import com.kx.system.service.ISysUserService;
import com.kx.system.service.es.EsAnchorPointService;
import com.kx.system.util.EsSearchUtil;
import com.kx.system.util.GeocodingUtil;
import com.kx.system.util.vgrid.GridUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnchorGridCodeSyncJob {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Resource
    private AnchorPointMapper anchorPointMapper;

    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;

    @Resource
    private GeocodingUtil geocodingUtil;

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private CreditCustomerInfoMapper creditCustomerInfoMapper;

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private EsAnchorPointService esAnchorPointService;

    @Resource
    private CashServicePointsMapper csMapper;


    /**
     * 锚点落格，客户更新归属网格信息
     *
     * @param anchorPoints 待处理锚点
    // */
    public void initAnchor(List<AnchorPoint> anchorPoints) {
        List<String> codes = defineArea();

        //本级以及下级区域列表
        HashMap<String, List<PaAddrInfoType>> parentChildAreaMap = getChild();

        for (AnchorPoint anchorPoint : anchorPoints) {
            if (anchorPoint.getLon() != null && anchorPoint.getLat() != null) {

                for (String code : codes) {
                    selectGridByLocation(code, anchorPoint, parentChildAreaMap);
                    if (StringUtils.isNotEmpty(anchorPoint.getBelongGrid())) {
                        break;
                    }
                }

                //现金服务网点所属网格
                AnchorPoint anchor = new AnchorPoint();
                anchor.setId(anchorPoint.getId());
                anchor.setBelongGrid(anchorPoint.getBelongGrid());
                anchor.setBelongGridName(anchorPoint.getBelongGridName());
                anchorPointMapper.updateAnchorGridCode(anchor);
            }

        }
    }
    /**
     * 锚点落格，客户更新归属网格信息
     *
     * @param anchorPoints 待处理锚点
    // */
    public void initAnchor2(String code,int addrLevel,List<AnchorPoint> anchorPoints,List<PaAddrInfoType> paAddrInfoTypeList) {
        for (AnchorPoint anchorPoint : anchorPoints) {
            if (anchorPoint.getLon() != null && anchorPoint.getLat() != null) {

                selectGridByLocation(code,addrLevel,anchorPoint, paAddrInfoTypeList);

                //现金服务网点所属网格
                AnchorPoint anchor = new AnchorPoint();
                anchor.setId(anchorPoint.getId());
                anchor.setBelongGrid(anchorPoint.getBelongGrid());
                anchor.setBelongGridName(anchorPoint.getBelongGridName());
                anchorPointMapper.updateAnchorGridCode(anchor);
            }

        }
    }
    /**
     * 划定客户所在区域
     *
     * @return
     */
    private List<String> defineArea() {
        List<String> codes = new ArrayList<>();
        codes.add("330000");
        // codes.add("330200");
        return codes;
    }

    /**
     * 获取所有父区域的下级区域集合，边界处理
     *
     * @return
     */
    private HashMap<String, List<PaAddrInfoType>> getChild() {
        HashMap<String, List<PaAddrInfoType>> map = new HashMap<>();
        List<String> parents = paAddrInfoTypeMapper.selectParentsByCode();
        for (String parent : parents) {
            List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectSelfAndChildByParentCode(parent);
            for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
                double[][][][] coor = JSON.parseObject(paAddrInfoType.getCoordinates(), double[][][][].class);
                paAddrInfoType.setCoor(coor);
            }
            map.put(parent, paAddrInfoTypes);
        }
        return map;

    }

    /**
     * 递归确定点在哪个网格中
     *initAnchor
     * @param code
     * @param anchorPoint
     * @param bianjieMap
     */
    public void selectGridByLocation(String code, AnchorPoint anchorPoint, HashMap<String, List<PaAddrInfoType>> bianjieMap) {
        List<PaAddrInfoType> addrInfoTypes = bianjieMap.get(code);
        if (CollectionUtils.isEmpty(addrInfoTypes)) {
            return;
        }

        for (PaAddrInfoType addrInfoType : addrInfoTypes) {
            if (StringUtils.isNotEmpty(addrInfoType.getCoordinates())) {
                double[][][][] coordinates = addrInfoType.getCoor();
                if (GridUtil.isPointInMultiPolygon(anchorPoint.getLon().doubleValue(), anchorPoint.getLat().doubleValue(), coordinates)) {
                    anchorPoint.setBelongGrid(addrInfoType.getCode());
                    anchorPoint.setBelongGridName(addrInfoType.getName());
                    anchorPoint.setGridManager(addrInfoType.getUserManagerNum());
                    anchorPoint.setAreaCode(addrInfoType.getCode());
                    anchorPoint.setUpdateTime(new Date());
                    anchorPoint.setAncestorCodes(addrInfoType.getAncestorCodes());
                    selectGridByLocation(addrInfoType.getCode(), anchorPoint, bianjieMap);
                }
            }
        }
    }

    /**
     * 递归确定点在哪个网格中
     *initAnchor
     * @param code
     * @param anchorPoint
     * @param allAddrInfoTypes
     */
    public void selectGridByLocation(String code,int addrLevel,AnchorPoint anchorPoint, List<PaAddrInfoType> allAddrInfoTypes) {
        List<PaAddrInfoType> addrInfoTypes = allAddrInfoTypes.stream().filter(a->a.getCode().startsWith(code)&&a.getAddrLevel().equals(addrLevel)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(addrInfoTypes)) {
            return;
        }

        for (PaAddrInfoType addrInfoType : addrInfoTypes) {
            if (StringUtils.isNotEmpty(addrInfoType.getCoordinates())) {
                double[][][][] coordinates = JSONObject.parseObject(addrInfoType.getCoordinates(), double[][][][].class);
                if (GridUtil.isPointInMultiPolygon(anchorPoint.getLon().doubleValue(), anchorPoint.getLat().doubleValue(), coordinates)) {
                    anchorPoint.setBelongGrid(addrInfoType.getCode());
                    anchorPoint.setBelongGridName(addrInfoType.getName());
                    anchorPoint.setGridManager(addrInfoType.getUserManagerNum());
                    anchorPoint.setAreaCode(addrInfoType.getCode());
                    anchorPoint.setUpdateTime(new Date());
                    anchorPoint.setAncestorCodes(addrInfoType.getAncestorCodes());
                    selectGridByLocation(EsSearchUtil.resolvePrefix(addrInfoType.getCode()),addrLevel+1, anchorPoint, allAddrInfoTypes);
                }
            }
        }
    }
}
