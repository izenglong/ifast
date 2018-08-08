package com.ifast.oss.sdk;

import lombok.Data;

/**
 * <pre>
 * 七牛对象存储配置
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
@Data
public class OSSConfig {
    private String qiNiuAccessKey;
    private String qiNiuSecretKey;
    private String qiNiuBucket;
    private String qiNiuAccessURL;

}
