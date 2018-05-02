package com.ifast.api.service.impl;

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
import com.ifast.common.type.EnumErrorCode;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
@Service
public class UserServiceImpl extends CoreServiceImpl<AppUserDao, AppUserDO> implements UserService {

    @Override
    public TokenVO getToken(String uname, String passwd) {

        AppUserDO user = findByUname(uname);
        if (null == user) {
            throw new IFastApiException(EnumErrorCode.apiUserLoginError.getCodeStr());
        }

        if (!user.getPasswd().equals(passwd)) {
            throw new IFastApiException(EnumErrorCode.apiUserLoginError.getCodeStr());
        }

        TokenVO vo = new TokenVO();
        vo.setToken(JWTUtil.sign(user.getId() + "", uname + passwd));
        vo.setRefleshToken("refleshToken");
        return vo;
    }

    @Override
    public AppUserDO findByUname(String uname) {
        AppUserDO userDO = new AppUserDO();
        userDO.setUname(uname);
        Wrapper<AppUserDO> wrapper = new EntityWrapper<AppUserDO>(userDO);
        AppUserDO bean = selectOne(wrapper);
        return bean;
    }

}
