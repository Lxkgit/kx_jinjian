package com.kx.system.mapper;


import com.kx.system.domain.CustomInfo;
import com.kx.system.domain.CustomInfoVo;
import com.kx.system.domain.dto.ManagerCustInfoDto;
import com.kx.system.domain.dto.MangerIncrTrendsNumberDto;
import com.kx.system.domain.vo.ClientRange;
import com.kx.system.domain.vo.PaCustomInfoVO;
import com.kx.system.domain.vo.TmCustomInfoVo;
import com.kx.system.domain.vo.ValuableClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomInfoMapper {



    List<TmCustomInfoVo> selectCustomInfoByClientCodes(@Param("list") List<String> clientCodeList);

    /**
     * 修改客户的生日
     * @param id
     * @param birthday
     * @return
     */
    int updateCustomBirthdayBYId(@Param("nm") Long nm, @Param("birthday") String birthday);

    List<String> getAllManagerIds();

    /**
     * 总客户数
     *
     * @return
     */
    List<ManagerCustInfoDto> getCustStatistics();

    /**
     * 本年客户增量
     *
     * @return
     */
    List<ManagerCustInfoDto> getIncreClientThisYear();

    /**
     * 本月客户增量
     *
     * @return
     */
    List<ManagerCustInfoDto> getIncreClientThisMonth();

    /**
     * 本日客户增量
     *
     * @return
     */
    List<ManagerCustInfoDto> getIncreClientThisDay();

    List<String> selectByTownShipAndArea(@Param("townShip") String townShip, @Param("area") String area);

    Integer selectIncreCoustomThisYear(@Param("townShip") String townShip, @Param("area") String area, @Param("thisYear") String thisYear);

    Integer selectIncreCoustomThisMonth(@Param("townShip") String townShip, @Param("area") String area, @Param("thisMonth") String thisMonth);

    Integer selectIncreCoustomThisDay(@Param("townShip") String townShip, @Param("area") String area, @Param("thisDay") String thisDay);

    List<CustomInfoVo> queryCustomerInfoByNm(String nm);

    List<ValuableClient> selectManageValuableClient(String code);

    List<ValuableClient> selectManageValuableClientByJobNumberList(@Param("list") List<String> jobNumberList);

    /**
     * 获取客户增长趋势
     *
     * @return
     */
    List<MangerIncrTrendsNumberDto> getCustMangerIncrTrends();

    List<ValuableClient> selectManageValuableClientByYear(@Param("code") String code, @Param("thisYear") Integer thisYear);


    List<ValuableClient> selectManageValuableClientByJobNumberListByYear(@Param("list") List<String> jobNumberList, @Param("year") int year);

    List<ValuableClient> selectVillageValuableClient(String code);

    List<ValuableClient> selectTownValuableClient(String code);

    List<ValuableClient> selectHeadValuableClient();


    List<ValuableClient> selectVillageValuableClientByYear(@Param("code") String code, @Param("year") int year);

    List<ValuableClient> selectTownValuableClientByYear(@Param("code") String code, @Param("year") int year);

    List<ValuableClient> selectHeadValuableClientByYear(int year);

    List<ClientRange> selectVillageAccountOpenTimeCompare(String code);

    List<ClientRange> selectTownAccountOpenTimeCompare(String code);

    List<ClientRange> selectHeadAccountOpenTimeCompare();


    List<ClientRange> selectVillageUnitAgeCompare(@Param("villageCode") String villageCode, @Param("townCode") String townCode);

    List<ClientRange> selectVillageUnitSexCompare(@Param("villageCode") String villageCode, @Param("townCode") String townCode);

    List<CustomInfo> selectNeedSynchronizeCustomInfo(String nowDate);

    /**
     * 根据锚点Id查询信息
     * @param clientAnchorId 锚点id
     * @return
     */
    List<PaCustomInfoVO> selectPaCustomInfoByClientAnchorId(Long clientAnchorId);

    /**
     * 通过区域code, 获取客户信息
     * @param areaCode 区域code
     * @return 客户信息
     */
    List<CustomInfo> selectCustomInfoByAreaCode(String areaCode);

    /**
     * 通过区域code, 获取客户数量
     * @param areaCode 区域code
     * @return 客户数量
     */
    Long selectCountByAreaCode(String areaCode);
}

