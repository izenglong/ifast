package com.ifast.email.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Component
//@PropertySource("classpath:config/application-mail.properties")
//@ConfigurationProperties(prefix = "ifast.mail")
public class EmailConfig {
 
	/**邮件发送服务器的主机*/
    @Value("${spring.mail.host}")
	private String host;
    
    /**邮件发送服务器的端口*/
	@Value("${spring.mail.port}")
	private int port;
	
	/**账户*/
	@Value("${spring.mail.username}")
	private String account;
	
	/**密码*/
	@Value("${spring.mail.password}")
	private String password;
	
	/**邮件发送协议*/
	@Value("${spring.mail.protocol}")
	private String protocol;
	
	/**一般情况下，发送邮件都是要进行验证*/
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String isAuth;
	
	/**邮件发送超时时间*/
	@Value("${spring.mail.properties.mail.smtp.timeout}")
	private String outTime;
	
	/**默认编码模式*/
	@Value("${spring.mail.default-encoding}")
	private String encoding;
	
	/**模板地址*/
	@Value("${spring.mail.templateLoaderPath}") 
	private String templateLoaderPath;

	/**
	 *  邮件服务
	 * @return
	 */
	@Bean(name = "javaMailSender")
	public JavaMailSenderImpl getMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(host);
		javaMailSender.setPort(port);
		javaMailSender.setUsername(account);
		javaMailSender.setPassword(password);
		javaMailSender.setProtocol(protocol);
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", isAuth);
		properties.put("mail.smtp.timeout", outTime);
		
//		javaMailSender.setHost("smtp.163.com");
//		javaMailSender.setPort(25);
//		javaMailSender.setUsername("18291548738@163.com");
//		javaMailSender.setPassword("zyl163com");
//		javaMailSender.setProtocol("smtp");
//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", true);
//		properties.put("mail.smtp.timeout", 25000);
		
		javaMailSender.setJavaMailProperties(properties);
		return javaMailSender;
	}
	
	
	/**
	  * 配置FreeMarker
	  * @return
	  */
	@Bean(name = "configuration")
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean freeMarkerConfiguration = new FreeMarkerConfigurationFactoryBean();
		freeMarkerConfiguration.setTemplateLoaderPath(templateLoaderPath);//指定模板文件路径 
		freeMarkerConfiguration.setDefaultEncoding(encoding);//设置freekMarker编码格式
		
//		freeMarkerConfiguration.setTemplateLoaderPath("classpath:/freemarker");//指定模板文件路径 
//		freeMarkerConfiguration.setDefaultEncoding("UTF-8");//设置freekMarker编码格式
		return freeMarkerConfiguration;
	}
	
 
}
