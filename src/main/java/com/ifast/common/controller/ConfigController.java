package com.ifast.common.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.domain.ConfigDO;
import com.ifast.common.service.ConfigService;
import com.ifast.common.utils.Result;

/**
 * 
 * 
 * @author Aron
 * @email izenglong@163.com
 * @date 2018-04-06 01:05:22
 */

@Controller
@RequestMapping("/common/config")
public class ConfigController {
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
    public Result<Page<ConfigDO>> list(Integer pageNumber, Integer pageSize, ConfigDO configDTO) {
        // 查询列表数据
        Page<ConfigDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<ConfigDO> wrapper = new EntityWrapper<ConfigDO>(configDTO);
        page = configService.selectPage(page, wrapper);
        int total = configService.selectCount(wrapper);
        page.setTotal(total);
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
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:config:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        configService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

}
