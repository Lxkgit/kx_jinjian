package com.kx.system.domain;

import com.kx.system.domain.dto.LabelGroupDTO;
import lombok.Data;

import java.util.List;

@Data
public class VisitLabelVO {
    //网格标签
    private List<LabelGroupDTO> gridLabels;

    //系统标签
    private List<LabelGroupDTO> sysGridLabels;
}
