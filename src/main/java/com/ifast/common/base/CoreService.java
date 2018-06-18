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
    List<T> findByKv(Object... param);

    T findOneByKv(Object... param);

    /**
     * <pre>
     *
     * </pre>
     *
     * <small> 2018/6/14 17:32 | Aron</small>
     * @param [clazz, param]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     *
     */

    Map<String, Object> convertToMap(Object... param);

    /**
     * <pre>
     *
     * </pre>
     *
     * <small> 2018/6/14 17:14 | Aron</small>
     * @param [clazz, params]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     *
     */
    EntityWrapper<T> convertToEntityWrapper(Object... params);

}
