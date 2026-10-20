package com.kx.system.domain;

import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 特色网格边界数据
 *
 * @TableName pa_addr_info_type
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PaAddrInfoType extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 行政区划编码
     */
    private String code;

    /**
     * 行政区名称
     */
    private String name;

    /**
     * 父级行政区划编码
     */
    private String parentCode;

    /**
     * 所有祖先网格code
     */
    private String ancestorCodes;

    /**
     * 是否被维护，1-已维护，0-未维护
     */
    private Integer isMaintained;

    /**
     * 所属网点id
     */
    private String belongNetworkId;

    /**
     * 所属网点编码
     */
    private String belongNetworkCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 信用村标志名
     */
    private String creditLevel;

    /**
     * 经度
     */
    private BigDecimal lon;

    /**
     * 维度
     */
    private BigDecimal lat;

    /**
     * 别名/简称
     */
    private String anotherName;

    /**
     * 边界点集
     */
    private String boundaryPoints;

    /**
     * 中心点坐标
     */
    private String centerPoint;

    /**
     * 边界数据
     */
    private String coordinates;

    /**
     * 行政编码层级
     */
    private Integer addrLevel;

    /**
     * 边界类型 0行政 1产业园区 2小区 3商圈
     */
    private String type;

    /**
     *
     */
    private String etlDt;

    /**
     * 边界类型
     */
    @NotBlank(message = "网格分类不能为空")
    private Long typeCode;

    /**
     * 网格主管员userid
     */
    private Long userId;

    /**
     * 网格主管员姓名
     */
    private String userName;
    /**
     * 网格主管员工号
     */
    private String userManagerNum;

    /**
     * 网格副管员userid
     */
    private List<String> secondUserId;

    /**
     * 网格副管员姓名
     */
    private List<String> secondUserName;
    /**
     * 网格副管员工号
     */
    private List<String> secondManagerNum;
    /**
     * 责任机构id
     */
    private Long deptId;

    /**
     * 责任机构名称
     */
    private String deptName;

    /**
     * 客户经理工号
     */
    private String managerNum;

    /**
     * 网格类型(0-非农村网格，1-农村网格)
     */
    private String ruralGridType;

    /**
     * 责任机构code编码
     */
    private String deptCode;

    /**
     * 以下非实体类型属性
     **/
    private Boolean isManage = false;

    /**
     * 是否含有叶子节点
     */
    private Boolean isLeaf;

    /**
     * 上级网格名称
     */
    private String parentName;

    /**
     * 字网格数量
     */
    private Integer childNum;

    /**
     * 最小网格数量
     */
    private Integer minimumNum;

    /**
     * 是否有权限编辑
     */
    private boolean editable = true;

    @ApiModelProperty(value = "是否现金服务重点网格  0-否 1-是")
    private String isCash;

    @ApiModelProperty(value = "是否有责任网点  0-否 1-是")
    private String isDutyAnchor;

    @ApiModelProperty(value = "三级网格")
    private String threeGrid;

    @ApiModelProperty(value = "四级网格")
    private String fourGrid;

    private String anchorName;

    private Long anchorId;
    /**
     * 高亮或者置灰
     */
    private boolean lightOrGray = false;

    private double[][][][] coor;

    /**
     * 分页查询参数
     */
    private Integer pageNum;
    private Integer pageSize;
}
