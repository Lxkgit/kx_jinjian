package com.kx.system.domain.filing;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kx.system.util.JsonToStringDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuBoWu
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("filing_message_detail")
@ApiModel(value="对象", description="")
public class FilingMessageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    @ApiModelProperty(value = "模板id")
    private Long modelId;

    @JsonDeserialize(using = JsonToStringDeserializer.class)
    @ApiModelProperty(value = "模板信息(json格式)")
    private Object jsonMessage;

    @JsonDeserialize(using = JsonToStringDeserializer.class)
    @ApiModelProperty(value = "建档完成(json格式)")
    private Object jsonFinish;


    @ApiModelProperty(value = "建档id")
    private Long filingId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @ApiModelProperty(value = "更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
