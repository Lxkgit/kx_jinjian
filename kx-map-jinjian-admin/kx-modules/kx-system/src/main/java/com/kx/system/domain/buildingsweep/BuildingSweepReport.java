package com.kx.system.domain.buildingsweep;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 扫楼上报记录。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingSweepReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 排查编号，由服务端生成。 */
    private String reportNo;

    @NotNull(message = "排查时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionTime;

    /** 牵头部门编码。 */
    @NotBlank(message = "请选择牵头部门")
    private String leadDeptCode;

    @NotBlank(message = "牵头部门不能为空")
    private String leadDeptName;

    /** 参与部门编码，多个使用英文逗号分隔。 */
    private String participantDeptCodes;
    private String participantDeptNames;

    /** 参与排查登录账号，多个使用英文逗号分隔。 */
    private String participantPersonUserNames;
    private String participantPersonNames;

    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String streetCode;

    @NotBlank(message = "街道不能为空")
    private String streetName;

    private String communityCode;

    @NotBlank(message = "社区不能为空")
    private String communityName;

    private String areaFullName;

    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    /** 多个主体类型使用英文逗号分隔：1商户、2小微企业、3办公室。 */
    @NotBlank(message = "主体类型不能为空")
    private String subjectTypes;

    @NotNull(message = "覆盖主体数不能为空")
    @Min(value = 1, message = "覆盖主体数必须大于0")
    private Integer coveredSubjectCount;

    @NotNull(message = "排查人次不能为空")
    @Min(value = 1, message = "排查人次必须大于0")
    private Integer inspectionPersonTimes;

    private String issueSummary;

    /** MinIO文件对象名，多个使用英文逗号分隔。 */
    private String evidenceMaterials;

    /** 详情查询时临时填充，不保存到数据库。 */
    private Map<String, String> evidencePreviewUrls = new LinkedHashMap<>();

    /** 填报人工号暂时允许为空。 */
    private String reporterUserName;

    private String reporterNickName;

    /** 1待复核、2复核通过、3退回补正。 */
    private Integer reviewStatus;
    private String reviewOpinion;
    private String reviewerUserName;
    private String reviewerNickName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    private String relatedClueNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reportTime;

    private String delFlag;

    /** 查询条件。 */
    private String keyword;
    private Integer subjectType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inspectionTimeEnd;
}
