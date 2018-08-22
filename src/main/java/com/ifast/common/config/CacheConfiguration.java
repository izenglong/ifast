package com.ifast.common.config;

import com.ifast.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存配置
 * 策略:
 * 1. 默认ehcache
 * 2. 如果配置spring.redis.host 则使用redis
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

	private static Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

	/** 配置spring.redis.host时使用RedisCacheManager，否则使用EhCacheCacheManager */
	@Bean
    @ConditionalOnProperty(prefix="spring.redis", name="host", havingValue="false", matchIfMissing=true)
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

    @Bean
    @ConditionalOnBean(EhCacheManagerFactoryBean.class)
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject()) {
			@Override
			protected Cache getMissingCache(String name) {
				Cache cache = super.getMissingCache(name);
				if (cache == null) {
					//使用default配置克隆缓存
					getCacheManager().addCacheIfAbsent(name);
					cache = super.getCache(name);
				}
				return cache;
			}
        };
    }

    /**
     *  动态配置缓存，
     *  示例：<code>Cache fiveMinutes = CacheConfiguration.dynaConfigCache("5min", 300);</code>
     */
    @SuppressWarnings("unchecked")
    public static Cache dynaConfigCache(String name, long timeToLiveSeconds) {
    	CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
    	if(cacheManager instanceof RedisCacheManager) {
    		if(log.isDebugEnabled()){
				log.debug("使用RedisCacheManager");
			}
			Field expiresField = ReflectionUtils.findField(RedisCacheManager.class, "expires");
			ReflectionUtils.makeAccessible(expiresField);
			Map<String, Long> expires = (Map<String, Long>)ReflectionUtils.getField(expiresField, cacheManager);
			if(expires == null) {
				ReflectionUtils.setField(expiresField, cacheManager, expires = new HashMap<>());
			}
			expires.put(name, timeToLiveSeconds);
		}else if(cacheManager instanceof EhCacheCacheManager) {
			if(log.isDebugEnabled()){
				log.debug("使用EhCacheCacheManager");
			}
			net.sf.ehcache.Cache ehCacheCache = (net.sf.ehcache.Cache)cacheManager.getCache(name).getNativeCache();
    		net.sf.ehcache.config.CacheConfiguration cacheConfiguration = ehCacheCache.getCacheConfiguration();
    		cacheConfiguration.timeToLiveSeconds(timeToLiveSeconds);
    	}
    	return cacheManager.getCache(name);
    }
}