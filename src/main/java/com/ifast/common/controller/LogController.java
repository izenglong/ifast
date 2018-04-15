package com.ifast.common.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.base.BaseController;
import com.ifast.common.domain.LogDO;
import com.ifast.common.service.LogService;
import com.ifast.common.utils.Result;

/**
 * <pre>
 * 日志
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/common/log")
@Controller
public class LogController extends BaseController {
    @Autowired
    LogService logService;
    String prefix = "common/log";

    @GetMapping()
    String log() {
        return prefix + "/log";
    }

    @ResponseBody
    @GetMapping("/list")
    public Result<Page<LogDO>> list(LogDO logDTO) {
        // 查询列表数据
        Page<LogDO> page = getPage(LogDO.class);

        Wrapper<LogDO> wrapper = new EntityWrapper<LogDO>(logDTO);
        page = logService.selectPage(page, wrapper);
        return Result.ok(page);
    }

    @ResponseBody
    @PostMapping("/remove")
    Result<String> remove(Long id) {
        logService.deleteById(id);
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        logService.deleteBatchIds(Arrays.asList(ids));
        return Result.fail();
    }
}
