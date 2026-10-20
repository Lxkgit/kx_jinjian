package com.kx.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.PaBacklogMapper;
import com.kx.system.domain.PaBacklog;
import com.kx.system.service.IPaBacklogService;

/**
 * 待办事项Service业务层处理
 *
 * @author kangxu
 * @date 2024-01-31
 */
@Service
public class PaBacklogServiceImpl implements IPaBacklogService
{
    @Autowired
    private PaBacklogMapper paBacklogMapper;

    /**
     * 查询待办事项
     *
     * @param id 待办事项主键
     * @return 待办事项
     */
    @Override
    public PaBacklog selectPaBacklogById(Long id)
    {
        return paBacklogMapper.selectPaBacklogById(id);
    }

    /**
     * 查询待办事项列表
     *
     * @param paBacklog 待办事项
     * @return 待办事项
     */
    @Override
    public List<PaBacklog> selectPaBacklogList(PaBacklog paBacklog)
    {
        return paBacklogMapper.selectPaBacklogList(paBacklog);
    }

    /**
     * 新增待办事项
     *
     * @param paBacklog 待办事项
     * @return 结果
     */
    @Override
    public int insertPaBacklog(PaBacklog paBacklog)
    {
        return paBacklogMapper.insertPaBacklog(paBacklog);
    }

    /**
     * 修改待办事项
     *
     * @param paBacklog 待办事项
     * @return 结果
     */
    @Override
    public int updatePaBacklog(PaBacklog paBacklog)
    {
        return paBacklogMapper.updatePaBacklog(paBacklog);
    }

    /**
     * 批量删除待办事项
     *
     * @param ids 需要删除的待办事项主键
     * @return 结果
     */
    @Override
    public int deletePaBacklogByIds(Long[] ids)
    {
        return paBacklogMapper.deletePaBacklogByIds(ids);
    }

    /**
     * 删除待办事项信息
     *
     * @param id 待办事项主键
     * @return 结果
     */
    @Override
    public int deletePaBacklogById(Long id)
    {
        return paBacklogMapper.deletePaBacklogById(id);
    }
}
