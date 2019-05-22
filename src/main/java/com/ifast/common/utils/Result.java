package com.ifast.common.utils;

import com.ifast.common.type.EnumErrorCode;

/**
 * <pre>
 * 
 * 自定义响应结构
 * </pre>
 * 
 * @author Aron
 * @date 2017年5月9日
 */
public class Result<T> {

    public final static Integer CODE_SUCCESS = EnumErrorCode.success.getCode();
    public final static Integer CODE_FAIL = EnumErrorCode.fail.getCode();
    public final static String MSG_SUCCESS = EnumErrorCode.success.getMsg();
    public final static String MSG_FAIL = EnumErrorCode.fail.getMsg();

    /**
     * 响应业务状态 0 成功， 1失败
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private T data;

    public Result() {}

    public Result(Integer status, String msg, T data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = 0;
        this.msg = MSG_SUCCESS;
        this.data = data;
    }

    // method

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> ok() {
        return new Result<>(CODE_SUCCESS, MSG_SUCCESS, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(CODE_FAIL, MSG_FAIL, null);
    }

    public static <T> Result<T> build(Integer status, String msg) {
        return build(status, msg, null);
    }

    public static <T> Result<T> build(Integer status, String msg, T data) {
        return new Result<>(status, msg, data);
    }

    // get set

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
