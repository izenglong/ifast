package com.ifast.sys.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_dept")
public class DeptDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    // 上级部门ID，一级部门为0
    private Long parentId;
    // 部门名称
    private String name;
    // 排序
    private Integer orderNum;
    // 是否删除 -1：已删除 0：正常
    private Integer delFlag;

    /**
     * 设置：上级部门ID，一级部门为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：上级部门ID，一级部门为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置：是否删除 -1：已删除 0：正常
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取：是否删除 -1：已删除 0：正常
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeptDO [id=" + id + ", parentId=" + parentId + ", name=" + name + ", orderNum=" + orderNum
                + ", delFlag=" + delFlag + "]";
    }

}
