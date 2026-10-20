package com.kx.system.mapper;

import com.kx.system.domain.CreditCustomerInfo;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.PerformanceCustomerInfo;
import com.kx.system.domain.es.EsCreditMemberDto;
import com.kx.system.domain.param.CreditHeatMapParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 授信客户信息Mapper接口
 *
 * @author kangxu
 * @date 2024-04-01
 */
@Mapper
public interface CreditCustomerInfoMapper {
    /**
     * 查询授信客户信息
     *
     * @param customNm 授信客户信息主键
     * @return 授信客户信息
     */
    public CreditCustomerInfo selectCreditCustomerInfoByCustomNm(String customNm);

    /**
     * 查询授信客户信息列表
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 授信客户信息集合
     */
    public List<CreditCustomerInfo> selectCreditCustomerInfoList(CreditCustomerInfo creditCustomerInfo);

    public List<CreditCustomerInfo> selectCreditCustomerList(@Param("creditCustomerInfo") CreditCustomerInfo creditCustomerInfo);

    public int selectCreditCustomerCount(@Param("creditCustomerInfo") CreditCustomerInfo creditCustomerInfo);

    /**
     * 新增授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    public int insertCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo);

    /**
     * 修改授信客户信息
     *
     * @param creditCustomerInfo 授信客户信息
     * @return 结果
     */
    public int updateCreditCustomerInfo(CreditCustomerInfo creditCustomerInfo);

    /**
     * 删除授信客户信息
     *
     * @param customNm 授信客户信息主键
     * @return 结果
     */
    public int deleteCreditCustomerInfoByCustomNm(String customNm);

    /**
     * 批量删除授信客户信息
     *
     * @param customNms 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCreditCustomerInfoByCustomNms(String[] customNms);

    /**
     * 查询客户的Id集合
     *
     * @param customerInfo
     * @return
     */
    List<Long> selectCreditCustomerInfoIdList(CreditCustomerInfo customerInfo);

    int selectCreditCustomerInfoIdCount(CreditCustomerInfo customerInfo);

    List<CreditCustomerInfo> list(CreditCustomerInfo creditCustomerInfo);

    List<CreditCustomerInfo> selectCustomerInfo(CustomInfoDTO customInfoDTO);

    void insertBatch(@Param("creditCustomerInfos") List<CreditCustomerInfo> creditCustomerInfos);

    Integer countGridCreditCustomerNumber(CreditHeatMapParam param);

    void updateIsProduct();

    void updateGridManager(String code);

    void updateFtpCredit(CreditCustomerInfo newCredit);

    void updateCreBelongGrid();

    List<EsCreditMemberDto> selectEsDataByGroupId(@Param("groupId") Long groupId, @Param("startIndex") int startIndex, @Param("endIndex") int endIndex);
    CreditCustomerInfo selectCustomerInfoByNm(String nm);

    List<CreditCustomerInfo> selectAll();

    List<CreditCustomerInfo> selectLikeNm(String nm);

    PerformanceCustomerInfo selectBranchCustormerInfoList(String customerNm);
}
