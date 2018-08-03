package com.ifast.demo.domain;


import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ifast.common.domain.BaseDO;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 * <pre>
 * 基础表
 * </pre>
 * <small> 2018-08-03 11:56:37 | Aron</small>
 */
@Data
@SuppressWarnings("serial")
@TableName("app_demo_base")
@EqualsAndHashCode(callSuper=true) 
public class DemoBaseDO extends BaseDO {
	/** 默认ID_WORKER，mybatis-plus.global-config.id-type:2，应用可以自定义 */
	@TableId
	private Long id;
	
    /** 标题 */
    private String title;

    /** 发布时间 */
    private Date publish;

    /** 正文 */
    private String content;

    /** 价格 */
    private BigDecimal price;

}
