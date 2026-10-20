package com.kx.system.mapper;


import com.kx.system.domain.PaTaskPosition;
import com.kx.system.domain.param.ListPaTaskPositionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ManagerTransferReferIndexMapper {

    int batchInsert(@Param("paTaskPositions") List<PaTaskPosition> paTaskPositions);

    void truncate();

    /**
     * 查询时空轨迹数据
     *
     * @param param
     * @return
     */
    List<PaTaskPosition> list(ListPaTaskPositionParam param);
}

