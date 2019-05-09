package com.ifast.api.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
public class JWTAuthenticationTokenToken implements AuthenticationToken {

    private static final long serialVersionUID = 5320481558604267958L;
    /**
     * 密钥
     */
    private String token;

    public JWTAuthenticationTokenToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        if(StringUtils.isBlank(token)){
            return "";
        }
        return token;
    }

    @Override
    public String getCredentials() {
        return token;
    }
}