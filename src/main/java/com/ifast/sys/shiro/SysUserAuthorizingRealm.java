package com.ifast.sys.shiro;

import com.ifast.common.utils.ShiroUtils;
import com.ifast.common.utils.SpringContextHolder;
import com.ifast.sys.dao.UserDao;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.MenuService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
public class SysUserAuthorizingRealm extends AuthorizingRealm {
    
    private final static Logger log = LoggerFactory.getLogger(SysUserAuthorizingRealm.class);
    
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object next = principals.getPrimaryPrincipal();
        log.debug("next class:" + next.getClass());
        SimpleAuthorizationInfo info = null;
        if(next instanceof UserDO) { // 避免授权报错
            Long userId = ShiroUtils.getUserId();
            MenuService menuService = SpringContextHolder.getBean(MenuService.class);
            Set<String> permsSet = menuService.listPerms(userId);
            info = new SimpleAuthorizationInfo();
            info.setStringPermissions(permsSet);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if(!supports(token)) {
            return null;
        }
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
