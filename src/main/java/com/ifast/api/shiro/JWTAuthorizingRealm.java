package com.ifast.api.shiro;

import com.ifast.api.util.JWTUtil;
import com.ifast.sys.service.MenuService;
import com.ifast.sys.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collection;
import java.util.HashSet;


/**
 * <pre>
 * </pre>
 * <small> 2018年4月22日 | Aron</small>
 */
@Slf4j
@AllArgsConstructor
public class JWTAuthorizingRealm extends AuthorizingRealm {

    private final MenuService menuService;
    private final RoleService roleService;

    @Override
    public String getName() {
        return "jwtRealm";
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
        SimpleAuthorizationInfo authz = new SimpleAuthorizationInfo();
        Collection realm = principals.fromRealm(getName());
        if(realm.isEmpty()){
            return authz;
        }
        String jwt = (String) principals.getPrimaryPrincipal();
        if(log.isDebugEnabled()){
            log.debug(this.getName() + " get PrimaryPrincipal:" + jwt);
        }
        String userId = JWTUtil.getUserId(jwt);
        if(StringUtils.isBlank(userId)){
            return authz;
        }
        authz.setStringPermissions(menuService.listPerms(Long.parseLong(userId)));

        HashSet<String> roles = new HashSet<>();
        roleService.findListByUserId(userId).stream().forEach(bean -> roles.add(bean.getRoleSign()));
        authz.setRoles(roles);
        return authz;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(log.isDebugEnabled()){
            log.debug("token:" + token.getCredentials() + ", " + token.getPrincipal());
        }
        String credentials = (String) token.getCredentials();
        return new SimpleAuthenticationInfo(credentials, credentials, getName());
    }
}
