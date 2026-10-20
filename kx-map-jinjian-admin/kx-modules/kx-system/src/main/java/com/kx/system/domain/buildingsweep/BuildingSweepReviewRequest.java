package com.kx.system.domain.buildingsweep;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 扫楼上报复核参数。 */
@Data
public class BuildingSweepReviewRequest {

    @NotNull(message = "记录ID不能为空")
    private Long id;

    /** 2复核通过、3退回补正。 */
    @NotNull(message = "复核结果不能为空")
    private Integer reviewResult;

    @NotBlank(message = "复核意见不能为空")
    private String reviewOpinion;

    /** 是否关联线索：0否、1是。 */
    @NotNull(message = "请选择是否关联线索")
    private Integer isRelatedClue;

    /** 关联线索编号，选择关联时必填。 */
    private String relatedClueNo;

    /** 复核人工号暂时允许为空。 */
    private String reviewerUserName;

    private String reviewerNickName;
}
