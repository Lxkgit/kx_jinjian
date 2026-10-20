package com.kx.system.service;

import java.util.List;
import com.kx.system.domain.CreditRecord;

/**
 * 授信Service接口
 * 
 * @author kangxu
 * @date 2024-04-12
 */
public interface ICreditRecordService 
{
    /**
     * 查询授信
     * 
     * @param customId 授信主键
     * @return 授信
     */
    public CreditRecord selectCreditRecordByCustomId(Long customId);

    /**
     * 查询授信列表
     * 
     * @param creditRecord 授信
     * @return 授信集合
     */
    public List<CreditRecord> selectCreditRecordList(CreditRecord creditRecord);

    /**
     * 新增授信
     * 
     * @param creditRecord 授信
     * @return 结果
     */
    public int insertCreditRecord(CreditRecord creditRecord);

    /**
     * 修改授信
     * 
     * @param creditRecord 授信
     * @return 结果
     */
    public int updateCreditRecord(CreditRecord creditRecord);

    /**
     * 批量删除授信
     * 
     * @param customIds 需要删除的授信主键集合
     * @return 结果
     */
    public int deleteCreditRecordByCustomIds(Long[] customIds);

    /**
     * 删除授信信息
     * 
     * @param customId 授信主键
     * @return 结果
     */
    public int deleteCreditRecordByCustomId(Long customId);
}
