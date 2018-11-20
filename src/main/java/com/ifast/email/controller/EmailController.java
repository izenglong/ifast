package com.ifast.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifast.email.IMailsendService;
import com.ifast.common.utils.CaptchaUtils.Type;


/**
 * 邮件测试类
 * @author admin
 *
 */
@RequestMapping("/emil")
@Controller
public class EmailController {

	
	/**使用模板发送邮件*/
	@Autowired
	@Qualifier(value = "createMatterServiceImpl")
	private IMailsendService   createMatterService;
	
	/**发送带附件的邮件*/
	@Autowired
	@Qualifier(value = "attMailsendServiceImpl")
	private IMailsendService   attMailsendService;
	
	/**发送简单邮件*/
	@Autowired
	@Qualifier(value = "simpleMailsendServiceImpl")
	private IMailsendService   simpleMailsendService;
	
	/**
	 * 发送简单邮件
	 */
	@RequestMapping("/simpleMailsend")
	public void simpleMailsend() {
		simpleMailsendService.sendMessage("18291548738@163.com", "1045877180@qq.com", "发送简单邮件！", null);
	    System.out.println("发送成功");
	}
	/**
	 * 发送带附件的邮件
	 */
	@RequestMapping("/attMailsend")
	public void attMailsend() {
		attMailsendService.sendMessage("18291548738@163.com", "1045877180@qq.com", null, null);
		System.out.println("发送成功");
	}
	
	/**
	 * 使用模板发送邮件
	 */
	@RequestMapping("/createMatterMailsend")
	public void createMatterMailsend() {
		createMatterService.sendMessage("18291548738@163.com", "1045877180@qq.com", "123456", Type.Register);
		System.out.println("发送成功");
	}
	
	
}
