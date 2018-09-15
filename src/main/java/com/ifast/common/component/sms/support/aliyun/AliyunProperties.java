package com.ifast.common.component.sms.support.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "ifast.sms.aliyun")
@Data
public class AliyunProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private Map<String, String> scenes;
}
