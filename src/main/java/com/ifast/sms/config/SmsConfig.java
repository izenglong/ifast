package com.ifast.sms.config;

import com.ifast.common.config.CacheConfiguration;
import com.ifast.sms.support.SmsManager;
import com.ifast.sms.support.SmsSender;
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
public class SmsConfig {

    @Bean
    SmsManager smsManager(SmsSender sender, SmsBasicConfigProperties properties) {
        Cache cache = CacheConfiguration.dynaConfigCache(properties.getCacheKey(), properties.getCodeExpireTime());
        return new SmsManager(sender, properties, cache);
    }

}
