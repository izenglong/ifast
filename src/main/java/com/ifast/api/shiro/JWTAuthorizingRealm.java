package com.ifast.api.shiro;

import com.ifast.api.util.JWTUtil;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.sys.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <pre>
 * </pre>
 * <small> 2018年4月22日 | Aron</small>
 */
@Service
public class JWTAuthorizingRealm extends AuthorizingRealm {

    private  Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    @Override
    public String getName() {
        return "apiRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTAuthenticationTokenToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String jwt = (String) principals.getPrimaryPrincipal();
        if(log.isDebugEnabled()){
            log.debug("jwt:" + jwt);
        }
        SimpleAuthorizationInfo authz = new SimpleAuthorizationInfo();
        String userId = JWTUtil.getUserId(jwt);
        if(StringUtils.isBlank(userId)){
            throw new IFastException(EnumErrorCode.apiAuthorizationFailed.getCodeStr());
        }
        authz.setStringPermissions(menuService.listPerms(Long.parseLong(userId)));
        return authz;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authc) throws AuthenticationException {
        if(log.isDebugEnabled()){
            log.debug("authc:" + authc.getCredentials() + ", " + authc.getPrincipal());
        }
        String token = (String) authc.getCredentials();
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
