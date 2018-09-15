package com.ifast.demo.controller;

import com.ifast.common.annotation.Log;
import com.ifast.common.utils.Result;
import com.ifast.common.component.sms.dto.SendDTO;
import com.ifast.common.component.sms.dto.VerifyDTO;
import com.ifast.common.component.sms.support.SmsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 短信测试
 * </pre>
 * <small> 2018/8/30 19:17 | Aron</small>
 */
@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired private SmsManager smsSerevice;


    @PostMapping("/send")
    @Log("发送验证码")
    public Result send(@RequestBody SendDTO dto) {
        smsSerevice.send(dto.getMobile(), dto.getScene());
        return Result.build(200, "发送成功");
    }

    @PostMapping("/verify")
    @Log("校验验证码")
    public Result verify(@RequestBody VerifyDTO dto) {
        smsSerevice.verify(dto.getMobile(), dto.getCode());
        return Result.ok();
    }
}
