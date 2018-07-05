package com.ifast.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public TokenVO getToken(String uname, String passwd) {

        AppUserDO user = findByUname(uname);
        if (null == user) {
            throw new IFastApiException(EnumErrorCode.apiUserLoginError.getCodeStr());
        }

        if (!user.getPasswd().equals(passwd)) {
            throw new IFastApiException(EnumErrorCode.apiUserLoginError.getCodeStr());
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
				if(verify) return createToken(user);
    		}
    	}
    	throw new IFastApiException(EnumErrorCode.apiAuthorizationInvalid.getCodeStr());
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
}
