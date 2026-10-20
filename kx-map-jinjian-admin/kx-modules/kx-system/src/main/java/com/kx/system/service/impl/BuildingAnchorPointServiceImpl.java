package com.kx.system.service.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.domain.anchor.BuildingAnchorPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.BuildingAnchorPointMapper;
import com.kx.system.service.IBuildingAnchorPointService;

/**
 * 楼宇锚点Service业务层处理
 *
 * @author kangxu
 * @date 2024-01-23
 */
@Service
public class BuildingAnchorPointServiceImpl implements IBuildingAnchorPointService
{
    @Autowired
    private BuildingAnchorPointMapper buildingAnchorPointMapper;

    /**
     * 查询楼宇锚点
     *
     * @param id 楼宇锚点主键
     * @return 楼宇锚点
     */
    @Override
    public BuildingAnchorPoint selectBuildingAnchorPointById(Long id)
    {
        return buildingAnchorPointMapper.selectBuildingAnchorPointById(id);
    }

    /**
     * 查询楼宇锚点列表
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 楼宇锚点
     */
    @Override
    public List<BuildingAnchorPoint> selectBuildingAnchorPointList(BuildingAnchorPoint buildingAnchorPoint)
    {
        if (StringUtils.isNotNull(buildingAnchorPoint.getPageNum()) && StringUtils.isNotNull(buildingAnchorPoint.getPageSize())) {
            PageHelper.startPage(buildingAnchorPoint.getPageNum(), buildingAnchorPoint.getPageSize());
        }
        return buildingAnchorPointMapper.selectBuildingAnchorPointList(buildingAnchorPoint);
    }

    /**
     * 新增楼宇锚点
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 结果
     */
    @Override
    public int insertBuildingAnchorPoint(BuildingAnchorPoint buildingAnchorPoint) {
        if(StringUtils.isNotEmpty(buildingAnchorPoint.getNetworkCode())){
            String networkCode = buildingAnchorPoint.getNetworkCode();
            String[] networkCodeArr = networkCode.replace("\"","").split(",");
            buildingAnchorPoint.setBelongGrid(Long.parseLong(networkCodeArr[networkCodeArr.length-1].trim()));
        }
        return buildingAnchorPointMapper.insertBuildingAnchorPoint(buildingAnchorPoint);
    }

    /**
     * 修改楼宇锚点
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 结果
     */
    @Override
    public int updateBuildingAnchorPoint(BuildingAnchorPoint buildingAnchorPoint)
    {
        return buildingAnchorPointMapper.updateBuildingAnchorPoint(buildingAnchorPoint);
    }

    /**
     * 批量删除楼宇锚点
     *
     * @param ids 需要删除的楼宇锚点主键
     * @return 结果
     */
    @Override
    public int deleteBuildingAnchorPointByIds(Long[] ids)
    {
        return buildingAnchorPointMapper.deleteBuildingAnchorPointByIds(ids);
    }

    /**
     * 删除楼宇锚点信息
     *
     * @param id 楼宇锚点主键
     * @return 结果
     */
    @Override
    public int deleteBuildingAnchorPointById(Long id)
    {
        return buildingAnchorPointMapper.deleteBuildingAnchorPointById(id);
    }
}
