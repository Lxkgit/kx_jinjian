package com.kx.system.task.indicator;

import com.kx.common.core.utils.StringUtils;
import com.kx.system.api.domain.SysDept;
import com.kx.system.api.domain.SysUser;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.mapper.*;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.util.ListSplitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 网格授信用户统计
 */
@Component
public class FilingCustomerIndicatorSyncService {
    private static final Logger log = LoggerFactory.getLogger(FilingCustomerIndicatorSyncService.class);

    @Resource
    private IPaAddrInfoTypeService paAddrInfoTypeService;
    @Resource
    private FilingCustomerInfoMapper filingCustomerInfoMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private PaViewGridOverviewManagerMapper mMapper;
    @Resource
    private PaViewGridOverviewOrgMapper oMapper;
    @Resource
    private PaViewGridOverviewManagerGridMapper mgMapper;
    @Resource
    private PaViewGridOverviewOrgGridMapper ogMapper;


    @Transactional
    public void syncFilingGridStatistic() {
        List<PaAddrInfoType> addrInfoTypes = paAddrInfoTypeService.listAllAddrInfoWithOutCoordinates();
        Map<String, PaAddrInfoType> codeAddrMap = addrInfoTypes.stream().collect(Collectors.toMap(PaAddrInfoType::getCode, Function.identity()));
        FilingCustomerInfo search = new FilingCustomerInfo();
        search.setFilingStatus("1");
        List<FilingCustomerInfo> filingCustomerInfoList = filingCustomerInfoMapper.list(search);
        managerStatistic(filingCustomerInfoList, codeAddrMap);
        orgStatistic(filingCustomerInfoList, codeAddrMap);
    }

    private void managerStatistic(List<FilingCustomerInfo> filingCustomerInfoList, Map<String, PaAddrInfoType> addrMap) {
        //客户经理维度统计
        List<PaViewGridOverview> managerViews = new ArrayList<>();
        //客户经理网格维度统计
        List<PaViewGridOverview> managerGridViews = new ArrayList<>();
        List<SysUser> sysUsers = sysUserMapper.selectUserList(new SysUser());
        for (SysUser u : sysUsers) {
            if (StringUtils.isEmpty(u.getManagerNum())) {
                continue;
            }
            PaViewGridOverview managerView = new PaViewGridOverview();
            managerView.setManagerCode(u.getManagerNum());
            //该客户经理的建档客户总数
            List<FilingCustomerInfo> filingCustomerInfos = filingCustomerInfoList.stream().filter(t -> u.getManagerNum().equals(t.getFilingManagerCode())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(filingCustomerInfos)) {
                continue;
            }
            int total = filingCustomerInfos.size();
            managerView.setRecordCustomerNum(total);
            //该客户经理管辖的网格
            List<String> managerdCodes = addrMap.values().stream().filter(t -> u.getUserId().equals(t.getUserId())).map(PaAddrInfoType::getCode).collect(Collectors.toList());
            int inSide = (int) filingCustomerInfos.stream().filter(t -> belongGrid(addrMap, managerdCodes, t.getBelongGridCode())).count();
            managerView.setRecordInsideCustomerNum(inSide);
            managerView.setRecordOutsideCustomerNum(total - inSide);
            managerView.setRecordInsideCustomerRatio(percent(inSide, total));
            managerView.setRecordOutsideCustomerRatio(percent(total - inSide, total));
            managerViews.add(managerView);

            //获取所有子网格
            List<String> managedAddrCodes = getSubAddrCodes(addrMap, managerdCodes);
            //对于某一网格，网格内的建档客户数
            for (String gc : managedAddrCodes) {
                PaViewGridOverview managerGridView = new PaViewGridOverview();
                managerGridView.setManagerCode(u.getManagerNum());
                managerGridView.setGridCode(gc);
                int count = Math.toIntExact(filingCustomerInfos.stream().filter(t -> belongGrid(addrMap, gc, t.getBelongGridCode())).count());
                if (count == 0) {
                    continue;
                }
                managerGridView.setRecordCustomerNum(count);
                managerGridView.setRecordInsideCustomerNum(count);
                managerGridView.setRecordInsideCustomerRatio("100.00");
                managerGridViews.add(managerGridView);
            }
        }
        //保存
        save(managerViews,
                mMapper.list(new PaViewGridOverview()),
                v -> v.getManagerCode(),
                mMapper::batchInsert,
                mMapper::updatePaViewGridOverviewManager);
        save(managerGridViews,
                mgMapper.list(new PaViewGridOverview()),
                v -> v.getManagerCode() + "_" + v.getGridCode(),
                mgMapper::batchInsert,
                mgMapper::updatePaViewGridOverviewManagerGrid);
    }

