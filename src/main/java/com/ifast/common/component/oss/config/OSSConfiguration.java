package com.ifast.common.component.oss.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ifast.common.service.ConfigService;
import com.ifast.common.component.oss.support.UploadServer;
import com.ifast.common.component.oss.support.aliyun.AliyunOSSProperties;
import com.ifast.common.component.oss.support.aliyun.AliyunUploadServer;
import com.ifast.common.component.oss.support.local.LocalUploadProperties;
import com.ifast.common.component.oss.support.local.LocalUploadServer;
import com.ifast.common.component.oss.support.qiniu.QiNiuProperties;
import com.ifast.common.component.oss.support.qiniu.QiNiuUploadServer;
import com.qiniu.common.Zone;

@Configuration
@EnableCaching
public class OSSConfiguration {

    @Autowired
    private ConfigService configService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 本地上传
     */
    @Bean
    @ConditionalOnProperty(prefix="ifast.oss.local", name="localPath")
    public UploadServer localUploadServer(LocalUploadProperties properties) {
    	if(log.isDebugEnabled()){
    		log.debug("启用本地上传服务");
    	}
        return new LocalUploadServer(properties);
    }
    
    /**
     * 阿里云OSS上传
     */
    @Bean
    @ConditionalOnProperty(prefix="ifast.oss.aliyun", name="accessKeySecret")
    public UploadServer aliyunUploadServer(AliyunOSSProperties properties) {
    	if(log.isDebugEnabled()){
    		log.debug("启用阿里云上传服务");
    	}
    	return new AliyunUploadServer(properties);
    }

    /**
     * 七牛上传
     */
    @Bean
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer qiNiuUploadServer() {
    	if(log.isDebugEnabled()){
    		log.debug("启用七牛云上传服务");
    	}
        QiNiuProperties ossConfig = configService.getOssConfigProperties();
        return new QiNiuUploadServer(ossConfig, Zone.zone2());
    }
}
