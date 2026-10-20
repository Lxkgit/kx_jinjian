package com.kx.system.service;

import com.kx.system.domain.grid.GridCruxInfo;
import java.util.List;
/**
 * <p>
 * 网格关键信息维护表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridCruxInfoService {

  /**
  * @Description: 列表查询
  * @param gridCruxInfo
  * @return List<GridCruxInfo>
  */
  List<GridCruxInfo> list(GridCruxInfo gridCruxInfo);

    /**
    * @Description: 根据id查询
    * @param id
    * @return GridCruxInfo
    */
    GridCruxInfo getById(Long id);

    /**
    * @Description: 新增/修改
    * @param gridCruxInfo
    * @return GridCruxInfo
    */
    GridCruxInfo saveGridCruxInfo(GridCruxInfo gridCruxInfo);

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
