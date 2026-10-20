package com.kx.system.api.domain.shop.param;

import lombok.Data;

@Data
public class GridInfoParam {

    private Integer pageNum;

    private Integer pageSize;

    public GridInfoParam(){
        this.pageNum = 1;
        this.pageSize = 2000;
    }

}
