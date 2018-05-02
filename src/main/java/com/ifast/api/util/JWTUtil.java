package com.ifast.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifast.api.exception.IFastApiException;
import com.ifast.common.config.IFastConfig;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.SpringContextHolder;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <pre>
 * jwt工具类
 * </pre>
 * 
 * <small> 2018年4月28日 | Aron</small>
 */
public class JWTUtil {

    private static IFastConfig ifastConfig = SpringContextHolder.getBean(IFastConfig.class);

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
            JWTVerifier verifier = JWT.require(algorithm).withClaim(ifastConfig.getJwt().getUserPrimaryKey(), userId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException exception) {
            throw new IFastApiException(EnumErrorCode.apiAuthorizationOutOfTime.getCodeStr());
        }catch (InvalidClaimException exception2){
            throw new IFastApiException(EnumErrorCode.apiAuthorizationHeaderInvalid.getCodeStr());
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
            return jwt.getClaim(ifastConfig.getJwt().getUserPrimaryKey()).asString();
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
    public static String sign(String userId, String secret) {
        try {
            Date date = new Date(System.currentTimeMillis() + ifastConfig.getJwt().getExpireTime());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create().withClaim(ifastConfig.getJwt().getUserPrimaryKey(), userId).withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new IFastApiException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
        }
    }
}
