package com.kx.system.mapper;

import com.kx.system.domain.PaAddrInfo;
import com.kx.system.domain.PaVillageInfo;
import com.kx.system.domain.Township;
import com.kx.system.domain.Village;
import com.kx.system.domain.grid.AddrInfoDto;
import com.kx.system.domain.grid.AddrInfoParam;
import com.kx.system.domain.vo.AddrInfoVO;
import com.kx.system.domain.vo.KpiTargetVO;
import com.kx.system.domain.vo.LocationOfTownGovernmentVo;
import com.kx.system.domain.vo.TaskSelectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 乡镇村Mapper接口
 *
 * @author gary
 * @date 2021-11-25
 */
@Mapper
public interface PaAddrInfoMapper {
    /**
     * 查询乡镇村
     *
     * @param id 乡镇村ID
     * @return 乡镇村
     */
    public PaAddrInfo selectAddrInfoById(Long id);

    /**
     * 查询乡镇村列表
     *
     * @param paAddrInfo 乡镇村
     * @return 乡镇村集合
     */
    public List<PaAddrInfo> selectAddrInfoList(PaAddrInfo paAddrInfo);

    /**
     * 新增乡镇村
     *
     * @param paAddrInfo 乡镇村
     * @return 结果
     */
    public int insertAddrInfo(PaAddrInfo paAddrInfo);

    /**
     * 修改乡镇村
     *
     * @param paAddrInfo 乡镇村
     * @return 结果
     */
    public int updateAddrInfo(PaAddrInfo paAddrInfo);

    /**
     * 删除乡镇村
     *
     * @param id 乡镇村ID
     * @return 结果
     */
    public int deleteAddrInfoById(Long id);

    /**
     * 批量删除乡镇村
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAddrInfoByIds(Long[] ids);

    List<Township> selectAllTownList();

    List<Village> selectAllVillageList();

    PaAddrInfo selectByCode(String code);

    List<Village> selectUnMaintainedList();

    List<Village> selectVillageListByNetworkCode(String networkCode);

    int deleteAreaDistribution(PaAddrInfo paAddrInfo);

    List<PaAddrInfo> selectByName(String name);

    List<PaAddrInfo> selectByNames(@Param("list") List<String> names);

    List<PaAddrInfo> selectByCodeList(@Param("codeList") List<String> codeList);

    List<PaAddrInfo> selectByParentCodeList(@Param("codeList") List<String> codeList);

    List<PaAddrInfo> selectByParentCode(@Param("parentCode") String parentCode);

    List<PaAddrInfo> selectByNetworkCode(String networkCode);

    List<PaAddrInfo> selectByNotNetworkCode(Long networkCode);

    List<PaAddrInfo> selectAllCode();


    int checkArea(String area);

    String selectCodeByName(String townShip);

    List<String> selectCodeByNames(@Param("list") List<String> townShips);

    List<String> selectByNetworkCodes(@Param("networkCodes") List<String> networkCodes);

    String select(@Param("towncode") String towncode, @Param("area") String area);

    List<PaAddrInfo> selectVillageListByParentCode(String code);

    List<PaVillageInfo> getVillageByTownCode(String code);

    void updateCreditVillage(@Param("villageCode") String villageCode, @Param("s") String s);

    List<LocationOfTownGovernmentVo> getTownGovernmentAddr();

    String getTownVillageStrByCodeList(@Param("codeList") List<String> codeList);

    PaAddrInfo selectOneByName(String townShip);

    PaAddrInfo selectOneByNameAndParentName(@Param("area") String area, @Param("township") String township);

    List<String> selectNameListByIds(@Param("codeList") String[] codeList);

    List<KpiTargetVO> listByName(@Param("name") String name);

    List<KpiTargetVO> listByIds(@Param("codeList") List<String> codeList);

    String selectParentCodeByCode(@Param("code") String code);

    List<KpiTargetVO> listByNameAndBelogNetworkCodes(@Param("name") String name, @Param("networkCodes") List<String> networkCodes);

    List<AddrInfoDto> listAllVillage(AddrInfoParam addrInfoParam);

    List<TaskSelectVo> getAreaManagers(AddrInfoParam addrInfoParam);

    List<PaAddrInfo> selectAllGridList();

    void deleteAddrInfoByCode(String code);

    List<PaAddrInfo> selectByCodeAndJurisdiction(@Param("code") String code, @Param("jurisdiction") String jurisdiction);

    List<PaAddrInfo> selectByCodesAndJurisdiction(@Param("codeList") List<String> codeList,
                                                  @Param("jurisdiction") String jurisdiction);

    List<PaAddrInfo> selectCodeIsNull();

    List<PaAddrInfo> getAllAreaCoordinates();

    List<PaAddrInfo> getAreaCoordinatesByAreaCode(@Param("areaCode") String areaCode);

    List<PaAddrInfo> selectByNameAndAreaCode(@Param("name") String name, @Param("areaCode") String areaCode);

    List<PaAddrInfo> selectByCodeAndLevel(@Param("code") String code, @Param("level") int level);

    List<AddrInfoVO> selectRootAddr();


    List<AddrInfoVO> selectRootAddrByTypeCode(@Param("typeCode") String typeCode);

    List<AddrInfoVO> selectByParentCodes(@Param("parentCodes") List<String> parentCodes);

    int batchUpdate(@Param("paAddrInfoList") List<PaAddrInfo> paAddrInfoList);


}
