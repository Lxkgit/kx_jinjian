package com.kx.system.domain;

import lombok.Data;

import java.util.List;

/**
 * @author gary
 */
@Data
public class Township {
    private Long id;
    private String name;
    private String code;
    private List<Village> villageList;
}
