package com.kx.system.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kx.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 网点对象 sys_network
 *
 * @author xiatian
 * @date 2020-08-07
 */
public class SysNetwork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 网点id */
    private Long networkId;

    /** 父网点id */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 网点名称 */
    private String networkName;

    /** 网点编号 */
    private String networkCode;

    /** 显示顺序 */
    private Integer orderNum;

    /** 负责人 */
    private String leader;

    /** 类型营业中心-1，商圈性-2，社区型-3，乡镇型-4，市场型-5，园区型-6 */
    private Integer type;

    /** 同类排名 */
    private Long similarRank;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 地址 */
    private String address;

    /** 经度 */
    private String lng;

    /** 纬度 */
    private String lat;

    /** 网点状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父网点名称 */
    private String parentName;

    /** 子网点 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SysNetwork> children;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date AreaManageUpdateTime;

    public Date getAreaManageUpdateTime() {
        return AreaManageUpdateTime;
    }

    public void setAreaManageUpdateTime(Date areaManageUpdateTime) {
        AreaManageUpdateTime = areaManageUpdateTime;
    }

    public void setNetworkId(Long networkId)
    {
        this.networkId = networkId;
    }

    public Long getNetworkId()
    {
        return networkId;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setNetworkName(String networkName)
    {
        this.networkName = networkName;
    }

    public String getNetworkName()
    {
        return networkName;
    }
    public void setNetworkCode(String networkCode)
    {
        this.networkCode = networkCode;
    }

    public String getNetworkCode()
    {
        return networkCode;
    }
    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }
    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public String getLeader()
    {
        return leader;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setSimilarRank(Long similarRank)
    {
        this.similarRank = similarRank;
    }

    public Long getSimilarRank()
    {
        return similarRank;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setLng(String lng)
    {
        this.lng = lng;
    }

    public String getLng()
    {
        return lng;
    }
    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public String getLat()
    {
        return lat;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<SysNetwork> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysNetwork> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("networkId", getNetworkId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("networkName", getNetworkName())
            .append("networkCode", getNetworkCode())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("type", getType())
            .append("similarRank", getSimilarRank())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("address", getAddress())
            .append("lng", getLng())
            .append("lat", getLat())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
