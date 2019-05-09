package com.ifast.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月28日 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast.jwt")
@Data
public class JWTConfigProperties {

    private String userPrimaryKey;

    private String expireTokenKeyPrefix;

    /**
     * jwt过期时间,默认2小时，单位为毫秒
     */
    private Long expireTime = 7200000L;
    /**
     * <pre>
     *   refresh_token过期时间，默认7天，单位为毫秒
     *
     *   常用时间：
     *      1 day:86400000
     *      7 day:604800000
     *      14 day:1209600000
     *      30 day:2592000000
     *  </pre>
     */
    private Long refreshTokenExpire = 604800000L;

}
