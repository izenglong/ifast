package com.ifast.common.component.sms.support;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/31 11:48 | Aron</small>
 */
public interface SmsVerify {

    /**
     * <pre>
     * 校验验证码
     * </pre>
     * <small> 2018/8/30 19:14 | Aron</small>
     * @param phone 手机号码
     * @param code 验证码
     */
    void verify(String phone, String code);
}
