package com.kx.system.domain.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchAnchorChangeLogParam extends ExportPageBaseParam {
    private String anchorName;
    private String newAddressTypeCodes;
    private String oldAddressTypeCodes;
    private String operatorName;
}
