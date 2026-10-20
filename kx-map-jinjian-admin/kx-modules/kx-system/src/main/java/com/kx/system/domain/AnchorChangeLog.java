package com.kx.system.domain;

import com.kx.common.core.annotation.Excel;
import com.kx.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 锚点变更记录对象 anchor_change_log
 *
 * @author kangxu
 * @date 2024-04-08
 */
public class AnchorChangeLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 锚点id */
    @Excel(name = "锚点id")
    private Long anchorId;

    /** 锚点名称 */
    @Excel(name = "锚点名称")
    private String anchorName;

    /** 锚点类型代码 */
    @Excel(name = "锚点类型代码")
    private Integer type;

    /** 锚点类型名称 */
    @Excel(name = "锚点类型名称")
    private String typeName;

    /** 原网格地址 */
    @Excel(name = "原网格地址")
    private String oldAddress;

    /** 原网格地址类型代码 */
    @Excel(name = "原网格地址类型代码")
    private Integer oldAddressTypeCode;

    /** 原网格地址类型 */
    @Excel(name = "原网格地址类型")
    private String oldAddressType;

    /** 新网格地址 */
    @Excel(name = "新网格地址")
    private String newAddress;

    /** 新网格地址类型代码 */
    @Excel(name = "新网格地址类型代码")
    private Integer newAddressTypeCode;

    /** 新网格地址类型 */
    @Excel(name = "新网格地址类型")
    private String newAddressType;

    /** 操作人 */
    @Excel(name = "操作人")
    private String createUser;

    private List<Integer> oldAddressTypeCodes;

    private List<Integer> newAddressTypeCodes;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAnchorId(Long anchorId)
    {
        this.anchorId = anchorId;
    }

    public Long getAnchorId()
    {
        return anchorId;
    }
    public void setAnchorName(String anchorName)
    {
        this.anchorName = anchorName;
    }

    public String getAnchorName()
    {
        return anchorName;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return typeName;
    }
    public void setOldAddress(String oldAddress)
    {
        this.oldAddress = oldAddress;
    }

    public String getOldAddress()
    {
        return oldAddress;
    }
    public void setOldAddressTypeCode(Integer oldAddressTypeCode)
    {
        this.oldAddressTypeCode = oldAddressTypeCode;
    }

    public Integer getOldAddressTypeCode()
    {
        return oldAddressTypeCode;
    }
    public void setOldAddressType(String oldAddressType)
    {
        this.oldAddressType = oldAddressType;
    }

    public String getOldAddressType()
    {
        return oldAddressType;
    }
    public void setNewAddress(String newAddress)
    {
        this.newAddress = newAddress;
    }

    public String getNewAddress()
    {
        return newAddress;
    }
    public void setNewAddressTypeCode(Integer newAddressTypeCode)
    {
        this.newAddressTypeCode = newAddressTypeCode;
    }

    public Integer getNewAddressTypeCode()
    {
        return newAddressTypeCode;
    }
    public void setNewAddressType(String newAddressType)
    {
        this.newAddressType = newAddressType;
    }

    public String getNewAddressType()
    {
        return newAddressType;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public List<Integer> getOldAddressTypeCodes() {
        return oldAddressTypeCodes;
    }

    public void setOldAddressTypeCodes(List<Integer> oldAddressTypeCodes) {
        this.oldAddressTypeCodes = oldAddressTypeCodes;
    }

    public List<Integer> getNewAddressTypeCodes() {
        return newAddressTypeCodes;
    }

    public void setNewAddressTypeCodes(List<Integer> newAddressTypeCodes) {
        this.newAddressTypeCodes = newAddressTypeCodes;
    }
}
