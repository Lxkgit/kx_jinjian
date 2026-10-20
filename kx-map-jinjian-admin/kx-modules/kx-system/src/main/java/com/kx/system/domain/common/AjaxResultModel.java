package com.kx.system.domain.common;

import com.kx.common.core.constant.HttpStatus;
import com.kx.common.core.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 包含泛型结构的返回体
 * @param <T>
 */
@Data
public class AjaxResultModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("消息状态码")
    private int code;
    @ApiModelProperty("消息内容")
    private String msg;
    @ApiModelProperty("响应数据")
    private T data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResultModel()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResultModel(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResultModel(int code, String msg, T data)
    {
        this.code = code;
        this.msg = msg;
        if (StringUtils.isNotNull(data)) {
            this.data = data;
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResultModel success()
    {
        return AjaxResultModel.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> AjaxResultModel<T> success(T data)
    {
        return AjaxResultModel.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResultModel success(String msg)
    {
        return AjaxResultModel.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResultModel<T> success(String msg, T data)
    {
        return new AjaxResultModel<T>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResultModel error()
    {
        return AjaxResultModel.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResultModel error(String msg)
    {
        return AjaxResultModel.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> AjaxResultModel<T> error(String msg, T data)
    {
        return new AjaxResultModel<T>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResultModel error(int code, String msg)
    {
        return new AjaxResultModel(code, msg, null);
    }

    /**
     * 返回无数据消息
     *
     * @return 无数据消息
     */
    public static AjaxResultModel noContent()
    {
        return AjaxResultModel.noContent("无数据");
    }

    /**
     * 返回无数据数据
     *
     * @return 无数据消息
     */
    public static <T> AjaxResultModel<T> noContent(T data)
    {
        return AjaxResultModel.noContent("无数据", data);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResultModel noContent(String msg)
    {
        return AjaxResultModel.noContent(msg, null);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResultModel<T> noContent(String msg, T data)
    {
        return new AjaxResultModel<T>(HttpStatus.NO_CONTENT, msg, data);
    }

    /**
     * 返回无数据消息
     *
     * @return 无数据消息
     */
    public static AjaxResultModel badRequest()
    {
        return AjaxResultModel.badRequest("请求校验未通过");
    }

    /**
     * 返回无数据数据
     *
     * @return 无数据消息
     */
    public static <T> AjaxResultModel<T> badRequest(T data)
    {
        return AjaxResultModel.badRequest("请求校验未通过", data);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResultModel badRequest(String msg)
    {
        return AjaxResultModel.badRequest(msg, null);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResultModel<T> badRequest(String msg, T data)
    {
        return new AjaxResultModel(HttpStatus.BAD_REQUEST, msg, data);
    }

    /**
     * 返回资源没有被修改消息
     *
     * @return 无数据消息
     */
    public static AjaxResultModel notModified()
    {
        return AjaxResultModel.notModified("请求校验未通过");
    }

    /**
     * 返回无数据数据
     *
     * @return 无数据消息
     */
    public static <T> AjaxResultModel<T> notModified(T data)
    {
        return AjaxResultModel.notModified("请求校验未通过", data);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResultModel notModified(String msg)
    {
        return AjaxResultModel.notModified(msg, null);
    }

    /**
     * 返回无数据消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResultModel<T> notModified(String msg, T data)
    {
        return new AjaxResultModel<T>(HttpStatus.NOT_MODIFIED, msg, data);
    }



}
