package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kx.common.core.utils.DateUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.redis.service.RedisService;
import com.kx.common.security.service.TokenService;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.config.constants.Constants;
import com.kx.system.config.enums.IsMaintainedEnum;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.config.exception.CustomException;
import com.kx.system.domain.*;
import com.kx.system.domain.dto.GridBoundaryDTO;
import com.kx.system.domain.grid.AddrInfoDto;
import com.kx.system.domain.grid.AddrInfoParam;
import com.kx.system.domain.grid.GridPoint;
import com.kx.system.domain.model.SysNetwork;
import com.kx.system.domain.vo.AddrInfoVO;
import com.kx.system.domain.vo.PaAddrBaseInfo;
import com.kx.system.domain.vo.TaskSelectVo;
import com.kx.system.mapper.PaAddrBaseInfoMapper;
import com.kx.system.mapper.PaAddrInfoMapper;
import com.kx.system.mapper.PaManageAreaMapper;
import com.kx.system.mapper.SysNetworkMapper;
import com.kx.system.service.BaseInfoService;
import com.kx.system.service.IPaManageAreaService;
import com.kx.system.service.ISysDictDataService;
import com.kx.system.util.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kx.system.config.constants.SysDictTypeConstants.ADDR_INFO_TYPE;

@Service
public class PaManageAreaServiceImpl implements IPaManageAreaService {

    @Resource
    private PaAddrInfoMapper paAddrInfoMapper;
    @Resource
    private SysNetworkMapper sysNetworkMapper;
    @Resource
    private PaAddrBaseInfoMapper paAddrBaseInfoMapper;
    @Autowired
    private BaseInfoService baseInfoService;
    @Resource
    private PaManageAreaMapper manageAreaMapper;
    @Autowired
    private Gson gson;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private TokenService tokenService;

    @Resource
    private UserUtil userUtil;
    @Autowired
    private RedisService redisService;

    @Override
    public AjaxResult unMaintainedArea() {
        //所有乡镇 村落
        List<Township> townshipList = paAddrInfoMapper.selectAllTownList();
        List<Village> villageList = paAddrInfoMapper.selectUnMaintainedList();
        if (!CollectionUtils.isEmpty(townshipList) && !CollectionUtils.isEmpty(villageList)) {
            for (int k = 0; k < townshipList.size(); k++) {
                //添加村落信息
                List<Village> villages = new ArrayList<>();
                for (int i = 0; i < villageList.size(); i++) {
                    Village village = villageList.get(i);
                    if (village.getParentCode().equals(townshipList.get(k).getCode())) {
                        villages.add(village);
                    }
                }
                townshipList.get(k).setVillageList(villages);
            }
        }

        return AjaxResult.success(townshipList);
    }

    @Override
    public List<MaintainNetwork> manageAreaList(String networkName, String villageName) {
        SysNetwork network = new SysNetwork();
        network.setNetworkName(networkName);
        List<SysNetwork> sysNetworks = sysNetworkMapper.selectNetworkList(network);
        List<MaintainNetwork> result = new ArrayList<>();
        for (SysNetwork sysNetwork : sysNetworks) {
            if (sysNetwork.getNetworkCode().length() > 6 || sysNetwork.getParentId() == 0 || sysNetwork.getNetworkCode().startsWith(Constants.TS_HEADER_PREFIX)) {
                continue;
            }
            MaintainNetwork maintainNetwork = new MaintainNetwork();
            maintainNetwork.setNetworkId(sysNetwork.getNetworkId());
            maintainNetwork.setNetworkName(sysNetwork.getNetworkName());
            //查询该网点所辖的村
            List<Village> villages = paAddrInfoMapper.selectVillageListByNetworkCode(sysNetwork.getNetworkCode());
            if (StringUtils.isNotNull(villageName) && StringUtils.isNotEmpty(villageName) && villages.size() > 0) {
                int checkNum = 0;
                Iterator<Village> iterator = villages.iterator();
                while (iterator.hasNext()) {
                    Village next = iterator.next();
                    if (next.getName().contains(villageName)) {
                        checkNum++;
                    } else {
                        iterator.remove();
                    }
                }
                if (checkNum == 0) {
                    continue;
                }
            } else if (StringUtils.isNotNull(villageName) && StringUtils.isNotEmpty(villageName) && villages.size() == 0) {
                continue;
            }
            maintainNetwork.setVillageList(villages);
            maintainNetwork.setUpdateTime(sysNetwork.getAreaManageUpdateTime());
            result.add(maintainNetwork);
        }
        return result;
    }

