package com.kx.system.service;

import cn.hutool.json.JSONObject;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.GridExport;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.grid.GridMessageInfo2;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
/**
 * <p>
 * 网格基础信息维护表 服务类
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridMessageInfoService {

  /**
  * @Description: 列表查询
  * @param gridMessageInfo
  * @return List<GridMessageInfo2>
  */
  List<GridMessageInfo2> list(GridMessageInfo2 gridMessageInfo);

    /**
    * @Description: 根据id查询
    * @param id
    * @return GridMessageInfo2
    */
    GridMessageInfo2 getById(Long id);

    /**
    * @Description: 新增/修改
    * @param gridMessageInfo
    * @return GridMessageInfo2
    */
    GridMessageInfo2 saveGridMessageInfo(GridMessageInfo2 gridMessageInfo);

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

    GridMessageInfo2 getByGridCode(String gridCode);

    void gridMessageSynchroPaAddrInfo(String code);

    List<AnchorPoint> getGridInfo(String gridCode);

    List<PaAddrInfoType> getAllByGridCode(String gridCode);

    void selectAllGrid(HttpServletResponse response, GridMessageInfo2 gridExport) throws UnsupportedEncodingException;

  JSONObject pageLsit(GridMessageInfo2 gridMessageInfo);
}
