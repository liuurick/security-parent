package com.liuurick.security.authentication.mobile;

/**
 * @author liubin
 */
public interface SmsSend {
    /**
     * 发送短信
     * @param mobile
     * @param content
     * @return
     */
    boolean sendSms(String mobile, String content);
}
