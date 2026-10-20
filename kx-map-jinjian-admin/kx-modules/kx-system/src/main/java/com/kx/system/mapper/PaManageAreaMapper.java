package com.kx.system.mapper;

import com.kx.system.domain.PaManageArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 辖区信息Mapper接口
 *
 * @author gary
 * @date 2021-12-07
 */
@Mapper
public interface PaManageAreaMapper {
    /**
     * 查询辖区信息
     *
     * @param id 辖区信息ID
     * @return 辖区信息
     */
    public PaManageArea selectPaManageAreaById(Long id);

    /**
     * 查询辖区信息列表
     *
     * @param paManageArea 辖区信息
     * @return 辖区信息集合
     */
    public List<PaManageArea> selectPaManageAreaList(PaManageArea paManageArea);

    /**
     * 新增辖区信息
     *
     * @param paManageArea 辖区信息
     * @return 结果
     */
    public int insertPaManageArea(PaManageArea paManageArea);

    /**
     * 修改辖区信息
     *
     * @param paManageArea 辖区信息
     * @return 结果
     */
    public int updatePaManageArea(PaManageArea paManageArea);

    /**
     * 删除辖区信息
     *
     * @param id 辖区信息ID
     * @return 结果
     */
    public int deletePaManageAreaById(Long id);

    /**
     * 批量删除辖区信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePaManageAreaByIds(@Param("list") List<Long> ids);

    List<String> selectByManageId(String manageId);

    List<String> selectByNotManageId(String manageId);

    int deleteByCode(String code);

    List<PaManageArea> selectByAreaCode(String manageAreaCode);

    List<String> selectByManagerId(@Param("managerId") String managerId);

    void updataLike();

    List<Long> selectId();

    /**
     * 判断是否辖内
     * @param jobNumber
     * @param belongCode
     * @return
     */
    Boolean inJurisdiction(@Param("jobNumber") String jobNumber, @Param("belongCode") String belongCode);

    List<String> selectBelongManagerUserJobNum(@Param("codeList") String[] codeList);

    void deleteManageAreaByCode(String code);
}
