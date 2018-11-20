package com.ifast.common.utils;

/**
 * 
 * TODO:验证码相关常量
 * 
 * @author ZYL
 * @since:2017年9月12日
 * @author ZYL 2017年9月12日
 * @version
 */
public class CaptchaUtils {

	/**
	 * TODO:验证码类型
	 * 
	 * @author EDCwifi-170428
	 * @since:2017年9月12日
	 * @author EDCwifi-170428 2017年9月12日
	 * @version
	 */
	public static enum Type {
		/** 注册 */
		Register,
		/** 绑定手机 */
		BinDingPhone,
		/** 绑定邮箱 */
		BinDingEmail,
		/** 忘记密码 */
		ForgetPswd,
		/** 修改手机号码 */
		AlterMobile,
		/** 设置支付密码发送验证码 */
		Paypwd,
		/** 修改邮箱 */
		AlterEmail;
	}

}
