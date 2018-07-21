package com.ifast.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ifast.common.base.CoreService;
import com.ifast.common.domain.Tree;
import com.ifast.sys.domain.MenuDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface MenuService extends CoreService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);
}
