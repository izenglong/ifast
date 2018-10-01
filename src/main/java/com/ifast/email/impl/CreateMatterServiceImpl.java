package com.ifast.email.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.ifast.common.utils.CaptchaUtils.Type;
import com.ifast.email.IMailsendService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * TODO:模板发送带html格式的邮件
 * 
 * @author zyl
 * @since:2017年9月6日
 * @author zyl 2017年9月6日
 * @version
 */
@Service(value = "createMatterServiceImpl")
public class CreateMatterServiceImpl implements IMailsendService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Configuration configuration;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: sendMessage
	 * </p>
	 * <p>
	 * Description(描述):使用后模板发送邮件
	 * </p>
	 * 
	 * @see org.service.IMailsendService#sendMessage()
	 */

	@Override
	public void sendMessage(String form, String to,String captcha,Type type) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(form);
			helper.setTo(to);
			String  subiect=getSubject(type);
			helper.setSubject("请输入验证码  "+captcha+subiect);
			helper.setText(getText(to,captcha,type), true);
			// 从模板里面读取
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO 异常执行块！
			e.printStackTrace();
		}
	}

	/**
	 *TODO:根据验证码类型决定不同的主题
	 *@author ZYL 2017年9月27日
	 *@param type
	 *@return
	 *@version 1.1
	 */
	private String getSubject(Type type) {
		String result = null;
		switch (type) {
		case Register:
			result = " 完成注册";
			break;
		case BinDingPhone:
			result = " 完成绑定手机";
			break;
		case BinDingEmail:
			result = " 完成绑定邮箱";
			break;
		case ForgetPswd:
			result = " 完成忘记密码";
			break;
		case Paypwd:
			result = " 完成设置支付密码";
			break;
		case AlterMobile:
			result = " 完成修改手机";
			break;
		case AlterEmail:
			result = " 完成修改邮箱";
			break;
		default:
			throw new IllegalArgumentException("The Type isn't supported!Please see Captcha.Type!");
		}
		return result;
	}

	/**
	 *TODO:根据验证码类型决定不同的模板
	 *@author ZYL 2017年9月27日
	 *@param type
	 *@return
	 *@version 1.1
	 */
	private String getSMSContent(Type type) {
		String result = null;
		switch (type) {
		case Register:
			result = "/mail.ftl";
			break;
		case BinDingPhone:
			result = "/mailBinDingPhone.ftl";
			break;
		case BinDingEmail:
			result = "/mailBinDingEmail.ftl";
			break;
		case ForgetPswd:
			result = "/mailForgetPswd.ftl";
			break;
		case AlterMobile:
			result = "/mailAlterMobile.ftl";
			break;
		case AlterEmail:
			result = "/mailAlterEmail.ftl";
			break;
		default:
			throw new IllegalArgumentException("The Type isn't supported!Please see Captcha.Type!");
		}
		return result;
	}

	// 读取模板
	private String getText(String username, String captcha,Type type) {
		String txt = "";
		try {
			String file=getSMSContent(type);
			Template template = configuration.getTemplate(file);
			// 通过map传递动态数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", username);
			map.put("captcha", captcha);
			// 解析模板文件
			txt = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		} catch (IOException e) {
			// TODO 异常执行块！
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO 异常执行块！
			e.printStackTrace();
		}

		return txt;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}