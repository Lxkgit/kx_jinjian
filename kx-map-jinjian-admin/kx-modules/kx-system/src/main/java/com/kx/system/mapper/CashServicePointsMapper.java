package com.kx.system.mapper;

import com.kx.system.domain.CashServicePointExport;
import com.kx.system.domain.CashServicePoints;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.dto.CashServicePointsDTO;
import com.kx.system.domain.dto.CashServicePointsExcelDTO;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CashServicePointsMapper {

    List<CashServicePoints> selectServiceMapper(CashServicePointsDTO cash);

    CashServicePoints selectCashDetails(long id);

    List<CashServicePoints> getAnchorInfo(String gridCode);


    void updateCashServiceGridCode(CashServicePoints cashServicePoints);

    List<AnchorPoint> selectAll();

    List<AnchorPoint> selectAnchorPointByAreaCodes();

    String selectCashServiceTotal(String gridCode);

    void insert(@Param("list") List<CashServicePointsExcelDTO> list);

    List<CashServicePointExport> exportBatch(CashServicePoints cashServicePoints);
}


