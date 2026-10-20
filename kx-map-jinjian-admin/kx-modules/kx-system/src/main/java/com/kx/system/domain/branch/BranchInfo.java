package com.kx.system.domain.branch;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BranchInfo {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String girdCode;

    /** sys_dept表parent_id字段 */
    private Long parentId;

    /** 父级部门名称 */
    private String parentDeptName;

    /** 父部门编号 */
    private String parentOrgNum;

    /** sys_dept表org_num字段（网点编码） */
    private String orgNum;

    /** 网点名称 */
    private String branchName;

    /** 网点地址 */
    private String branchAddr;

//    /** 网点级别 */
//    private Integer branchLevel;

    /** 网点负责人 */
    private String branchChargePerson;

    /** 负责人固定电话 */
    private String chargeTelephone;

    /** 负责人移动电话 */
    private String chargeMobilePhone;

    /** 网点介绍 */
    private String branchDesc;

    /** 网点服务柜台数 */
    private Integer branchServiceMachines;

    /** 自助取款机ATM数量 */
    private Integer atmMachines;

    /** 小面额自助现金机具数量（整换零） */
    private Integer antoSmallCashMachines;

    /** 小面额功能机具数量 */
    private Integer smallCashMachines;

    /** 存款取一体机CRS */
    private Integer crsMachines;

    /** 现金柜员机 */
    private Integer artificialCashMachines;

    /** 硬币兑换机数量 */
    private Integer coinExchangeMachines;

    /** 网点功能（存储字典表dict_value）使用 "," 分割 */
    private String branchFunction;

    /** 网点零钱包兑换数量 */
    private Integer changeWalletsNum;

    /** 网点零钱包兑换次数 */
    private Integer changeWalletsCount;

//    /** 网点零钱包兑换金额 */
//    private BigDecimal changeWalletsAmount;

    /** 残损币收兑金额 */
    private BigDecimal brokenCoinAmount;

    /** 残损币收兑次数 */
    private Integer brokenCoinCount;

    /** 硬币兑换金额 */
    private BigDecimal coinExchangeAmount;

    /** 硬币兑换枚数 */
    private Integer coinExchangeCount;

    /** 经度 */
    private BigDecimal lon;

    /** 维度 */
    private BigDecimal lat;

//    /** 网点是否办理人工现金业务 1是 0否 */
//    private Integer artificialCashFlag;
//
//    /** 网点是否设有零钞兑换兑换绿色通道1是 0否 */
//    private Integer changeExchangeFlag;
//
//    /** 人工现金柜台数 */
//    private Integer artificialCashNum;
//
//    /** 启用人工现金柜台数 */
//    private Integer enableArtificialCashNum;
//
//    /** 弹性人工现金柜台数 */
//    private Integer elasticityCounterNum;
//
//    /** 自助现金机具数 */
//    private Integer autoCashMachinesNum;
//
//    /** 具备小面额功能机具数 */
//    private Integer smallCashMachinesNum;
//
//    /** 硬币兑换机数量 */
//    private Integer coinMachinesNum;
//
//    /** 小面额自助兑换机具数量（整换零） */
//    private Integer antoSmallCashMachinesNum;

    /** 所属银行机构名称 */
    private String orgName;

    /** 银行机构类型（国有商业银行/股份制商业银行/城市商业银行/农村商业银行等） */
    private String orgType;

    /** 网点类型（字典 dot_type：0特色 1特殊 2网格责任 3其它，逗号分隔） */
    private String branchType;

    /** 所属网格名称 */
    private String gridName;

    /** 网点所在地类型（字典 new_grid_type：0行政村 1社区） */
    private String branchAddrType;

    /** 文章删除标识 0已删除 1 正常 */
    private Integer delFlag;

    /** 创建时间 */
    private Date createTime;

    /** 创建人名称 */
    private String createName;

    /** 创建人账号 */
    private Long createBy;

    /** 最近修改时间 */
    private Date updateTime;

    /** 修改人名称 */
    private String updateName;

    /** 修改人账号 */
    private Long updateBy;

//    // 事件id
//    private long id;
//    // 网点id
//    private long branchId;
//    // 投诉人
//    @NotBlank(message = "投诉人不能为空")
//    private String complainant;
//    // 投诉人电话
//    @NotBlank(message = "投诉人电话不能为空")
//    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "投诉人电话格式不正确")
//    private String complainantPhone;
//    // 投诉人类型
//    @NotBlank(message = "投诉人类型不能为空")
//    private String complainantType;
//    // 投诉时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime complainTime;
//    // 投诉描述
//    @NotBlank(message = "投诉描述不能为空")
//    private String description;
//    // 处理人
//    @NotBlank(message = "处理人不能为空")
//    private String handler;
//    // 处理人电话
//    @NotBlank(message = "上级负责人电话格式不正确")
//    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "处理人电话格式不正确")
//    private String handlerPhone;
//    // 处理状态
//    private String status;
//    // 网点名称
//    @NotBlank(message = "网点名称不能为空")
//    private String customerName;
//    // 创建时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime createTime;
//
//    //更改时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updateTime;
//
//    //上级负责人
//    private String superiorsName;
//
//    //上级负责人电话
//    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "上级负责人电话格式不正确")
//    private String superiorsPhone;


}
