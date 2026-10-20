package com.kx.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xifan_h
 * @date 2022/8/11 10:47
 * @project bg-panorama-vr-center
 * @description 网格边界
 */

@Data
public class GridBoundaryDTO {
    private Attributes attributes = new Attributes();
    private Geometry geometry = new Geometry();

    //网格信息
    @Data
    public static class Attributes {
        //网格编码
        @JsonProperty("XZQBM")
        private String XZQBM;
        //网格名称
        @JsonProperty("XZQMC")
        private String XZQMC;
    }

    //网格边界
    @Data
    public static class Geometry {
        private List<List<List<Double>>> rings = new ArrayList<>();
    }
}
