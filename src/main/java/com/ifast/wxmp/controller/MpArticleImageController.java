package com.ifast.wxmp.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.BaseController;
import com.ifast.common.utils.Result;
import com.ifast.wxmp.domain.MpArticleDO;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.service.MpArticleService;
import com.ifast.wxmp.service.MpConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2018-10-18 21:21:36 | Aron</small>
 */
@Controller
@RequestMapping("/wxmp/mpArticle/image")
public class MpArticleImageController extends BaseController {
	@Autowired
	private MpArticleService mpArticleService;
	@Autowired
	private MpConfigService mpConfigService;
	
	@GetMapping()
	@RequiresPermissions("wxmp:mpArticleImage:mpArticle")
	String MpArticle(){
	    return "wxmp/mpArticleImage/mpArticle";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("wxmp:mpArticleImage:mpArticle")
	public Result<Page<MpArticleDO>> list(MpArticleDO mpArticleDTO, String appId){
        Wrapper<MpArticleDO> wrapper = new EntityWrapper<>(mpArticleDTO).eq("mpId", mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId()).orderBy("id", false);
        Page<MpArticleDO> page = mpArticleService.selectPage(getPage(MpArticleDO.class), wrapper);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("wxmp:mpArticleImage:add")
	String add(){
	    return "wxmp/mpArticleImage/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("wxmp:mpArticleImage:edit")
	String edit(@PathVariable("id") Long id,Model model){
		MpArticleDO mpArticle = mpArticleService.selectById(id);
		model.addAttribute("mpArticle", mpArticle);
	    return "wxmp/mpArticleImage/edit";
	}
	
	@Log("添加")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("wxmp:mpArticleImage:add")
	public Result<String> save( MpArticleDO mpArticle, String appId){
		mpArticle.setMpId(mpConfigService.selectOne(new EntityWrapper<>(MpConfigDO.builder().appId(appId).build())).getId());
		mpArticleService.insert(mpArticle);
        return Result.ok();
	}
	
	@Log("修改")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("wxmp:mpArticleImage:edit")
	public Result<String>  update( MpArticleDO mpArticle){
		boolean update = mpArticleService.updateById(mpArticle);
		return update ? Result.ok() : Result.fail();
	}
	
	@Log("删除")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("wxmp:mpArticleImage:remove")
	public Result<String>  remove( Long id){
		mpArticleService.deleteById(id);
        return Result.ok();
	}
	
	@Log("批量删除")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("wxmp:mpArticleImage:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Long[] ids){
		mpArticleService.deleteBatchIds(Arrays.asList(ids));
		return Result.ok();
	}
	
}
