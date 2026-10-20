package com.kx.system.task.indicator;

import com.kx.system.api.domain.SysDept;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.StatisticsOperator;
import com.kx.system.domain.StatisticsValue;
import com.kx.system.domain.small.PaViewGridOverview;
import com.kx.system.mapper.PaViewGridOverviewMapper;
import com.kx.system.mapper.StatisticsOperatorMapper;
import com.kx.system.mapper.SysDeptMapper;
import com.kx.system.service.IPaAddrInfoTypeService;
import com.kx.system.util.ListSplitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kx.system.domain.enums.StatisticOperatorType.COUNT;
import static com.kx.system.domain.enums.StatisticOperatorType.SUM;

/**
 * 首页数据统计定时任务<br>
 * 根据原始数据，统计各地区【小区数量、小区人口（户数）、企业数量、金融机构数量、工业园数量、网格人数、经济总量（万元）】<br>
 * 并写入到pa_addr_info_chouzhou表中
 * <p>
 * 分组字段： <br>
 * province_code(省) <br>
 * city_code(市) <br>
 * admin_code(区) <br>
 * town_code(镇) <br>
 * village_code(村)<br>
 *
 * <p>
 * 统计逻辑： <br>
 * 小区数量（villageNum）- pa_out_data_community_info:count() <br>
 * 小区人口(户数)(villagePeopleNum) - pa_out_data_community_info:sum(total_households) <br>
 * 企业数量(companyNum) - pa_out_data_company_total:sum(total) <br>
 * 金融机构数量(financialInstitutionNum) - pa_out_data_financial_org:count() <br>
 * 工业园数量(industrialParkNum) - pa_out_data_park:count() <br>
 * 网格人数(gridPeopleNum) - pa_out_data_people_num:sum(total_num) <br>
 * 经济总量(亿元转万元)(gdp) - pa_out_data_economic_info:sum(gdp*10000) <br>
 */
@Component
public class GridInfoStatisticService {
    private static final Logger log = LoggerFactory.getLogger(GridInfoStatisticService.class);

    @Resource
    private IPaAddrInfoTypeService paAddrInfoTypeService;
    @Resource
    private StatisticsOperatorMapper statisticsOperatorMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private PaViewGridOverviewMapper paViewGridOverviewMapper;

    private static SysDept EMPTY_DEPT = new SysDept();
    private static final List<StatisticsOperator> statisticsOperatorList;

