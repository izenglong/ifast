package com.ifast.wxmp.aspect;

import com.ifast.common.utils.HttpContextUtils;
import com.ifast.wxmp.util.WxMpConfigHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 切 wxmp下的controller，
 * 将参数appId设绑定为当前的微信SDK处理的公众号，保证微信API调用时，不会错乱
 * </pre>
 * <small> 2018/11/22 17:25 | Aron</small>
 */
@Aspect
@Component
@Slf4j
public class BoundAppIdAspect {

//    @Autowired
//    private MpConfigService mpConfigService;

    @Pointcut("execution(* com.ifast.wxmp.controller..*.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String appId = HttpContextUtils.getHttpServletRequest().getParameter("appId");

        checkAppId(appId);

        if(StringUtils.isNotBlank(appId)){
            if(log.isDebugEnabled()){
                log.debug("WxMpConfigHolder.setCurrentAppId(), appId : {}", appId);
            }
            WxMpConfigHolder.setCurrentAppId(appId);
        }
        Object result = point.proceed();
        return result;
    }

    private void checkAppId(String appId) {
//        List<MpConfigDO> mpConfigs = mpConfigService.findByKv("appId", appId);
//        if(mpConfigs.isEmpty()){
//            throw new IFastApiException(EnumErrorCode.apiWxMpAppIdError.getCodeStr());
//        }
    }

}
