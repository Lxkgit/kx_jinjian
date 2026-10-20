package com.kx.system.domain.req;

import com.kx.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomGroupReq extends BaseEntity implements Serializable {

    private Long id;

    @ApiModelProperty("客群类型（1：绩效客户；2：授信客户；3：建档客户）")
    private Integer customType;

    @ApiModelProperty("客群分类id（1：绩效客户；2：授信客户；3：建档客户）")
    private Long categoryId;

    @ApiModelProperty("客群名称")
    private String customGroupName;

    @ApiModelProperty("说明")
    private String groupDesc;

    @ApiModelProperty("客群标签")
    private List<Long> labelIds;

    @ApiModelProperty("标签关联关系 0：或；1：且")
    private Integer updateFlag;

    private int size;

}
