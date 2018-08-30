package com.ifast.sys.shiro;

import com.ifast.common.utils.ShiroUtils;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.MenuService;
import com.ifast.sys.service.RoleService;
import com.ifast.sys.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Component
public class SysUserAuthorizingRealm extends AuthorizingRealm {
    
    private final static Logger log = LoggerFactory.getLogger(SysUserAuthorizingRealm.class);

    private final MenuService menuService;
    private final RoleService roleService;
    private final UserService userService;

    @Autowired public SysUserAuthorizingRealm(MenuService menuService, RoleService roleService,
            UserService userService) {
        this.menuService = menuService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object next = principals.getPrimaryPrincipal();
        log.debug("auth class:" + next.getClass());
        SimpleAuthorizationInfo authz = null;
        if(next instanceof UserDO) { // 避免授权报错
            Long userId = ShiroUtils.getUserId();
            Set<String> permsSet = menuService.listPerms(userId);
            authz = new SimpleAuthorizationInfo();
            authz.setStringPermissions(permsSet);


            HashSet<String> roles = new HashSet<>();
            roleService.findListByUserId(userId).forEach(bean -> roles.add(bean.getRoleSign()));
            authz.setRoles(roles);
        }
        return authz;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(!supports(token)) {
            return null;
        }
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 查询用户信息
        UserDO user = userService.findOneByKv("username", username);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        // 账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(user, password, getName());
    }

}
