package com.liuurick.security.config;

import com.liuurick.security.authentication.mobile.SmsSend;
import com.liuurick.security.authentication.mobile.impl.SmsCodeSender;
import com.liuurick.security.authentication.session.CustomInvalidSessionStrategy;
import com.liuurick.security.authentication.session.CustomSessionInformationExpiredStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author liubin
 */
@Configuration
public class SecurityConfigBean {

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }

    /**
     * 注入Session失效策略实例
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy();
    }

    /**
     * @ConditionalOnMissingBean(SmsSend.class)
     * 默认情况下，采用的是SmsCodeSender实例 ，
     * 但是如果容器当中有其他的SmsSend类型的实例，
     * 那当前的这个SmsCodeSender就失效 了
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }
}

