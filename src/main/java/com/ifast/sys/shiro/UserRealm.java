package com.ifast.sys.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.ifast.common.utils.ShiroUtils;
import com.ifast.common.utils.SpringContextHolder;
import com.ifast.sys.dao.UserDao;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.MenuService;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
public class UserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Long userId = ShiroUtils.getUserId();
        MenuService menuService = SpringContextHolder.getBean(MenuService.class);
        Set<String> permsSet = menuService.listPerms(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        UserDao userMapper = SpringContextHolder.getBean(UserDao.class);
        UserDO entity = new UserDO();
        entity.setUsername(username);
        // 查询用户信息
        UserDO user = userMapper.selectOne(entity );

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
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

}
