package com.kx.system.mapper;

import com.kx.system.domain.grid.GridRatingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>
 * 网格评级信息维护表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridRatingInfoMapper {

    List<GridRatingInfo> list(GridRatingInfo gridRatingInfo);

    GridRatingInfo getById(Long id);

    GridRatingInfo getByYear(@Param("ratingYear") String ratingYear, @Param("gridCode") String gridCode);

    Long insertGridRatingInfo(GridRatingInfo gridRatingInfo);

    Long updateGridRatingInfo(GridRatingInfo gridRatingInfo);

    void deleteById(Long id);
}
