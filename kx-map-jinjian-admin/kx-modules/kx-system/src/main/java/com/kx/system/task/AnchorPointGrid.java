package com.kx.system.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zxy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnchorPointGrid {

    private String id;
    // 经度
    private double lon;
    // 纬度
    private double lat;
    // 网格编码
    private String code;
}
