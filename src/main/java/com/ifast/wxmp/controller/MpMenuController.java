package com.ifast.wxmp.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.BaseController;
import com.ifast.common.domain.Tree;
import com.ifast.common.utils.Result;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.domain.MpMenuDO;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.wxmp.service.MpMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 微信菜单表
 * </pre>
 * <small> 2018-10-19 15:47:16 | Aron</small>
 */
@Controller
@RequestMapping("/wxmp/mpMenu")
public class MpMenuController extends BaseController {
    @Autowired
    private MpMenuService mpMenuService;
    @Autowired
    private MpConfigService mpConfigService;

    @GetMapping()
    @RequiresPermissions("wxmp:mpMenu:mpMenu")
    String MpMenu() {
        return "wxmp/mpMenu/mpMenu";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("wxmp:mpMenu:mpMenu")
    public List<MpMenuDO> list(MpMenuDO mpMenuDTO, String appId) {
        mpMenuDTO.setMpid(mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId());
        Wrapper<MpMenuDO> wrapper = new EntityWrapper<>(mpMenuDTO).orderBy("parentidx, sort");
        List<MpMenuDO> list = mpMenuService.selectList(wrapper);
        return list;
    }

    @GetMapping("/add")
    @RequiresPermissions("wxmp:mpMenu:add")
    String add() {
        return "wxmp/mpMenu/add";
    }


    @GetMapping("/add/{pId}")
    @RequiresPermissions("wxmp:mpMenu:add")
    String add(@PathVariable("pId") Long pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "菜单0");
        } else {
            model.addAttribute("pName", mpMenuService.selectById(pId).getMenuname());
        }

        MpMenuDO menuDO = mpMenuService.selectById(pId);
        if (!Objects.isNull(menuDO)) {
            model.addAttribute("parentName", menuDO.getMenuname());
        }
        return "wxmp/mpMenu/add";
    }


    @GetMapping("/edit/{id}")
    @RequiresPermissions("wxmp:mpMenu:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        MpMenuDO mpMenu = mpMenuService.selectById(id);
        model.addAttribute("mpMenu", mpMenu);
        MpMenuDO menuDO = mpMenuService.selectById(mpMenu.getParentidx());
        if (!Objects.isNull(menuDO)) {
            model.addAttribute("parentName", menuDO.getMenuname());
        }
        return "wxmp/mpMenu/edit";
    }

    @Log("添加微信菜单表")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("wxmp:mpMenu:add")
    public Result<String> save(MpMenuDO mpMenu, String appId) {
        mpMenuService.saveMenu(mpMenu, appId);
        return Result.ok();
    }

    @Log("修改微信菜单表")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("wxmp:mpMenu:edit")
    public Result<String> update(MpMenuDO mpMenu) {
        boolean update = mpMenuService.updateById(mpMenu);
        return update ? Result.ok() : Result.fail();
    }

    @Log("删除微信菜单表")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpMenu:remove")
    public Result<String> remove(Long id) {
        mpMenuService.deleteById(id);
        return Result.ok();
    }

    @Log("批量删除微信菜单表")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpMenu:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        mpMenuService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<MpMenuDO> tree() {
        Tree<MpMenuDO> tree = mpMenuService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return "wxmp/mpMenu/mpMenuTree";
    }

}
