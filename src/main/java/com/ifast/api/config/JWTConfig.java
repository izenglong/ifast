package com.ifast.api.config;

import lombok.Data;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月28日 | Aron</small>
 */
@Data
public class JWTConfig {
    private String userPrimaryKey;
    /**
     * jwt过期时间,默认2小时，单位为毫秒
     */
    private Long expireTime = 7200000L;
    /**
     *  refresh_token过期时间，默认7天，单位为毫秒
     */
    private Long refreshTokenExpire = 604800000L;

}
