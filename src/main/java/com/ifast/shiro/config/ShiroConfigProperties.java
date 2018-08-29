package com.ifast.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/27 21:50 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast.shiro")
@Data
public class ShiroConfigProperties {
    private String sessionKeyPrefix = "ifast:session";
    private String jsessionidKey = "SESSION";
}