    static {
        try {
            statisticsOperatorList = new ArrayList<>();
            List<String> village = Arrays.asList("province_code", "city_code", "admin_code", "town_code", "village_code");
            List<String> town = Arrays.asList("province_code", "city_code", "admin_code", "town_code");
            List<String> admin = Arrays.asList("province_code", "city_code", "admin_code");
            // village_num-小区数量-pa_out_data_community_info:count()
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_community_info")
                    .operatorType(COUNT)
                    .groupByColumn(village)
                    .result(PaViewGridOverview.class, "setVillageNum", Integer.class)
                    .build());
            // village_people_num-小区人口(暂时改为户数)-pa_out_data_community_info:sum(total_households)
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_community_info")
                    .operatorType(SUM)
                    .groupByColumn(village)
                    .statisticColumn("total_households")
                    .result(PaViewGridOverview.class, "setVillagePeopleNum", Integer.class).build());
            // company_num-企业数量-pa_out_data_company_total:sum(total)
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_company_total")
                    .operatorType(SUM)
                    .groupByColumn(town)
                    .statisticColumn("total")
                    .result(PaViewGridOverview.class, "setCompanyNum", Integer.class).build());
            // financial_institution_num-金融机构数量-pa_out_data_financial_org:count()
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_financial_org")
                    .operatorType(COUNT)
                    .groupByColumn(village)
                    .result(PaViewGridOverview.class, "setFinancialInstitutionNum", Integer.class).build());
            // Industrial_park_num-工业园数量-pa_out_data_park:count()
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_park")
                    .operatorType(COUNT)
                    .groupByColumn(village)
                    .result(PaViewGridOverview.class, "setIndustrialParkNum", Integer.class).build());
            // grid_people_num-网格人数-pa_out_data_people_num:sum(total_num)
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_people_num")
                    .operatorType(SUM)
                    .groupByColumn(town)
                    .statisticColumn("total_num")
                    .result(PaViewGridOverview.class, "setGridPeopleNum", Integer.class).build());
            // gdp-经济总量（亿元）-pa_out_data_economic_info:sum() 需要转万元
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_economic_info")
                    .operatorType(SUM)
                    .groupByColumn(admin)
                    .statisticColumn("regexp_replace(gdp,'[亿]')*10000")
                    .result(PaViewGridOverview.class, "setGdp", String.class).build());
            // 商圈数量-pa_out_data_economic_info:sum() 需要转万元
            statisticsOperatorList.add(StatisticsOperator.builder()
                    .tableName("pa_out_data_shopping_mall")
                    .operatorType(COUNT)
                    .groupByColumn(village)
                    .result(PaViewGridOverview.class, "setBusinessAreaNum", Integer.class).build());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void syncPaViewGridStatistic() {
        try {
            Map<String, PaViewGridOverview> statisticVoMap = new HashMap<>();

            List<PaAddrInfoType> addrInfoTypes = paAddrInfoTypeService.listAllAddrInfoWithOutCoordinates();
            List<String> addrCodes = addrInfoTypes.stream().map(PaAddrInfoType::getCode).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            List<SysDept> sysDepts = sysDeptMapper.selectDeptList(EMPTY_DEPT);
            Map<Long, SysDept> deptMap = sysDepts.stream().collect(Collectors.toMap(SysDept::getDeptId, Function.identity()));

            //统计网格内资源
            int total = statisticsOperatorList.size();
            int current = 1;
            for (StatisticsOperator o : statisticsOperatorList) {
                log.info("Process[{}/{}]", current++, total);
                for (String groupByColumns : o.getGroupByColumn()) {
                    List<StatisticsValue> statisticsValues = o.getOperatorType() == COUNT
                            ? statisticsOperatorMapper.countByColumn(o.getTableName(), groupByColumns)
                            : statisticsOperatorMapper.sumByColumn(o.getTableName(), o.getStatisticColumn(), groupByColumns);
                    statisticsValues = statisticsValues.stream().filter(t -> addrCodes.contains(t.getName())).collect(Collectors.toList());
                    for (StatisticsValue v : statisticsValues) {
                        o.getResultSetMethod().invoke(putIfAbsentAndGet(statisticVoMap, v.getName()), castTo(v.getValue(), o.getResultType()));
                    }
                }
            }
            //统计子网格数
            for (PaAddrInfoType addrInfoType : addrInfoTypes) {
                //4级网格数
                long sub4 = addrInfoTypes.stream().filter(t ->
                        t.getAddrLevel() == 4
                                && t.getAncestorCodes().contains("," + addrInfoType.getCode() + ",")).count();
                //5级网格数
                long sub5 = addrInfoTypes.stream().filter(t ->
                        t.getAddrLevel() == 5
                                && t.getAncestorCodes().contains("," + addrInfoType.getCode() + ",")).count();
                PaViewGridOverview view = putIfAbsentAndGet(statisticVoMap, addrInfoType.getCode());
                view.setGridCode(addrInfoType.getCode());
                view.setGridName(addrInfoType.getName());
                view.setGridUserName(addrInfoType.getUserName());
                view.setOrgCode(deptMap.getOrDefault(addrInfoType.getDeptId(), EMPTY_DEPT).getOrgNum());
                view.setOrgName(deptMap.getOrDefault(addrInfoType.getDeptId(), EMPTY_DEPT).getDeptName());
                //没去取
                view.setGridLevel(null);
                view.setLevelFourGridNum((int) sub4);
                view.setLevelFiveGridNum((int) sub5);
            }

            paViewGridOverviewMapper.truncate();
            ListSplitUtils.split(statisticVoMap.values(), 50, items -> paViewGridOverviewMapper.batchInsert(items));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object castTo(Object value, Class<?> paramType) {
        if (paramType.equals(String.class)) {
            return value.toString();
        }
        if (value instanceof Long && paramType.equals(Integer.class)) {
            return ((Long) value).intValue();
        }
        if (value instanceof BigDecimal && paramType.equals(Integer.class)) {
            return ((BigDecimal) value).intValue();
        }
        return paramType.cast(value);
    }

    private PaViewGridOverview putIfAbsentAndGet(Map<String, PaViewGridOverview> map, String code) {
        if (!map.containsKey(code)) {
            PaViewGridOverview paViewGridStatisticVo = new PaViewGridOverview();
            paViewGridStatisticVo.setGridCode(code);
            map.put(code, paViewGridStatisticVo);
        }
        return map.get(code);
    }
}
