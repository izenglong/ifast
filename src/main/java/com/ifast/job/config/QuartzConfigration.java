package com.ifast.job.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.ifast.job.quartz.JobFactory;

@Configuration
public class QuartzConfigration {

    @Autowired
    JobFactory jobFactory;
    @Autowired
    DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try {
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            Properties quartzProperties = quartzProperties();
            schedulerFactoryBean.setQuartzProperties(quartzProperties);
            //如果开启了cluster特性，又没有自定义dataSource，则使用ifast定义好的DataSource，需要先导入quartz.sql
            if("true".equalsIgnoreCase(quartzProperties.getProperty("org.quartz.jobStore.isClustered"))
            		&& StringUtils.isBlank(quartzProperties.getProperty("org.quartz.jobStore.dataSource"))) {
            	schedulerFactoryBean.setDataSource(dataSource);
            }
            schedulerFactoryBean.setJobFactory(jobFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedulerFactoryBean;
    }

    // 指定quartz.properties
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    // 创建schedule
    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
