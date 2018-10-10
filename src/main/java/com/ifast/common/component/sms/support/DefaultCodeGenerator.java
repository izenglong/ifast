package com.ifast.common.component.sms.support;

import java.util.Random;

/**
 * <pre>
 * 短信验证码生成器
 * </pre>
 *
 * <small> 2018/10/10 15:03 | Aron</small>
 */
public class DefaultCodeGenerator implements  CodeGenerator{

    @Override
    public String getCode() {
        return new Random().nextInt(8999) + 1000 + "";
    }

}
