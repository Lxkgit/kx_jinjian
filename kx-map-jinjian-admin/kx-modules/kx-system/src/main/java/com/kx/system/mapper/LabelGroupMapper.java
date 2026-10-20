package com.kx.system.mapper;

import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.label.LabelGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 86176
* @description 针对表【label_group(标签分组表)】的数据库操作Mapper
* @createDate 2024-01-18 11:07:38
* @Entity com.kx.system.domain.label.LabelGroup
*/
@Mapper
public interface LabelGroupMapper {

    LabelGroup selectLabelGroupById(Long id);

    void updateLabelGroup(LabelGroup labelGroup);

    void insertLabelGroup(LabelGroup labelGroup);

    List<LabelGroup> selectAll();

    Integer queryQuote(@Param("ids") List<Long> ids);

    List<LabelGroup> queryList(@Param("ids") List<Long> ids);

    List<Long> queryListByParentId(Long labelGroupId);

    void delLabelGroups(@Param("ids") List<Long> ids);

    List<LabelGroupDTO> selectLabelGroupByParentId(LabelGroupDTO labelGroupDTO);

    List<LabelGroup> selectGridLabel();
}




