package com.ifast.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.IService;
import com.ifast.common.domain.Tree;
import com.ifast.sys.domain.MenuDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface MenuService extends IService<MenuDO> {
    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);
}
