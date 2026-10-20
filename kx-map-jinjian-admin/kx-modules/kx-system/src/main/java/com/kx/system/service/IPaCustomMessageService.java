package com.kx.system.service;



import com.kx.system.domain.PaCustomMessage;
import com.kx.system.domain.dto.PaCustomMessageDTO;
import com.kx.system.domain.vo.PaCustomMessageStatisticsVo;

import java.util.Date;
import java.util.List;

/**
 * 消息Service接口
 *
 * @author lc
 * @date 2022-06-01
 */
public interface IPaCustomMessageService {

    /**
     * 查询消息列表
     *
     * @param paCustomMessageDTO 消息
     * @return 消息集合
     */
    public List<PaCustomMessage> selectPaCustomMessageList(PaCustomMessageDTO paCustomMessageDTO);

    /**
     * 查询消息列表
     *
     * @param paCustomMessageDTO 消息
     * @return 消息集合
     */
    public List<PaCustomMessageStatisticsVo> listMessageStatistics(PaCustomMessageDTO paCustomMessageDTO);

    /**
     * 新增消息
     *
     * @param paCustomMessage 消息
     * @return 结果
     */
    public int insertPaCustomMessage(PaCustomMessage paCustomMessage);




    int batchMessageStar(String[] idList, String messageStar);

    int batchMessageState(String managerId, String[] idList, String messageStar);

}
