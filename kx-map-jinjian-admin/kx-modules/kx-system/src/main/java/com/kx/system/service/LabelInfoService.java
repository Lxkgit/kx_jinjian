package com.kx.system.service;


import com.kx.common.core.web.domain.AjaxResult;
import com.kx.common.core.web.page.TableDataInfo;
import com.kx.system.domain.VisitLabelVO;
import com.kx.system.domain.dto.LabelDTO;
import com.kx.system.domain.dto.LabelGroupDTO;
import com.kx.system.domain.label.LabelInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kx.system.domain.req.LabelReq;

import java.util.List;

/**
* @author 86176
* @description 针对表【label_info】的数据库操作Service
* @createDate 2024-01-18 11:07:38
*/
public interface LabelInfoService extends IService<LabelInfo> {

    AjaxResult addOrEditLabel(LabelDTO labelDTO);

    AjaxResult deleteLabelByIds(List<Long> ids);

    TableDataInfo queryPage(LabelReq labelReq);

    List<LabelGroupDTO> selectChildLabel(Long id);

    VisitLabelVO selectSysAndGridLabel(Long id);
}
