package com.kx.system.mapper;

import com.kx.system.domain.vo.PaAddrBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaAddrBaseInfoMapper {

    int updateBaseInfoManagerCodeByCode(PaAddrBaseInfo paAddrBaseInfo);

    int updateBaseInfoNetWorkByCode(PaAddrBaseInfo paAddrBaseInfo);

    PaAddrBaseInfo selectBaseInfoByCode(String addrCode);

    List<String> selectBelongManagerUserJobNum(@Param("codeList") String[] codeList);

    List<Long> selectCodeListByName(@Param("addrName") String addrName);

    List<String> selectManagersByCode(@Param("addrCode") String addrCode);

    void updatePaAddrBaseInfoByAddrCode(PaAddrBaseInfo paAddrBaseInfo);

    void insertPaAddrBaseInfo(PaAddrBaseInfo paAddrBaseInfo);

    void deletePaAddrBaseInfoByCode(String code);
}
