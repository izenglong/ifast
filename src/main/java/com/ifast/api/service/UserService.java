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
    
    TokenVO getToken(String uname, String passwd) ;
    AppUserDO findByUname(String uname);
}
