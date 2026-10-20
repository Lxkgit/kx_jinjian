package com.kx.system.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.kx.system.api.domain.SysDeptExcel;
import com.kx.system.api.model.LoginUser;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.filing.FilingCustomerInfo;
import com.kx.system.domain.filing.FilingModelInfo;
import com.kx.system.mapper.*;
import com.kx.system.task.CustomInfoSyncJob;
import com.kx.system.util.EasyExcelUtil;
import com.kx.system.util.GeocodingUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class SysDeptDataListener implements ReadListener<SysDeptExcel> {

    /**
     * 标准表头
     */
    private static final List<String> HEAD_LIST = Arrays.asList("银行机构名称(此列不可修改)", "网点名称(此列不可修改)", "网点负责人", "网点负责人固定电话", "网点负责人移动电话", "网点服务\n" +
            "（输入对应服务编码，多个服务以“,”相隔）");

    /**
     * 批量更新阈值
     */
    private static final int BATCH_COUNT = 500;

    /**
     * 阈值集合
     */
    private List<SysDeptExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);




    private SysDeptMapper sysDeptMapper;

    private PerformanceCustomerInfoMapper performanceCustomerInfoMapper;

    private GridMessageInfoMapper gridMessageInfoMapper;

    //校验表头
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        EasyExcelUtil.verifyHead(HEAD_LIST, headMap);
        ReadListener.super.invokeHead(headMap, context);
    }

    //mapper注入
    public SysDeptDataListener(SysDeptMapper sysDeptMapper, PerformanceCustomerInfoMapper performanceCustomerInfoMapper,GridMessageInfoMapper gridMessageInfoMapper) {
        this.sysDeptMapper = sysDeptMapper;
        this.performanceCustomerInfoMapper = performanceCustomerInfoMapper;
        this.gridMessageInfoMapper = gridMessageInfoMapper;
    }

    //数据校验
    @Override
    public void invoke(SysDeptExcel data, AnalysisContext analysisContext) {
        /**
         * todo
         * 待定数据校验
         */
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //保存数据
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        //网点责任人、固定电话、移动电话更新
        sysDeptMapper.UpdateBatchByDeptName(cachedDataList);
        //网格维护中的责任网点的网点责任人、固定电话、移动电话同步更新
//        gridMessageInfoMapper.UpdateBatchByDeptName(cachedDataList);
        //网点服务信息更新
        performanceCustomerInfoMapper.UpdateBatchByDeptName(cachedDataList);
        log.info("存储数据库成功！");
    }



}
