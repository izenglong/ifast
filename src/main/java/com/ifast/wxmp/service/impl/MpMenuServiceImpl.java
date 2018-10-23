package com.ifast.wxmp.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.domain.Tree;
import com.ifast.common.utils.BuildTree;
import com.ifast.wxmp.dao.MpMenuDao;
import com.ifast.wxmp.domain.MpMenuDO;
import com.ifast.wxmp.service.MpMenuService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 微信菜单表
 * </pre>
 * <small> 2018-10-19 15:47:16 | Aron</small>
 */
@Service
public class MpMenuServiceImpl extends CoreServiceImpl<MpMenuDao, MpMenuDO> implements MpMenuService {

    @Override
    public Tree<MpMenuDO> getTree() {
        List<Tree<MpMenuDO>> nodes = baseMapper.selectList(null).stream().map(menu -> {
            Tree<MpMenuDO> node = new Tree<>();
            node.setId(menu.getId() + "");
            node.setText(menu.getMenuname());
            node.setParentId(menu.getParentidx() + "");
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            node.setState(state);
            return node;
        }).collect(Collectors.toList());


        return BuildTree.build(nodes);
    }
}
