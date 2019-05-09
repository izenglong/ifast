package com.ifast.wxmp.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.base.BaseController;
import com.ifast.common.utils.Result;
import com.ifast.wxmp.domain.MpConfigDO;
import com.ifast.wxmp.domain.MpFansDO;
import com.ifast.wxmp.service.MpConfigService;
import com.ifast.wxmp.service.MpFansService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 * 微信粉丝表
 * </pre>
 * <small> 2018-04-11 23:27:06 | Aron</small>
 */
@Controller
@RequestMapping("/wxmp/mpFans")
public class MpFansController extends BaseController {
    @Autowired
    private MpFansService mpFansService;
    @Autowired
    private MpConfigService mpConfigService;

    @GetMapping()
    @RequiresPermissions("wxmp:mpFans:mpFans")
    String MpFans() {
        return "wxmp/mpFans/mpFans";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("wxmp:mpFans:mpFans")
    public Result<Page<MpFansDO>> list(String searchValue, String appId) {
        Wrapper<MpFansDO> wrapper = new EntityWrapper<>();
        wrapper.eq("mpId", mpConfigService.selectOne(MpConfigDO.builder().appId(appId).build()).getId());
        if (StringUtils.isNotBlank(searchValue)) {
            wrapper.andNew().like("nickname", searchValue)
                    .or().like("openid", searchValue)
                    .or().like("subscribeKey", searchValue);
        }
        Page<MpFansDO> page = mpFansService.selectPage(getPage(MpFansDO.class), wrapper);
        return Result.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("wxmp:mpFans:add")
    String add() {
        return "wxmp/mpFans/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("wxmp:mpFans:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        MpFansDO mpFans = mpFansService.selectById(id);
        model.addAttribute("mpFans", mpFans);
        return "wxmp/mpFans/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("wxmp:mpFans:add")
    public Result<String> save(MpFansDO mpFans) {
        mpFansService.insert(mpFans);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("wxmp:mpFans:edit")
    public Result<String> update(MpFansDO mpFans) {
        mpFansService.updateById(mpFans);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpFans:remove")
    public Result<String> remove(Long id) {
        mpFansService.deleteById(id);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("wxmp:mpFans:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        mpFansService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 同步粉丝，强制更新用户数据
     */
    @PostMapping("/sync")
    @ResponseBody
    @RequiresPermissions("wxmp:mpFans:sync")
    public Result<String> sync(@RequestParam("ids[]") Long[] ids) {
        mpFansService.sync(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 同步粉丝到服务器，存在即不更新
     */
    @PostMapping("/sync/wxmp")
    @ResponseBody
    @RequiresPermissions("wxmp:mpFans:sync")
    public Result<String> syncWxMp(String appId) {
        mpFansService.syncWxMp(appId);
        return Result.ok();
    }


}
