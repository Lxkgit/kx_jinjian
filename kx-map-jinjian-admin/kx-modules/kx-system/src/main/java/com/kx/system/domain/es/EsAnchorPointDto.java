package com.kx.system.domain.es;

import com.kx.system.domain.anchor.AnchorPoint;
import lombok.Data;

@Data
public class EsAnchorPointDto extends AnchorPoint {

    private String ancestorsDeptIds;
    private Long deptId;
    private Long userId;


}
