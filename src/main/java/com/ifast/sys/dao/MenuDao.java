package com.ifast.sys.dao;

import java.util.List;

import com.ifast.common.base.BaseDao;
import com.ifast.sys.domain.MenuDO;

/**
 * <pre>
 * 菜单管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface MenuDao extends BaseDao<MenuDO> {
	
	List<MenuDO> listMenuByUserId(Long id);
	
	List<String> listUserPerms(Long id);
}
