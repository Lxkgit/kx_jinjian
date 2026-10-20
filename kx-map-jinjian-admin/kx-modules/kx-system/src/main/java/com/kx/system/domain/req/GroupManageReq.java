package com.kx.system.domain.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupManageReq extends CustomerReq implements Serializable {

    @ApiModelProperty("群id")
    private Long groupId;

    @ApiModelProperty("客群类型（1：绩效客户；2：授信客户；2：建档客户）")
    private Integer customType;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("证件号码")
    private String customerIdentity;

    @ApiModelProperty("手机号码")
    private String customerPhone;

    @ApiModelProperty("管户人")
    private String creditManager;

    @ApiModelProperty("所属网格编号")
    private String belongGridCode;

    @ApiModelProperty("所属网格编号集合")
    private List<String> belongGridCodeArr;

    @ApiModelProperty("新增、删除ids")
    private List<Long> ids;


}
