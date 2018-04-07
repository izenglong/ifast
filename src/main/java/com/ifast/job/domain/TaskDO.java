package com.ifast.job.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_task")
public class TaskDO extends Model<TaskDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    //
    @TableId
    private Long id;
    // cron表达式
    private String cronExpression;
    // 任务调用的方法名
    private String methodName;
    // 任务是否有状态
    private String isConcurrent;
    // 任务描述
    private String description;
    // 更新者
    private String updateBy;
    // 任务执行时调用哪个类的方法 包名+类名
    private String beanClass;
    // 创建时间
    private Date createDate;
    // 任务状态
    private String jobStatus;
    // 任务分组
    private String jobGroup;
    // 更新时间
    private Date updateDate;
    // 创建者
    private String createBy;
    // Spring bean
    private String springBean;
    // 任务名
    private String jobName;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取：cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置：任务调用的方法名
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * 获取：任务调用的方法名
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * 设置：任务是否有状态
     */
    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    /**
     * 获取：任务是否有状态
     */
    public String getIsConcurrent() {
        return isConcurrent;
    }

    /**
     * 设置：任务描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取：任务描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置：更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取：更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置：任务执行时调用哪个类的方法 包名+类名
     */
    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取：任务执行时调用哪个类的方法 包名+类名
     */
    public String getBeanClass() {
        return beanClass;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置：任务状态
     */
    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 获取：任务状态
     */
    public String getJobStatus() {
        return jobStatus;
    }

    /**
     * 设置：任务分组
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * 获取：任务分组
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * 设置：更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取：更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置：创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置：Spring bean
     */
    public void setSpringBean(String springBean) {
        this.springBean = springBean;
    }

    /**
     * 获取：Spring bean
     */
    public String getSpringBean() {
        return springBean;
    }

    /**
     * 设置：任务名
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取：任务名
     */
    public String getJobName() {
        return jobName;
    }

    @Override
    public String toString() {
        return "TaskDO{" + "id=" + id + ", cronExpression='" + cronExpression + '\'' + ", methodName='" + methodName
                + '\'' + ", isConcurrent='" + isConcurrent + '\'' + ", description='" + description + '\''
                + ", updateBy='" + updateBy + '\'' + ", beanClass='" + beanClass + '\'' + ", createDate=" + createDate
                + ", jobStatus='" + jobStatus + '\'' + ", jobGroup='" + jobGroup + '\'' + ", updateDate=" + updateDate
                + ", createBy='" + createBy + '\'' + ", springBean='" + springBean + '\'' + ", jobName='" + jobName
                + '\'' + '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
