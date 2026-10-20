package com.kx.system.mapper;

import java.util.List;
import com.kx.system.domain.AnchorChangeLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 锚点变更记录Mapper接口
 * 
 * @author kangxu
 * @date 2024-04-08
 */
@Mapper
public interface AnchorChangeLogMapper 
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
     * 删除锚点变更记录
     * 
     * @param id 锚点变更记录主键
     * @return 结果
     */
    public int deleteAnchorChangeLogById(Long id);

    /**
     * 批量删除锚点变更记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAnchorChangeLogByIds(Long[] ids);
}
