package com.kx.system.mapper;


import com.kx.system.domain.PaTaskPosition;
import com.kx.system.domain.param.ListPaTaskPositionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaTaskPositionMapper {

    int batchInsert(@Param("paTaskPositions") List<PaTaskPosition> paTaskPositions);

    void truncate();

    /**
     * 查询时空轨迹数据
     *
     * @param param
     * @return
     */
    List<PaTaskPosition> list(ListPaTaskPositionParam param);

    /**
     * 清空某一天数据
     * @param date
     */
    void deleteByDate(@Param("date") Date date);
}

