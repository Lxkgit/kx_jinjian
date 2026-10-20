package com.kx.system.domain.model;

import lombok.Data;

import java.util.List;

/**
 * @author huhao
 * @created 2022/5/23
 * @Description 数据导出参数
 */
@Data
public class ExportDataParam {
    private List<Long> idList;
    private Order order;
    private List<String> filters;
}
