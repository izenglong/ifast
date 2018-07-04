package com.ifast.common.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ifast.common.utils.SpringContextHolder;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
        return new EhCacheCacheManager(bean.getObject()) {
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

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
    
    /** 动态配置ehcache缓存，示例：<br><code>Cache fiveMinutes = CacheConfiguration.dynaConfigCache("5min", 0, 300, 2000, 0);</code> */
    public static Cache dynaConfigCache(String name, long timeToIdleSeconds, long timeToLiveSeconds, int maxElementsInMemory) {
    	CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
    	net.sf.ehcache.Cache ehCacheCache = (net.sf.ehcache.Cache)cacheManager.getCache(name).getNativeCache();
    	net.sf.ehcache.config.CacheConfiguration cacheConfiguration = ehCacheCache.getCacheConfiguration();
    	cacheConfiguration.timeToIdleSeconds(timeToIdleSeconds).timeToLiveSeconds(timeToLiveSeconds).maxEntriesLocalHeap(maxElementsInMemory);
    	return cacheManager.getCache(name);
    }
}