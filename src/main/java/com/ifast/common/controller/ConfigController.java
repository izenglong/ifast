package com.ifast.common.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.BaseController;
import com.ifast.common.domain.ConfigDO;
import com.ifast.common.service.ConfigService;
import com.ifast.common.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 
 * 
 * @author Aron
 * @email izenglong@163.com
 * @date 2018-04-06 01:05:22
 */

@Controller
@RequestMapping("/common/config")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigService configService;
    
    @GetMapping()
    @RequiresPermissions("common:config:config")
    String Config() {
        return "common/config/config";
    }
    
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:config:config")
    public Result<Page<ConfigDO>> list(ConfigDO configDTO) {
        Page<ConfigDO> page = configService.selectPage(getPage(ConfigDO.class), configService.convertToEntityWrapper("k", configDTO.getK()));
        return Result.ok(page);
    }
    
    @GetMapping("/add")
    @RequiresPermissions("common:config:add")
    String add() {
        return "common/config/add";
    }
    
    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:config:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        ConfigDO config = configService.selectById(id);
        model.addAttribute("config", config);
        return "common/config/edit";
    }

    /**
     * 保存
     */
    @Log("添加系统配置")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:config:add")
    public Result<String> save(ConfigDO config) {
        if (configService.insert(config)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 修改
     */
    @Log("更新系统配置")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:config:edit")
    public Result<String> update(ConfigDO config) {
        configService.updateById(config);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除系统配置")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:config:remove")
    public Result<String> remove(Long id) {
        configService.deleteById(id);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("批量删除系统配置")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:config:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        configService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

}
