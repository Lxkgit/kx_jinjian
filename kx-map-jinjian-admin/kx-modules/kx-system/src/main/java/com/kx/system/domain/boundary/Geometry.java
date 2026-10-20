package com.kx.system.domain.boundary;

import lombok.Data;

import java.io.Serializable;

@Data
public class Geometry implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "MultiPolygon";

    private Object coordinates;
}
