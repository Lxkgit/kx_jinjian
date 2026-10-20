package com.kx.system.mapper;


import com.kx.system.domain.OpeningCardRecord;
import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.vo.CustomerJourneyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 开卡Mapper接口
 * 
 * @author kangxu
 * @date 2024-04-08
 */
@Mapper
public interface OpeningCardRecordMapper 
{

    /**
     * 查询客户旅程相关信息
     * @param customInfoDTO
     * @return
     */
    List<CustomerJourneyVO> listInfo(CustomInfoDTO customInfoDTO);

    void insertBatch(@Param("records") List<OpeningCardRecord> records);

    void clear();

    void updateIdentity();
}
