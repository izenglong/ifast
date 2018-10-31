package com.ifast.wxmp.service.impl;

import com.ifast.common.base.CoreServiceImpl;
import com.ifast.common.domain.Tree;
import com.ifast.common.exception.IFastException;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.BuildTree;
import com.ifast.wxmp.dao.MpMenuDao;
import com.ifast.wxmp.domain.MpMenuDO;
import com.ifast.wxmp.service.MpMenuService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    private static final int MAIN_MENU_SIZE = 3;
    private static final int SUB_MENU_SIZE = 5;

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

    @Override
    public void saveMenu(MpMenuDO mpMenu) {
        // 主菜单不能超过3个
        Long parentIdx = mpMenu.getParentidx();
        if(Objects.isNull(parentIdx) || parentIdx.equals(0L)){
            int count = this.selectCount(convertToEntityWrapper("parentidx", parentIdx));
            if(count >= MAIN_MENU_SIZE){
                throw new IFastException(EnumErrorCode.wxmpMenuSaveMainError.getCodeStr());
            }
        }else{
            // 子菜单不能超过5个
            int count = this.selectCount(convertToEntityWrapper("parentidx", parentIdx));
            if(count >= SUB_MENU_SIZE){
                throw new IFastException(EnumErrorCode.wxmpMenuSaveSubError.getCodeStr());
            }

        }
        insert(mpMenu);

    }
}
