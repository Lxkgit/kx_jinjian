package com.kx.system.domain.grid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class GridPoint implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double x;
    private Double y;
}
