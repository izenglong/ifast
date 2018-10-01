package com.ifast.email.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ifast.email.IMailsendService;
import com.ifast.common.utils.CaptchaUtils.Type;


/**
 * TODO:发送带附件的邮件
 * 
 * @author zyl
 * @since:2017年9月7日
 * @author zyl 2017年9月7日
 * @version
 */
@Service("attMailsendServiceImpl")
public class AttMailsendServiceImpl implements IMailsendService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: sendMessage
	 * </p>
	 * <p>
	 * Description(描述):发送带附件的邮件
	 * </p>
	 * 
	 * @see org.service.IMailsendService#sendMessage()
	 */

	@Override
	public void sendMessage(String form, String to,String content,Type type) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(form);
			helper.setTo(to);
			helper.setSubject("带附件的邮件");
			// 普通格式的
			// helper.setText("发送一个附件内容！<a
			// href='http://www.baidu.com'>百度搜索</a>");
			// html格式的
			helper.setText("发送一个附件内容！<a href='http://www.baidu.com'>百度搜索</a>", true);
			// 添加附件1
			ClassPathResource file1 = new ClassPathResource("/freemarker/doc.txt");
			helper.addAttachment(file1.getFilename(), file1.getFile());
			// 添加附件2
			ClassPathResource file2 = new ClassPathResource("/freemarker/text.txt");
			helper.addAttachment(file2.getFilename(), file2.getFile());
			javaMailSender.send(message);

		} catch (Exception e) {
			// TODO 异常执行块！
			e.printStackTrace();
		}

	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

}
