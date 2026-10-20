package com.kx.system.domain.anchor;

import lombok.Data;

/**
 * @Author: Gong
 * @Date: 2023/6/4 15:10
 * @Description:
 */
@Data
public class Coordinate {

    private Double lon;
    private Double lat;

    public Coordinate(double lon, double lat) {
        this.lon= lon;
        this.lat= lat;
    }
    public Coordinate() {
    }
}

