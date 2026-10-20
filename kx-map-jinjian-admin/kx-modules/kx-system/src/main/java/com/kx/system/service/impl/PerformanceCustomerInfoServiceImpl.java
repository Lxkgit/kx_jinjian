package com.kx.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.utils.ManagerStringUtil;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.utils.poi.ExcelUtil;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.ServiceTypeExcel;
import com.kx.system.api.domain.SysDeptExcel;
import com.kx.system.api.domain.SysDictData;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.enums.CustomTypeNewEnum;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.BankType;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.grid.GridMessageInfo;
import com.kx.system.listener.FilingCustomerInfoDataListener;
import com.kx.system.listener.SysDeptDataListener;
import com.kx.system.mapper.*;
import com.kx.system.service.IAnchorPointService;
import com.kx.system.service.IPerformanceCustomerInfoService;
import com.kx.system.service.ISysUserService;
import com.kx.system.service.es.EsPerformanceCustomerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * 绩效客户信息Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Service
@Slf4j
public class PerformanceCustomerInfoServiceImpl implements IPerformanceCustomerInfoService {

    @Resource
    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private EsPerformanceCustomerInfoService esPerformanceCustomerInfoService;
    @Resource
    private IAnchorPointService anchorPointService;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private GridMessageInfoMapper gridMessageInfoMapper;
    @Resource
    private SysDictDataMapper sysDictDataMapper;
    private PerformanceCustomerInfoServiceImpl exportUsersToExcel;

    /**
     * 查询绩效客户信息
     *
     * @param id 绩效客户信息主键
     * @return 绩效客户信息
     */
    @Override
    public PerformanceCustomerInfo selectPerformanceCustomerInfoById(Long id) {
        return performanceCustomerInfoMapper.selectPerformanceCustomerInfoById(id);
    }


//    @Resource
//    private EsQueryUtil esQueryUtil;
    /**
     * 查询绩效客户信息列表
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 绩效客户信息
     */
    @Override
    @DataScope(deptAlias = "u", userAlias = "u")
    public List<PerformanceCustomerInfo> selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo) {
        List<PerformanceCustomerInfo> performanceCustomerInfos = performanceCustomerInfoMapper.selectPerformanceCustomerInfoList(performanceCustomerInfo);
        performanceCustomerInfos.forEach(a->{
            if (StringUtils.isNotEmpty(a.getGridManagerName()) && StringUtils.isNotEmpty(a.getGridManager())){
                String gridManagerName = ManagerStringUtil.managerNumSplitName(a.getGridManager(), a.getGridManagerName());
                //原工号字段 改为  姓名（工号） 显示
                a.setGridManager(gridManagerName);
            }
        });
        return performanceCustomerInfos;
    }


    @Override
    public TableDataInfo esQueryPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo, LoginUser loginUser) {
        TableDataInfo rspData = new TableDataInfo();

        //权限
        //获取当前登陆人信息
        JSONObject esUserInfo = sysUserService.getESUserInfo(loginUser);
        performanceCustomerInfo.setQueryEsRole(esUserInfo);
        String indexName = "es_performance_customer_info";
        if (performanceCustomerInfo.getGroupIdList() != null && performanceCustomerInfo.getGroupIdList().size()>0){
            indexName = "es_group_customer_info";
        }

        JSONObject jsonObject = esPerformanceCustomerInfoService.selectPerformanceCustomerInfoList(performanceCustomerInfo, indexName, performanceCustomerInfo.getPageSize(), performanceCustomerInfo.getPageNum());
        //生成searchSourceBuilder
        List<PerformanceCustomerInfo> performanceCustomerInfos = JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(), PerformanceCustomerInfo.class);
        //获取客户锚点数据
        List<String> customerNmList = performanceCustomerInfos.stream().map(PerformanceCustomerInfo::getCustomerNm).collect(Collectors.toList());
        Map<String, AnchorPoint> anchorPointMap = anchorPointService.getAnchorPointByCustomerNm(customerNmList, CustomTypeNewEnum.PERFORMANCE.getCode().toString());

        BigDecimal decimal = new BigDecimal(0);
        Integer total = jsonObject.getInteger("total");
        performanceCustomerInfos.forEach(a->{
            if (StringUtils.isNotEmpty(a.getGridManagerName()) && StringUtils.isNotEmpty(a.getGridManager())){
                String gridManagerName = ManagerStringUtil.managerNumSplitName(a.getGridManager(), a.getGridManagerName());
                //原工号字段 改为  姓名（工号） 显示
                a.setGridManager(gridManagerName);
            }
            AnchorPoint p = anchorPointMap.get(a.getCustomerNm());
            a.setLon(null!=p? p.getLon(): decimal);
            a.setLat(null!=p? p.getLat(): decimal);
        });
        rspData.setMsg("查询成功");
        rspData.setTotal(total);
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(performanceCustomerInfos);
        return rspData;
    }
    /**
     * 新增绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    @Override
    public int insertPerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo) {
        return performanceCustomerInfoMapper.insertPerformanceCustomerInfo(performanceCustomerInfo);
    }

    /**
     * 修改绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    @Override
    public int updatePerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo) {
        return performanceCustomerInfoMapper.updatePerformanceCustomerInfo(performanceCustomerInfo);
    }

    /**
     * 批量删除绩效客户信息
     *
     * @param ids 需要删除的绩效客户信息主键
     * @return 结果
     */
    @Override
    public int deletePerformanceCustomerInfoByIds(Long[] ids) {
        return performanceCustomerInfoMapper.deletePerformanceCustomerInfoByIds(ids);
    }

    /**
     * 删除绩效客户信息信息
     *
     * @param id 绩效客户信息主键
     * @return 结果
     */
    @Override
    public int deletePerformanceCustomerInfoById(Long id) {
        return performanceCustomerInfoMapper.deletePerformanceCustomerInfoById(id);
    }

    @Override
