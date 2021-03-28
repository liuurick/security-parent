package com.liuurick.security.authentication.mobile.impl;

import com.liuurick.security.authentication.mobile.SmsSend;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liubin
 */
@Slf4j
public class SmsCodeSender implements SmsSend {
    @Override
    public boolean sendSms(String mobile, String content) {
        String sendcontent = String.format("尊敬的客户，您的验证码为%s，请勿泄露他人。", content);
        log.info("验证码"+content);
        return true;
    }
}
