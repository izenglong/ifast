package com.ifast.common.exception.handler;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.Result;

/**
 * 异常处理器
 * 
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * API异常
     */
    @ExceptionHandler(IFastApiException.class)
    public Result<String> handleIFastApiException(IFastApiException e) {
        log.info("handleIFastApiException");
        try {
            int code = Integer.parseInt(e.getMessage());
            return Result.build(code, EnumErrorCode.getMsgByCode(code));
        } catch (NumberFormatException e1) {
            log.info("错误码使用错误，异常内容请抛出EnumErrorCode类的枚举值");
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(IFastException.class)
    public Result<String> handleIFastException(IFastException e) {
        log.info("handleIFastException");
        try {
            int code = Integer.parseInt(e.getMessage());
            return Result.build(code, EnumErrorCode.getMsgByCode(code));
        } catch (NumberFormatException e1) {
            log.info("错误码使用错误，异常内容请抛出EnumErrorCode类的枚举值");
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.duplicateKeyExist.getCode(), EnumErrorCode.duplicateKeyExist.getMsg());
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.pageNotFound.getCode(), EnumErrorCode.pageNotFound.getMsg());
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result<String> handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.notAuthorization.getCode(), EnumErrorCode.notAuthorization.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
    }
}
