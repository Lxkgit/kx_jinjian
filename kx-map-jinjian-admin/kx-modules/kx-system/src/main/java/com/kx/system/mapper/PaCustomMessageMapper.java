package com.kx.system.mapper;



import com.kx.system.domain.PaCustomMessage;
import com.kx.system.domain.dto.PaCustomMessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 消息Mapper接口
 *
 * @author lc
 * @date 2022-06-01
 */
@Mapper
public interface PaCustomMessageMapper {

    /**
     * 查询消息列表
     *
     * @param paCustomMessageDTO 消息
     * @return 消息集合
     */
    public List<PaCustomMessage> selectPaCustomMessageList(PaCustomMessageDTO paCustomMessageDTO);

    /**
     * 根据通知对象，查询最新的四条数据
     *
     * @param notifyObject 消息
     * @return 消息集合
     */
    public List<PaCustomMessage> selectPaCustomMessageByNotifyObject(String notifyObject);

    /**
     * 查询未读消息数量
     *
     * @return 结果
     */
    public int selectCountNotReadMessage(String notifyObject);

    /**
     * 新增消息
     *
     * @param paCustomMessage 消息
     * @return 结果
     */
    public int insertPaCustomMessage(PaCustomMessage paCustomMessage);

    /**
     * 批量新增消息
     *
     * @param paCustomMessages 消息
     * @return 结果
     */
    public int batchInsertPaCustomMessage(@Param("paCustomMessages") List<PaCustomMessage> paCustomMessages);

    /**
     * 修改消息
     *
     * @param paCustomMessage 消息
     * @return 结果
     */
    public int updatePaCustomMessage(PaCustomMessage paCustomMessage);

    /**
     * 删除消息
     *
     * @param id 消息ID
     * @return 结果
     */
    public int deletePaCustomMessageById(Long id);

    /**
     * 批量设置已读
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int updateMessageReadState(String[] ids);


    /**
     * 批量设置消息状态
     */
    int updateMessageState(@Param("managerId") String managerId, @Param("ids") String[] ids, @Param("messageState") String messageState);

    /**
     * 批量设置消息星级
     */
    int updateMessageStar(@Param("ids") String[] ids, @Param("messageStar") String messageStar);


    Integer countPaCustomMessageByNotifyObject(PaCustomMessageDTO paCustomMessage);

    List<PaCustomMessage> getOverdueLoanCustomersByNotifyTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
