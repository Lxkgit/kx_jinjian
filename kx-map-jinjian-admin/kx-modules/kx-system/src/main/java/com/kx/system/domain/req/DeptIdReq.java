package com.kx.system.domain.req;

import lombok.Data;

import java.util.List;

/**
 * @author kangxu
 * @date 2024/3/15
 */
@Data
public class DeptIdReq {
    /**
     * 部门Id列表
     */
    private List<Long> deptIdList;

    /**
     * 执行人姓名
     */
    private String managerName;
}
