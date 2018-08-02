package com.ifast.common.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.ifast.common.annotation.Log;
import com.ifast.common.dao.LogDao;
import com.ifast.common.domain.LogDO;
import com.ifast.common.utils.HttpContextUtils;
import com.ifast.common.utils.IPUtils;
import com.ifast.common.utils.JSONUtils;
import com.ifast.common.utils.ShiroUtils;
import com.ifast.sys.domain.UserDO;

/**
 * <pre>
 * 日志切面
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Component
public class LogAspect {
    @Autowired
    private LogDao logMapper;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.ifast.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }
    
    @Pointcut("execution(public * com.ifast.*.controller.*.*(..))")
    public void logController(){}
    
    @Around("logController()")
    public Object controller(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("{} {} {} {}.{}{}", request.getMethod(), request.getRequestURI(), IPUtils.getIpAddr(request), point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));
        
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        
        log.info("result({}) {}", time, JSON.toJSONString(result));
        return result;
    }
    
    @Pointcut("execution(public * com.ifast.*.service.impl.*.*(..))")
    public void logService(){}
    
    @Around("logService()")
    public Object service(ProceedingJoinPoint point) throws Throwable {
    	log.info("call {}.{}{}", point.getTarget().getClass().getSimpleName(), point.getSignature().getName(), Arrays.toString(point.getArgs()));
    	
    	long beginTime = System.currentTimeMillis();
    	Object result = point.proceed();
    	long time = System.currentTimeMillis() - beginTime;
    	
    	log.info("result({}) {}", time, JSON.toJSONString(result));
    	return result;
    }

    /**
     * <pre>
     * 保存日志
     * </pre>
     * <small> 2018年3月22日 | Aron</small>
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONUtils.beanToJson(args[0]).substring(0, 4999);
            sysLog.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getSysUser();
        if (null == currUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId(-1L);
                sysLog.setUsername(sysLog.getParams());
            } else {
                sysLog.setUserId(-1L);
                sysLog.setUsername("获取用户信息为空");
            }
        } else {
            sysLog.setUserId(ShiroUtils.getUserId());
            sysLog.setUsername(ShiroUtils.getSysUser().getUsername());
        }
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logMapper.insert(sysLog);
    }
}
