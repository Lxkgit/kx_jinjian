package com.kx.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author xifan_h
 * @date 2022/4/27 15:55
 * @project bg-panorama-vr-center
 * @description
 */
@Data
public class KpiTargetVO {
    private String id;
    private String parentId;
    private String name;
    private List<KpiTargetVO> sonList;
}
