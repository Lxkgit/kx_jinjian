package com.kx.system.domain.grid;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class AddrInfoDto {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 别名/简称
     */
    private String anotherName;
    /**
     * 信用级别
     */
    private String creditLevel;
    /**
     * 父级编码
     */
    private String parentCode;
    /**
     * 是否被维护 0-否 1-是
     */
    private Integer isMaintained;
    /**
     * 所属网点id
     */
    private Long belongNetworkId;
    /**
     * 归属网格code
     */
    private String belongNetworkCode;
    /**
     * 归属网格名称
     */
    private String networkName;
    /**
     * 归属管辖code
     */
    private String managerId;
    /**
     * 归属管辖名称
     */
    private String managerName;
    /**
     * 边界点集
     */
    private String boundaryPoints;
    /**
     *  网格级别
     */
    private String gridLevel;
    /**
     *  下辖网格个数
     */
    private Integer num;
    /**
     *  更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 是否有下级网格
     */
    private List<AddrInfoDto> children;
    /**
     *  归属网格id
     */
    private Long networkId;
    /**
     *  父级名称
     */
    private String fatherName;
}
