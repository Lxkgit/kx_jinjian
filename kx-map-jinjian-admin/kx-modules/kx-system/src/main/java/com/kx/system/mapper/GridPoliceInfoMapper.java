package com.kx.system.mapper;

import com.kx.system.domain.grid.message.GridPoliceInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 网格评级信息维护表 Mapper 接口
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
public interface GridPoliceInfoMapper {

    void insertGridPoliceInfo(GridPoliceInfo gridPoliceInfo);

    void deleteGridPoliceInfoById(@Param("id") Long id);

    void updateGridPoliceInfo(GridPoliceInfo gridPoliceInfo);

    GridPoliceInfo selectGridPoliceInfoByGridCode(@Param("gridCode") String gridCode);

    GridPoliceInfo selectGridPoliceInfoById(@Param("id") Long id);


//    List<GridPoliceInfo> list(GridPoliceInfo gridRatingInfo);
//
//    GridPoliceInfo getById(Long id);
//
//    GridPoliceInfo getByYear(@Param("ratingYear") String ratingYear, @Param("gridCode") String gridCode);
//
//    Long insertGridRatingInfo(GridPoliceInfo gridRatingInfo);
//
//    Long updateGridRatingInfo(GridPoliceInfo gridRatingInfo);
//
//    void deleteById(Long id);
}
