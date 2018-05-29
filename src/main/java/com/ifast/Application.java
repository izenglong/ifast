package com.ifast;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ifast.common.config.IFastConfig;
import com.ifast.common.utils.SpringContextHolder;

/**
 * <pre>
 * ifast 入口
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.ifast.*.dao")
@SpringBootApplication
public class Application {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	/**
	 * <pre>
	 * </pre>
	 * <small> 2018年3月22日 | Aron</small>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		printProjectConfigs();
	}
	
	private static void printProjectConfigs() {
		ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
		DataSourceProperties dataSourceProperties = SpringContextHolder.getApplicationContext().getBean(DataSourceProperties.class);
		IFastConfig config = SpringContextHolder.getApplicationContext().getBean(IFastConfig.class);
		log.info("开启演示模式：" + config.isDemoMode());
		log.info("开启调试模式：" + config.isDevMode());
		log.info("数据库：" + dataSourceProperties.getUrl());
		log.info("==================> run at http://localhost:" + serverProperties.getPort() + serverProperties.getContextPath() + "  <==================");
	}
	
}