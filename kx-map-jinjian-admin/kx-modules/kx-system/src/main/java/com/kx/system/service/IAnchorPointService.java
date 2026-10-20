package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.api.model.LoginUser;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.PaAddrInfoType;
import com.kx.system.domain.anchor.AnchorPoint;
import com.kx.system.domain.anchor.AnchorPointMini;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.customer.CustomerParam;
import com.kx.system.domain.customer.CustomerSelect;
import com.kx.system.domain.dto.AnchorPointDTO;
import com.kx.system.domain.dto.AnchorTypeCountDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 锚点Service接口
 *
 * @author kangxu
 * @date 2024-04-03
 */
public interface IAnchorPointService
{
    /**
     * 查询锚点
     *
     * @param id 锚点主键
     * @return 锚点
     */
    public AnchorPoint selectAnchorPointById(Long id);

    /**
     * 查询锚点列表
     *
     * @param anchorPoint 锚点
     * @return 锚点集合
     */
    public List<AnchorPoint> selectAnchorPointList(AnchorPoint anchorPoint);
    AjaxResult  esAnchorPointList(AnchorPoint anchorPoint, LoginUser loginUser);
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

    /**
     * 批量删除锚点
     *
     * @param ids 需要删除的锚点主键集合
     * @return 结果
     */
    public int deleteAnchorPointByIds(Long[] ids);

    /**
     * 删除锚点信息
     *
     * @param id 锚点主键
     * @return 结果
     */
    public int deleteAnchorPointById(Long id);

    AjaxResult selectAnchorPointDetail(CustomInfoDTO customInfoDTO);

    List<AnchorPoint> queryByCustomerType(CustomerSelect select);

    List<AnchorPointMini> queryByCustomerTypeMini(CustomerSelect select);

    List<AnchorPointMini> esByCustomerTypeMini(CustomerSelect select,LoginUser loginUser);

    List<AnchorPoint> queryGroup(CustomerSelect select);

    List<AnchorPointMini> queryGroupMini(CustomerSelect select);
    List<AnchorPointMini> esGroupMini(CustomerSelect select);
    AjaxResult selectAnchorPointVisit(CustomInfoDTO customInfoDTO);

    List<String> selectLabelByAnchorId(CustomInfoDTO customInfoDTO);

    List<CreditRecord> selectCreditRecordByAnchorId(CustomInfoDTO customInfoDTO);

    List<AnchorPoint>  queryCustomerByName(CustomerParam param);

    void insertBatch(List<AnchorPoint> anchorPoints, CountDownLatch countDownLatch, int length, int i);

    Map<String, AnchorPoint> getAnchorPointByCustomerNm(List<String> customerNmList , String customerType);
    List<AnchorPoint>  queryBusinessByBoundary( CustomerSelect select);

    List<AnchorPoint> queryCustomerByAddr(String addressDesc);

    List<AnchorPoint> queryCustomerByAnchor(String anchorName);

    List<PaAddrInfoType> queryCustomerByBranch(String branchName);

    String tests();

    //筛选网点计数
    AnchorTypeCountDTO getTotalCount(String gridCode, Integer groupId);

    List<AnchorPoint> allAnchor(String anchorName);

    List<AnchorPoint> getEquipment(String anchorName);
//    void saveFilingAnchor(FilingCustomerInfo filingCustomerInfo);

    void anchCode();

}
