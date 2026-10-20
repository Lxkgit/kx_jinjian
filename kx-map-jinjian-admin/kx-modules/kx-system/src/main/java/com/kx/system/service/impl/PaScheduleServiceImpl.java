package com.kx.system.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.kx.common.core.exception.ServiceException;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.config.enums.DictDataEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.PaScheduleMapper;
import com.kx.system.domain.PaSchedule;
import com.kx.system.service.IPaScheduleService;

/**
 * 日程Service业务层处理
 *
 * @author kangxu
 * @date 2024-01-25
 */
@Service
public class PaScheduleServiceImpl implements IPaScheduleService {
    @Autowired
    private PaScheduleMapper paScheduleMapper;

    /**
     * 查询日程
     *
     * @param id 日程主键
     * @return 日程
     */
    @Override
    public PaSchedule selectPaScheduleById(Long id) {
        return paScheduleMapper.selectPaScheduleById(id);
    }

    /**
     * 查询日程列表
     *
     * @param paSchedule 日程
     * @return 日程
     */
    @Override
    public List<PaSchedule> selectPaScheduleList(PaSchedule paSchedule) {
        return paScheduleMapper.selectPaScheduleList(paSchedule);
    }

    /**
     * 新增日程
     *
     * @param paSchedule 日程
     * @return 结果
     */
    @Override
    public int insertPaSchedule(PaSchedule paSchedule) {
        //判断是否需要重新生成
        if (paSchedule.getIsRepeat() == DictDataEnum.IS_TRUE.getKey()) {
            LocalDateTime startTime = paSchedule.getStartTime();
            LocalDateTime lineTime = paSchedule.getStartTime();
            int count = 0; //日期循环次数
            while (paSchedule.getEndTime().isAfter(lineTime)) {
                paSchedule.setOwner(SecurityUtils.getUserId());
                paSchedule.setCreateBy(SecurityUtils.getUserId().toString());
                if (StringUtils.isNotEmpty(paSchedule.getOwnerIds())) {
                    String[] ownerIdArr = paSchedule.getOwnerIds().split(",");
                    for (int i = 0; i < ownerIdArr.length; i++) {
                        paSchedule.setOwner(Long.parseLong(ownerIdArr[i])); // 每次设置不同的 owner
                        if (count == 0) {
                            paSchedule.setStartTime(startTime);
                            paSchedule.setEndTime(paSchedule.getEndTime().isAfter(addDay(startTime, 1)) ? addDay(startTime, 1) : paSchedule.getEndTime());
                        } else {
                            paSchedule.setStartTime(startTime.toLocalDate().plusDays(count).atStartOfDay());
                            paSchedule.setEndTime(paSchedule.getEndTime().isAfter(addDay(startTime, count + 1)) ? addDay(startTime, count + 1) : paSchedule.getEndTime());
                        }
                        paScheduleMapper.insertPaSchedule(paSchedule);
                    }
                } else {
                    throw new ServiceException("参与人员不能为空");
                }
                //再加一天
                lineTime = addDay(lineTime, 1);
                count++;
            }
        } else {
            if (StringUtils.isNotEmpty(paSchedule.getOwnerIds())) {
                // 不需要重新生成时，执行一次插入操作
                String[] ownerIdArr = paSchedule.getOwnerIds().split(",");
                for (int i = 0; i < ownerIdArr.length; i++) {
                    paSchedule.setOwner(Long.parseLong(ownerIdArr[i])); // 每次设置不同的 owner
                    paSchedule.setCreateBy(SecurityUtils.getUserId().toString());
                    paScheduleMapper.insertPaSchedule(paSchedule);
                }
            } else {
                throw new ServiceException("参与人员不能为空");
            }
        }
        return 1;
    }

    private LocalDateTime addDay(LocalDateTime lineTime, int num) {
        return LocalDateTime.parse(lineTime.toLocalDate().plusDays(num).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 修改日程
     *
     * @param paSchedule 日程
     * @return 结果
     */
    @Override
    public int updatePaSchedule(PaSchedule paSchedule) {
        return paScheduleMapper.updatePaSchedule(paSchedule);
    }

    /**
     * 批量删除日程
     *
     * @param ids 需要删除的日程主键
     * @return 结果
     */
    @Override
    public int deletePaScheduleByIds(Long[] ids) {
        return paScheduleMapper.deletePaScheduleByIds(ids);
    }

    /**
     * 删除日程信息
     *
     * @param id 日程主键
     * @return 结果
     */
    @Override
    public int deletePaScheduleById(Long id) {
        return paScheduleMapper.deletePaScheduleById(id);
    }
}
