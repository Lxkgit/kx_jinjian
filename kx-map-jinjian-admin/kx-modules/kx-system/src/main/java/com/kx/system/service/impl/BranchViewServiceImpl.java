package com.kx.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.kx.system.config.exception.CustomException;
import com.kx.system.domain.BranchEvents;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.VillageFinancialServicePoints;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.dto.BranchInfoDTO;
import com.kx.system.domain.dto.GridManageInfoDTO;
import com.kx.system.domain.grid.GridMessageInfo;
import com.kx.system.mapper.AnchorPointMapper;
import com.kx.system.mapper.BranchViewMapper;
import com.kx.system.mapper.PaAddrInfoTypeMapper;
import com.kx.system.service.BranchViewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Lenovo
 */
@Service
public class BranchViewServiceImpl implements BranchViewService {
    private final static String DUTY_NETWORK = "责任网点";
    private final static String ASSIST_NETWORK = "协助网点";

    @Autowired
    private BranchViewMapper branchViewMapper;

    @Autowired
    private AnchorPointMapper anchorPointMapper;
    @Resource
    private PaAddrInfoTypeMapper paAddrInfoTypeMapper;

    @Override
    public String branchBifferentiate(String anchorName) {
        if(!branchViewMapper.selectBranch(anchorName)){
           throw new CustomException("网点信息错误");
        }
        GridMessageInfo flag = branchViewMapper.selectBranchBifferentiate(anchorName);
        return Optional.ofNullable(flag)
                .map(name -> DUTY_NETWORK)
                .orElse(ASSIST_NETWORK);
    }

    @Override
    public BranchInfoDTO selectBranchInfo(String customerName) {
        // 获取 网点信息
        BranchInfoDTO gridinfo = branchViewMapper.selectGridInfo(customerName);

        if (ObjectUtil.isNull(gridinfo)) {
            throw new CustomException("未找到该网点的信息");
        }
        // 构建并返回 BranchInfoDTO 对象
        return gridinfo;
    }

    @Override
    public List<String> selectBranchService(String customerName) {
        PerformanceCustomerInfo performanceInfo = branchViewMapper.selectPerformanceInfo(customerName);
        // 确保 performanceInfo 不为 null
        if (performanceInfo == null) {
            throw new CustomException("未找到该网点的信息");
        }
        String serviceType = performanceInfo.getServiceType();
        // 如果 serviceType 为 null 或空字符串，抛出异常
        if (ObjectUtil.isNull(serviceType) || serviceType.isEmpty()) {
            return null;
        }
        // 使用 Arrays.asList 创建一个可变的列表
        List<String> list = new ArrayList<>();
        // 分割 serviceType 并添加到列表中
        String[] split = serviceType.split(",");
        list.addAll(Arrays.asList(split));

        return list;
    }

    @Override
    public BranchEvents queryBranchEvent(String customerName) {
        BranchEvents branchEvents = branchViewMapper.selectBranchEvent(customerName);
        return branchEvents;
    }

    @Override
    public List<BranchEvents> queryBranchEventAll(String customerName) {
        List<BranchEvents> eventAll = branchViewMapper.selectBranchEventAll(customerName);
        return eventAll;
    }

    @Override
    public Integer insertBranchEvent(BranchEvents branchEvents) {
        boolean isAnyFieldBlank = Stream.of(
                        branchEvents.getComplainant(),
                        branchEvents.getComplainantPhone(),
                        branchEvents.getComplainantType(),
                        branchEvents.getComplainTime(),
                        branchEvents.getDescription(),
                        branchEvents.getHandler(),
                        branchEvents.getHandlerPhone(),
                        branchEvents.getCustomerName(),
                        branchEvents.getSuperiorsName(),
                        branchEvents.getSuperiorsPhone()
                ).map(String::valueOf)
                .anyMatch(StringUtils::isBlank);

        if (isAnyFieldBlank) {
            throw new CustomException("输入信息不完整");
        } else {
            branchEvents.setCreateTime(LocalDateTime.now());
            Integer result = branchViewMapper.insertBranchEvent(branchEvents);
            return result;
        }

    }



    @Override
    public String deleteBranchEvent(Long id) {
        branchViewMapper.deleteBranchEvent(id);
        return "删除成功";
    }

    @Override
    public Integer updateBranchEvent(BranchEvents branchEvents) {
        branchEvents.setUpdateTime(LocalDateTime.now());
        return branchViewMapper.updateBranchEvent(branchEvents);

    }

    @Override
    public HashMap<String, String> getCategoryPath(String code) {
         StringBuilder path = new StringBuilder();
        String currentCode = code;
        String topLevelCode = null;

        // 首先获取顶级父类的code
        while (!Objects.isNull(currentCode) && Objects.isNull(topLevelCode)) {
            String parentCode = branchViewMapper.getParentCode(currentCode);
            if (Objects.isNull(parentCode)) {
                topLevelCode = currentCode;
            } else {
                currentCode = parentCode;
            }
        }

        // 然后重新从传入的code开始构建路径
        currentCode = code;
        // 标记是否已经添加了顶级分类的名称
        boolean isTopLevelAdded = false;

        while (!Objects.isNull(currentCode) && !currentCode.equals(topLevelCode)) {
            String name = branchViewMapper.getCategoryName(currentCode);
            if (name != null) {
                if (isTopLevelAdded) {
                    // 如果已经添加了顶级分类的名称，则添加斜杠
                    path.insert(0,  name+"/");
                } else {
                    // 如果是顶级分类，则直接添加名称
                    path.insert(0, name);
                    // 更新标记，表示已经处理了顶级分类
                    isTopLevelAdded = true;
                }
            }
            currentCode = branchViewMapper.getParentCode(currentCode);
        }
        HashMap<String, String> map = new HashMap<>();
        // 如果顶级分类的code与传入的code相同，说明传入的code是顶级分类
        if (Objects.isNull(topLevelCode) || topLevelCode.equals(code)) {
            // 直接返回顶级分类的名称
            String categoryName = branchViewMapper.getCategoryName(code);
            map.put("path",categoryName);
        }


        map.put("path",path.toString());
        if (Objects.isNull(code)|| code.isEmpty()|| "330700".equals(code)){
            map.put("minGridCount",paAddrInfoTypeMapper.getMinimumCount().toString());
            return map;
        }
        map.put("minGridCount",paAddrInfoTypeMapper.getMinimumCountByParentCode(code).toString());
        return map;
    }

    @Override
    public List<GridManageInfoDTO> getGridManageInfo(String dutyNetworkName) {
        if (dutyNetworkName == null || dutyNetworkName.isEmpty()) {
            throw new IllegalArgumentException("dutyNetworkName不能为空");
        }
        List<String> gridList =  anchorPointMapper.selectManageInfo(dutyNetworkName);
        if (gridList != null && !gridList.isEmpty()){
            return branchViewMapper.selectGridManageInfo(gridList);
        }
        return null;
    }

    @Override
    public List<CashServicePoints> getCashServiceInfo(List<String> gridCodes) {
        if (gridCodes.isEmpty()){
            return null;
        }
        return branchViewMapper.selectCashServiceInfo(gridCodes);
    }

    @Override
    public List<VillageFinancialServicePoints> getFinancialServiceInfo(List<String> gridCodes) {
        if (gridCodes.isEmpty()){
            return null;
        }
        return branchViewMapper.selectFinancialServiceInfo(gridCodes);
    }
}
