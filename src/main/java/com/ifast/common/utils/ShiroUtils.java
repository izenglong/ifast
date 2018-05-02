package com.ifast.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ifast.api.dao.AppUserDao;
import com.ifast.api.pojo.domain.AppUserDO;
import com.ifast.api.util.JWTUtil;
import com.ifast.sys.domain.UserDO;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	
	// api
	private final static AppUserDao appUserDao = SpringContextHolder.getBean(AppUserDao.class);
	public static AppUserDO getAppUserDO() {
	    String jwt = (String)getSubjct().getPrincipal();
	    String userId = JWTUtil.getUserId(jwt);
	    return appUserDao.selectById(userId);
	}
	
	// admin
	public static UserDO getSysUser() {
		return (UserDO)getSubjct().getPrincipal();
	}
	public static Long getUserId() {
		return getSysUser().getId();
	}
	
	public static void logout() {
		getSubjct().logout();
	}
}
