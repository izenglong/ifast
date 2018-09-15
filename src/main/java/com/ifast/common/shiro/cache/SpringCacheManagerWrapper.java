package com.ifast.common.shiro.cache;

import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

@SuppressWarnings({"unchecked","rawtypes"})
public class SpringCacheManagerWrapper implements CacheManager {

    @Setter
    private org.springframework.cache.CacheManager cacheManager;

	@Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }
}
