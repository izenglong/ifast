package com.ifast.common.component.sms.support.zhutong;

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
@ConfigurationProperties(prefix = "ifast.sms.zt")
@Data
public class ZhuTongProperties {
    private String uname;
    private String passwd;
    private String productId;
    private Map<String, String> scenes;
}
