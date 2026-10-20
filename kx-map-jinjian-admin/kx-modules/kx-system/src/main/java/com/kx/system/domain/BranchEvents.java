package com.kx.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchEvents {
    // 事件id
    private long id;
    // 网点id
    private long branchId;
    // 投诉人
    @NotBlank(message = "投诉人不能为空")
    private String complainant;
    // 投诉人电话
    @NotBlank(message = "投诉人电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "投诉人电话格式不正确")
    private String complainantPhone;
    // 投诉人类型
    @NotBlank(message = "投诉人类型不能为空")
    private String complainantType;
    // 投诉时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime complainTime;
    // 投诉描述
    @NotBlank(message = "投诉描述不能为空")
    private String description;
    // 处理人
    @NotBlank(message = "处理人不能为空")
    private String handler;
    // 处理人电话
    @NotBlank(message = "上级负责人电话格式不正确")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "处理人电话格式不正确")
    private String handlerPhone;
    // 处理状态
    private String status;
    // 网点名称
    @NotBlank(message = "网点名称不能为空")
    private String customerName;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //更改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    //上级负责人
    private String superiorsName;

    //上级负责人电话
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "上级负责人电话格式不正确")
    private String superiorsPhone;


}