    @Override
    public int areaDistribution(PaAddrInfo paAddrInfo) {

        SysNetwork sysNetwork = sysNetworkMapper.selectNetworkById(paAddrInfo.getBelongNetworkId());
        if (sysNetwork == null) {
            throw new CustomException("未查询到该机构！");
        }

        paAddrInfo.setUpdateTime(DateUtils.getNowDate());
        paAddrInfo.setIsMaintained(IsMaintainedEnum.IS_MAINTAINED.getKey());
        paAddrInfo.setBelongNetworkCode(sysNetwork.getNetworkCode());
        int i = paAddrInfoMapper.updateAddrInfo(paAddrInfo);
        if (i > 0) {
            sysNetworkMapper.updateManageAreaTime(paAddrInfo.getBelongNetworkId());
        }

        //更新pa_addr_base_info中的所属机构
        PaAddrInfo paAddrInfo1 = paAddrInfoMapper.selectAddrInfoById(paAddrInfo.getId());
        if (paAddrInfo1 != null) {
            PaAddrBaseInfo paAddrBaseInfo = new PaAddrBaseInfo();
            paAddrBaseInfo.setAddrCode(paAddrInfo1.getCode());
            paAddrBaseInfo.setNetworkCode(sysNetwork.getNetworkCode());
            paAddrBaseInfo.setNetworkName(sysNetwork.getNetworkName());
            paAddrBaseInfoMapper.updateBaseInfoNetWorkByCode(paAddrBaseInfo);
        }
        return i;
    }

    @Override
    public int deleteAreaDistribution(PaAddrInfo paAddrInfo) {
        paAddrInfo.setUpdateTime(DateUtils.getNowDate());
        paAddrInfo.setIsMaintained(IsMaintainedEnum.NOT_MAINTAINED.getKey());
        PaAddrInfo oldInfo = paAddrInfoMapper.selectAddrInfoById(paAddrInfo.getId());
        int i = paAddrInfoMapper.deleteAreaDistribution(paAddrInfo);
        if (i > 0) {
            //删除该村所关联的客户经理
            manageAreaMapper.deleteByCode(oldInfo.getCode());
            sysNetworkMapper.updateManageAreaTime(oldInfo.getBelongNetworkId());
        }
        return i;
    }

    @Override
    public List<AddrInfoDto> listAreaGrid(AddrInfoParam addrInfoParam) {
        return null;
    }

    @Override
    public List<TaskSelectVo> getAreaManagers(AddrInfoParam addrInfoParam) {
        List<TaskSelectVo> list = paAddrInfoMapper.getAreaManagers(addrInfoParam);
        return list;
    }

