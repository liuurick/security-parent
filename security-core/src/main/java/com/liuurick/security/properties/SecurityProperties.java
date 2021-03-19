package com.liuurick.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author liubin
 */
@Component
@ConfigurationProperties(prefix = "liubin.security")
public class SecurityProperties {

    /**
     * 会将 security.authentication 下面的值绑定到AuthenticationProperties对象上
      */
    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
