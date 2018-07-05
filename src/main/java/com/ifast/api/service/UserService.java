package com.ifast.api.service;

import com.ifast.api.pojo.domain.AppUserDO;
import com.ifast.api.pojo.vo.TokenVO;
import com.ifast.common.base.CoreService;

/**
 * <pre>
 * </pre>
 * <small> 2018年4月27日 | Aron</small>
 */
public interface UserService extends CoreService<AppUserDO> {
    /** 申请token */
    TokenVO getToken(String uname, String passwd) ;
    /** 刷新token */
    TokenVO refreshToken(String uname, String refreshToken);
    /** 注销token */
    Boolean expireToken(String token, String refreshToken);
    /** 检查token是否已注销 */
    boolean checkExpire(String token);
    AppUserDO findByUname(String uname);
}
