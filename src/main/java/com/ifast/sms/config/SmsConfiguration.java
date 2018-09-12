package com.ifast.sms.config;

import com.ifast.common.config.CacheConfiguration;
import com.ifast.sms.sender.AliyunSender;
import com.ifast.sms.sender.ZhuTongSender;
import com.ifast.sms.support.SmsManager;
import com.ifast.sms.support.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/31 19:37 | Aron</small>
 */
@Configuration
public class SmsConfiguration {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    SmsManager smsManager(SmsSender sender, SmsBasicProperties properties) {
        Cache cache = CacheConfiguration.dynaConfigCache(properties.getCacheKey(), properties.getCodeExpireTime());
        return new SmsManager(sender, properties, cache);
    }

    @Bean
    @ConditionalOnProperty(prefix="ifast.sms.zt", name="passwd", matchIfMissing=false)
    SmsSender zhuTongSender(ZhuTongProperties properties){
        if(log.isDebugEnabled()){
            log.debug("启用上海助通短信服务");
        }
        SmsSender sender = new ZhuTongSender(properties);
        return sender;
    }
    
    @Bean
    @ConditionalOnProperty(prefix="ifast.sms.aliyun", name="accessKeySecret", matchIfMissing=false)
    SmsSender aliyunSender(AliyunProperties properties){
    	if(log.isDebugEnabled()){
    		log.debug("启用阿里云短信服务");
    	}
    	SmsSender sender = new AliyunSender(properties);
    	return sender;
    }

}
