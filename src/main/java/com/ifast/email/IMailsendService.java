package com.ifast.email;

import com.ifast.common.utils.CaptchaUtils.Type;

public interface IMailsendService {
	/**
	 * 
	 * @Title: sendMessage @Description: 该方法的主要作用：发送邮件 @param 设定文件 @return
	 * 返回类型：void @throws
	 */
	void sendMessage(String form,String to,String content,Type type);
	
}
