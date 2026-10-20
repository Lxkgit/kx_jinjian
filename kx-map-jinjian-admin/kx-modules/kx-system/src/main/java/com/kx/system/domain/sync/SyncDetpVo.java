package com.kx.system.domain.sync;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class SyncDetpVo {
    private String id;

    @CsvBindByPosition(position = 1)
    private String orgId;

    @CsvBindByPosition(position = 2)
    private String orgName;

    @CsvBindByPosition(position = 3)
    private String upOrgId;
}
