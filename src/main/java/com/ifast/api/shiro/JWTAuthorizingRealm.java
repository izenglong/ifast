package com.ifast.api.shiro;

import com.ifast.api.pojo.domain.AppUserDO;
import com.ifast.api.service.UserService;
import com.ifast.api.util.JWTUtil;
import com.ifast.common.type.EnumErrorCode;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月22日 | Aron</small>
 */
@Service
public class JWTAuthorizingRealm extends AuthorizingRealm {

    private static final Logger log = LogManager.getLogger(JWTAuthorizingRealm.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        log.debug("==> jwt realm doGetAuthorizationInfo");
        Object principal = principals.getPrimaryPrincipal();
        log.debug("next class:" + principal.getClass());
        SimpleAuthorizationInfo simpleAuthorizationInfo = null;
        if (principal instanceof String) {
            simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRole("apiRole");
            Set<String> permission = new HashSet<>(Arrays.asList("api:user:update,select,add,del".split(",")));
            simpleAuthorizationInfo.addStringPermissions(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.debug("==> jwt realm doGetAuthenticationInfo");
        if (!supports(auth)) {
            return null;
        }
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new IncorrectCredentialsException(EnumErrorCode.apiAuthorizationHeaderInvalid.getCodeStr());
        }

        AppUserDO userDO = userService.selectById(userId);
        if (userDO == null) {
            throw new IncorrectCredentialsException(EnumErrorCode.apiAuthorizationHeaderInvalid.getCodeStr());
        }

        if (!JWTUtil.verify(token, userDO.getId() + "", userDO.getUname() + userDO.getPasswd())) {
            throw new IncorrectCredentialsException(EnumErrorCode.apiAuthorizationHeaderInvalid.getCodeStr());
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
