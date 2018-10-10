package com.ifast.common.component.sms.support;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.component.sms.config.SmsBasicProperties;
import com.ifast.common.type.EnumErrorCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/31 16:45 | Aron</small>
 */
@Data
public class SmsManager implements SmsVerify {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private SmsSender smsSender;
    private SmsBasicProperties properties;
    private Cache cache;
    private CodeGenerator codeGenerator = new DefaultCodeGenerator();
    private SceneRepository sceneRepository;


    public SmsManager(){}

    public SmsManager(SmsSender sender, SmsBasicProperties properties, Cache cache, SceneRepository sceneRepository){
        this.smsSender = sender;
        this.properties = properties;
        this.cache = cache;
        this.sceneRepository = sceneRepository;
    }

    public void send(String mobile, String scene) {
        String code = codeGenerator.getCode();
        String content = sceneRepository.getContent(scene);
        smsSender.send(mobile, code, content);

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

}
