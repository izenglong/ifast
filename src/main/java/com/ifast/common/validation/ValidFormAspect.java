package com.ifast.common.validation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 全局表单自动验证
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Aspect
@Component
public class ValidFormAspect {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.ifast..controller.*.*(..))")
    public void validFormAspect() {
    }

    @Around("validFormAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        for(Object arg : args){
            ValidForm validForm = arg.getClass().getAnnotation(ValidForm.class);
            if(validForm != null){
                ValidationResult validationResult = ValidateUtils.validateEntity(arg);
                if(validationResult.isHasErrors()){
                    throw new IllegalArgumentException(validationResult.toString());
                }
            }
        }
        Object result = point.proceed();
        return result;
    }


    

}
