package com.kx.system.mapper;

import java.util.List;
import com.kx.system.domain.PaBacklog;

/**
 * 待办事项Mapper接口
 * 
 * @author kangxu
 * @date 2024-01-31
 */
public interface PaBacklogMapper 
{
    /**
     * 查询待办事项
     * 
     * @param id 待办事项主键
     * @return 待办事项
     */
    public PaBacklog selectPaBacklogById(Long id);

    /**
     * 查询待办事项列表
     * 
     * @param paBacklog 待办事项
     * @return 待办事项集合
     */
    public List<PaBacklog> selectPaBacklogList(PaBacklog paBacklog);

    /**
     * 新增待办事项
     * 
     * @param paBacklog 待办事项
     * @return 结果
     */
    public int insertPaBacklog(PaBacklog paBacklog);

    /**
     * 修改待办事项
     * 
     * @param paBacklog 待办事项
     * @return 结果
     */
    public int updatePaBacklog(PaBacklog paBacklog);

    /**
     * 删除待办事项
     * 
     * @param id 待办事项主键
     * @return 结果
     */
    public int deletePaBacklogById(Long id);

    /**
     * 批量删除待办事项
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePaBacklogByIds(Long[] ids);
}
