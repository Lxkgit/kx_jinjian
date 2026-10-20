package com.kx.system.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class AddrInfoVO {
    private String code;

    private String name;

    private String parentCode;

    private List<AddrInfoVO> childList;
}
