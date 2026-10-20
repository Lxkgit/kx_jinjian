package com.kx.system.service;

import com.kx.system.domain.PaTaskPosition;
import com.kx.system.domain.param.ListPaTaskPositionParam;

import java.util.List;

public interface IPaTaskPositionService {
    /**
     * 查看客户经理某一天时空轨迹数据
     *
     * @param param
     * @return
     */
    List<PaTaskPosition> list(ListPaTaskPositionParam param);
}
