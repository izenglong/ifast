package com.ifast.api.pojo.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月27日 | Aron</small>
 */
@TableName("app_user")
@Data
public class AppUserDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 6442209044151926545L;
    @TableId
    private Long id;
    private String realName;
    private String mobile;
    private Date createTime;
    private Date lastLoginTime;
    private String uname;
    private String passwd;
    private String openid;
}
