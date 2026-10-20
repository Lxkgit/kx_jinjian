package com.kx.system.mapper;

import java.util.List;

import com.kx.system.domain.PaAddrInfoChouzhou;

/**
 * @author 王滨琦
 * @description 针对表【pa_addr_info_chouzhou(稠州网格统计数据)】的数据库操作Mapper
 * @createDate 2024-04-18 15:38:45
 * @Entity com.kx.system.domain.PaAddrInfoChouzhou
 */
public interface PaAddrInfoChouzhouMapper {

    List<PaAddrInfoChouzhou> list();

    int deleteByPrimaryKey(Long id);

    int insert(PaAddrInfoChouzhou record);

    int insertSelective(PaAddrInfoChouzhou record);

    PaAddrInfoChouzhou selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaAddrInfoChouzhou record);

    int updateByPrimaryKey(PaAddrInfoChouzhou record);

}
