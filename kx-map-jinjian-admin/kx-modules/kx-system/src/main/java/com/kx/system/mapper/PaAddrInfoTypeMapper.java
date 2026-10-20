package com.kx.system.mapper;

import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.api.domain.SysUser;
import com.kx.system.api.domain.shop.dto.GridInfoDto;
import com.kx.system.api.domain.shop.dto.OrgGridInfoDto;
import com.kx.system.api.domain.shop.param.OrgGridInfoParam;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.Township;
import com.kx.system.domain.Village;
import com.kx.system.domain.anchor.PaPocAddress;
import com.kx.system.domain.grid.AddrInfoDto;
import com.kx.system.domain.grid.AddrInfoParam;
import com.kx.system.domain.param.SearchAddrInfoUserParam;
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
public interface PaAddrInfoTypeMapper {
    /**
     * 查询乡镇村
     *
     * @param id 乡镇村ID
     * @return 乡镇村
     */
    PaAddrInfoType selectAddrInfoById(Long id);

    /**
     * 初始化网格
     *
     * @param paAddrInfoType
     */
    void initAddrInfoType(PaAddrInfoType paAddrInfoType);

    /**
     * 查询网格数量
     *
     * @param paAddrInfoType 网格参数
     * @return 数量
     */
    int getSum(PaAddrInfoType paAddrInfoType);

    /**
     * 查询网格
     *
     * @param PaAddrInfoType 网格参数
     * @return 网格数据
     */
    List<PaAddrInfoType> getPaAddrInfoTypeList(PaAddrInfoType PaAddrInfoType);

    /**
     * 根据行政区划编码查询本级或下辖区域的编码和边界数据
     *
     * @param areaCode  行政区划编码
     * @param isCurrent true-查本级，false-查下辖
     * @return 编码+边界数据列表
     */
    List<PaAddrInfoType> selectCoordinatesByAreaCode(@Param("areaCode") String areaCode, @Param("isCurrent") Boolean isCurrent);


    /**
     * 查询网格
     *
     * @param PaAddrInfoType 参数
     * @return 网格数据
     */
    List<PaAddrInfoType> getAddrInfoTypeBottom(PaAddrInfoType PaAddrInfoType);

    /**
     * 查询网格管理员
     *
     * @param param 参数
     * @return 网格数据
     */
    List<SysUser> getPaAddrInfoTypeUser(SearchAddrInfoUserParam param);

    /**
     * 新增乡镇村
     *
     * @param PaAddrInfoType 乡镇村
     * @return 结果
     */
    int insertAddrInfo(PaAddrInfoType PaAddrInfoType);

    /**
     * 清空子网格
     *
     * @param parentCode
     * @param typeCode
     */
    void cleanChildGrid(@Param("parentCode") String parentCode, @Param("typeCode") Long typeCode);

    /**
     * 修改乡镇村
     *
     * @param PaAddrInfoType 乡镇村
     * @return 结果
     */
    int updateAddrInfo(PaAddrInfoType PaAddrInfoType);
    /**
     * 修改乡镇村
     *secondUserId 允许为空
     * @param PaAddrInfoType 乡镇村
     * @return 结果
     */
    int updateAddrInfo2(PaAddrInfoType PaAddrInfoType);
    /**
     * 修改乡镇村
     *
     * @param PaAddrInfoType 乡镇村
     * @return 结果
     */
    int updateAddrInfoByCode(PaAddrInfoType PaAddrInfoType);

    /**
     * 删除乡镇村
     *
     * @param id 乡镇村ID
     * @return 结果
     */
    void deleteAddrInfoById(Long id);

    /**
     * 删除网格
     *
     * @param code 网格code
     * @return 结果
     */
    void deleteAddrInfoByCode(@Param("code") String code, @Param("typeCode") long typeCode);

    PaAddrInfoType selectByCode(@Param("code") String code, @Param("typeCode") long typeCode);



    List<PaAddrInfoType> selectByCodeList(@Param("codeList") List<String> codeList);

    List<PaAddrInfoType> selectByParentCodeList(@Param("codeList") List<String> codeList);

    List<PaAddrInfoType> selectByParentCode(@Param("parentCode") String parentCode);

    List<PaAddrInfoType> selectSelfAndChildByParentCode(@Param("parentCode") String parentCode);

    List<PaAddrInfoType> selectByDeptId(@Param("deptId") Long deptId);

    List<PaAddrInfoType> selectByUserId(@Param("userId") Long userId);

    List<PaAddrInfoType> selectAllCode();

    List<PaAddrInfoType> testSelect();

    List<PaPocAddress> address();

    void addressUpdateByLonLat(@Param("list") List<PaPocAddress> addressList);


    List<PaAddrInfoType> selectCodeAndParentCodeAll();

    List<PaAddrInfoType> listByIds(@Param("codeList") List<String> codeList, @Param("typeCode") Long typeCode);

    List<PaAddrInfoType> selectByCodeAndJurisdiction(@Param("code") String code, @Param("typeCode") String typeCode,
                                                     @Param("jurisdiction") String jurisdiction);

    List<PaAddrInfoType> selectBaseByCodeAndJurisdiction(@Param("code") String code,
                                                         @Param("jurisdiction") String jurisdiction);

    /**
     * 查询网格下所有子孙网格
     *
     * @param parentCode 父网格ID
     * @param typeCode   网格类型
     * @return
     */
    List<PaAddrInfoType> listAllChildGrid(@Param("parentCode") String parentCode, @Param("typeCode") Long typeCode);

    /**
     * 根据typeCode查询网格主管员和责任机构
     *
     * @param dictCode 网格类型
     * @return 网格数据
     */
    List<PaAddrInfoType> selectDeptIdAndUserIdByTypeCode(Long dictCode);

    /**
     * 清空网格员
     *
     * @param id
     */
    void cleanUser(@Param("id") Long id);

    List<String> selectParentsByCode();

    List<String> selectParentsByCode2(String parentCode);


    PaAddrInfoType getByGridCode(String gridCode);

    List<PaAddrInfoType> getPaAddrInfoTypeListByPermission(PaAddrInfoType paAddrInfoType);

    List<PaAddrInfoType> selectByDeptCode(@Param("deptCode") String deptCode);


    Integer getMinAddrLevel(@Param("typeCode")Long typeCode,@Param("userId")Long userId);

    Integer batchUpdate(@Param("paAddrInfoTypeList") List<PaAddrInfoType> paAddrInfoTypeList);


    Integer selectByChildNum(String code);

    Integer getMinimumCount();

    Integer getMinimumCountByParentCode(String parentCode);

    List<OrgGridInfoDto> getPaAddrInfoTypeByOrgCode(OrgGridInfoParam orgGridInfoParam);

    List<GridInfoDto> getGridInfo();

    GridInfoDto getGridInfoByCode(@Param("gridCode") String gridCode);
}
