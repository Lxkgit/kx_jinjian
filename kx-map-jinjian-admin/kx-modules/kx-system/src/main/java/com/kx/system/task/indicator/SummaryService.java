package com.kx.system.task.indicator;

import com.kx.system.api.domain.SysDept;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.util.ListSplitUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class SummaryService<T> {

    private static final Set<String> CUSTOMER_TYPE = new HashSet<>();

    static {
        CUSTOMER_TYPE.add("1");
        CUSTOMER_TYPE.add("2");
        CUSTOMER_TYPE.add("3");
    }

    @Resource
    private SysDeptMapper sysDeptMapper;

    public void summaryByOrg() {
        if (statisticed()) {
            return;
        }
        List<SysDept> sysDepts = sysDeptMapper.selectAll();
        //查询所有贷款指标
        List<T> views = listAllIndicators();
        //构建索引Map<orgCode,Map<gridCode,Map<gridCode,view>>>
        Map<String, Map<String, Map<String, T>>> index = buildIndex(views);
        //统计记录
        List<T> statisticViews = new ArrayList<>();
        //已统计过指标的机构
        Set<String> finishedCodes = views.stream().map(this::getIndicatorOrgCode).collect(Collectors.toSet());
        for (SysDept d : sysDepts) {
            if (finishedCodes.contains(d.getOrgNum())) {
                continue;
            }
            /**
             *  |- b (1,2,3)
             * a|
             *  |- c (2,3,4)
             */
            //获取需要统计的子机构 (b , c)
            Set<String> childDeptCodes = getChildDeptExistLists(sysDepts, d, index.keySet());
            //获取需要统计的网格 (1, 2, 3, 4)
            Set<String> gridCodes = new HashSet<>();
            for (String childDeptCode : childDeptCodes) {
                gridCodes.addAll(index.get(childDeptCode).keySet());
            }
            //针对每个网格单独汇总
            if (!CollectionUtils.isEmpty(gridCodes)) {
                //(b1),(b2,c2),(b3,c3),(c4)
                for (String gridCode : gridCodes) {
                    //每个网格的不同客户类型也要进行汇总
                    for (String customerType : CUSTOMER_TYPE) {
                        List<T> subLists = new ArrayList<>();
                        for (String childDeptCode : childDeptCodes) {
                            if (!index.containsKey(childDeptCode)
                                    || !index.get(childDeptCode).containsKey(gridCode)
                                    || !index.get(childDeptCode).get(gridCode).containsKey(customerType)) {
                                continue;
                            }
                            subLists.add(index.get(childDeptCode).get(gridCode).get(customerType));
                        }
                        statisticViews.add(statisticSub(subLists, d.getOrgNum(), gridCode, customerType));
                    }
                }
            }
        }

        //保存统计数据
        ListSplitUtils.split(statisticViews, 50, this::batchInsert);
    }

    protected abstract void batchInsert(List<T> lists);

    protected abstract T statisticSub(List<T> subLists, String orgNum, String gridCode, String customerType);

    protected abstract List<T> listAllIndicators();

    abstract boolean statisticed();

    protected Set<String> getChildDeptExistLists(List<SysDept> sysDepts, SysDept d, Set<String> codes) {
        return sysDepts.stream()
                .filter(t -> t.getAncestors().contains("," + d.getDeptId() + ","))
                .map(SysDept::getOrgNum)
                .filter(t -> codes.contains(t))
                .collect(Collectors.toSet());
    }


    protected String percentStr(Integer fz, Integer fm) {
        if (fz == null || fm == null || fm == 0) {
            return "0.00";
        }
        return String.format("%.2f", ((double) fz / fm) * 100);
    }

    protected String percentStr(String fz, String fm) {
        if (!StringUtils.hasLength(fz) || !StringUtils.hasLength(fm) || Double.parseDouble(fm) == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) (Double.parseDouble(fz) / Double.parseDouble(fm) * 100));
    }

    protected String sumStringByField(List<T> subLists, Function<T, String> getField,
                                      boolean isFloat) {
        Double sum = subLists.stream().map(getField).filter(StringUtils::hasLength).mapToDouble(Double::parseDouble).sum();
        if (isFloat) {
            return String.format("%.2f", sum.doubleValue());
        } else {
            return String.format("%d", sum.intValue());
        }
    }

    protected Integer sumIntegerByField(List<T> subLists, Function<T, Integer> getField) {
        return subLists.stream().map(getField).filter(Objects::nonNull).mapToInt(Integer::new).sum();
    }

    private Map<String, Map<String, Map<String, T>>> buildIndex(List<T> views) {
        Map<String, Map<String, Map<String, T>>> index = new HashMap<>();
        for (T view : views) {
            String orgCode = getIndicatorOrgCode(view);
            String gridCode = getIndicatorGridCode(view);
            String customerType = getIndicatorCustomerType(view);
            if (!StringUtils.hasLength(customerType) || !StringUtils.hasLength(orgCode) || !StringUtils.hasLength(gridCode)) {
                continue;
            }
            if (!index.containsKey(orgCode)) {
                index.put(orgCode, new HashMap<>());
            }
            Map<String, Map<String, T>> typeMap = index.get(orgCode);
            if (!typeMap.containsKey(gridCode)) {
                typeMap.put(gridCode, new HashMap<>());
            }
            typeMap.get(gridCode).put(customerType, view);
        }
        return index;
    }

    protected abstract String getIndicatorCustomerType(T view);

    protected abstract String getIndicatorGridCode(T view);

    protected abstract String getIndicatorOrgCode(T view);

}
