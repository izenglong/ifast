package com.ifast.common.component.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/30 19:27 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast.sms")
@Data
public class SmsBasicProperties {

    private String cacheKey = "ifast:cache";
    private String cacheKeyPrefix = "sms";
    /**
     * 过期时间，单位为秒
     */
    private long codeExpireTime = 900;

    /**
     * 短信场景值&内容
     */
    private Map<String, String> scenes;
}
