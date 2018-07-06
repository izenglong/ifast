package com.ifast.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ifast.api.dao.AppUserDao;
import com.ifast.api.exception.IFastApiException;
import com.ifast.api.pojo.domain.AppUserDO;
import com.ifast.api.pojo.vo.TokenVO;
import com.ifast.api.service.UserService;
import com.ifast.api.util.JWTUtil;
import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.config.CacheConfiguration;
import com.ifast.common.config.IFastConfig;
import com.ifast.common.type.EnumErrorCode;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
@Service
public class UserServiceImpl extends CoreServiceImpl<AppUserDao, AppUserDO> implements UserService {
	@Autowired private IFastConfig ifastConfig;
	private Cache tokenExpires = null;

	@Override
    public TokenVO getToken(String uname, String passwd) {
        AppUserDO user = findByUname(uname);
        if (null == user) {
            throw new IFastApiException(EnumErrorCode.apiAuthorizationLoginFailed.getCodeStr());
        }

        if (!user.getPasswd().equals(passwd)) {
            throw new IFastApiException(EnumErrorCode.apiAuthorizationLoginFailed.getCodeStr());
        }

        return createToken(user);
    }

    @Override
	public TokenVO refreshToken(String uname, String refreshToken) {
    	String userId = JWTUtil.getUserId(refreshToken);
    	if(userId!=null && userId.equals(uname)) {
    		AppUserDO user = findByUname(uname);
    		if(user != null) {
				boolean verify = JWTUtil.verify(refreshToken, uname, user.getId() + user.getPasswd());
				if(verify && logoutToken(null, refreshToken)) {
					return createToken(user);
				}else if(verify) {
					throw new IFastApiException(EnumErrorCode.apiAuthorizationLoggedout.getCodeStr());
				}else {
					throw new IFastApiException(EnumErrorCode.apiAuthorizationFailed.getCodeStr());
				}
    		}
    	}else if(userId != null) {
    		throw new IFastApiException(EnumErrorCode.apiAuthorizationLoginFailed.getCodeStr());
    	}
    	throw new IFastApiException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
	}

	@Override
	public Boolean logoutToken(String token, String refreshToken) {
		Boolean expire = Boolean.FALSE;
		String userId = null, uname = null; AppUserDO user = null;
		if(token!=null && (userId=JWTUtil.getUserId(token))!=null) {
			user = selectById(userId);
			if(user!=null && JWTUtil.verify(token, user.getId() + "", user.getUname() + user.getPasswd())) {
				if(tokenExpires().putIfAbsent(token, null)==null) expire = Boolean.TRUE;
			}
		}
		if(refreshToken!=null && (uname=JWTUtil.getUserId(refreshToken))!=null) {
			if(user == null) user = findByUname(uname);
			if(user!=null && uname.equals(user.getUname()) && JWTUtil.verify(refreshToken, uname, user.getId() + user.getPasswd())) {
				if(tokenExpires().putIfAbsent(refreshToken, null)==null) expire = Boolean.TRUE;
			}
		}
		return expire;
	}

	@Override
	public boolean checkLogout(String token) {
		return tokenExpires().get(token)!=null;
	}

	@Override
    public AppUserDO findByUname(String uname) {
        AppUserDO userDO = new AppUserDO();
        userDO.setUname(uname);
        Wrapper<AppUserDO> wrapper = new EntityWrapper<AppUserDO>(userDO);
        AppUserDO bean = selectOne(wrapper);
        return bean;
    }

	private TokenVO createToken(AppUserDO user) {
        TokenVO vo = new TokenVO();
        vo.setToken(JWTUtil.sign(user.getId() + "", user.getUname() + user.getPasswd()));
        vo.setRefleshToken(JWTUtil.refreshToken(user.getUname(), user.getId() + user.getPasswd()));
        vo.setTokenExpire(ifastConfig.getJwt().getExpireTime());
        vo.setRefreshTokenExpire(ifastConfig.getJwt().getRefreshTokenExpire());
        return vo;
	}
	
	private Cache tokenExpires() {
		if(tokenExpires == null) tokenExpires = CacheConfiguration.dynaConfigCache("tokenExpires", 0, ifastConfig.getJwt().getRefreshTokenExpire(), 1000);
		return tokenExpires;
	}
}
