package com.ifast.common.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.AdminBaseController;
import com.ifast.common.domain.LogDO;
import com.ifast.common.service.LogService;
import com.ifast.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 * 日志
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/common/log")
@Controller
public class LogController extends AdminBaseController {
    @Autowired
    LogService logService;
    String prefix = "common/log";
    
    @Log("进入系统日志列表页面")
    @GetMapping()
    String log() {
        return prefix + "/log";
    }
    
    @Log("查询系统日志列表")
    @ResponseBody
    @GetMapping("/list")
    public Result<Page<LogDO>> list(LogDO logDTO) {
        // 查询列表数据
        Page<LogDO> page = logService.selectPage(getPage(LogDO.class), logService.convertToEntityWrapper("username", logDTO.getUsername(), "operation", logDTO.getOperation()));
        return Result.ok(page);
    }
    
    @Log("删除系统日志")
    @ResponseBody
    @PostMapping("/remove")
    Result<String> remove(Long id) {
        logService.deleteById(id);
        return Result.ok();
    }
    
    @Log("批量删除系统日志")
    @ResponseBody
    @PostMapping("/batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        logService.deleteBatchIds(Arrays.asList(ids));
        return Result.fail();
    }
}
