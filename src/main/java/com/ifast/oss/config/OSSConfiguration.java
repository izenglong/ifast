package com.ifast.oss.config;

import com.ifast.common.service.ConfigService;
import com.ifast.oss.support.UploadServer;
import com.ifast.oss.support.local.LocalUploadProperties;
import com.ifast.oss.support.local.LocalUploaderServer;
import com.ifast.oss.support.qiniu.QiNiuProperties;
import com.ifast.oss.support.qiniu.QiNiuUploaderServer;
import com.qiniu.common.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class OSSConfiguration {

    @Autowired
    private ConfigService configService;


    /**
     * 七牛上传
     */
    @Bean
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer qiNiuUploadServer() {
        QiNiuProperties ossConfig = configService.getOssConfigProperties();
        return new QiNiuUploaderServer(ossConfig, Zone.zone2());
    }

    /**
     * 本地上传
     */
    @Bean
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer localUploadServer(LocalUploadProperties properties) {
        return new LocalUploaderServer(properties);
    }


}
