package com.ifast.api.pojo.vo;

import lombok.Data;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
@Data
public class TokenVO {
    private String token;
    private Long tokenExpire;
    private String refleshToken;
    private Long refreshTokenExpire;

}
