package com.kx.system.domain.boundary;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BoundaryData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type = "FeatureCollection";

    private List<Features> features;

}
