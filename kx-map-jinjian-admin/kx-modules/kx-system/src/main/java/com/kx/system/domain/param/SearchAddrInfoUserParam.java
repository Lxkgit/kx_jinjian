package com.kx.system.domain.param;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SearchAddrInfoUserParam extends BaseEntity {
    private String deptId;
    private List<String> search;
    private int type;
}
