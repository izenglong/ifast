package com.ifast.wxmp.service;

import com.ifast.common.domain.Tree;
import com.ifast.wxmp.domain.MpMenuDO;
import com.ifast.common.base.CoreService;

/**
 * 
 * <pre>
 * 微信菜单表
 * </pre>
 * <small> 2018-10-19 15:47:16 | Aron</small>
 */
public interface MpMenuService extends CoreService<MpMenuDO> {

    Tree<MpMenuDO> getTree();

    void saveMenu(MpMenuDO mpMenu, String appId);
}