    @Override
    @Transactional(value = "masterTransactionManager", rollbackFor = Exception.class)
    public int editAreaDistribution(AddrInfoParam addrInfoParam) {
        if (addrInfoParam == null || addrInfoParam.getId() == null || StringUtils.isEmpty(addrInfoParam.getNetworkId()) || StringUtils.isEmpty(addrInfoParam.getManagerId()) || StringUtils.isEmpty(addrInfoParam.getName())) {
            throw new BusinessValidationException("参数不能为空!");
        }
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectAddrInfoById(addrInfoParam.getId());
        if (paAddrInfo == null) {
            throw new BusinessValidationException("记录不存在!");
        }
        SysNetwork sysNetwork = sysNetworkMapper.selectNetworkById(Long.valueOf(addrInfoParam.getNetworkId()));
        if (sysNetwork == null) {
            throw new CustomException("未查询到该机构！");
        }
        PaManagerBaseInfo paManagerBaseInfo = baseInfoService.getManagerByManagerId(addrInfoParam.getManagerId());
        if (paManagerBaseInfo == null) {
            throw new BusinessValidationException("未查询到客户经理信息!");
        }
        /**更新区域范围表信息*/
        if (!StringUtils.isEmpty(addrInfoParam.getPoint())) {
            paAddrInfo.setBoundaryPoints(JSON.toJSONString(addrInfoParam.getPoint()));
        }
        paAddrInfo.setUpdateTime(DateUtils.getNowDate());
        paAddrInfo.setIsMaintained(IsMaintainedEnum.IS_MAINTAINED.getKey());
        paAddrInfo.setBelongNetworkCode(sysNetwork.getNetworkCode());
        paAddrInfo.setName(addrInfoParam.getName());
        paAddrInfo.setAnotherName(addrInfoParam.getName());
        int i = paAddrInfoMapper.updateAddrInfo(paAddrInfo);
        if (i > 0) {
            sysNetworkMapper.updateManageAreaTime(paAddrInfo.getBelongNetworkId());
        }
        /**三级村网格归属客户经理辖区*/
        List<PaManageArea> areas = manageAreaMapper.selectByAreaCode(paAddrInfo.getCode());
        if (StringUtils.isEmpty(areas)) {
            PaManageArea paManageArea = new PaManageArea();
            paManageArea.setAreaCode(paAddrInfo.getCode());
            paManageArea.setAreaName(paAddrInfo.getName());
            paManageArea.setBelongManageId(paManagerBaseInfo.getManagerCode());
            paManageArea.setBelongNetworkId(sysNetwork.getNetworkId());
            paManageArea.setDelFlag("0");
            manageAreaMapper.insertPaManageArea(paManageArea);
        } else {
            PaManageArea paManageArea = areas.get(0);
            paManageArea.setAreaCode(paAddrInfo.getCode());
            paManageArea.setAreaName(paAddrInfo.getName());
            paManageArea.setBelongManageId(paManagerBaseInfo.getManagerCode());
            paManageArea.setBelongNetworkId(sysNetwork.getNetworkId());
            manageAreaMapper.updatePaManageArea(paManageArea);
        }
        /**更新pa_addr_base_info中的所属机构,客户经理*/
        PaAddrBaseInfo paAddrBaseInfo = new PaAddrBaseInfo();
        paAddrBaseInfo.setAddrCode(paAddrInfo.getCode());
        paAddrBaseInfo.setNetworkCode(sysNetwork.getNetworkCode());
        paAddrBaseInfo.setNetworkName(sysNetwork.getNetworkName());
        paAddrBaseInfo.setManagerCode(paManagerBaseInfo.getManagerCode());
        paAddrBaseInfo.setManagerName(paManagerBaseInfo.getManagerName());
        paAddrBaseInfoMapper.updatePaAddrBaseInfoByAddrCode(paAddrBaseInfo);
        /**修改四级网格网点和客户经理信息*/
        List<PaAddrInfo> childs= paAddrInfoMapper.selectByParentCode(paAddrInfo.getCode());
        if (StringUtils.isNotEmpty(childs)){
            for (PaAddrInfo child:childs) {
                child.setBelongNetworkCode(sysNetwork.getNetworkCode());
                paAddrInfoMapper.updateAddrInfo(child);
                List<PaManageArea> area=manageAreaMapper.selectByAreaCode(child.getCode());
                PaManageArea areaone=area.get(0);
                areaone.setBelongManageId(paManagerBaseInfo.getManagerCode());
                areaone.setBelongNetworkId(sysNetwork.getNetworkId());
                manageAreaMapper.updatePaManageArea(areaone);
                PaAddrBaseInfo baseInfo= paAddrBaseInfoMapper.selectBaseInfoByCode(child.getCode());
                baseInfo.setManagerCode(paManagerBaseInfo.getManagerCode());
                baseInfo.setManagerName(paManagerBaseInfo.getManagerName());
                baseInfo.setNetworkCode(sysNetwork.getNetworkCode());
                baseInfo.setNetworkName(sysNetwork.getNetworkName());
                paAddrBaseInfoMapper.updatePaAddrBaseInfoByAddrCode(baseInfo);
            }
        }
        return i;
    }

