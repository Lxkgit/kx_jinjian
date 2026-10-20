package com.kx.system.domain.param;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class ExportPageBaseParam extends BaseEntity {
    /**
     * 导出参数
     */
    private int exportPageStart = 1;
    private int exportPageEnd = 1;

    private int start = 0;
    private int limit = 10;

    public void handlePageExportParam() {
        int startPos = (exportPageStart - 1) * getPageSize();
        int count = (exportPageEnd - exportPageStart + 1) *  getPageSize();
        start = startPos < 0 ? 0 : startPos;
        limit = count < 0 ? 0 : count;
    }
}
