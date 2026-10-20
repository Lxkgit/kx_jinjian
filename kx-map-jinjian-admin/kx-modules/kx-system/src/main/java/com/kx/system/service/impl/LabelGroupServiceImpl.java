package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.label.LabelGroup;
import com.kx.system.domain.label.LabelInfo;
import com.kx.system.mapper.CustomGroupMapper;
import com.kx.system.mapper.CustomerLabelMapper;
import com.kx.system.mapper.LabelInfoMapper;
import com.kx.system.service.LabelGroupService;
import com.kx.system.mapper.LabelGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 86176
* @description 针对表【label_group(标签分组表)】的数据库操作Service实现
* @createDate 2024-01-18 11:07:38
*/
@Service
public class LabelGroupServiceImpl implements LabelGroupService{

    @Resource
    private LabelGroupMapper labelGroupMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private CustomGroupMapper customGroupMapper;

    @Autowired
    private CustomerLabelMapper customerLabelMapper;

    @Override
    public AjaxResult addOrEditGroup(LabelGroupDTO labelGroupDTO) {
        if (StrUtil.isEmpty(labelGroupDTO.getLabelGroupName()) || null == labelGroupDTO.getParentId()) {
            return AjaxResult.error("参数异常！");
        }
        if (null != labelGroupDTO.getId()) {
            List<Long> ids = new ArrayList<>();
            ids.add(labelGroupDTO.getId());
            //如果是父级标签，则查询子级标签一起判断
            List<LabelInfo> labelInfoList = labelInfoMapper.selectLabelInfoByGroupId(labelGroupDTO.getId());
            if(CollectionUtil.isNotEmpty(labelInfoList)){
                labelInfoList.stream().forEach(e -> {
                    ids.add(e.getId());
                });
            }
            //判断标签被使用过的数量
            int count = customGroupMapper.getCountByLabelIds(ids);
            long usedCount = customerLabelMapper.getUsedCount(ids);
            if(count > 0 || usedCount > 0){
                return AjaxResult.warn("该类型已被使用，无法修改！");
            }
            //编辑
            LabelGroup labelGroup = labelGroupMapper.selectLabelGroupById(labelGroupDTO.getId());
            if (null == labelGroup) {
                return AjaxResult.error("参数异常！");
            }
            BeanUtil.copyProperties(labelGroupDTO, labelGroup);
            labelGroupMapper.updateLabelGroup(labelGroup);
        } else {
            //新增
            LabelGroup labelGroup = new LabelGroup();
            BeanUtil.copyProperties(labelGroupDTO, labelGroup);
            labelGroup.setType(0);
            labelGroupMapper.insertLabelGroup(labelGroup);
        }
        return AjaxResult.success("操作成功！");
    }

    @Override
    public AjaxResult labelDirectory() {
        List<LabelGroupDTO> labelGroupDTOS = new ArrayList<>();
        //只展示网格标签
        List<LabelGroup> list = labelGroupMapper.selectGridLabel();
        if (CollectionUtil.isEmpty(list)) {
            return AjaxResult.success(labelGroupDTOS);
        }
        list.forEach(l -> {
            if (l.getParentId().equals(0L)) {
                LabelGroupDTO dto = new LabelGroupDTO();
                BeanUtil.copyProperties(l, dto);
                List<LabelGroup> collect = list.stream().filter(labelGroup -> labelGroup.getParentId().equals(dto.getId())).collect(Collectors.toList());
                dto.setChild(collect);
                labelGroupDTOS.add(dto);
            }
        });
        return AjaxResult.success(labelGroupDTOS);
    }

    @Override
    public AjaxResult deleteDirectoryByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return AjaxResult.success();
        }
        //如果是父级标签，则查询子级标签一起判断
        List<LabelInfo> labelInfoList = labelInfoMapper.selectLabelInfoByGroupId(ids.get(0));
        if(CollectionUtil.isNotEmpty(labelInfoList)){
            labelInfoList.stream().forEach(e -> {
                ids.add(e.getId());
            });
        }
        //判断标签被使用过的数量
        long usedCount = customerLabelMapper.getUsedCount(ids);
        if(usedCount > 0){
            return AjaxResult.warn("该类型下有标签已被使用，无法删除！");
        }
        Integer i = labelGroupMapper.queryQuote(ids);
        if (i > 0) {
            return AjaxResult.warn("该类型下有标签存在，不能删除！");
        }
        labelGroupMapper.delLabelGroups(ids);
        return AjaxResult.success();
    }

}