    @Override
    @Transactional(value = "masterTransactionManager", rollbackFor = Exception.class)
    public int addFourGrid(AddrInfoParam addrInfoParam) {
        if (addrInfoParam == null || addrInfoParam.getId() == null || StringUtils.isEmpty(addrInfoParam.getName()) || StringUtils.isEmpty(addrInfoParam.getPoint())) {
            throw new BusinessValidationException("参数不能为空!");
        }
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectAddrInfoById(addrInfoParam.getId());
        if (paAddrInfo == null) {
            throw new BusinessValidationException("三级网格不存在!");
        }
        /**查询所有四级网格*/
        List<PaAddrInfo> list = paAddrInfoMapper.selectAllGridList();
        List<String> addrCodes = list.stream().map(PaAddrInfo::getCode).collect(Collectors.toList());
        String addrCodeNew = createNewAddrCode(paAddrInfo.getCode(), addrCodes);
        /**新增四级网格*/
        PaAddrInfo paAddrInfoNew = new PaAddrInfo();
        BeanUtils.copyProperties(paAddrInfo, paAddrInfoNew);
        paAddrInfoNew.setId(null);
        paAddrInfoNew.setCode(addrCodeNew);
        paAddrInfoNew.setParentCode(paAddrInfo.getCode());
        paAddrInfoNew.setName(addrInfoParam.getName());
        paAddrInfoNew.setAnotherName(addrInfoParam.getName());
        paAddrInfoNew.setBoundaryPoints(JSON.toJSONString(addrInfoParam.getPoint()));
        paAddrInfoNew.setUpdateTime(DateUtils.getNowDate());
        paAddrInfoNew.setAddrLevel(paAddrInfo.getAddrLevel() + 1);
        paAddrInfoMapper.insertAddrInfo(paAddrInfoNew);

        /**三级村网格归属客户经理辖区*/
        List<PaManageArea> areas = manageAreaMapper.selectByAreaCode(paAddrInfo.getCode());
        if (!StringUtils.isEmpty(areas)) {
            PaManageArea paManageArea = new PaManageArea();
            BeanUtils.copyProperties(areas.get(0), paManageArea);
            paManageArea.setId(null);
            paManageArea.setAreaCode(addrCodeNew);
            paManageArea.setAreaName(addrInfoParam.getName());
            paManageArea.setDelFlag("0");
            manageAreaMapper.insertPaManageArea(paManageArea);
        }

        /**更新pa_addr_base_info中的所属机构,客户经理*/
        PaAddrBaseInfo paAddrBaseInfo = paAddrBaseInfoMapper.selectBaseInfoByCode(paAddrInfo.getCode());
        if (paAddrBaseInfo == null) {
            throw new BusinessValidationException("业务信息不存在!");
        }
        PaAddrBaseInfo paAddrBaseInfoNew = new PaAddrBaseInfo();
        BeanUtils.copyProperties(paAddrBaseInfo, paAddrBaseInfoNew);
        paAddrBaseInfo.setId(null);
        paAddrBaseInfo.setAddrCode(addrCodeNew);
        paAddrBaseInfo.setAddrName(addrInfoParam.getName());
        paAddrBaseInfoMapper.insertPaAddrBaseInfo(paAddrBaseInfo);
        return 1;
    }

    @Override
    public List<PaAddrInfo> getFourGridList(String addrCode) {
        return paAddrInfoMapper.selectByParentCode(addrCode);
    }

    @Override
    @Transactional(value = "masterTransactionManager", rollbackFor = Exception.class)
    public int mergeFourGrid(AddrInfoParam addrInfoParam) {
        if (addrInfoParam == null || addrInfoParam.getId() == null || StringUtils.isEmpty(addrInfoParam.getName()) || StringUtils.isEmpty(addrInfoParam.getCodes())) {
            throw new BusinessValidationException("参数不能为空!");
        }
        if (addrInfoParam.getCodes().size()<2){
            throw new BusinessValidationException("请选择至少2个网格!");
        }
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectAddrInfoById(addrInfoParam.getId());
        if (paAddrInfo == null) {
            throw new BusinessValidationException("三级网格不存在!");
        }
        List<PaAddrInfo> grids = paAddrInfoMapper.selectByCodeList(addrInfoParam.getCodes());
        if (StringUtils.isEmpty(grids)) {
            throw new BusinessValidationException("未找到对应的四级网格记录");
        }
        List<List<GridPoint>> points = new ArrayList<>();
        grids.stream().forEach(a -> {
            if (!StringUtils.isEmpty(a.getBoundaryPoints())) {
                List<List> arr = JSON.parseArray(a.getBoundaryPoints(), List.class);
                for (List list : arr) {
                    List<GridPoint> arrone = JSON.parseArray(JSON.toJSONString(list), GridPoint.class);
                    points.add(arrone);
                }
            }
        });
        /**查询所有四级网格*/
        List<PaAddrInfo> list = paAddrInfoMapper.selectAllGridList();
        List<String> addrCodes = list.stream().map(PaAddrInfo::getCode).collect(Collectors.toList());
        String addrCodeNew = createNewAddrCode(paAddrInfo.getCode(), addrCodes);
        /**新增四级网格*/
        PaAddrInfo paAddrInfoNew = new PaAddrInfo();
        BeanUtils.copyProperties(paAddrInfo, paAddrInfoNew);
        paAddrInfoNew.setId(null);
        paAddrInfoNew.setCode(addrCodeNew);
        paAddrInfoNew.setParentCode(paAddrInfo.getCode());
        paAddrInfoNew.setName(addrInfoParam.getName());
        paAddrInfoNew.setBoundaryPoints(JSON.toJSONString(points));
        paAddrInfoNew.setUpdateTime(DateUtils.getNowDate());
        paAddrInfoMapper.insertAddrInfo(paAddrInfoNew);

        /**三级村网格归属客户经理辖区*/
        List<PaManageArea> areas = manageAreaMapper.selectByAreaCode(paAddrInfo.getCode());
        if (!StringUtils.isEmpty(areas)) {
            PaManageArea paManageArea = new PaManageArea();
            BeanUtils.copyProperties(areas.get(0), paManageArea);
            paManageArea.setId(null);
            paManageArea.setAreaCode(addrCodeNew);
            paManageArea.setAreaName(addrInfoParam.getName());
            paManageArea.setDelFlag("0");
            manageAreaMapper.insertPaManageArea(paManageArea);
        }
        /**更新pa_addr_base_info中的所属机构,客户经理*/
        PaAddrBaseInfo paAddrBaseInfo = paAddrBaseInfoMapper.selectBaseInfoByCode(paAddrInfo.getCode());
        if (paAddrBaseInfo == null) {
            throw new BusinessValidationException("业务信息不存在!");
        }
        PaAddrBaseInfo paAddrBaseInfoNew = new PaAddrBaseInfo();
        BeanUtils.copyProperties(paAddrBaseInfo, paAddrBaseInfoNew);
        paAddrBaseInfo.setId(null);
        paAddrBaseInfo.setAddrCode(addrCodeNew);
        paAddrBaseInfo.setAddrName(addrInfoParam.getName());
        paAddrBaseInfoMapper.insertPaAddrBaseInfo(paAddrBaseInfo);

        /**删除原四级网格对应的信息*/
        for (String code : addrInfoParam.getCodes()) {
            paAddrInfoMapper.deleteAddrInfoByCode(code);
            manageAreaMapper.deleteManageAreaByCode(code);
            paAddrBaseInfoMapper.deletePaAddrBaseInfoByCode(code);
        }
        return 1;
    }

