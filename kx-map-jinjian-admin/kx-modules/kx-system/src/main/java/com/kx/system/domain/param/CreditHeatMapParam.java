package com.kx.system.domain.param;

import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class CreditHeatMapParam extends BaseEntity {
    List<String> gridCodes;

    String gridCode;
}
