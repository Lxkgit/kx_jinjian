package com.kx.system.domain.boundary;

import lombok.Data;

import java.io.Serializable;

@Data
public class Features implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "Feature";

    private Properties properties;

    private Geometry geometry;
}