    @Override
    public int editFourAreaDistribution(AddrInfoParam addrInfoParam) {
        if (addrInfoParam == null || addrInfoParam.getId() == null || StringUtils.isEmpty(addrInfoParam.getName())) {
            throw new BusinessValidationException("参数不能为空!");
        }
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectAddrInfoById(addrInfoParam.getId());
        if (paAddrInfo == null) {
            throw new BusinessValidationException("四级网格不存在!");
        }
        /**更新四级网格*/
        paAddrInfo.setName(addrInfoParam.getName());
        paAddrInfo.setAnotherName(addrInfoParam.getName());
        if (!StringUtils.isEmpty(addrInfoParam.getPoint())) {
            paAddrInfo.setBoundaryPoints(JSON.toJSONString(addrInfoParam.getPoint()));
        }
        paAddrInfo.setUpdateTime(DateUtils.getNowDate());
        paAddrInfoMapper.updateAddrInfo(paAddrInfo);
        return 1;
    }

    @Override
    @Transactional(value = "masterTransactionManager", rollbackFor = Exception.class)
    public int deleteFourGrid(AddrInfoParam addrInfoParam) {
        if (addrInfoParam == null || addrInfoParam.getId() == null) {
            throw new BusinessValidationException("参数不能为空!");
        }
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectAddrInfoById(addrInfoParam.getId());
        if (paAddrInfo == null) {
            throw new BusinessValidationException("四级网格不存在!");
        }
        paAddrInfoMapper.deleteAddrInfoByCode(paAddrInfo.getCode());
        manageAreaMapper.deleteManageAreaByCode(paAddrInfo.getCode());
        paAddrBaseInfoMapper.deletePaAddrBaseInfoByCode(paAddrInfo.getCode());
        return 1;
    }

