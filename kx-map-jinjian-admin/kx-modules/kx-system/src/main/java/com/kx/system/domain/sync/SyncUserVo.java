package com.kx.system.domain.sync;

import com.alibaba.excel.annotation.ExcelProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SyncUserVo {
    @CsvBindByPosition(position = 10)
    private String empId;

    @CsvBindByPosition(position = 11)
    private String empName;

    @CsvBindByPosition(position = 8)
    private String unit5Code;

    @CsvBindByPosition(position = 9)
    private String unit5Name;
}
