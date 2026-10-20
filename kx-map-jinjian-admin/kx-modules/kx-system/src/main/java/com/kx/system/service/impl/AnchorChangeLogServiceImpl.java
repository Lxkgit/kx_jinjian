package com.kx.system.service.impl;

import java.util.List;
import com.kx.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.AnchorChangeLogMapper;
import com.kx.system.domain.AnchorChangeLog;
import com.kx.system.service.IAnchorChangeLogService;

/**
 * 锚点变更记录Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-08
 */
@Service
public class AnchorChangeLogServiceImpl implements IAnchorChangeLogService
{
    @Autowired
    private AnchorChangeLogMapper anchorChangeLogMapper;

    /**
     * 查询锚点变更记录
     *
     * @param id 锚点变更记录主键
     * @return 锚点变更记录
     */
    @Override
    public AnchorChangeLog selectAnchorChangeLogById(Long id)
    {
        return anchorChangeLogMapper.selectAnchorChangeLogById(id);
    }

    /**
     * 查询锚点变更记录列表
     *
     * @param anchorChangeLog 锚点变更记录
     * @return 锚点变更记录
     */
    @Override
    public List<AnchorChangeLog> selectAnchorChangeLogList(AnchorChangeLog anchorChangeLog)
    {
        return anchorChangeLogMapper.selectAnchorChangeLogList(anchorChangeLog);
    }

    /**
     * 新增锚点变更记录
     *
     * @param anchorChangeLog 锚点变更记录
     * @return 结果
     */
    @Override
    public int insertAnchorChangeLog(AnchorChangeLog anchorChangeLog)
    {
        anchorChangeLog.setCreateTime(DateUtils.getNowDate());
        return anchorChangeLogMapper.insertAnchorChangeLog(anchorChangeLog);
    }

    /**
     * 修改锚点变更记录
     *
     * @param anchorChangeLog 锚点变更记录
     * @return 结果
     */
    @Override
    public int updateAnchorChangeLog(AnchorChangeLog anchorChangeLog)
    {
        return anchorChangeLogMapper.updateAnchorChangeLog(anchorChangeLog);
    }

    /**
     * 批量删除锚点变更记录
     *
     * @param ids 需要删除的锚点变更记录主键
     * @return 结果
     */
    @Override
    public int deleteAnchorChangeLogByIds(Long[] ids)
    {
        return anchorChangeLogMapper.deleteAnchorChangeLogByIds(ids);
    }

    /**
     * 删除锚点变更记录信息
     *
     * @param id 锚点变更记录主键
     * @return 结果
     */
    @Override
    public int deleteAnchorChangeLogById(Long id)
    {
        return anchorChangeLogMapper.deleteAnchorChangeLogById(id);
    }
}
