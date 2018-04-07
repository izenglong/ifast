package com.ifast.common.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ifast.common.base.BaseController;
import com.ifast.common.domain.DictDO;
import com.ifast.common.service.DictService;
import com.ifast.common.utils.Result;

/**
 * <pre>
 * 数据字典
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/sysDict")
public class DictController extends BaseController {
    @Autowired
    private DictService sysDictService;

    @GetMapping()
    @RequiresPermissions("common:sysDict:sysDict")
    String sysDict() {
        return "common/sysDict/sysDict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public Result<Page<DictDO>> list(Integer pageNumber, Integer pageSize, DictDO dictDTO) {
        // 查询列表数据
        Page<DictDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<DictDO> wrapper = new EntityWrapper<DictDO>(dictDTO);
        page = sysDictService.selectPage(page, wrapper);
        int total = sysDictService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        DictDO sysDict = sysDictService.selectById(id);
        model.addAttribute("sysDict", sysDict);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public Result<String> save(DictDO sysDict) {
        sysDictService.insert(sysDict);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public Result<String> update(DictDO sysDict) {
        sysDictService.updateById(sysDict);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:remove")
    public Result<String> remove(Long id) {
        sysDictService.deleteById(id);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysDictService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    @GetMapping("/type")
    @ResponseBody
    public List<DictDO> listType() {
        return sysDictService.listType();
    };

    // 类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }

    @ResponseBody
    @GetMapping("/list/{type}")
    public List<DictDO> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        List<DictDO> dictList = sysDictService.selectByMap(map);
        return dictList;
    }
}
