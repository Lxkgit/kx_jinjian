package com.kx.system.api.domain.shop.param;

import lombok.Data;

@Data
public class OrgGridInfoParam {

    private String orgNum;

    private String gridCode;

    private String gridName;

    private String branchName;

    private Integer pageNum;

    private Integer pageSize;


    public void clearPageParam() {
        this.pageNum = null;
        this.pageSize = null;
    }

}
