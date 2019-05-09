package com.ifast.api.shiro;

import com.ifast.api.service.impl.AppUserServiceImpl;
import com.ifast.api.util.JWTUtil;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.JSONUtils;
import com.ifast.common.utils.Result;
import com.ifast.sys.domain.UserDO;
import com.ifast.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;


/**
 * <pre>
 * </pre>
 * <small> 2018年4月22日 | Aron</small>
 */
@Slf4j
public class JWTAuthenticationFilter extends AuthenticatingFilter {

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final UserService userService;

    public JWTAuthenticationFilter(UserService userService) {
        this(userService, null);
    }


    public JWTAuthenticationFilter(UserService userService, String loginUrl) {
        this.userService = userService;
        if (StringUtils.isBlank(loginUrl)) {
            loginUrl = DEFAULT_LOGIN_URL;
        }
        this.setLoginUrl(loginUrl);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        }

        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) {
            log.error("token无效", e);
        } catch (Exception e) {
            log.error("登录失败", e);
        }
        return allowed || super.isPermissive(mappedValue);

    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        String jwt = WebUtils.toHttp(request).getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.isNotBlank(jwt) && !JWTUtil.isTokenExpired(jwt)) {
            return new JWTAuthenticationTokenToken(jwt);
        }
        return null;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = WebUtils.toHttp(response);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        Writer writer = null;
        try {
            writer= resp.getWriter();
            writer.write(JSONUtils.beanToJson(Result.build(EnumErrorCode.apiAuthorizationInvalid.getCode(), EnumErrorCode.apiAuthorizationInvalid.getMsg())));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(Objects.nonNull(writer)){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTAuthenticationTokenToken) {
            JWTAuthenticationTokenToken jwt = (JWTAuthenticationTokenToken) token;
            UserDO user = userService.selectById(Long.parseLong(JWTUtil.getUserId(jwt.getPrincipal())));
            newToken = JWTUtil.sign(user.getId() + "", user.getUsername() + user.getPassword(), AppUserServiceImpl.Holder.jwtConfig.getExpireTime());
        }
        if (StringUtils.isNotBlank(newToken)) {
            httpResponse.setHeader(HEADER_AUTHORIZATION, newToken);
        }
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
    }
}
