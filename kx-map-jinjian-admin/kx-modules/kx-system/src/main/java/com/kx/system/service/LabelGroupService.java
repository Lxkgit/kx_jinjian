package com.kx.system.service;

import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.domain.dto.LabelDTO;
import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.label.LabelGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86176
* @description 针对表【label_group(标签分组表)】的数据库操作Service
* @createDate 2024-01-18 11:07:38
*/
public interface LabelGroupService {

    AjaxResult addOrEditGroup(LabelGroupDTO labelGroupDTO);

    AjaxResult labelDirectory();

    AjaxResult deleteDirectoryByIds(List<Long> ids);

}
