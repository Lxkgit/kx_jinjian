package com.kx.system.mapper;

import com.kx.system.domain.customer.CustomInfoDTO;
import com.kx.system.domain.label.LabelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86176
* @description 针对表【label_info】的数据库操作Mapper
* @createDate 2024-01-18 11:07:38
* @Entity com.kx.system.domain.label.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    void batchDeleteByIds(@Param("ids") List<Long> ids, @Param("updateBy") String updateBy);

    List<LabelInfo> queryListByIds(@Param("ids") List<Long> ids);

    List<LabelInfo> selectPageList(@Param("labelName") String labelName, @Param("list") List<Long> list);

    void insertLabelInfo(@Param("labelInfo") LabelInfo labelInfo);

    void updateLabelInfo(@Param("labelInfo") LabelInfo labelInfo);

    LabelInfo selectLabelInfoById(@Param("id") Long id);

    LabelInfo selectExist(@Param("id") Long id, @Param("labelName") String labelName, @Param("labelGroupId") Long labelGroupId);

    List<LabelInfo> selectLabelInfoByGroupId(Long groupId);

    List<String> selectCustomerLabels(CustomInfoDTO customInfoDTO);

    LabelInfo selectLabelGroupById(Long id);

    LabelInfo selectByNameAndGroup(@Param("name") String name, @Param("parentId") Long parentId);
}




