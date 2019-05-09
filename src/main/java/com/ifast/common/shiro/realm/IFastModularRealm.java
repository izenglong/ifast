package com.ifast.common.shiro.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * <pre>
 * </pre>
 * <small> 2018年5月1日 | Aron</small>
 */
@Slf4j
public class IFastModularRealm extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {
        if (!realm.supports(token)) {
            throw new UnsupportedTokenException("不支持的token类型");
        }

        AuthenticationInfo info = realm.getAuthenticationInfo(token);
        if (info == null) {
            throw new UnknownAccountException("token无效");
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

            if (realm.supports(token)) {
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

        try {
            aggregate = strategy.afterAllAttempts(token, aggregate);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        if (ex != null){
            throw ex;
        }

        return aggregate;
    }

}