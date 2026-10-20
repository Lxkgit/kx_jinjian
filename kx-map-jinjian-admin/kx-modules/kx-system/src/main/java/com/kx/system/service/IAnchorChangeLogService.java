package com.kx.system.service;

import java.util.List;
import com.kx.system.domain.AnchorChangeLog;

/**
 * 锚点变更记录Service接口
 * 
 * @author kangxu
 * @date 2024-04-08
 */
public interface IAnchorChangeLogService 
{
    /**
     * 查询锚点变更记录
     * 
     * @param id 锚点变更记录主键
     * @return 锚点变更记录
     */
    public AnchorChangeLog selectAnchorChangeLogById(Long id);

    /**
     * 查询锚点变更记录列表
     * 
     * @param anchorChangeLog 锚点变更记录
     * @return 锚点变更记录集合
     */
    public List<AnchorChangeLog> selectAnchorChangeLogList(AnchorChangeLog anchorChangeLog);

    /**
     * 新增锚点变更记录
     * 
     * @param anchorChangeLog 锚点变更记录
     * @return 结果
     */
    public int insertAnchorChangeLog(AnchorChangeLog anchorChangeLog);

    /**
     * 修改锚点变更记录
     * 
     * @param anchorChangeLog 锚点变更记录
     * @return 结果
     */
    public int updateAnchorChangeLog(AnchorChangeLog anchorChangeLog);

    /**
     * 批量删除锚点变更记录
     * 
     * @param ids 需要删除的锚点变更记录主键集合
     * @return 结果
     */
    public int deleteAnchorChangeLogByIds(Long[] ids);

    /**
     * 删除锚点变更记录信息
     * 
     * @param id 锚点变更记录主键
     * @return 结果
     */
    public int deleteAnchorChangeLogById(Long id);
}
