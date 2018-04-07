package com.ifast.job.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.ifast.common.utils.DateUtils;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月22日 | Aron</small>
 */
@Component
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("测试任务执 | " + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_19)
                + " | 定时统计人数：\" + userService.selectCount(null)");
    }

}
