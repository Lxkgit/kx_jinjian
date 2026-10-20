package com.kx.system.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huhao
 * Date: 2021-12-03
 */
@Data
public class VRLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double ath;
    private Double atv;
}
