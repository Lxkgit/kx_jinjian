package com.kx.system.mapper;

import com.kx.common.datascope.annotation.DataScope;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.AnchorPointMini;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerParam;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.dto.AnchorPointDTO;
import com.kx.system.domain.dto.AnchorTypeCountDTO;
import com.kx.system.domain.es.EsAnchorPointDto;
import com.kx.system.domain.es.EsAnchorPointGroupDto;
import com.kx.system.domain.vo.CustomerJourneyVO;
import com.kx.system.domain.vo.TaskViewVO;
import com.kx.system.task.AnchorPointGrid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 锚点Mapper接口
 *
 * @author kangxu
 * @date 2024-04-03
 */
public interface AnchorPointMapper {
    /**
     * 查询锚点
     *
     * @param id 锚点主键
     * @return 锚点
     */
    public AnchorPoint selectAnchorPointById(Long id);

    /**
     * 查询锚点
     *
     * @param ids 锚点主键
     * @return 锚点
     */
    public List<AnchorPoint> selectAnchorPointByIds(List<Long> ids);

    /**
     * 查询锚点列表
     *
     * @param anchorPoint 锚点
     * @return 锚点集合
     */
    public List<AnchorPoint> selectAnchorPointList(AnchorPoint anchorPoint);

    /**
     * 新增锚点
     *
     * @param anchorPoint 锚点
     * @return 结果
     */
    public int insertAnchorPoint(AnchorPoint anchorPoint);

    /**
     * 修改锚点
     *
     * @param anchorPoint 锚点
     * @return 结果
     */
    public int updateAnchorPoint(AnchorPoint anchorPoint);
    public int updateAnchorPointBush(@Param("anchorPointList") List<AnchorPoint> anchorPointList);

    /**
     * 删除锚点
     *
     * @param id 锚点主键
     * @return 结果
     */
    public int deleteAnchorPointById(Long id);

    /**
     * 批量删除锚点
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAnchorPointByIds(Long[] ids);

    AnchorPoint selectCustomerIdByAnchorPoint(AnchorPoint anchorPoint);

    @DataScope(deptAlias = "su", userAlias = "su")
    List<AnchorPoint> selectCreditCustomerList(CustomerSelect select);

    @DataScope(deptAlias = "su", userAlias = "su")
    List<AnchorPoint> selectByCustomerType(CustomerSelect select);

    @DataScope(deptAlias = "su", userAlias = "su")
    List<AnchorPointMini> selectByCustomerTypeMini(CustomerSelect select);

    List<AnchorPoint> queryGroup(CustomerSelect select);

    List<AnchorPointMini> queryGroupMini(CustomerSelect select);

    List<CustomerJourneyVO> selectCreditRecord(CustomInfoDTO customInfoDTO);

    String selectCustomerId(CustomInfoDTO customInfoDTO);

    List<AnchorPoint> queryCustomerByName(CustomerParam param);

    List<AnchorPoint> listTaskAnchor(@Param("clients") List<TaskViewVO> clients);

    void insertBatch(@Param("anchorPoints") List<AnchorPoint> anchorPoints);

    List<AnchorPoint> listUnhandledAnchor();

    List<AnchorPoint> selectFilingAnchor();

    AnchorPoint selectExistFilingAnchor(Long id);

    List<AnchorPoint> selectByAreaCode(AnchorPoint anchorPoint);

    List<AnchorPoint> selectAnchorPointByBelongGrid(String s);

    void updateSameDayPerCustomerId();

    void updateFullPerCustomerId();

    void updateSameDayCreCustomerId();

    void updateFullCreCustomerId();

    List<AnchorPoint> selectAnchorPointByAreaCodes(@Param("codes") List<String> codes);

    void updateGridManagerByBelongGrid(@Param("code") String code,@Param("userManagerNum") String userManagerNum);

    int updateAnchorPointByNm(AnchorPoint anchorPoint);

    List<EsAnchorPointGroupDto> selectEsDataByGroupId(@Param("groupId") Long groupId, @Param("customerType") Integer customerType, @Param("startIndex")int startIndex, @Param("endIndex")int endIndex);

    AnchorPoint selectByCustNm(@Param("nm") String nm,@Param("type")String type);
    List<AnchorPoint> selectBusinessAll(
            @Param("maxLon") double maxLon,
            @Param("minLon") double minLon,
            @Param("maxLat") double maxLat,
            @Param("minLat") double minLat);


    List<AnchorPoint> queryCustomerByAddr(@Param("addressDesc") String addressDesc);

    List<AnchorPoint> queryCustomerByAnchor(String anchorName);

    List<PaAddrInfoType> queryCustomerByBranch(String branchName);

    List<AnchorPoint> selectAll();

    List<AnchorPoint> selectEsAnchorPoints();

    AnchorTypeCountDTO getTotalCount(@Param("gridCode") String gridCode, @Param("groupId") Integer groupId);

    void updateAnchorGridCode(AnchorPoint anchor);

    AnchorTypeCountDTO getAnchorTotal(@Param("gridCode") String gridCode);

    List<String> selectManageInfo(@Param("dutyNetworkName") String dutyNetworkName);

    List<AnchorPoint> selectAllAnchor(@Param("anchorName") String anchorName);

    List<AnchorPoint> selectEquipment(String anchorName);

    List<AnchorPointGrid> selectPaPocAddress(@Param("a") int a, @Param("b") int b);

    int selectPaPocAddressCount();

    void updatePaPocAddressCode(List<AnchorPointGrid> anchorPointGrids);

    List<String> codeByIds(@Param("deptIds") List<Long> deptIds);

    List<AnchorPoint> selectAnchorPageForSync(@Param("offset") int offset, @Param("limit") int limit);

    int selectAnchorTotalCount();
}

