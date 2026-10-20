package com.kx.system.service;

import com.kx.system.domain.enums.OverviewType;
import com.kx.system.domain.param.PaViewSmallParam;
import com.kx.system.domain.small.PaViewGridOverview;

public interface IPaViewService {

    Object query(PaViewSmallParam paViewSmallParam, OverviewType type);

    PaViewGridOverview queryOverview(PaViewSmallParam paViewSmallParam);
}
