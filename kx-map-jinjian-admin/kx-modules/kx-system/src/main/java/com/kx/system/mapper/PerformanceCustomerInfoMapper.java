package com.kx.system.mapper;

import com.kx.system.api.domain.SysDeptExcel;
import com.kx.system.domain.AnchorExport;
import com.kx.system.domain.ExportAnchor;
import com.kx.system.domain.customer.BankType;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.es.EsPerMemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 绩效客户信息Mapper接口
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Mapper
public interface PerformanceCustomerInfoMapper {
    /**
     * 查询绩效客户信息
     *
     * @param id 绩效客户信息主键
     * @return 绩效客户信息
     */
    PerformanceCustomerInfo selectPerformanceCustomerInfoById(Long id);

    /**
     * 查询绩效客户信息列表
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 绩效客户信息集合
     */
    List<PerformanceCustomerInfo> selectPerformanceCustomerInfoList(PerformanceCustomerInfo performanceCustomerInfo);

    List<PerformanceCustomerInfo> selectPerformanceCustomerList(@Param("performanceCustomerInfo") PerformanceCustomerInfo performanceCustomerInfo);

    int selectPerformanceCustomerCount(@Param("performanceCustomerInfo") PerformanceCustomerInfo performanceCustomerInfo);

    /**
     * 新增绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    int insertPerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo);

    /**
     * 修改绩效客户信息
     *
     * @param performanceCustomerInfo 绩效客户信息
     * @return 结果
     */
    int updatePerformanceCustomerInfo(PerformanceCustomerInfo performanceCustomerInfo);


    /**
     * 删除绩效客户信息
     *
     * @param id 绩效客户信息主键
     * @return 结果
     */
    int deletePerformanceCustomerInfoById(Long id);

    /**
     * 批量删除绩效客户信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deletePerformanceCustomerInfoByIds(Long[] ids);

    List<Long> selectPerformanceCustomerInfoIdList(PerformanceCustomerInfo customerInfo);

    int selectPerformanceCustomerInfoIdCount(PerformanceCustomerInfo customerInfo);

    List<PerformanceCustomerInfo> list(PerformanceCustomerInfo performanceCustomerInfo);

    List<PerformanceCustomerInfo> listByRenHang(PerformanceCustomerInfo performanceCustomerInfo);


    List<PerformanceCustomerInfo> selectCustomerInfo(CustomInfoDTO customInfoDTO);

    void insertBatch(@Param("customerInfos") List<PerformanceCustomerInfo> customerInfos);

    void updateGridManager(@Param("code") String code);

    void updateFtpPerformance(PerformanceCustomerInfo newPerformance);

    void updatePerBelongGrid();


    List<EsPerMemberDto> selectEsDataByGroupId(@Param("groupId") Long groupId, @Param("startIndex") int startIndex, @Param("endIndex") int endIndex);

    PerformanceCustomerInfo selectPerformanceCustomerInfoByNm(String nm);

    List<PerformanceCustomerInfo> selectAll();
    List<PerformanceCustomerInfo> selectLikeNm(@Param("nm") String nm);


    List<BankType> listByBank(@Param("groupId") Long groupId);

    void UpdateBatchByDeptName(@Param("cachedDataList") List<SysDeptExcel> cachedDataList);

    List<AnchorExport> selectAllAnchor(PerformanceCustomerInfo anchorExport);

    List<ExportAnchor> selectAllAnchorTest(PerformanceCustomerInfo anchorExport);
}
