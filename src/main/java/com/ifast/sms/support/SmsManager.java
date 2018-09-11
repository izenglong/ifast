package com.ifast.sms.support;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.sms.config.SmsBasicProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.util.Random;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/31 16:45 | Aron</small>
 */
public class SmsManager implements SmsVerify {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private SmsSender smsSender;
    private SmsBasicProperties properties;
    private Cache cache;

    public SmsManager(){}

    public SmsManager(SmsSender sender, SmsBasicProperties properties, Cache cache){
        this.smsSender = sender;
        this.properties = properties;
        this.cache = cache;

    }

    public void send(String mobile, String scene) {
        String code = getCode();
        smsSender.send(mobile, code, scene);
        if (StringUtils.isNotBlank(code)) {
            String key = properties.getCacheKeyPrefix() + ":" + mobile;

            // 加入缓存
            if(log.isDebugEnabled()){
                log.debug("【SMS】cache.put({}, {})", properties.getCacheKey() + ":" + key, code);
            }
            cache.put(key, code);
        }
    }

    @Override
    public void verify(String mobile, String code) {
        String key = properties.getCacheKeyPrefix() + ":" + mobile;
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null || !valueWrapper.get().equals(code)) {
            throw new IFastApiException(EnumErrorCode.apiSmsCodeInvalid.getCodeStr());
        }

        // 移除缓存
        if(log.isDebugEnabled()){
            log.debug("【SMS】cache.evict({}, {})", properties.getCacheKey() + ":" + key, code);
        }

        cache.evict(key);
    }

    /**
     * <pre>
     * 生成随机码
     * </pre>
     */
    public String getCode(){
        return new Random().nextInt(8999) + 1000 + "";
    }
}
