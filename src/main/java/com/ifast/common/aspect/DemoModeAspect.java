package com.ifast.common.aspect;

import com.ifast.common.config.IFastProperties;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.HttpContextUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 演示模式切面处理
 * </pre>
 * 
 * <small> 2018年3月22日 | Aron</small>
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "ifast", name = "demoModel", havingValue = "true")
public class DemoModeAspect {

    private static final Logger log = LoggerFactory.getLogger(DemoModeAspect.class);

    @Autowired
    private IFastProperties config;

    @Pointcut("execution(* com.ifast.*.controller.*.*(..))")
    public void testUserPointCut() {
    }

    @Around("testUserPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        if (config.isDemoMode()) {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            RequiresPermissions rp = method.getAnnotation(RequiresPermissions.class);
            if (rp != null) {
                String[] value = rp.value();
                List<String> asList = Arrays.asList(value);
                String string = asList.toString();
                if (!HttpContextUtils.getHttpServletRequest().getMethod().equalsIgnoreCase("GET")
                        && (string.contains("remove") || string.contains("Remove") || string.contains("edit")
                                || string.contains("update") || string.contains("delete"))) {
                    log.error("当前用户未演示账户无权操作！");
                    throw new IFastException(EnumErrorCode.notAuthorization.getCodeStr());
                }
            }
        }
        Object result = point.proceed();
        return result;
    }

}
