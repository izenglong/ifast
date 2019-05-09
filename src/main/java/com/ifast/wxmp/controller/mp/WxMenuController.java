package com.ifast.wxmp.controller.mp;

import com.ifast.api.exception.IFastApiException;
import com.ifast.common.base.BaseController;
import com.ifast.common.utils.JSONUtils;
import com.ifast.common.utils.Result;
import com.ifast.wxmp.domain.MpMenuDO;
import com.ifast.wxmp.pojo.type.Const;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.wxmp.service.MpMenuService;
import com.ifast.wxmp.service.WeixinService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * </pre>
 *
 * @Author Aron
 * @Date 2018/5/3
 */
@RestController
@RequestMapping("/wx/mp/menu/api")
@Slf4j
public class WxMenuController extends BaseController {

    @Autowired
    private WeixinService wxService;
    @Autowired
    private MpMenuService mpMenuService;
    @Autowired
    private MpConfigService mpConfigService;

    @PostMapping("/sync")
    public Result sync(String appId) {

        log.info("菜单同步");
        List<WxMenuButton> buttons = mpMenuService.findByKv("mpId", mpConfigService.findOneByKv("appId", appId).getId()).stream().filter(menu -> menu.getParentidx() == 0L).sorted(Comparator.comparing(MpMenuDO::getParentidx).thenComparing(MpMenuDO::getSort)).map(menuDO -> {
            // 一级菜单
            WxMenuButton button = new WxMenuButton();
            String menuType = convertMenuType(menuDO.getMenutype());
            button.setType(menuType);
            button.setName(menuDO.getMenuname());

            // click
            if (StringUtils.isNotBlank(menuDO.getMenukey())) {
                button.setKey(menuDO.getMenukey());
            }
            // 链接
            if (WxConsts.MenuButtonType.VIEW.equals(menuType)) {
                button.setUrl(menuDO.getMenuurl());
            }

            // 小程序

            List<WxMenuButton> subButtonList = mpMenuService.findByKv("parentidx", menuDO.getId()).stream().map(menu -> {
                WxMenuButton subButton = new WxMenuButton();
                String subMenuType = convertMenuType(menu.getMenutype());
                subButton.setType(subMenuType);
                subButton.setName(menu.getMenuname());
                // click
                if (StringUtils.isNotBlank(menu.getMenukey())) {
                    subButton.setKey(menu.getMenukey());
                }
                // 链接
                if (WxConsts.MenuButtonType.VIEW.equals(subMenuType)) {
                    subButton.setUrl(menu.getMenuurl());
                }
                log.info("subButton: {}", JSONUtils.beanToJson(subButton));
                return subButton;
            }).collect(Collectors.toList());


            button.setSubButtons(subButtonList);
            log.info("button: {}", JSONUtils.beanToJson(button));
            return button;
        }).collect(Collectors.toList());


        try {
            WxMenu menu = new WxMenu();
            menu.setButtons(buttons);
            wxService.init();
            String json = JSONUtils.beanToJson(menu);
            log.info("菜单：{}", json);
            String s = wxService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new IFastApiException("菜单同步失败2!");
        }

        return Result.ok();
    }

    private String convertMenuType(String menutype) {
        switch (menutype) {
            // 主菜单
            case Const.MenuKey.MAIN:
                return null;
            // 链接
            case Const.MenuKey.LINK:
                return WxConsts.MenuButtonType.VIEW;
            // 文本
            case Const.MenuKey.TEXT:
                return WxConsts.MenuButtonType.CLICK;
            // 关键字
            case Const.MenuKey.KEY_WORD:
                return WxConsts.MenuButtonType.CLICK;
            // 扫码
            case Const.MenuKey.SCAN:
                return WxConsts.MenuButtonType.SCANCODE_PUSH;
            // 发图
            case Const.MenuKey.PIC:
                return WxConsts.MenuButtonType.CLICK;
            // 发位置
            case Const.MenuKey.LOCATION:
                return WxConsts.MenuButtonType.LOCATION_SELECT;
            default:
                return WxConsts.MenuButtonType.CLICK;
        }
    }

}
