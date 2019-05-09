package com.ifast.wxmp.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ifast.common.utils.JSONUtils;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.pojo.type.Const;
import com.ifast.wxmp.service.MpConfigService;
import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

/**
 * <pre>
 * </pre>
 *
 * <small> 2018年6月13日 | Aron</small>
 */
@Component
public class WxMpConfigHolder implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(WxMpConfigHolder.class);

    @Autowired
    private MpConfigService mpConfigService;

    private static final Hashtable<String, WxMpInMemoryConfigStorage> mpConfigs = new Hashtable<>();

    private static final ThreadLocal<String> currentAppId = new ThreadLocal<>();

    public static void setCurrentAppId(String aliasName) {
        currentAppId.set(aliasName);
    }

    public static String getCurrentAppId() {
        return currentAppId.get();
    }

    public static WxMpInMemoryConfigStorage getWxMpInMemoryConfigStorage() {
        return mpConfigs.get(currentAppId.get());
    }

    @Override
    public void afterPropertiesSet() {
        mpConfigService.selectList(
                new EntityWrapper<>(MpConfigDO.builder().appType(Const.appType.SERIVCE).build()))
                .forEach(bean -> {
                    WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
                    // 设置微信公众号的appid
                    config.setAppId(bean.getAppId());
                    // 设置微信公众号的app corpSecret
                    config.setSecret(bean.getAppSecret());
                    // 设置微信公众号的token
                    config.setToken(bean.getToken());
                    // 设置消息加解密密钥
                    config.setAesKey(bean.getMsgSecret());

                    config.setApacheHttpClientBuilder(DefaultApacheHttpClientBuilder.get());

                    log.debug("公众号配置初始化：{}", JSONUtils.beanToJson(config));
                    mpConfigs.put(bean.getAppId(), config);

                });
    }
}
