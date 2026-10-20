package com.kx.system.domain.grid;

import lombok.Data;

import java.util.List;

@Data
public class AddrInfoParam {
    /**
     * 三级网格id
     */
    private Long id;
    /**
     * 责任机构(查询)
     */
    private String networkId;
    /**
     * 责任机构(查询)
     */
    private List<String> queryList;
    /**
     * 网格名称
     */
    private String name;
    /**
     * 客户经理code
     */
    private String managerId;
    /**
     * 登陆用户机构及下辖
     */
    private List<String> userList;
    /**
     * 边界顶点集
     */
    private List<List<GridPoint>> point;
    /**
     * 合并网格codes
     */
    private List<String> codes;
    /**
     * 父级code长度
     */
    private Integer len;
    /**
     * 父级code长度
     */
    private String ParentCode;


    private String areaCode;
}
