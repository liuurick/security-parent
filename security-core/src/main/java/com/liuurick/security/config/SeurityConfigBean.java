package com.liuurick.security.config;

import com.liuurick.security.authentication.mobile.SmsSend;
import com.liuurick.security.authentication.mobile.impl.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author liubin
 */
@Configuration
public class SeurityConfigBean {

    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend getSmsSend(){
        return new SmsCodeSender();
    }

}
