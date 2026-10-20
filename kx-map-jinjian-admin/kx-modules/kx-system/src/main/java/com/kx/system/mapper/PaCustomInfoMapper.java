package com.kx.system.mapper;

import com.kx.system.domain.CustomInfo;
import com.kx.system.domain.PaCustomInfo;
import com.kx.system.domain.dto.MainBusinessData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PaCustomInfoMapper {
    int insert(PaCustomInfo record);

    int insertSelective(PaCustomInfo record);

    PaCustomInfo selectByNm(String nm);

    PaCustomInfo queryByNm(String nm);

    int batchInsertPaCustomInfo(@Param("list") List<PaCustomInfo> list);

    int batchDeletePaCustomInfo(@Param("list") List<CustomInfo> list);

    void updatePaCustomInfo(PaCustomInfo paCustomInfo);

    List<String> getAllManagerIds();

    List<PaCustomInfo> selectByManegerId(String managerCode);

    List<PaCustomInfo> selectAll();

    MainBusinessData getCustTypeByNetWorkCode(@Param("agencyNo") String agencyNo, @Param("date") String date);
    int countByOutCode(@Param("outletCode") String outletCode);
    int countBymanagerId(@Param("managerId") String managerId);

    /**
     * 根据身份证获取今天是身份的七星级客户
     * @return
     */
    List<PaCustomInfo> selectCurrentBirthday();

    /**
     * 查询全行七星级及以上的客户
     * @return
     */
    List<PaCustomInfo> selectSevenStarCustomers();

    /**
     * 查询星级下降的客户信息
     * @return
     */
    List<PaCustomInfo> selectStarDropCustomers();

    /**
     * 查询存款流失的客户信息
     * @return
     */
    List<PaCustomInfo> selectLossOfDepositsCustomers();

    List<PaCustomInfo> selectByEtlDtBetween(@Param("beginEtlDt") Date beginEtlDt, @Param("endEtlDt") Date endEtlDt);

    List<PaCustomInfo> selectByAreaCode(@Param("areaCode") String areaCode);
}

