package com.kx.common.core.domain.branch.branch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BranchInfo extends BaseEntity {

    /** 自增id */
    private Long id;

    /** 网格编码 */
    private String gridCode;

    /** sys_dept表parent_id字段 */
    private Long parentId;

    /** 父级部门名称 */
    private String parentDeptName;

    /** 父部门编号 */
    private String parentOrgNum;

    /** 网点编码（对应sys_dept表org_num字段） */
    private String orgNum;

    /** 网点名称 */
    private String branchName;

    /** 网点地址 */
    private String branchAddr;

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

    /** 存款取一体机CRS数量 */
    private Integer crsMachines;

    /** 现金柜员机数量 */
    private Integer artificialCashMachines;

    /** 硬币兑换机数量 */
    private Integer coinExchangeMachines;

    /** 网点功能（字典值，逗号分隔） */
    private String branchFunction;

    /** 经度 */
    private BigDecimal lon;

    /** 维度 */
    private BigDecimal lat;

    /** 网点零钱包兑换数量 */
    private Integer changeWalletsNum;

    /** 网点零钱包兑换次数 */
    private Integer changeWalletsCount;

    /** 网点零钱包兑换金额 */
    private BigDecimal changeWalletsAmount;

    /** 残损币收兑金额 */
    private BigDecimal brokenCoinAmount;

    /** 残损币收兑次数 */
    private Integer brokenCoinCount;

    /** 硬币兑换金额 */
    private BigDecimal coinExchangeAmount;

    /** 硬币兑换枚数 */
    private Integer coinExchangeCount;

    /** 是否办理人工现金业务（1是 0否） */
    private Integer artificialCashFlag;

    /** 是否设有零钞兑换绿色通道（1是 0否） */
    private Integer changeExchangeFlag;

    /** 所属银行机构名称 */
    private String orgName;

    /** 银行机构类型（国有商业银行/股份制商业银行/城市商业银行/农村商业银行等） */
    private String orgType;

    /** 网点类型（字典 dot_type：0特色 1特殊 2网格责任 3其它，逗号分隔） */
    private String branchType;

    /**
     * 网点类型搜索入参（多选，对应 branchType 字段，前端发数组）
     */
    private List<String> branchTypeList;

    /** 所属网格名称 */
    private String gridName;

    /** 网点所在地类型（字典 new_grid_type：0行政村 1社区） */
    private String branchAddrType;

    /** 数据状态（0已删除 1正常） */
    private Integer delFlag;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人名称 */
    private String createName;

    /** 最近修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 修改人名称 */
    private String updateName;
}