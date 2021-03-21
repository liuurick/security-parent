package com.liuurick.security.properties;

import lombok.Data;

/**
 * @author liubin
 */
@Data
public class AuthenticationProperties {

    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};


    /**
     * 登录成功后响应 JSON , 还是重定向
     * 如果application.yml 中没有配置，则取此初始值 REDIRECT
    */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    public LoginResponseType getLoginType() {
        return loginType;
    }
    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }
}
