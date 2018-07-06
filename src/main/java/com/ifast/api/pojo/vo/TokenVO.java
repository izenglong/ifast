package com.ifast.api.pojo.vo;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
public class TokenVO {

    private String token;
    private Long tokenExpire;
    private String refleshToken;
    private Long refreshTokenExpire;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefleshToken() {
        return refleshToken;
    }

    public void setRefleshToken(String refleshToken) {
        this.refleshToken = refleshToken;
    }

	public Long getTokenExpire() {
		return tokenExpire;
	}

	public void setTokenExpire(Long tokenExpire) {
		this.tokenExpire = tokenExpire;
	}

	public Long getRefreshTokenExpire() {
		return refreshTokenExpire;
	}

	public void setRefreshTokenExpire(Long refreshTokenExpire) {
		this.refreshTokenExpire = refreshTokenExpire;
	}

	@Override
    public String toString() {
        return "TokenVO [token=" + token + ", tokenExpire=" + tokenExpire + ", refleshToken=" + refleshToken + ", refreshTokenExpire=" + refreshTokenExpire + "]";
    }
}
