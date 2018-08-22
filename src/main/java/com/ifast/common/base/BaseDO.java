package com.ifast.common.base;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@SuppressWarnings("serial")
public class BaseDO implements Serializable {
	/** 由mybatis-plus.global-config.sql-injector:com.baomidou.mybatisplus.mapper.LogicSqlInjector自动维护 */
	@TableLogic
	private boolean deleted;
	/** 由MyBatisConfig.optimisticLockerInterceptor自动维护 */
	@Version
	private int version;
	/** 由MySQL自动维护 */
	private Date createAt;
	private Date updateAt;
	/** 由LogAspect.logMapper自动维护 */
	private Long createBy;
	private Long updateBy;
}
