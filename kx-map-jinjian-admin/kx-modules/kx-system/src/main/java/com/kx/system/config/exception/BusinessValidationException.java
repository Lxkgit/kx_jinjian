package com.kx.system.config.exception;

/**
 * 驾驶舱业务异常
 *
 * @author yuanshiyi
 */
public class BusinessValidationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public BusinessValidationException(String message)
    {
        this.message = message;
    }

    public BusinessValidationException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public BusinessValidationException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }
}
