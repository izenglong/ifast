package com.ifast.common.shiro.cache;

import net.sf.ehcache.Ehcache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.*;

/**
 * <pre>
 *
 * </pre>
 */
public class SpringCacheWrapper implements Cache {
    private final static Logger log = LoggerFactory.getLogger(SpringCacheWrapper.class);
    private org.springframework.cache.Cache springCache;
    private boolean isEhcache;
    private Set<Object> keys;

    SpringCacheWrapper(org.springframework.cache.Cache springCache) {
        if(log.isDebugEnabled()){
            log.debug("spring cache Class init：" + springCache.getClass());
        }
        this.springCache = springCache;
        this.isEhcache = springCache.getNativeCache() instanceof Ehcache;
        if(!this.isEhcache) {
            keys = new HashSet<>();
        }
    }

    @Override
    public Object get(Object key) throws CacheException {
        Object value = springCache.get(isEhcache ? key : key.toString());
        if (value instanceof SimpleValueWrapper) {
            return ((SimpleValueWrapper) value).get();
        }
        return value;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        springCache.put(isEhcache ? key : key.toString(), value);
        if(!isEhcache) {
            keys.add(key.toString());
        }
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        springCache.evict(isEhcache ? key : key.toString());
        if(!isEhcache) {
            keys.remove(key.toString());
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        springCache.clear();
        if(!isEhcache) {
            keys.clear();
        }
    }

    @Override
    public int size() {
        if(isEhcache) {
            Ehcache ehcache = (Ehcache) springCache.getNativeCache();
            return ehcache.getSize();
        }else {
            return keys.size();
        }
    }

    @Override
    public Set keys() {
        if(isEhcache) {
            Ehcache ehcache = (Ehcache) springCache.getNativeCache();
            return new HashSet<Object>(ehcache.getKeys());
        }else {
            return keys;
        }
    }

    @Override
    public Collection values() {
        if (isEhcache) {
            Ehcache ehcache = (Ehcache) springCache.getNativeCache();
            List keys = ehcache.getKeys();
            if (!CollectionUtils.isEmpty(keys)) {
                List<Object> values = new ArrayList<>(keys.size());
                for (Object key : keys) {
                    Object value = get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } else {
            List<Object> values = new ArrayList<>(keys.size());
            for (Object key : keys) {
                Object value = get(key);
                values.add(value);
            }
            return Collections.unmodifiableCollection(values);
        }
    }
}
