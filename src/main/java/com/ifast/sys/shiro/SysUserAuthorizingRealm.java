package com.ifast.sys.shiro;

import com.ifast.common.utils.ShiroUtils;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.MenuService;
import com.ifast.sys.service.RoleService;
import com.ifast.sys.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Slf4j
@AllArgsConstructor
public class SysUserAuthorizingRealm extends AuthorizingRealm {

    private final MenuService menuService;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authz = new SimpleAuthorizationInfo();
        Collection realm = principals.fromRealm(getName());
        if(realm.isEmpty()){
            return authz;
        }
        Long userId = ShiroUtils.getUserId();
        Set<String> permsSet = menuService.listPerms(userId);

        authz.setStringPermissions(permsSet);

        HashSet<String> roles = new HashSet<>();
        roleService.findListByUserId(userId).forEach(bean -> roles.add(bean.getRoleSign()));
        authz.setRoles(roles);
        return authz;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        if(log.isDebugEnabled()){
            log.debug("token:" + token.getCredentials() + ", " + token.getPrincipal());
        }

        String username = (String) token.getPrincipal();

        // 查询用户信息
        UserDO user = userService.findOneByKv("username", username);
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        ByteSource salt = new Sha256Hash(user.getSalt());

        return new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
    }

}