    private void orgStatistic(List<FilingCustomerInfo> filingCustomerInfoList, Map<String, PaAddrInfoType> addrMap) {
        //机构维度统计
        List<PaViewGridOverview> orgViews = new ArrayList<>();
        //机构网格维度统计
        List<PaViewGridOverview> orgGridViews = new ArrayList<>();
        List<SysDept> sysDepts = sysDeptMapper.selectAll();
        Map<String, SysDept> codeDeptMap = sysDepts.stream().collect(Collectors.toMap(SysDept::getOrgNum, Function.identity()));
        for (SysDept d : sysDepts) {
            if (StringUtils.isEmpty(d.getOrgNum())) {
                continue;
            }
            PaViewGridOverview orgView = new PaViewGridOverview();
            orgView.setOrgCode(d.getOrgNum());
            //该机构下的建档客户总数
            List<FilingCustomerInfo> filingCustomerInfos = filingCustomerInfoList.stream()
                    .filter(t -> isSubOrg(codeDeptMap, d, t.getFilingOrgCode()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(filingCustomerInfos)) {
                continue;
            }
            int total = filingCustomerInfos.size();
            orgView.setRecordCustomerNum(total);
            //该机构管辖的网格
            List<String> manageredCodes = addrMap.values().stream().filter(t -> d.getDeptId().equals(t.getDeptId())).map(PaAddrInfoType::getCode).collect(Collectors.toList());
            int inSide = (int) filingCustomerInfos.stream().filter(t -> belongGrid(addrMap, manageredCodes, t.getBelongGridCode())).count();
            orgView.setRecordInsideCustomerNum(inSide);
            orgView.setRecordOutsideCustomerNum(total - inSide);
            orgView.setRecordInsideCustomerRatio(percent(inSide, total));
            orgView.setRecordOutsideCustomerRatio(percent(total - inSide, total));
            orgViews.add(orgView);

            //对于某一网格，网格内的建档客户数
            List<String> managedAddrCodes = getSubAddrCodes(addrMap, manageredCodes);
            for (String gc : managedAddrCodes) {
                PaViewGridOverview orgGridView = new PaViewGridOverview();
                orgGridView.setOrgCode(d.getOrgNum());
                orgGridView.setGridCode(gc);
                //该网格下的建档客户数
                int count = Math.toIntExact(filingCustomerInfos.stream().filter(t -> belongGrid(addrMap, gc, t.getBelongGridCode())).count());
                if (count == 0) {
                    continue;
                }
                inSide += count;
                orgGridView.setRecordCustomerNum(count);
                orgGridView.setRecordInsideCustomerNum(count);
                orgGridView.setRecordInsideCustomerRatio("100.00");
                orgGridViews.add(orgGridView);
            }
        }
        //保存
        save(orgViews,
                oMapper.list(new PaViewGridOverview()),
                v -> v.getOrgCode(),
                oMapper::batchInsert,
                oMapper::updatePaViewGridOverviewOrg);
        save(orgGridViews,
                ogMapper.list(new PaViewGridOverview()),
                v -> v.getOrgCode() + "_" + v.getGridCode(),
                ogMapper::batchInsert,
                ogMapper::updatePaViewGridOverviewOrgGrid);
    }

    private <T> void save(List<T> views,
                          List<T> existLists,
                          Function<T, String> key,
                          Consumer<List<T>> insert,
                          Consumer<T> update) {
        Set<String> existSet = existLists.stream().map(t -> key.apply(t)).collect(Collectors.toSet());

        List<T> updateList = views.stream().filter(v -> existSet.contains(key.apply(v))).collect(Collectors.toList());
        views.removeAll(updateList);
        if (!CollectionUtils.isEmpty(updateList)) {
            for (T o : updateList) {
                update.accept(o);
            }
        }
        ListSplitUtils.split(views, 50, temp -> insert.accept(temp));
    }

    private List<String> getSubAddrCodes(Map<String, PaAddrInfoType> addrMap, List<String> managerdCodes) {
        //获取所有子网格
        List<String> managedAddrCodes = addrMap.values().stream().filter(t -> {
            for (String code : managerdCodes) {
                if (t.getAncestorCodes().contains("," + code + ",")) {
                    return true;
                }
            }
            return false;
        }).map(PaAddrInfoType::getCode).collect(Collectors.toList());
        return managedAddrCodes;
    }

    private boolean isSubOrg(Map<String, SysDept> sysDeptMap, SysDept dept, String code) {
        if (!sysDeptMap.containsKey(code)) {
            return false;
        }
        SysDept sysDept = sysDeptMap.get(code);
        return sysDept.getAncestors().contains("," + dept.getDeptId() + ",");
    }

    private boolean belongGrid(Map<String, PaAddrInfoType> addrMap, String gridCode, String filingGridCode) {
        if (!addrMap.containsKey(gridCode) || !addrMap.containsKey(filingGridCode)) {
            return false;
        }
        return addrMap.get(filingGridCode).getAncestorCodes().contains("," + gridCode + ",");
    }

    private boolean belongGrid(Map<String, PaAddrInfoType> addrMap, List<String> gridCodes, String filingGridCode) {
        if (CollectionUtils.isEmpty(gridCodes)) {
            return false;
        }
        for (String gridCode : gridCodes) {
            if (!addrMap.containsKey(gridCode) || !addrMap.containsKey(filingGridCode)) {
                continue;
            }
            if (addrMap.get(filingGridCode).getAncestorCodes().contains("," + gridCode + ",")) {
                return true;
            }
        }
        return false;
    }

    private String percent(int fz, int fm) {
        if (fz == 0 || fm == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) fz / fm * 100);
    }
}
