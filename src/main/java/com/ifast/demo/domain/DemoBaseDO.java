package com.ifast.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ifast.common.domain.BaseDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@SuppressWarnings("serial")
@TableName("app_demo_base")
@EqualsAndHashCode(callSuper=true)
public class DemoBaseDO extends BaseDO {
    /** 标题 */
    private String title;
    /** 发布时间 */
    private Date publish;
    /** 正文 */
    private String content;
}
