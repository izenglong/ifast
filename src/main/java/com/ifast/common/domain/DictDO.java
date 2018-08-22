package com.ifast.common.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_dict")
@Data
public class DictDO extends Model<DictDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public final static DictDO dao = new DictDO();
    
    // 编号
    @TableId
    private Long id;
    // 标签名
    private String name;
    // 数据值
    private String value;
    // 类型
    private String type;
    // 描述
    private String description;
    // 排序（升序）
    private BigDecimal sort;
    // 父级编号
    private Long parentId;
    // 创建者
    private Integer createBy;
    // 创建时间
    private Date createDate;
    // 更新者
    private Long updateBy;
    // 更新时间
    private Date updateDate;
    // 备注信息
    private String remarks;
    // 删除标记
    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
