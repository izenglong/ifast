package com.ifast.email.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ifast.email.IMailsendService;
import com.ifast.common.utils.CaptchaUtils.Type;

/**
 *TODO:发送简单邮件
 *@author zyl
 *@since:2017年9月7日
 *@author zyl 2017年9月7日
 *@version
 */
@Service(value="simpleMailsendServiceImpl")
public class SimpleMailsendServiceImpl implements IMailsendService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}



	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}



	@Override
	public void sendMessage(String form, String to,String content,Type type) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(form);// 发件地址
		msg.setTo(to);// 收件地址
		msg.setSubject("测试");// 标题
		msg.setText("第一个邮件！");// 内容
		try {
			javaMailSender.send(msg);// 发送邮件
			System.out.println("成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
