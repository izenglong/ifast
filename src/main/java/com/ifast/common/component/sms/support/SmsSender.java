package com.ifast.common.component.sms.support;

import com.ifast.api.exception.IFastApiException;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/30 19:10 | Aron</small>
 */
public interface SmsSender {

    /**
     * <pre>
     * 根据产品id、手机号码发送短信,发送失败将抛出异常
     * </pre>
     * <small> 2018/8/30 19:13 | Aron</small>
     * @param mobile 目标手机号码,如果是多个手机号，请英文逗号","分隔
     * @param code 验证码，如果不需要则传null
     * @param scene 发送的短信内容场景值
     */
    void send(String mobile, String code, String scene) throws IFastApiException;

}
