package com.ifast.api.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifast.api.exception.IFastApiException;
import com.ifast.common.type.EnumErrorCode;

/**
 * <pre>
 * jwt工具类
 * </pre>
 * 
 * <small> 2018年4月28日 | Aron</small>
 */
public class JWTUtil {
	
	public static String mykey = "mykey";
	
    /**
     * <pre>
     * </pre>
     * 
     * <small> 2018年4月28日 | Aron</small>
     * 
     * @param token
     *            即jwt
     * @param userId
     *            用户id
     * @param secret
     *            用户的secret
     * @return
     */
    public static boolean verify(String token, String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(mykey, userId).build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException exception) {
            throw new ExpiredCredentialsException(EnumErrorCode.apiAuthorizationExpired.getMsg());
        }catch (InvalidClaimException exception2){
            throw new AuthenticationException(EnumErrorCode.apiAuthorizationInvalid.getMsg());
        }catch (Exception exception3){
            return false;
        }
    }

    /**
     * <pre>
     * 获得token中的信息无需secret解密也能获得
     * </pre>
     * 
     * <small> 2018年4月28日 | Aron</small>
     * 
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(mykey).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * <pre>
     * </pre>
     * 
     * <small> 2018年4月28日 | Aron</small>
     * 
     * @param userId
     * @param secret
     * @return
     */
    public static String sign(String userId, String secret, long expire) {
        try {
            Date date = new Date(System.currentTimeMillis() + expire);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withClaim(mykey, userId).withExpiresAt(date).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new IFastApiException(EnumErrorCode.apiAuthorizationSignFailed.getCodeStr());
        }
    }
}
