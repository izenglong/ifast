package com.ifast.common.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 通用业务层实现
 * </pre>
 * 
 * <small> 2018年1月9日 | Aron</small>
 * 
 * @param <T>
 */
public interface CoreService<T> extends IService<T> {

    @Deprecated
    List<T> findByKv(Object... param);

    @Deprecated
    T findOneByKv(Object... param);

    /**
     * <pre>
     * </pre>
     * <small> 2018/6/14 17:32 | Aron</small>
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Deprecated
    Map<String, Object> convertToMap(Object... param);

    /**
     * <pre>
     * </pre>
     * <small> 2018/6/14 17:14 | Aron</small>
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Deprecated
    EntityWrapper<T> convertToEntityWrapper(Object... params);

    /**
     * 根据实体查询
     * @param t
     * @return
     */
    T selectOne(T t);
}
