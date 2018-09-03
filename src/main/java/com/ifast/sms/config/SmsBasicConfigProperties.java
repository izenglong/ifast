package com.ifast.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/30 19:27 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast.sms")
@Data
public class SmsBasicConfigProperties {

    private String cacheKey = "ifast:cache";
    private String cacheKeyPrefix = "sms";
    /**
     * 过期时间，单位为秒
     */
    private long codeExpireTime = 900;
}
