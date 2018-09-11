package com.ifast.sys.controller;

import com.ifast.common.annotation.Log;
import com.ifast.common.base.AdminBaseController;
import com.ifast.common.domain.Tree;
import com.ifast.common.type.EnumErrorCode;
import com.ifast.common.utils.MD5Utils;
import com.ifast.common.utils.Result;
import com.ifast.oss.domain.FileDO;
import com.ifast.oss.service.FileService;
import com.ifast.sys.domain.MenuDO;
import com.ifast.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
public class LoginController extends AdminBaseController {

    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;

    @GetMapping({ "/", "" })
    String welcome(Model model) {
        return "redirect:/login";
    }

    @GetMapping({ "/index" })
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("username", getUser().getUsername());
        FileDO fileDO = fileService.selectById(getUser().getPicId());
        model.addAttribute("picUrl", fileDO == null ? "/img/photo_s.jpg" : fileDO.getUrl());
        return "index_v1";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    Result<String> ajaxLogin(String username, String password) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);//记住我是可选项，但只有会话缓存到redis等持久存储才能真正记住
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());
        }
    }
    
    @GetMapping("/main")
    String main() {
        return "main";
    }

    @GetMapping("/403")
    String error403() {
        return "403";
    }

}
