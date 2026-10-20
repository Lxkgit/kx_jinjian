package com.kx.system.service.impl;

import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.domain.*;
import com.kx.system.mapper.*;
import com.kx.system.service.IPaWorkBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 工作看板Service业务层处理
 *
 * @author kangxu
 * @date 2024-01-23
 */
@Service
public class PaWorkBoardServiceImpl implements IPaWorkBoardService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private PaWorkBoardMapper paWorkBoardMapper;

    @Autowired
    private PaScheduleMapper paScheduleMapper;

    @Autowired
    private PaBacklogMapper paBacklogMapper;

//    @Autowired
//    private PaTaskMapper paTaskMapper;

    @Override
    public List<SysMenu> businessNavigation() {
        List<SysMenu> menus = null;
        Long userId = SecurityUtils.getUserId();
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return menus;
    }

    @Override
    public DataOverviewEntity dataOverview() {
        return paWorkBoardMapper.dataOverview();
    }

    @Override
    public List<PaSchedule> weekSchedule(PaSchedule paSchedule) {
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            paSchedule.setOwner(SecurityUtils.getUserId());
        }
        return paScheduleMapper.selectPaScheduleList(paSchedule);
    }

    @Override
    public List<PaBacklog> toDoList() {
        PaBacklog paBacklog = new PaBacklog();
        paBacklog.setStatus(0); //查询未完成
        return paBacklogMapper.selectPaBacklogList(paBacklog);
    }

    @Override
    public TaskOverviewEntity workTrackOverview() {
//        PaTask paTask = new PaTask();
//        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
//            paTask.setOwner(SecurityUtils.getUserId());
//        }
//        return paTaskMapper.workTrackOverview(paTask);
        return null;
    }

    @Override
    public List<TaskOverviewEntity> workTrackList(PaTask paTask) {
//        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
//            paTask.setOwner(SecurityUtils.getUserId());
//        }
//        return paTaskMapper.workTrackList(paTask);
        return null;
    }
}
