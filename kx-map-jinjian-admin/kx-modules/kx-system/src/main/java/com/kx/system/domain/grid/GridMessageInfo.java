package com.kx.system.domain.grid;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 网格基础信息维护表
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("grid_message_info")
@ApiModel(value = "网格基础信息维护表对象", description = "网格基础信息维护表")
public class GridMessageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

    /**
     * 网格编码
     */
    private String gridCode;

    /**
     * 是否重点网格 1是 0否
     */
    private Integer keyGridFlag;

    /**
     * 银行网格长
     */
    private String gridBankCharge;

    /**
     * 银行网格长联系电话
     */
    private String gridBankChargePhone;

    /**
     * 银行网格员
     */
    private String gridBankMember;

    /**
     * 银行网格员联系电话
     */
    private String gridBankMemberPhone;

    /**
     * 网格责任网点
     */
    private String gridBranchCharge;

    /**
     * 网格内摸排商户数
     */
    private Integer merchantNum;

    /**
     * 网格内现金收付摸排次数
     */
    private Integer cashNum;

    /**
     * 网格内宣传次数
     */
    private Integer publicityNum;

    /**
     * 宣传受众人数（万人）
     */
    private Integer publicityPersonNum;

    /**
     * 网格内大篷车、小钱箱等现金服务次数
     */
    private Integer cashServiceNum;

    /**
     * 发现拒收现金线索数量
     */
    private Integer refuseCashNum;

    /**
     * 网格内小面额兑出数量
     */
    private Integer cashExchangeNum;

    /**
     * 网格内小面额兑出金额
     */
    private BigDecimal cashExchangeAmount;

    /**
     * 残损币收兑数量
     */
    private Integer brokenCoinCount;

    /**
     * 残损币收兑金额
     */
    private BigDecimal brokenCoinAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建人账号
     */
    private Long createBy;

    /**
     * 最近修改时间
     */
    private Date updateTime;

    /**
     * 修改人名称
     */
    private String updateName;

    /**
     * 修改人账号
     */
    private Long updateBy;
//    @ApiModelProperty(value = "网格基础信息表主键id", required = true)
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id;
//
//
//    @ApiModelProperty(value = "网格编码")
//    private String gridCode;
//
//
//    @ApiModelProperty(value = "网格名称")
//    private String gridName;
//
//
//    @ApiModelProperty(value = "网格客户总数")
//    private String gridCustomerTotal;
//
//
//    @ApiModelProperty(value = "网格长名称")
//    private String gridMainManagerName;
//
//    @ApiModelProperty(value = "网格长工号")
//    private String gridMainManagerCode;
//
//    @ApiModelProperty(value = "网格长联系电话")
//    private String gridMainManagerPhone;
//
//    @ApiModelProperty(value = "责任网点名称")
//    private String dutyNetworkName;
//
//    private List<String> dutyNetworkNames;
//
//    @ApiModelProperty(value = "责任网点编码")
//    private String dutyNetworkCode;
//
//    @ApiModelProperty(value = "网点责任人名称")
//    private String dutyName;
//
//    @ApiModelProperty(value = "网点责任人编码")
//    private String dutyCode;
//
//    @ApiModelProperty(value = "网点责任人移动电话")
//    private String dutyPhone;
//
//    @ApiModelProperty(value = "网点责任人固定电话")
//    private String dutyTelephone;
//
//    @ApiModelProperty(value = "银行机构名称")
//    private String bankAgencyName;
//
//    @ApiModelProperty(value = "银行机构编码")
//    private String bankAgencyCode;
//
//    @ApiModelProperty(value = "责任网点地址")
//    private String dutyNetworkAddress;
//
//    @ApiModelProperty(value = "是否现金服务重点网格  0-否 1-是")
//    private String isCash;
//
//    @ApiModelProperty(value = "网格副管员名称")
//    private List<String> gridSideManagerName;
//
//
//    @ApiModelProperty(value = "网格副管员工号")
//    private List<String> gridSideManagerCode;
//
//    @ApiModelProperty(value = "网格员联系电话")
//    private String gridInspectorPhone;
//
//    @ApiModelProperty(value = "网格员名称")
//    private String gridInspectorName;
//
//
//
//    @ApiModelProperty(value = "所属团队")
//    private String belongTeam;
//
//
//    @ApiModelProperty(value = "一级支行")
//    private String firstLevelBranch;
//
//
//    @ApiModelProperty(value = "二级支行")
//    private String secondLevelBranch;
//
//
//    @ApiModelProperty(value = "分行")
//    private String branch;
//
//
//    @ApiModelProperty(value = "网格图片")
//    private String picture;
//
//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//
//    @ApiModelProperty(value = "更新日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;
//
//    @ApiModelProperty(value = "网格概况")
//    private String gridOverview;
//
//    @ApiModelProperty(value = "网格备注")
//    private String gridRemark;
//
//    @ApiModelProperty(value = "乡村网格类型")
//    private String ruralGridType;
//
//    @ApiModelProperty(value = "是否首次修改网格人数 0-否 1-是")
//    private String isModify;
//
//    private List<GridCruxInfo> cruxList;
//
//    private List<GridRatingInfo> ratingList;
//    private Integer peersNum;
//    private Integer communityNum;
//    private Integer districtNum;
//    private Integer hiqUnitsNum;
//    private String hiqUnitsSituation;
//
//    private Integer pageNum;
//    private Integer pageSize;
//
//    /**
//     * 查询父级code
//     */
//    private String code;
}