    @Override
    public List<GridBoundaryDTO> getSonGridBoundary(String addrCode) {
        List<PaAddrInfo> fourGridList = getFourGridList(addrCode);
        return fourGridList.stream().map(g -> {
            GridBoundaryDTO dto = new GridBoundaryDTO();
            //基本信息
            dto.getAttributes().setXZQMC(g.getName());
            dto.getAttributes().setXZQBM(g.getCode());
            //边界信息
            dto.getGeometry().setRings(generateRings(g.getBoundaryPoints()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public GridBoundaryDTO getGridBoundary(String addrCode) {
        PaAddrInfo paAddrInfo = paAddrInfoMapper.selectByCode(addrCode);
        GridBoundaryDTO dto = new GridBoundaryDTO();
        //基本信息
        dto.getAttributes().setXZQMC(paAddrInfo.getName());
        dto.getAttributes().setXZQBM(paAddrInfo.getCode());
        //边界信息
        dto.getGeometry().setRings(generateRings(paAddrInfo.getBoundaryPoints()));
        return dto;
    }

    @Override
    public List<AddrInfoVO> listAddrTree() {
        String treeAddr = redisService.getCacheObject("tree_addr");
        if(treeAddr != null){
            return JSON.parseArray(treeAddr,AddrInfoVO.class);
        }else {
            //先查询树状图根地址信息
            List<AddrInfoVO> rootAddrs = paAddrInfoMapper.selectRootAddr();
            listChildAddr(rootAddrs);
            redisService.setCacheObject("tree_addr",JSON.toJSONString(rootAddrs));
            return rootAddrs;
        }
    }

    @Override
    public List<AddrInfoVO> listAddrTreeWeight() {
        SysDictData dictData = new SysDictData();
        dictData.setDictType(ADDR_INFO_TYPE);
        dictData.setStatus("0");
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        SysDictData sysDictData = list.get(0);
        String typeCode = sysDictData.getDictValue();
        String treeAddr = redisService.getCacheObject(typeCode+"tree_addr");
        if(treeAddr != null){
            return JSON.parseArray(treeAddr,AddrInfoVO.class);
        }else {
            //先查询树状图根地址信息
            List<AddrInfoVO> rootAddrs = paAddrInfoMapper.selectRootAddrByTypeCode(typeCode);
            listChildAddr(rootAddrs);
            redisService.setCacheObject(typeCode+"tree_addr",JSON.toJSONString(rootAddrs));
            return rootAddrs;
        }
    }

    @Override
    public List<AddrInfoVO> listAddrChildren(String parentCode) {
        if (StringUtils.isEmpty(parentCode)) {
            SysDictData dictData = new SysDictData();
            dictData.setDictType(ADDR_INFO_TYPE);
            dictData.setStatus("0");
            List<SysDictData> list = dictDataService.selectDictDataList(dictData);
            if (list == null || list.isEmpty()) {
                return Collections.emptyList();
            }
            return paAddrInfoMapper.selectRootAddrByTypeCode(list.get(0).getDictValue());
        }
        return paAddrInfoMapper.selectByParentCodes(Collections.singletonList(parentCode));
    }

    //递归生成树状图
    private void listChildAddr(List<AddrInfoVO> addrs) {
        List<String> parentCodes = new ArrayList<>();
        for (AddrInfoVO addr : addrs) {
            parentCodes.add(addr.getCode());
        }
        List<AddrInfoVO> childList = paAddrInfoMapper.selectByParentCodes(parentCodes);
        // 将查询结果映射到父元素的子列表中
        for (AddrInfoVO addr : addrs) {
            List<AddrInfoVO> filteredChildList = childList.stream()
                    .filter(child -> child.getParentCode().equals(addr.getCode()))
                    .collect(Collectors.toList());
            if (!filteredChildList.isEmpty()) {
                addr.setChildList(filteredChildList);
                listChildAddr(filteredChildList);
            }
        }
    }

    /**
     * 将边界转换为多个边界的格式
     * @param boundaryPoints
     * @return
     */
    private List<List<List<Double>>> generateRings(String boundaryPoints) {
        if(StringUtils.isBlank(boundaryPoints)){
            return null;
        }
        Type type = new TypeToken<List<List<Object>>>(){}.getType();
        List<List<Object>> result = gson.fromJson(boundaryPoints, type);
        return result.stream().map(l ->
                l.stream().map(o -> {
                    List<Double> xy = new ArrayList<>();
                    xy.add((Double) BeanUtil.getFieldValue(o, "x"));
                    xy.add((Double) BeanUtil.getFieldValue(o, "y"));
                    return xy;
                }).collect(Collectors.toList())
        ).collect(Collectors.toList());
    }


    /**
     * 生成新的四级网格code
     *
     * @param code
     * @param addrCodes
     * @return
     */
    public String createNewAddrCode(String code, List<String> addrCodes) {
        String addrCode = "";
        boolean flag = true;
        do {
            addrCode = code + ((int) (Math.random() * 900) + 100);
            if (!addrCodes.contains(addrCode)) {
                flag = false;
            }
        } while (flag);
        return addrCode;
    }
}
