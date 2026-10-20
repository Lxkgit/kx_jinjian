package com.kx.system.service;

import java.util.List;
import com.kx.system.domain.anchor.BuildingAnchorPoint;

/**
 * 楼宇锚点Service接口
 *
 * @author kangxu
 * @date 2024-01-23
 */
public interface IBuildingAnchorPointService
{
    /**
     * 查询楼宇锚点
     *
     * @param id 楼宇锚点主键
     * @return 楼宇锚点
     */
    public BuildingAnchorPoint selectBuildingAnchorPointById(Long id);

    /**
     * 查询楼宇锚点列表
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 楼宇锚点集合
     */
    public List<BuildingAnchorPoint> selectBuildingAnchorPointList(BuildingAnchorPoint buildingAnchorPoint);

    /**
     * 新增楼宇锚点
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 结果
     */
    public int insertBuildingAnchorPoint(BuildingAnchorPoint buildingAnchorPoint);

    /**
     * 修改楼宇锚点
     *
     * @param buildingAnchorPoint 楼宇锚点
     * @return 结果
     */
    public int updateBuildingAnchorPoint(BuildingAnchorPoint buildingAnchorPoint);

    /**
     * 批量删除楼宇锚点
     *
     * @param ids 需要删除的楼宇锚点主键集合
     * @return 结果
     */
    public int deleteBuildingAnchorPointByIds(Long[] ids);

    /**
     * 删除楼宇锚点信息
     *
     * @param id 楼宇锚点主键
     * @return 结果
     */
    public int deleteBuildingAnchorPointById(Long id);
}
