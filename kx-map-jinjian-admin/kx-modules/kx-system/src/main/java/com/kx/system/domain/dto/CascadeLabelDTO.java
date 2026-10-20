package com.kx.system.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CascadeLabelDTO implements Serializable {

    private Long id;

    private String name;

    private Integer type;

    private List<CascadeLabelDTO> child;
}
