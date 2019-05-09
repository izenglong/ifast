package com.ifast.job.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.BaseController;
import com.ifast.common.utils.Result;
import com.ifast.job.domain.TaskDO;
import com.ifast.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 * 定时任务
 * </pre>
 *
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/job")
@Slf4j
public class JobController extends BaseController {
    @Autowired
    private JobService taskScheduleJobService;

    @GetMapping()
    String taskScheduleJob() {
        return "common/job/job";
    }

    @ResponseBody
    @GetMapping("/list")
    public Result<Page<TaskDO>> list(TaskDO taskDTO) {
        Wrapper<TaskDO> wrapper = new EntityWrapper<>(taskDTO);
        Page<TaskDO> page = taskScheduleJobService.selectPage(getPage(TaskDO.class), wrapper);
        return Result.ok(page);
    }

    @GetMapping("/add")
    String add() {
        return "common/job/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        TaskDO job = taskScheduleJobService.selectById(id);
        model.addAttribute("job", job);
        return "common/job/edit";
    }

    /**
     * 保存
     */
    @Log("添加定时任务")
    @ResponseBody
    @PostMapping("/save")
    public Result<String> save(TaskDO taskScheduleJob) {
        taskScheduleJobService.insert(taskScheduleJob);
        return Result.ok();
    }

    /**
     * 修改
     */
    @Log("更新定时任务")
    @ResponseBody
    @PostMapping("/update")
    public Result<String> update(TaskDO taskScheduleJob) {
        taskScheduleJobService.updateById(taskScheduleJob);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除定时任务")
    @PostMapping("/remove")
    @ResponseBody
    public Result<String> remove(Long id) {
        taskScheduleJobService.deleteById(id);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("批量删除定时任务")
    @PostMapping("/batchRemove")
    @ResponseBody
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        taskScheduleJobService.deleteBatchIds(Arrays.asList(ids));

        return Result.ok();
    }

    @Log("启停定时任务")
    @PostMapping(value = "/changeJobStatus")
    @ResponseBody
    public Result<String> changeJobStatus(Long id, String cmd) {
        String label = "停止";
        if ("start".equals(cmd)) {
            label = "启动";
        } else {
            label = "停止";
        }
        try {
            taskScheduleJobService.changeStatus(id, cmd);
            return Result.ok("任务" + label + "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok("任务" + label + "失败");
    }

    @Log("立即执行一次任务")
    @PostMapping(value = "/runNowOnce")
    @ResponseBody
    public Result<String> runNowOnce(Long id) {
        try {
            taskScheduleJobService.runNowOnce(id);
            return Result.ok("任务执行成功");
        } catch (Exception e) {
           log.error("执行任务失败",e);
        }
        return Result.ok("任务执行失败");
    }

}
