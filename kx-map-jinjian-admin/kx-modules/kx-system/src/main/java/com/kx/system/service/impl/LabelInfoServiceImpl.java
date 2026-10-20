package com.kx.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.config.enums.LabelTTypeEnum;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.VisitLabelVO;
import com.kx.system.domain.dto.LabelDTO;
import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.label.LabelGroup;
import com.kx.system.domain.label.LabelInfo;
import com.kx.system.domain.req.LabelReq;
import com.kx.system.mapper.CustomGroupMapper;
import com.kx.system.mapper.CustomerLabelMapper;
import com.kx.system.mapper.LabelGroupMapper;
import com.kx.system.service.LabelInfoService;
import com.kx.system.mapper.LabelInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 86176
* @description 针对表【label_info】的数据库操作Service实现
* @createDate 2024-01-18 11:07:38
*/
@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo>
    implements LabelInfoService{

    @Resource
    private LabelGroupMapper labelGroupMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private CustomGroupMapper customGroupMapper;

    @Autowired
    private CustomerLabelMapper customerLabelMapper;

    @Override
    public AjaxResult addOrEditLabel(LabelDTO labelDTO) {
        if (StrUtil.isEmpty(labelDTO.getLabelName()) || null == labelDTO.getLabelGroupId()) {
            return AjaxResult.error("参数异常！");
        }
        LabelInfo labelInfo1 = labelInfoMapper.selectExist(labelDTO.getId(),labelDTO.getLabelName(), labelDTO.getLabelGroupId());
        if (null != labelInfo1) {
            return AjaxResult.error("该分类下已存在相同标签名称，请重新编辑！");
        }

        String username = SecurityUtils.getUsername();
        if (null == labelDTO.getId()) {
            //新增
            LabelInfo labelInfo = new LabelInfo();
            BeanUtil.copyProperties(labelDTO, labelInfo);
            labelInfo.setCreateBy(username);
            labelInfo.setCreateTime(new Date());
            labelInfo.setType(LabelTTypeEnum.GRID.getCode());
            labelInfoMapper.insertLabelInfo(labelInfo);
        } else {
            List<Long> ids = new ArrayList<>();
            ids.add(labelDTO.getId());
            //判断标签被使用过的数量
            int count = customGroupMapper.getCountByLabelIds(ids);
            long usedCount = customerLabelMapper.getUsedCount(ids);
            if(count > 0 || usedCount > 0){
                return AjaxResult.warn("该标签已被使用，无法修改！");
            }
            //编辑
            LabelInfo byId = labelInfoMapper.selectLabelInfoById(labelDTO.getId());
            byId.setLabelName(labelDTO.getLabelName());
            byId.setLabelGroupId(labelDTO.getLabelGroupId());
            byId.setUpdateBy(username);
            byId.setUpdateTime(new Date());
            labelInfoMapper.updateLabelInfo(byId);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult deleteLabelByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return AjaxResult.success();
        }
        //判断标签被使用过的数量
        int count = customGroupMapper.getCountByLabelIds(ids);
        long usedCount = customerLabelMapper.getUsedCount(ids);
        if(count > 0 || usedCount > 0){
            return AjaxResult.warn("该标签已被使用，无法修改！");
        }
        String username = SecurityUtils.getUsername();
        labelInfoMapper.batchDeleteByIds(ids, username);
        return AjaxResult.success();
    }

    @Override
    public TableDataInfo queryPage(LabelReq labelReq) {
        List<Long> list = new ArrayList<>();
        if (null != labelReq.getLabelGroupId()) {
            if (labelReq.getLabelGroupId().equals(1L) || labelReq.getLabelGroupId().equals(2L) || labelReq.getLabelGroupId().equals(3L)) {
                //根目录查找下面标签组
                List<Long> list1 = labelGroupMapper.queryListByParentId(labelReq.getLabelGroupId());
                list.addAll(list1);
            } else {
                list.add(labelReq.getLabelGroupId());
            }
        }
        if (StringUtils.isNotNull(labelReq.getPageNum()) && StringUtils.isNotNull(labelReq.getPageSize())) {
            PageHelper.startPage(labelReq.getPageNum(), labelReq.getPageSize());
        }
        List<LabelInfo> labelInfos = labelInfoMapper.selectPageList(labelReq.getLabelName(), list);
        List<LabelDTO> labelDTOS = new ArrayList<>();
        List<LabelGroup> labelGroups = labelGroupMapper.selectAll();
        Map<Long, String> map = labelGroups.stream().filter(x -> x.getParentId().equals(0L)).collect(Collectors.toMap(LabelGroup::getId, LabelGroup::getLabelGroupName));
        Map<Long, Long> map1 = labelGroups.stream().filter(x -> !x.getParentId().equals(0L)).collect(Collectors.toMap(LabelGroup::getId, LabelGroup::getParentId));
        Map<Long, String> map2 = labelGroups.stream().filter(x -> !x.getParentId().equals(0L)).collect(Collectors.toMap(LabelGroup::getId, LabelGroup::getLabelGroupName));
        labelInfos.forEach(labelInfo -> {
            //拼接分组名称、对象转换
            LabelDTO labelDTO = new LabelDTO();
            BeanUtil.copyProperties(labelInfo, labelDTO);
            labelDTO.setLabelGroupName(map.get(map1.get(labelInfo.getLabelGroupId())) + "/" + map2.get(labelInfo.getLabelGroupId()));
            labelDTOS.add(labelDTO);
        });
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(labelDTOS);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(labelInfos).getTotal());
        return rspData;
    }

    @Override
    public List<LabelGroupDTO> selectChildLabel(Long id) {
        LabelGroupDTO labelGroupDTO = new LabelGroupDTO();
        labelGroupDTO.setId(id);
        //二级标签
        List<LabelGroupDTO> secondLabels = labelGroupMapper.selectLabelGroupByParentId(labelGroupDTO);
        for (LabelGroupDTO secondLabel : secondLabels) {
            List<LabelInfo> thirdLabels = labelInfoMapper.selectLabelInfoByGroupId(secondLabel.getId());
            secondLabel.setChildLabels(thirdLabels);
        }
        return secondLabels;
    }

    @Override
    public VisitLabelVO selectSysAndGridLabel(Long id) {
        LabelGroupDTO labelGroupDTO = new LabelGroupDTO();
        labelGroupDTO.setId(id);
        labelGroupDTO.setType(LabelTTypeEnum.GRID.getCode());
        //网格二级标签
        List<LabelGroupDTO> secondGridLabels = labelGroupMapper.selectLabelGroupByParentId(labelGroupDTO);
//        labelGroupDTO.setType(LabelTTypeEnum.SYSTEM.getCode());
//        //系统二级标签
//        List<LabelGroupDTO> secondSysLabels = labelGroupMapper.selectLabelGroupByParentId(labelGroupDTO);
        for (LabelGroupDTO secondLabel : secondGridLabels) {
            List<LabelInfo> thirdGridLabels = labelInfoMapper.selectLabelInfoByGroupId(secondLabel.getId());
            secondLabel.setChildLabels(thirdGridLabels);
        }
//        for (LabelGroupDTO secondSysLabel : secondSysLabels) {
//            List<LabelInfo> thirdSysLabels = labelInfoMapper.selectLabelInfoByGroupId(secondSysLabel.getId());
//            secondSysLabel.setChildLabels(thirdSysLabels);
//        }
        VisitLabelVO visitLabelVO = new VisitLabelVO();
        visitLabelVO.setGridLabels(secondGridLabels);
//        visitLabelVO.setSysGridLabels(secondSysLabels);
        return visitLabelVO;
    }
}




