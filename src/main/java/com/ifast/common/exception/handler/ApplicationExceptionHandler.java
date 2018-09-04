package com.ifast.common.exception.handler;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.HttpContextUtils;
import com.ifast.common.utils.Result;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 * 
 */
@RestControllerAdvice()
public class ApplicationExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    public final static String ERROR_DEFAULT_PAGE = "error/error";

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        return Result.build(EnumErrorCode.illegalArgument.getCode(), e.getMessage());
    }

    /**
     * API异常
     */
    @ExceptionHandler(IFastApiException.class)
    public Result<String> handleIFastAPIException(IFastApiException e) {
        return getStringResult(e.getMessage());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(IFastException.class)
    public Object handleIFastException(IFastException e) {

        if(!HttpContextUtils.isAjax()){
            ModelAndView mv = new ModelAndView();
            mv.setViewName(ERROR_DEFAULT_PAGE);
            return mv;
        }else{
            return getStringResult(e.getMessage());
        }
    }

    private Result<String> getStringResult(String message) {
        try {
            int code = Integer.parseInt(message);
            return Result.build(code, EnumErrorCode.getMsgByCode(code));
        } catch (NumberFormatException e1) {
            log.warn("错误码使用错误，异常内容请抛出EnumErrorCode类的枚举值");
            e1.printStackTrace();
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage());
        return Result.build(EnumErrorCode.duplicateKeyExist.getCode(), EnumErrorCode.duplicateKeyExist.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage());
        return Result.build(EnumErrorCode.pageNotFound.getCode(), EnumErrorCode.pageNotFound.getMsg());
    }

    @ExceptionHandler(ShiroException.class)
    public Result<String> handleAuthorizationException(ShiroException e) {
        log.error(e.getMessage());
        if(e instanceof IncorrectCredentialsException) {
        	return Result.build(EnumErrorCode.apiAuthorizationFailed.getCode(), EnumErrorCode.apiAuthorizationFailed.getMsg());
        }else if(e instanceof ExpiredCredentialsException) {
        	return Result.build(EnumErrorCode.apiAuthorizationExpired.getCode(), EnumErrorCode.apiAuthorizationExpired.getMsg());
        }
        return Result.build(EnumErrorCode.notAuthorization.getCode(), EnumErrorCode.notAuthorization.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        if(!HttpContextUtils.isAjax()){
            ModelAndView mv = new ModelAndView();
            mv.setViewName(ERROR_DEFAULT_PAGE);
            return mv;
        }else{
            log.error(e.getMessage());
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }


    }
}
