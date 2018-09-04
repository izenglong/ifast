package com.ifast.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 系统日志注解
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
	String value() default "";
}
