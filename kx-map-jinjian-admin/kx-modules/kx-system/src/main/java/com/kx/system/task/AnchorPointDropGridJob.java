package com.kx.system.task;

import com.alibaba.fastjson.JSON;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.util.vgrid.GridUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author zxy
 */
@Component
public class AnchorPointDropGridJob {


    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    private final String[] DEFAULT_AREA_CODES = {"330700"};

    public void initAnchor(List<AnchorPointGrid> anchorPointGrids) {
        this.initAnchor(anchorPointGrids, this.getChild(),DEFAULT_AREA_CODES);
    }

    /**
     * 锚点落格
     *
     * @param anchorPointGrids   需要落格的锚点 --> 设置code
     * @param parentChildAreaMap 所有父区域的下级区域集合
     * @param areaCodes          区域code
     */
    public void initAnchor(List<AnchorPointGrid> anchorPointGrids, HashMap<String, List<PaAddrInfoType>> parentChildAreaMap, String... areaCodes) {
        for (AnchorPointGrid anchorPointGrid : anchorPointGrids) {
            if (anchorPointGrid.getLon() != 0 && anchorPointGrid.getLat() != 0) {

                for (String code : areaCodes) {
                    // 判断当前锚点是否在区域code中, 如果存在则进行落格（设置code值）
                    selectGridByLocation(code, anchorPointGrid, parentChildAreaMap);
                    if (StringUtils.isNotEmpty(anchorPointGrid.getCode())) {
                        break;
                    }
                }
            }
        }
    }


    /**
     * @return 获取所有父区域的下级区域集合
     */
    public HashMap<String, List<PaAddrInfoType>> getChild() {
        HashMap<String, List<PaAddrInfoType>> map = new HashMap<>();
        // 查询所有父节点code
        List<String> parents = paAddrInfoTypeMapper.selectParentsByCode();
        for (String parent : parents) {
            //根据每个父节点code 查询出所有子节点
            List<PaAddrInfoType> paAddrInfoTypes = paAddrInfoTypeMapper.selectSelfAndChildByParentCode(parent);
            for (PaAddrInfoType paAddrInfoType : paAddrInfoTypes) {
                //解析边界网格数据
                double[][][][] coor = JSON.parseObject(paAddrInfoType.getCoordinates(), double[][][][].class);
                paAddrInfoType.setCoor(coor);
            }
            map.put(parent, paAddrInfoTypes);
        }
        return map;
    }



    /**
     * 递归确定点在哪个网格中
     *
     * @param code 顶级区域code
     * @param anchorPointGrid 需要落格的锚点
     * @param parentChildAreaMap 所有父区域的下级区域集合
     */
    public void selectGridByLocation(String code, AnchorPointGrid anchorPointGrid, HashMap<String, List<PaAddrInfoType>> parentChildAreaMap) {
        // 判断当前code区域是否存在
        List<PaAddrInfoType> addrInfoTypes = parentChildAreaMap.get(code);
        if (CollectionUtils.isEmpty(addrInfoTypes)) {
            //不存在 则返回
            return;
        }

        //当前code区域存在
        for (PaAddrInfoType addrInfoType : addrInfoTypes) {
            if (StringUtils.isNotEmpty(addrInfoType.getCoordinates())) {
                //获取边界数据
                double[][][][] coordinates = addrInfoType.getCoor();
                //判断点是否在边界中
                if (GridUtil.isPointInMultiPolygon(anchorPointGrid.getLon(), anchorPointGrid.getLat(), coordinates)) {
                    //设置边界网格code
                    anchorPointGrid.setCode(addrInfoType.getCode());
                    //递归设置最小网格
                    selectGridByLocation(addrInfoType.getCode(), anchorPointGrid, parentChildAreaMap);
                }
            }
        }
    }
}
