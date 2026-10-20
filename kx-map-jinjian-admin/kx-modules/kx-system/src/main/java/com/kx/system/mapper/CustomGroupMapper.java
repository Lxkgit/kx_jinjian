package com.kx.system.mapper;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.group.CustomGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kx.system.domain.group.CustomerGroupCategory;
import com.kx.system.domain.req.CustomGroupPageReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 86176
* @description 针对表【custom_group(客群)】的数据库操作Mapper
* @createDate 2024-01-18 11:08:03
* @Entity com.kx.system.domain.group.CustomGroup
*/
@Mapper
public interface CustomGroupMapper {

    CustomGroup selectOneByCategoryId(@Param("categoryId") Long categoryId);

    List<CustomGroup> selectCustomGroupList(CustomGroupPageReq req);

    CustomGroup selectCustomGroupById(@Param("id") Long id);

    void insertCustomGroup(@Param("customGroup") CustomGroup customGroup);

    void updateCustomGroup(@Param("customGroup") CustomGroup customGroup);

    void clearNum(@Param("customGroup") CustomGroup customGroup);

    void updateCustomGroupList(@Param("customGroup") CustomGroup customGroup);

    List<String> selectCustomGroupByIds(List<Long> customGroupIds);

    List<CustomGroup> getChildCustomGroup(Long customType);

    /**
     * 根据customGroupIds查询customGroupNames
     * @param customGroupIds
     * @return
     */
    List<String> selectCustomGroupNameByIds(@Param("customGroupIds") List<Long> customGroupIds);

    void updateStatusByGroupId(@Param("customGroup") CustomGroup customGroup);

    List<CustomGroup> selectGroupByType(@Param("type") Integer type,@Param("jobNo") String jobNo);

    int getCountByLabelIds(@Param("ids") List<Long> ids);
}




