package com.kx.system.service.impl;

import java.util.List;
import com.kx.common.core.utils.DateUtils;
import com.kx.system.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kx.system.mapper.CreditRecordMapper;
import com.kx.system.domain.CreditRecord;
import com.kx.system.service.ICreditRecordService;

/**
 * 授信Service业务层处理
 *
 * @author kangxu
 * @date 2024-04-12
 */
@Service
public class CreditRecordServiceImpl implements ICreditRecordService
{
    @Autowired
    private CreditRecordMapper creditRecordMapper;

    @Autowired
    private UserUtil userUtil;

    /**
     * 查询授信
     *
     * @param customId 授信主键
     * @return 授信
     */
    @Override
    public CreditRecord selectCreditRecordByCustomId(Long customId)
    {
        return creditRecordMapper.selectCreditRecordByCustomId(customId);
    }

    /**
     * 查询授信列表
     *
     * @param creditRecord 授信
     * @return 授信
     */
    @Override
    public List<CreditRecord> selectCreditRecordList(CreditRecord creditRecord)
    {
        String managerNum = userUtil.getLoginUser().getManagerNum();
        creditRecord.setManagerNum(managerNum);
        return creditRecordMapper.selectCreditRecordList(creditRecord);
    }

    /**
     * 新增授信
     *
     * @param creditRecord 授信
     * @return 结果
     */
    @Override
    public int insertCreditRecord(CreditRecord creditRecord)
    {
        creditRecord.setCreateTime(DateUtils.getNowDate());
        return creditRecordMapper.insertCreditRecord(creditRecord);
    }

    /**
     * 修改授信
     *
     * @param creditRecord 授信
     * @return 结果
     */
    @Override
    public int updateCreditRecord(CreditRecord creditRecord)
    {
        return creditRecordMapper.updateCreditRecord(creditRecord);
    }

    /**
     * 批量删除授信
     *
     * @param customIds 需要删除的授信主键
     * @return 结果
     */
    @Override
    public int deleteCreditRecordByCustomIds(Long[] customIds)
    {
        return creditRecordMapper.deleteCreditRecordByCustomIds(customIds);
    }

    /**
     * 删除授信信息
     *
     * @param customId 授信主键
     * @return 结果
     */
    @Override
    public int deleteCreditRecordByCustomId(Long customId)
    {
        return creditRecordMapper.deleteCreditRecordByCustomId(customId);
    }
}
