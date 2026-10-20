package com.kx.system.domain.vo;

import lombok.Data;

@Data
public class ClientRange {
    String star;
    String  range;//1年以下
    int total;

    public ClientRange(String star, int total) {
        this.star = star;
        this.total = total;
    }

    public ClientRange() {
    }
}
