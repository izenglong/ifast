package com.ifast.common.shiro.realm;

import com.ifast.api.shiro.JWTAuthenticationTokenToken;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * <pre>
 * </pre>
 * <small> 2018年5月1日 | Aron</small>
 */
public class IFastModularRealm extends ModularRealmAuthenticator {

    private static final Logger log = LoggerFactory.getLogger(IFastModularRealm.class);
    
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {
        if (!realm.supports(token)) {
            throw new UnsupportedTokenException("不支持的token类型") ;
        }
        
        AuthenticationInfo info;
        try {
            info = realm.getAuthenticationInfo(token);
            if (info == null) {
                throw new ShiroException("token不存在!");
            }
        } catch (Exception e) {
            throw new ShiroException("用户名或者密码错误!");
        }
        
        return info;
    }
    
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {

        AuthenticationStrategy strategy = getAuthenticationStrategy();

        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }
        AuthenticationException ex = null;
        for (Realm realm : realms) {
            aggregate = strategy.beforeAttempt(realm, token, aggregate);

            if (realm.supports(token) && token instanceof JWTAuthenticationTokenToken) {
                log.debug("-API doMultiRealmAuthentication");
                AuthenticationInfo info = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                    ex = e;
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, ex);

            }else if (realm.supports(token) && token instanceof UsernamePasswordToken) {
                log.debug("-ADMIN doMultiRealmAuthentication");
                AuthenticationInfo info = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                    ex = e;
                }

                aggregate = strategy.afterAttempt(realm, token, info, aggregate, ex);

            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        try{
        	aggregate = strategy.afterAllAttempts(token, aggregate);
        }catch (Exception e) {
			log.debug(e.getMessage());
		}

        if(ex != null) throw ex;
        
        return aggregate;
    }

}