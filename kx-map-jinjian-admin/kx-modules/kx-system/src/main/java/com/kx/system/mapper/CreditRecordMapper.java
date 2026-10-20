package com.kx.system.mapper;

import java.util.List;
import com.kx.system.domain.CreditRecord;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.vo.CreditRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 授信Mapper接口
 * 
 * @author kangxu
 * @date 2024-04-12
 */
@Mapper
public interface CreditRecordMapper
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
     * 删除授信
     * 
     * @param customId 授信主键
     * @return 结果
     */
    public int deleteCreditRecordByCustomId(Long customId);

    /**
     * 批量删除授信
     * 
     * @param customIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCreditRecordByCustomIds(Long[] customIds);

    /**
     * 查询客户旅程中的授信信息
     * @param customInfoDTO
     * @return
     */
    List<CreditRecordVO> listInfo(CustomInfoDTO customInfoDTO);

    void clear();

    void insertBatch(@Param("creditRecords") List<CreditRecord> creditRecords);

    void updateIdentity();
}
