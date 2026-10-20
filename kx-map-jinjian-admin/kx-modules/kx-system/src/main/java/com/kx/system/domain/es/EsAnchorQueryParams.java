package com.kx.system.domain.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsAnchorQueryParams {

    private List<String> belongGrid;

    private List<String> belongOrgCode;

    private List<String> belongManagerCode;

    private List<String> customerGroupId;

    private List<String> type;

    private String gridCode;
}
