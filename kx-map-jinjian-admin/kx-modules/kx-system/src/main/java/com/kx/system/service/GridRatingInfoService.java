package com.kx.system.service;

import com.kx.system.domain.grid.GridRatingInfo;
import java.util.List;
/**
 * <p>
 * 网格评级信息维护表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridRatingInfoService {

  /**
  * @Description: 列表查询
  * @param gridRatingInfo
  * @return List<GridRatingInfo>
  */
  List<GridRatingInfo> list(GridRatingInfo gridRatingInfo);

    /**
    * @Description: 根据id查询
    * @param id
    * @return GridRatingInfo
    */
    GridRatingInfo getById(Long id);

    /**
    * @Description: 新增/修改
    * @param gridRatingInfo
    * @return GridRatingInfo
    */
    GridRatingInfo saveGridRatingInfo(GridRatingInfo gridRatingInfo);

    /**
    * @Description: 批量删除
    * @param ids
    */
    String deleteByIds(String ids);

    /**
    * @Description: 删除
    * @param id
    */
    void deleteById(Long id);
}
