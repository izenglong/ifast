package com.ifast.common.exception.handler;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.HttpContextUtils;
import com.ifast.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 * 
 */
@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    public final static String ERROR_DEFAULT_PAGE = "error/error";

    /**
     * 参数校验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentException(IllegalArgumentException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:IllegalArgumentException[{}]:{}",e.getClass(),e.getMessage());
        }
        return Result.build(EnumErrorCode.illegalArgument.getCode(), e.getMessage());
    }

    /**
     * API异常
     */
    @ExceptionHandler(IFastApiException.class)
    public Result<String> handleIFastAPIException(IFastApiException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:IFastApiException[{}]:{}",e.getClass(),e.getMessage());
        }
        return getStringResult(e.getMessage());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(IFastException.class)
    public Object handleIFastException(IFastException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:IFastException[{}]:{}",e.getClass(),e.getMessage());
        }
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
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:DuplicateKeyException[{}]:{}",e.getClass(),e.getMessage());
        }
        return Result.build(EnumErrorCode.duplicateKeyExist.getCode(), EnumErrorCode.duplicateKeyExist.getMsg());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<String> noHandlerFoundException(NoHandlerFoundException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:NoHandlerFoundException[{}]:{}",e.getClass(),e.getMessage());
        }
        return Result.build(EnumErrorCode.pageNotFound.getCode(), EnumErrorCode.pageNotFound.getMsg());
    }

    @ExceptionHandler(ShiroException.class)
    public Result<String> handleAuthorizationException(ShiroException e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:ShiroException[{}][{}]:{}",e.getClass(),e.getClass(), e.getMessage());
        }
        if(e instanceof IncorrectCredentialsException) {
        	return Result.build(EnumErrorCode.apiAuthorizationFailed.getCode(), EnumErrorCode.apiAuthorizationFailed.getMsg());
        }else if(e instanceof ExpiredCredentialsException) {
        	return Result.build(EnumErrorCode.apiAuthorizationExpired.getCode(), EnumErrorCode.apiAuthorizationExpired.getMsg());
        } else if(e instanceof AuthenticationException){
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());

        }
        return Result.build(EnumErrorCode.notAuthorization.getCode(), EnumErrorCode.notAuthorization.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        if(log.isDebugEnabled()){
            log.debug("全局异常处理:Exception[{}]:{}",e.getClass(),e.getMessage());
        }
        if(!HttpContextUtils.isAjax()){
            ModelAndView mv = new ModelAndView();
            mv.setViewName(ERROR_DEFAULT_PAGE);
            return mv;
        }else{
            return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
        }


    }
}
