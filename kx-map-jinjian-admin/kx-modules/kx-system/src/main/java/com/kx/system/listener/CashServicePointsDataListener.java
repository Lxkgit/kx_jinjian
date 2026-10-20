package com.kx.system.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.kx.system.domain.dto.CashServicePointsExcelDTO;
import com.kx.system.mapper.CashServicePointsMapper;
import java.util.ArrayList;
import java.util.List;

public class CashServicePointsDataListener extends AnalysisEventListener<CashServicePointsExcelDTO> {

    private final CashServicePointsMapper mapper;
    private List<CashServicePointsExcelDTO> dataList;

    public CashServicePointsDataListener(CashServicePointsMapper cashServiceMapper) {
    this.mapper = cashServiceMapper;
    this.dataList = new ArrayList<>();
    }


    @Override
    public void invoke(CashServicePointsExcelDTO data, AnalysisContext context) {
        dataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据解析完成后，执行批量插入操作
        if (!ObjectUtil.isEmpty(dataList)) {
            mapper.insert(dataList);
        }
    }
}
