package com.ifast;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
    
    /** 
     * <pre>
     * </pre>
     * <small> 2018年3月22日 | Aron</small>
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
        System.out.println("==================> run at http://localhost:" + serverProperties.getPort() + serverProperties.getContextPath() + "  <==================");
    }

}