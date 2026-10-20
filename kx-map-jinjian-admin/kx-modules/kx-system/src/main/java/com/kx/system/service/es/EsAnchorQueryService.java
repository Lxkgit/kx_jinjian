package com.kx.system.service.es;

import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.customer.CustomerSelect;

import java.io.IOException;
import java.util.List;

public interface EsAnchorQueryService {

    List<AnchorPoint> selectByCustomerType(CustomerSelect select) throws IOException;

    List<AnchorPoint> queryGroup(CustomerSelect select) throws IOException;

    List<AnchorPoint> queryByCustomerType(CustomerSelect select) throws IOException;

    void syncDataToEs();
}
