package com.ifast.job.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ifast.common.annotation.Log;
import com.ifast.common.base.AdminBaseController;
import com.ifast.common.utils.Result;
import com.ifast.job.domain.TaskDO;
import com.ifast.job.service.JobService;
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
public class JobController extends AdminBaseController {
    @Autowired
    private JobService taskScheduleJobService;
    
    @Log("进入定时任务管理页面")
    @GetMapping()
    String taskScheduleJob() {
        return "common/job/job";
    }
    
    @Log("查询定时任务列表")
    @ResponseBody
    @GetMapping("/list")
    public Result<Page<TaskDO>> list(TaskDO taskDTO) {
        // 查询列表数据
        Wrapper<TaskDO> wrapper = new EntityWrapper<TaskDO>(taskDTO);
        Page<TaskDO> page = taskScheduleJobService.selectPage(getPage(TaskDO.class), wrapper);
        return Result.ok(page);
    }
    
    @Log("进入定时任务添加页面")
    @GetMapping("/add")
    String add() {
        return "common/job/add";
    }
    
    @Log("进入定时任务编辑页面")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        TaskDO job = taskScheduleJobService.selectById(id);
        model.addAttribute("job", job);
        return "common/job/edit";
    }

    /**
     * 信息
     */
    @Log("根据id查询定时任务信息")
    @RequestMapping("/info/{id}")
    public Result<TaskDO> info(@PathVariable("id") Long id) {
        TaskDO taskScheduleJob = taskScheduleJobService.selectById(id);
        return Result.ok(taskScheduleJob);
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
    
    @Log("根据id和cmd执行/停止定时任务")
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

}
