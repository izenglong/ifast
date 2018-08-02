package com.ifast.demo.controller;


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
import com.ifast.common.base.AdminBaseController;
import com.ifast.demo.domain.DemoBaseDO;
import com.ifast.demo.service.DemoBaseService;
import com.ifast.common.utils.Result;
import com.ifast.common.utils.ShiroUtils;

/**
 * 
 * <pre>
 * 基础表
 * </pre>
 * <small> 2018-07-27 23:38:24 | Aron</small>
 */
@Controller
@RequestMapping("/demo/demoBase")
public class DemoBaseController extends AdminBaseController {
	@Autowired
	private DemoBaseService demoBaseService;
	
	@GetMapping()
	@RequiresPermissions("demo:demoBase:demoBase")
	String DemoBase(){
	    return "demo/demoBase/demoBase";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("demo:demoBase:demoBase")
	public Result<Page<DemoBaseDO>> list(DemoBaseDO demoBaseDTO){
        Wrapper<DemoBaseDO> wrapper = new EntityWrapper<DemoBaseDO>().orderBy("id", false);
        wrapper.like("title", demoBaseDTO.getTitle());
        wrapper.like("content", demoBaseDTO.getContent());
        Page<DemoBaseDO> page = demoBaseService.selectPage(getPage(DemoBaseDO.class), wrapper);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("demo:demoBase:add")
	String add(){
	    return "demo/demoBase/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("demo:demoBase:edit")
	String edit(@PathVariable("id") Long id,Model model){
		DemoBaseDO demoBase = demoBaseService.selectById(id);
		model.addAttribute("demoBase", demoBase);
	    return "demo/demoBase/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("demo:demoBase:add")
	public Result<String> save( DemoBaseDO demoBase){
		demoBase.setCreateBy(ShiroUtils.getUserId());
		boolean insert = demoBaseService.insert(demoBase);
        return insert ? Result.ok() : Result.fail();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("demo:demoBase:edit")
	public Result<String>  update( DemoBaseDO demoBase){
		demoBase.setUpdateBy(ShiroUtils.getUserId());
		boolean updateById = demoBaseService.updateById(demoBase);
		return updateById ? Result.ok() : Result.fail();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:remove")
	public Result<String>  remove( Long id){
		demoBaseService.deleteById(id);
        return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("demo:demoBase:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] ids){
		demoBaseService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}
	
}
