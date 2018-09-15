package com.ifast.common.component.oss.support.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/9/11 15:49 | Aron</small>
 */
@Component
@Data
@ConfigurationProperties(prefix = "ifast.oss.local")
public class LocalUploadProperties {
    /**
     * <pre>
     * 本地上传目录，末尾不带分隔符
     * 如:
     *  /data/upload
     *  c:/data/upload
     * </pre>
     */
    private String localPath;

    /**
     * <pre>
     * 根路径访问URL,末尾不带分隔符
     * 如： http://ifast.site/upload
     * </pre>
     */
    private String rootURL;

}

