package com.ifast.api.pojo.dto;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/28 11:04 | Aron</small>
 */
@Data
public class UserLogoutDTO {
    private String token;
    private String refreshToken;
}
