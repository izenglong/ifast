package com.ifast.api.exception;

import com.ifast.common.exception.IFastException;

/**
 * <pre>
 * API异常基类
 * </pre>
 * 
 * <small> 2018年4月19日 | Aron</small>
 */
public class IFastApiException extends IFastException {

    private static final long serialVersionUID = -4891641110275580161L;

    public IFastApiException() {
        super();
    }

    public IFastApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public IFastApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IFastApiException(String message) {
        super(message);
    }

    public IFastApiException(Throwable cause) {
        super(cause);
    }

}
