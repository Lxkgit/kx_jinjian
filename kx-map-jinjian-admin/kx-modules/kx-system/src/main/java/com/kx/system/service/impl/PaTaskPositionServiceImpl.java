package com.kx.system.service.impl;

import com.kx.system.domain.PaTaskPosition;
import com.kx.system.domain.param.ListPaTaskPositionParam;
import com.kx.system.mapper.PaTaskPositionMapper;
import com.kx.system.service.IPaTaskPositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaTaskPositionServiceImpl implements IPaTaskPositionService {
    @Resource
    private PaTaskPositionMapper paTaskPositionMapper;

    @Override
    public List<PaTaskPosition> list(ListPaTaskPositionParam param) {
        return paTaskPositionMapper.list(param);
    }
}
