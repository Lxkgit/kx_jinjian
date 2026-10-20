package com.kx.system.mapper;

import com.kx.system.domain.PaBusinessInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaBusinessInfoMapper {

    PaBusinessInfo selectByBusinessLicenseNoAndBusinessIdentify(@Param("businessLicenseNo") String businessLicenseNo, @Param("businessIdentify") String businessIdentify);

    int insertSelective(PaBusinessInfo record);

    int batchInsertPaBusinessInfo(@Param("list") List<PaBusinessInfo> list);

    int updatePaBusinessInfo(PaBusinessInfo businessInfo);

    PaBusinessInfo selectByNm(@Param("nm") String nm);

    List<PaBusinessInfo> selectByEtlDtBetween(@Param("beginEtlDt") Date beginEtlDt, @Param("endEtlDt") Date endEtlDt);
}