//    @Async("asyncServiceExecutor")
    public void insertBatch(List<PerformanceCustomerInfo> customerInfos, CountDownLatch countDownLatch, int length, int i) {
        try {
            performanceCustomerInfoMapper.insertBatch(customerInfos);
            log.info("=========绩效客户跑批进度" + (i + 1) + "/" + length);
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    public List<PerformanceCustomerInfo> list(PerformanceCustomerInfo performanceCustomerInfo) {
        List<PerformanceCustomerInfo> list = performanceCustomerInfoMapper.listByRenHang(performanceCustomerInfo);
        return list;
    }

    @Override
    public List<BankType> listByBank(Long groupId) {
        return performanceCustomerInfoMapper.listByBank(groupId);
    }

    @Override
    public String modelDownload() {
        String fileName = "网点信息模板.xls";
        List<SysDeptExcel> list =sysDeptMapper.selectAllByRenHang();

        List<SysDictData> dictDataList = sysDictDataMapper.selectDictDataByType("service_type");
        List<ServiceTypeExcel> serviceList = new ArrayList<>();
        for (SysDictData sysDictData : dictDataList) {
            ServiceTypeExcel excel = new ServiceTypeExcel();
            excel.setServiceTypeName(sysDictData.getDictLabel());
            excel.setServiceTypeCode(sysDictData.getDictValue());
            serviceList.add(excel);
        }


        ExcelWriter excelWriter = EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName)).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(1, "网点信息").head(SysDeptExcel.class).build();
        excelWriter.write(list, sheet1);
        WriteSheet sheet2 = EasyExcel.writerSheet(2, "网点服务编码（不可修改）").head(ServiceTypeExcel.class).build();
        excelWriter.write(serviceList,sheet2);
        excelWriter.finish();
//        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName),SysDeptExcel.class).sheet("网点信息").doWrite(list);
//        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName), ServiceTypeExcel.class).sheet("网点服务编码（不可修改）").doWrite(list);
//        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName), SysDeptExcel.class)
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("网点信息")
//                .doWrite(list);
//
//        EasyExcel.write(ExcelUtil.getAbsoluteFile(fileName), ServiceTypeExcel.class)
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("网点服务编码（不可修改）")
//                .doWrite(serviceList);
        return fileName;
    }

    @Override
    @Async
    public void importBatch(InputStream file, HttpServletRequest request) {
        try {
            //使用监听器读取并写表
            EasyExcel.read(file, SysDeptExcel.class,
                    new SysDeptDataListener(sysDeptMapper,performanceCustomerInfoMapper,gridMessageInfoMapper)
            ).sheet().doRead();
        } catch (DuplicateKeyException dke) {

        }
    }

            @Override
        public List<AnchorExport> selectAllAnchor(PerformanceCustomerInfo anchorExport) {
                return  performanceCustomerInfoMapper.selectAllAnchor(anchorExport);
            }
}
